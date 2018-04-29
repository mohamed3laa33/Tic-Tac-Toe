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
    public static int gameId;
    public static int player1;
    public static int player2;
    public static Boolean amIX;
    
    /** Session.
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
    
    

    public static int getOpponent() {
        int opponent_id = 0;
        if (Session.player1 == Session.getAuth().getId()) {
            opponent_id = Session.player2;
        } else {
            opponent_id = Session.player1;
        }
        return opponent_id;
    }
    

    
}
