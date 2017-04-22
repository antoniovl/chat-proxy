/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.server;

import mx.icon.chatproxy.shared.OpenChatPkt;
import mx.icon.chatproxy.shared.OpenChatPktReply;
import mx.icon.chatproxy.shared.OpenChatResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Thread ran in server side and "talks" with the clients. Here we receive
 * the OpenChat command.
 *
 * @author antoniovl
 */
//public class ClientWorker implements Runnable {
public class ClientWorker extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ClientWorker.class);

    private Socket client;
    private Socket data;
    private Proxy proxy; // necesario para hacer Call Back

    public ClientWorker(Socket client, Proxy proxy) {
        this.client = client;
        this.proxy = proxy;
    }

    public Socket getClientSocket() {
        return this.client;
    }

    public Socket getDataSocket() {
        return this.data;
    }

    public void run() {

        boolean done = false;

        logger.info("Starting {}", Thread.currentThread().getName());

        try {
            client.setSoTimeout(1000);
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

            // Finds a data port
            int p = randomPort(50000, 60000);
            logger.info("Data Port on {}", p);
            oos.writeInt(p);
            oos.flush();

            // Wait for the client on the data port
            data = dataSocket(p);

            if (data == null) {
                logger.info("Error waiting connection on data port");
                proxy.unregister(this.client.getInetAddress().toString());
                client.close();
                return;
            } else {
                logger.info("Data Port connection open");
            }

            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());

            while (!done) {
                try {
                    Object o = ois.readObject();

                    if (o instanceof OpenChatPkt) {
                        // Remote client is trying to open a chat connection
                        OpenChatPkt ocp = (OpenChatPkt)o;

                        // sets up the chat and the data pipes
                        OpenChatResult result = proxy.openChat(ocp.getIp(), data);

                        OpenChatPktReply reply = new OpenChatPktReply();

                        if (result.isStatusOk()) {
                            reply.setSuccess(true);
                        } else {
                            // Could not set up the connection
                            reply.setSuccess(false);
                            reply.setMessage(result.getMessaje());
                        }

                        // Send the reply back
                        oos.writeObject(reply);
                        oos.flush();

                        // If the openChat was successful, since here
                        // both peers are connected.
                    }

                } catch (ClassNotFoundException cnfe) {
                    logger.error("Incorrect Command", cnfe);
                    continue;
                } catch (SocketTimeoutException ste) {

                }

                if (isInterrupted()) {
                    logger.info("Thread.interrupted detected.");
                    done = true;
                }
            }
        } catch (IOException ioe) {
            try {
                client.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Finds a random port number between the ranges specified.
     * @return
     */
    private int randomPort(int ini, int fin) {
        double inicio = (double)ini;
        double delta = (double)fin - (double)ini;
        Double d = Math.random() * delta;
        d = d + inicio;
        return d.intValue();
    }

    /**
     * Gets a data connection.
     * @param port
     * @return
     * @throws IOException
     */
    private Socket dataSocket(int port) throws IOException {

        ServerSocket skt = new ServerSocket(port);
        skt.setSoTimeout(30000);
        Socket s;

        try {
            s = skt.accept();
        } catch (SocketTimeoutException ste) {
            logger.info("dataSocket() timeout");
            s = null;
        }
        // Close the server socket
        skt.close();

        return s;
    }

}
