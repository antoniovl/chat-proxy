/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.server;

import mx.icon.chatproxy.shared.Config;
import mx.icon.chatproxy.shared.OpenChatResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Proxy Main Thread
 * @author antoniovl
 */
public class Proxy implements Runnable {

    public static final int SO_TIMEOUT = 1000;
    private final static Logger logger = LoggerFactory.getLogger(Proxy.class);

    private Map<String, ClientWorker> clients = new HashMap<>();

    private final Lock runningLock = new ReentrantLock(true);
    private boolean running = true;

    private final Lock clientsLock = new ReentrantLock(true);

    public boolean isRunning() {
        runningLock.lock();
        try {
            return running;
        } finally {
            runningLock.unlock();
        }
    }

    public void stop() {
        runningLock.lock();
        try {
            this.running = false;
        } finally {
            runningLock.unlock();
        }
    }

    public void run() {

        try {
            logger.info("Starting Proxy Server on {}", Config.PORT);

            ServerSocket skt = new ServerSocket(Config.PORT);
            skt.setSoTimeout(SO_TIMEOUT);

            boolean done = false;

            while (!done) {
                try {
                    // Wait for connections from ChatClients
                    Socket client = skt.accept();
                    String s = getIpAddrStr(client.getInetAddress());
                    logger.info("Connection from: {}", s);
                    
                    // Store the client reference
                    clientsLock.lock();
                    try {
                        if (clients.containsKey(s)) {
                            ClientWorker workerThread = clients.get(s);
                            workerThread.interrupt();
                        }

                        ClientWorker worker = new ClientWorker(client, this);
                        StringBuilder sb = new StringBuilder("ClientWorkerThread-");
                        sb.append(s).append(":").append(client.getPort());
                        worker.setDaemon(true);
                        worker.start();
                        clients.put(s, worker);

                    } finally {
                        clientsLock.unlock();
                    }

                } catch (SocketTimeoutException ste) {
                    if (!isRunning()) {
                        done = true;
                    }
                }
            }

        } catch (IOException ioe) {
            logger.error("SocketServer error", ioe);
            return;
        }
    }

    /**
     * Este método es el que efectivamente inicia la sesión de chat.
     * Básicamente determina si el destino existe en el Map de clientes,
     * y en caso afirmativo, conecta el InputStream de uno con el OutputStream
     * del otro y visceversa.
     *
     * @param destName
     * @param from
     * @return
     * @throws IOException
     */
    public OpenChatResult openChat(String destName, Socket from) throws IOException {

        OpenChatResult result = new OpenChatResult(true, null);

        // Determinamos si está conectado el destino
        ClientWorker cw = null;

        clientsLock.lock();
        try {
            cw = clients.get(destName);
        } finally {
            clientsLock.unlock();
        }
        if (cw == null) {
            // no such peer in the clients map
            result.setStatusOk(false);
            result.setMessaje("Destination IP not present");

            return result;
        }

        // Get the data socket
        Socket dest = cw.getDataSocket();

        // Pipes the input from source to destination...
        PipeData pipe1 = new PipeData(from.getInputStream(), dest.getOutputStream());
        Thread pipeThread1 = new Thread(pipe1);
        pipeThread1.start();

        // and from destination to source.
        PipeData pipe2 = new PipeData(dest.getInputStream(), from.getOutputStream());
        Thread pipeThread2 = new Thread(pipe2);
        pipeThread2.start();

        return result;
    }

    public Thread find(String destIp) {
        clientsLock.lock();
        try {
            return clients.get(destIp);
        } finally {
            clientsLock.unlock();
        }
    }

    public void unregister(String ip) {
        clientsLock.lock();
        try {
            clients.remove(ip);
        } finally {
            clientsLock.unlock();
        }
    }

    private String getIpAddrStr(InetAddress addr) {
        return addr.getHostAddress();
    }

}
