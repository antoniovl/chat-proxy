/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.shared;

import java.io.Serializable;

/**
 * Clase que representa la respuesta enviada el cliente cuando hace
 * la solicitud "OpenChat" (representada a su vez por OpenChatPkt).
 * @author antoniovl
 */
public class OpenChatPktReply implements Serializable {
    private boolean success;
    private String message;

    public OpenChatPktReply(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public OpenChatPktReply() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
}
