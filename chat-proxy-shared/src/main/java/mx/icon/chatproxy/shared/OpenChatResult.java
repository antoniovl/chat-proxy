/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.icon.chatproxy.shared;

/**
 * Esta clase se utiliza para obtener el resultado de establecer
 * la conexión internamente en el Proxy. No se utiliza para
 * transmitirse a los ChatClients.
 * 
 * @author antoniovl
 */
public class OpenChatResult {
    private boolean statusOk;
    private String messaje;

    public OpenChatResult() {
    }

    public OpenChatResult(boolean statusOk, String messaje) {
        this.statusOk = statusOk;
        this.messaje = messaje;

    }

    public String getMessaje() {
        return messaje;
    }

    public void setMessaje(String messaje) {
        this.messaje = messaje;
    }

    public boolean isStatusOk() {
        return statusOk;
    }

    public void setStatusOk(boolean statusOk) {
        this.statusOk = statusOk;
    }
    
}
