package edu.uc.labs.springzilla.json;


/**
 * A Bean that represents a json object sent from the restore form
 */
public class CloneJson {

	private String image;
	private String imageProtocol;
	private String imageType;
	private int numberOfClients;
	private int maxWaitTime;

  public void setImage(String image){
    this.image = image;
	}	

 	public String getImage(){
		return this.image;
	}

	public void setImageProtocol(String imageProtocol){
		this.imageProtocol = imageProtocol;
	}

	public String getImageProtocol(){
		return this.imageProtocol;
	}

	public void setImageType(String imageType){
		this.imageType = imageType;	
	}

	public String getImageType(){
		return this.imageType;
	}

	public void setNumberOfClients(int numberOfClients){
		this.numberOfClients = numberOfClients;
	}

	public int getNumberOfClients(){
		return this.numberOfClients;
	}

	public void setMaxWaitTime(int maxWaitTime){
		this.maxWaitTime = maxWaitTime;
	}

	public int getMaxWaitTime(){
		return this.maxWaitTime;
	}

}
