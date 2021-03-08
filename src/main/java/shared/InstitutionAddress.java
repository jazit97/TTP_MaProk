package shared;

import java.io.Serializable;


public class InstitutionAddress implements Serializable {
    private String ip;
    private int port;

    public InstitutionAddress(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
