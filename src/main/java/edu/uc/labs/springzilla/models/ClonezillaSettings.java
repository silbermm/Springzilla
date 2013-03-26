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
    private String settingName;
    private String settingValue;    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="name")
    public String getSettingName(){
        return settingName;
    }
    
    public void setSettingName(String settingName){
        this.settingName = settingName;
    }
    
    @Column(name="value")
    public String getSettingValue(){
        return settingValue;
    }
    
    public void setSettingValue(String settingValue){
        this.settingValue = settingValue;
    }
          
}
