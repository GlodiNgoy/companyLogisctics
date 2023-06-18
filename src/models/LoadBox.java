/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author glodi
 */
public class LoadBox {
   private int id;
   private String sender;
   private String receiver;
   private int dimension;
   private String type;
   private String city;

    public LoadBox(int id, String sender, String receiver, int dimension, String type, String city) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.dimension = dimension;
        this.type = type;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String name) {
        this.sender = name;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
   

    
    
    
    
    
}
