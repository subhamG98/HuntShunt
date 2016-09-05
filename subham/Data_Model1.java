package com.github.florent37.materialviewpager.subham;
 
/**
 * Created by SONU on 25/09/15.
 */
public class Data_Model1 {

    // Getter and Setter model for recycler view items
    private String image;
    private String sender;

    public Data_Model1(String image,String sender) {
        this.sender=sender;

        this.image = image;
    }
 

 
 
    public String getImage() {
        return image;
    }

    public String getSender() {
        return sender;
    }
}