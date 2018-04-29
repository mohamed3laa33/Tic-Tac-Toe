/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import model.User;
import java.io.Serializable;

/**
 *
 * @author marei
 */
public class Msg implements Serializable{
    public String type;//USER, REQUEST, ACCEPT, DECLINE, MOVE
    public String message;
    public int to;
    public int from;
    public int position;
    public User user;
    
    /**
     * Create new message
     * @param type
     * @param message
     * @param to
     * @param position
     * @param y 
     */
    public  Msg(String type, String message, int to, int from, int position){
        this.type = type;
        this.message = message;
        this.to = to;
        this.from = from;
        this.position = position;
    }
    
    /**
     * Send a normal text message
     * @param type
     * @param message
     * @param to
     * @param from 
     */
    public Msg(String type, String message, int to, int from) {
        this.type = type;
        this.message = message;
        this.to = to;
        this.from = from;
    }
    
    /**
     * Send a move
     * @param type
     * @param to
     * @param from
     * @param position 
     */
    public Msg(String type, int to, int from, int position) {
        this.type = type;
        this.to = to;
        this.from = from;
        this.position = position;
    }
    
    /**
     * Send Request, Accept, Reject
     * @param type
     * @param to
     * @param from 
     */
    public Msg(String type, int to, int from) {
        this.type = type;
        this.to = to;
        this.from = from;
    }
    
    /**
     * Send user id
     * @param type
     * @param user 
     */
    public Msg(String type, User user) {
        this.type = type;
        this.user = user;
    }
    
    
    /**
     * Send refresh request
     *
     * @param type
     */
    public Msg(String type) {
        this.type = type;
    }
}