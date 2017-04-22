/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.shared;

import java.io.Serializable;

/**
 * Clase que indica al servidor que un cliente desea establecer comunicación
 * con otro cliente.
 * Básicamente indica la dirección IP deseada, la cual deberá encontrarse
 * en el Map de conexiones del Proxy Server.
 * 
 * @author antoniovl
 */
public class OpenChatPkt implements Serializable {
    private String ip;

    public OpenChatPkt(String ip) {
        this.ip = ip;
    }

    public OpenChatPkt() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
