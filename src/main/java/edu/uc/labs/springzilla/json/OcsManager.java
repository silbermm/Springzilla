package edu.uc.labs.springzilla.json;

public class OcsManager {
    
    private String status;
    private int elapsedDays;
    private String elapsedTime;
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getStatus(){
        return this.status;
    }

    public int getElapsedDays() {
        return elapsedDays;
    }

    public void setElapsedDays(int elapsedDays) {
        this.elapsedDays = elapsedDays;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
