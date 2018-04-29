/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import model.User;
import java.io.Serializable;

/**
 *
 * @author marei
 */
public class Session implements Serializable{
    
    private static User authUser;
    
    /**
     * get the authenticated user
     * @return User;
     */
    public static User getAuth(){
        return authUser;
    }
    
    /**
     * 
     * 
     * @param usr as User
     */
    public static void setAuth(User usr) {
        authUser = usr;
    }
    
}