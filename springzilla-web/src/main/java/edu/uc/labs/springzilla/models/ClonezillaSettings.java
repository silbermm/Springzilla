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
    
   
    
}
