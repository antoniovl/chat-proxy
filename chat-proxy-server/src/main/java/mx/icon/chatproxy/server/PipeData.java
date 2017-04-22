/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.server;

import mx.icon.chatproxy.shared.DataPkt;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Clase que interconecta un InputStream con un OutputStream.
 * Debe utilizarse como Thread.
 * 
 * @author antoniovl
 */
public class PipeData implements Runnable {

    private InputStream in;
    private OutputStream out;

    public PipeData(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {

        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            ObjectOutputStream oos = new ObjectOutputStream(out);

            boolean done = false;
            while (!done) {
                // El paquete de datos obtenido de un lado lo
                // repetimos en el otro.
                Object o = ois.readObject();
                if (o instanceof DataPkt) {
                    DataPkt pkt = (DataPkt)o;
                    oos.writeObject(pkt);
                    oos.flush(); // importante, sin esto los datos no se envian.
                }
            }
        } catch (IOException ioe) {
            System.out.println("IOException en PipeData: " + ioe.toString());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("ClassNotFound en PipeData: " + cnfe.toString());
        }
    }

}
