package com.myhash.object;

public class Tag {
    private String name;
    private int num;

    public Tag(String name) {
        this.name = name;
        this.num = 0;
    }
    
    public Tag(String name, int num) {
    	this.name = name;
    	this.num = num;
    }
    
    public String getName() {
    	return name;
    }
    
    public int getNum() {
    	return num;
    }
    
    public void increaseN() {
    	this.num++;
    }
    
    public void decreaseN() {
    	this.num--;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(getName() + " ");
    	sb.append(getNum());
    	return sb.toString();
    }
}
