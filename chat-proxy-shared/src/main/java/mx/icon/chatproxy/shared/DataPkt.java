/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.shared;

import java.io.Serializable;

/**
 * Data Packet. Data structure exchanged between data sockets.
 * 
 * @author antoniovl
 */
public class DataPkt implements Serializable {
    private byte[] bytes;

    public DataPkt() {
    }

    public DataPkt(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    
}
