package edu.uc.labs.springzilla.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="settings")
public class ClonezillaSettings implements Serializable {
    
    private Long id;
    private String imageLocation;
    private int multicastPort;
    private String multicastRdvAddr;
    private String multicastSenderAddr;
    private int multicastTTL;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column
    public String getImageLocation(){
        return imageLocation;
    }
    
    public void setImageLocation(String imageLocation){
        this.imageLocation = imageLocation;
    }
    
    @Column
    public int getMulticastPort() {
        return multicastPort;
    }

    public void setMulticastPort(int multicastPort) {
        this.multicastPort = multicastPort;
    }

    @Column
    public String getMulticastRdvAddr() {
        return multicastRdvAddr;
    }

    public void setMulticastRdvAddr(String multicastRdvAddr) {
        this.multicastRdvAddr = multicastRdvAddr;
    }

    @Column
    public String getMulticastSenderAddr() {
        return multicastSenderAddr;
    }

    public void setMulticastSenderAddr(String multicastSenderAddr) {
        this.multicastSenderAddr = multicastSenderAddr;
    }

    @Column
    public int getMulticastTTL() {
        return multicastTTL;
    }

    public void setMulticastTTL(int multicastTTL) {
        this.multicastTTL = multicastTTL;
    }
    
}
