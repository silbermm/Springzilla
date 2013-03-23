package edu.uc.labs.springzilla.models;

public class MulticastSettings {
    
    private String multicastTTL;
    private String multicastPort;
    private String rdvAddress;
    private String senderAddress;

    public String getMulticastTTL() {
        return multicastTTL;
    }

    public void setMulticastTTL(String multicastTTL) {
        this.multicastTTL = multicastTTL;
    }

    public String getMulticastPort() {
        return multicastPort;
    }

    public void setMulticastPort(String multicastPort) {
        this.multicastPort = multicastPort;
    }

    public String getRdvAddress() {
        return rdvAddress;
    }

    public void setRdvAddress(String rdvAddress) {
        this.rdvAddress = rdvAddress;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }
    
    



}
