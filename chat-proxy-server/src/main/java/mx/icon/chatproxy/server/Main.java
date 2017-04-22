/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 *
 * @author antoniovl
 */
public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Thread t = new Thread(new Proxy(), "ChatProxyThread-0");
        t.setDaemon(true);
        t.start();

        InputStreamReader isr = new InputStreamReader(System.in);
        LineNumberReader lnr = new LineNumberReader(isr);

        boolean done = false;

        while (!done) {
            try {
                logger.info("Proxy started.");
                String tmp = lnr.readLine();
                
                if ("quit".equalsIgnoreCase(tmp)) {
                    done = true;
                }

            } catch (IOException ioe) {
                done = true;
            }
        }
    }

}
