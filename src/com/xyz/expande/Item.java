package com.xyz.expande;


public class Item {
    
   
	private String[]  cycle;
    private int imagea;
    private int imageb;
    private int imagec;
	public String[] getCycle() {
		return cycle;
	}
	public void setCycle(String[] cycle) {
		this.cycle = cycle;
	}
	public int getImagea() {
		return imagea;
	}
	public void setImagea(int imagea) {
		this.imagea = imagea;
	}
	public int getImageb() {
		return imageb;
	}
	public void setImageb(int imageb) {
		this.imageb = imageb;
	}
	public int getImagec() {
		return imagec;
	}
	public void setImagec(int imagec) {
		this.imagec = imagec;
	}
	public Item(String[] cycle, int imagea, int imageb, int imagec) {
		super();
		this.cycle = cycle;
		this.imagea = imagea;
		this.imageb = imageb;
		this.imagec = imagec;
	}
	
  

}
