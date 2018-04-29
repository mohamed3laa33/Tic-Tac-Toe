/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import database.DBM;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import session.Session;

/**
 *
 * @author marei
 */
public class User implements Serializable{

    private int id;
    private String userName;
    private String email;
    private String password;
    private Boolean status;
    
//    public void User(){
//        
//    }
    
    /**
     * Create a new user in only one step
     * @param id
     * @param userName
     * @param email
     * @param password 
     */
    public User(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Create a new user in only one step
     *
     * @param id
     * @param userName
     * @param email
     * @param password
     * @param status
     */
    public User(int id, String userName, String email, String password, Boolean status) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.status = status;
    }
    
    /**
     * Get user's id
     * @return id as integer
     */
    public int getId() {
        return this.id;
    }
    
    /**
     * Set user's id
     * @param id as integer
     */
    public void setId(int id) {
        this.id = id;
    }
    

    
    /**
     * Get username which is unique
     * @return username as String
     */
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * Set username which is unique
     *
     * @param userName as String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * Get user's email
     * @return email as String
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * Set user's email
     * @param email as String
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Get user's password
     * @return password as String
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Set user's password
     * @param password as String
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Get user status
     *
     * @return status as Boolean
     */
    public Boolean getStatus() {
        return this.status;
    }

    /**
     * Set user status
     *
     * @param status as Boolean
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    /**
     * login into the game only if the user has a valid username and password
     * @param username as String
     * @param password as String
     * @return boolean (true if login success, otherwise it will return false).
     */
    public static boolean login(String username, String password) {
        ResultSet rs = DBM.get("SELECT * FROM user WHERE username = ? AND password = ?",username, password );
        try {
            if(! rs.next())
            {
                System.out.println("Wrong username or password");
                return false;
               /**Here we should write that it is wrong username or password **/  
            }else{
                System.out.println("User ID is " + rs.getInt(1));
                Session.setAuth(new User(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4)));
                //String[] args = {"ad"};
                //XO_Client.main(args);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //return true if login success, otherwise it will return false.
        return true;
    }
    
    public static boolean exists(String username) {
       ResultSet rs = DBM.get("SELECT * FROM user WHERE username = ?", username);
        try {
            if (rs.next()) {
                System.out.println("This user already exists");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }

        //return true if login success, otherwise it will return false.
        return false;
    }
    
    /**
     * Register a new user
     * 
     * Creating a new row in users table
     * @param userName
     * @param email
     * @param password
     * @return boolean (true if registration success, otherwise it will return false)
     */
    public static boolean register(String userName, String email, String password ){
       
        System.out.println(userName);
        // create user into database
        try{
            DBM.exec("INSERT INTO user(username, email, password, status) values (?, ?, ?, 0)", userName, email, password);
            
        }
        catch(Exception e){
           e.printStackTrace(); 
           return false;
        }
        return true;
    }
    
    /**
     * Get all users
     * @return ArrayLis of users
     */
    public static ArrayList<User> all() {
        ArrayList<User> usersAL = new ArrayList<>();
        try {
            ResultSet rs = DBM.get("SELECT * FROM user WHERE status = 1 AND id <> ? UNION SELECT * FROM user WHERE status = 0 AND id <> ?", ""+ Session.getAuth().getId(), ""+Session.getAuth().getId());
            while (rs.next()) {
                usersAL.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5) ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersAL;
    }
    
    /**
     * Get user by id
     * @param id
     * @return User
     */
    public static User find(int id) {
        try {
            ResultSet rs = DBM.get("SELECT * FROM user WHERE id = ? ", id + "");
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getNString(3), rs.getString(4), rs.getBoolean(5));
            }
        } catch (SQLException ex) {
           // Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Get user's score
     * @return score as integer
     */
    public int score() {
        return 10;
    }
    
    /**
     * Get user status (Online/Offline)
     * @return userStatus as boolean
     */
    public boolean isOnline() {
        //return true if the user is online, otherwise it will return false.
        return true;
    }
    
    /**
     * Set user status (Online/Offline)
     * @param status as boolean(online = true, offline = false)
     */
    public void setStatus(boolean status) {
        
    }
    
    
    /**
     * Get the top 10 players according to their score
     * @return Users ArrayList
     */
    public static ArrayList<User> getBestPlayers(){
        ArrayList<User> usersAL = new ArrayList<>();
        usersAL.add(new User(1, "mareimorsy", "mareimorsy@gmail.com", "123"));
        return usersAL;
    }
}
