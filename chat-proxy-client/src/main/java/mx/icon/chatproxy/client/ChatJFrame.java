/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChatJFrame.java
 *
 * Created on Dec 5, 2009, 6:59:42 PM
 */

package mx.icon.chatproxy.client;

import mx.icon.chatproxy.shared.DataPkt;
import mx.icon.chatproxy.shared.OpenChatPkt;
import mx.icon.chatproxy.shared.OpenChatPktReply;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import mx.icon.chatproxy.shared.Config;

/**
 *
 * @author antoniovl
 */
public class ChatJFrame extends javax.swing.JFrame {

    /** Creates new form ChatJFrame */
    public ChatJFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        txtChat = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtIpAddr = new javax.swing.JTextField();
        btnConectar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(230, 220, 213));

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        btnEnviar.setText("Send");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(txtChat, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEnviar))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnviar)
                    .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnConectar.setText("Start Chat with IP Addr");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(txtIpAddr, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConectar)
                .addGap(78, 78, 78))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIpAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConectar))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action Handler invoked when send button it's pressed.
     * @param evt
     */
    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed

        StringBuilder sb = new StringBuilder();

        if (!isConnected()) {
            sb.append("No connection.");
            appendLine(sb.toString());
            return;
        }

        String s = this.txtChat.getText();

        if ((s != null) && (s.length() > 0)) {
            sb.append(s);
            sb.append("\n");

            try {
                byte[] b = sb.toString().getBytes("UTF-8");
                DataPkt pkt = new DataPkt(b);

                getDataObjectOutputStream().writeObject(pkt);
                getDataObjectOutputStream().flush();
                appendLine("ChatClient> {0}", sb.toString());
            } catch (IOException ioe) {
                appendLine("Ocurrió un Error: {0}", ioe.getMessage());
            }
        }
    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * ActionHandler invoked to connect.
     * @param evt
     */
    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed

        if (!isConnected()) {
            appendLine("There is no connection to the Proxy.");
            return;
        }

        String ip = this.txtIpAddr.getText();
        if ((ip == null) || (ip.length() == 0)) {
            appendLine("Destination IP address error.");
            return;
        }

        OpenChatPkt pkt = new OpenChatPkt(ip);

        try {
            ObjectOutputStream oos = getClientObjectOutputStream();
            // send request
            oos.writeObject(pkt);
            oos.flush();

            // wait for response
            ObjectInputStream ois = getClientObjectInputStream();
            Object o = ois.readObject();
            
            if (o instanceof OpenChatPktReply) {
                OpenChatPktReply reply = (OpenChatPktReply)o;
                if (reply.isSuccess()) {
                    appendLine("Connection success");
                } else {
                    appendLine("Connection error: {0}",
                            (reply.getMessage() == null) ? "" : reply.getMessage());
                }
            }

        } catch (IOException ioe) {
            appendLine("Error in writeObject(): {0}", ioe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            appendLine("ClassNotFoundException: {0}", cnfe.getMessage());
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    // <editor-fold defaultstate="collapsed" desc="dataObjectOutputStream">
    private ObjectOutputStream dataObjectOutputStream;

    public ObjectOutputStream getDataObjectOutputStream() {
        return dataObjectOutputStream;
    }

    public void setDataObjectOutputStream(ObjectOutputStream outpuStream) {
        this.dataObjectOutputStream = outpuStream;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isConnected">
    private boolean connected = false;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="clientObjectOutputStream">
    private ObjectOutputStream clientObjectOutputStream;

    public ObjectOutputStream getClientObjectOutputStream() {
        return clientObjectOutputStream;
    }

    public void setClientObjectOutputStream(ObjectOutputStream clientObjectOutputStream) {
        this.clientObjectOutputStream = clientObjectOutputStream;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="clientObjectInputStream">
    private ObjectInputStream clientObjectInputStream;

    public ObjectInputStream getClientObjectInputStream() {
        return clientObjectInputStream;
    }

    public void setClientObjectInputStream(ObjectInputStream clientObjectInputStream) {
        this.clientObjectInputStream = clientObjectInputStream;
    }
    // </editor-fold>

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {

        if (args.length != 1) {
            System.out.println("Uso: ChatClient <chatProxyIp>");
            return;
        }

        final ChatJFrame chatJFrame = new ChatJFrame();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                chatJFrame.setVisible(true);
            }
        });

        // Establece la conexion.
        InetAddress a;
        try {
            a = InetAddress.getByName(args[0]);
        } catch (UnknownHostException uhe) {
            System.out.println(uhe);
            return;
        }

        try {
            chatJFrame.appendLine("Connecting with Proxy {0}:{1,number,#00}...",
                    a.toString(), Config.PORT);

            Socket skt = new Socket(a, Config.PORT);
            skt.setKeepAlive(true);

            InputStream is = skt.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            // Save the input stream
            chatJFrame.setClientObjectInputStream(ois);

            int dataPort = ois.readInt();
            chatJFrame.appendLine("DataPort on {0,number,#00}", dataPort);

            // Let's give time to the proxy to setup
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
            }

            final Socket data = new Socket(a, dataPort);
            chatJFrame.appendLine("DataPort connection open");

            // This thread writes all data received in the textArea
            Thread t = new Thread(() -> {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(data.getInputStream());

                        while (true) {
                            Object o = objectInputStream.readObject();
                            if (o instanceof DataPkt) {
                                DataPkt pkt = (DataPkt)o;
                                String s = new String(pkt.getBytes(), "UTF-8");
                                chatJFrame.appendText("Remoto> {0}", s);
                            }
                        }
                    } catch (IOException ioe) {
                        chatJFrame.appendLine("IOException: {0}", ioe.getMessage());
                    } catch (ClassNotFoundException cnfe) {
                        chatJFrame.appendLine("ClassNotFoundException: {0}", cnfe.getMessage());
                    }

            });
            t.setDaemon(true);
            t.start();
            chatJFrame.appendLine("InputStream Thread started.");

            ObjectOutputStream oos = new ObjectOutputStream(data.getOutputStream());
            chatJFrame.setDataObjectOutputStream(oos);

            OutputStream os = skt.getOutputStream();
            chatJFrame.setClientObjectOutputStream(new ObjectOutputStream(os));

            chatJFrame.setConnected(true);

        } catch (IOException ioe) {
            chatJFrame.appendLine("General IO Error: {0}", ioe.toString());
        }
    }

    public void appendText(String format, Object ... args) {
        String s = MessageFormat.format(format, args);
        this.textArea.append(s);
    }

    public void appendLine(String format, Object ... args) {
        appendText(format, args);
        appendText("\n");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConectar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField txtChat;
    private javax.swing.JTextField txtIpAddr;
    // End of variables declaration//GEN-END:variables

}
