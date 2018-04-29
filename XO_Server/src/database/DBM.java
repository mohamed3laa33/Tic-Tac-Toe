package database;

import helpers.Helper;
import model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marei
 */
public class DBM {
    protected static ResultSet rs;
    protected static Connection con;
    protected static Statement stmt;
    protected static PreparedStatement pst;

    public static void exec(String update_from_students_SET_lname_where_id__, String hamada, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void get(String select_username_FROM_user, ArrayList<User> usersAL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private void DBM(){}
    
    /**
     * Connect to Database
     * 
     * This function uses singleton design pattern to prevent creating redundant connections
     * @return Connection con
     */
    public static Connection connect()
    {
        if (con == null)
        {
            try {
            con = DriverManager.getConnection("jdbc:mariadb://localhost/xo","root","1");
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            } catch (SQLException ex) {
                Logger.getLogger(DBM.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return con;
    }
    
    /**
     * Close the connection.
     */
    public static void close()
    {
        try {
            con.close();
            con = null;
        } catch (SQLException ex) {
            Logger.getLogger(DBM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * select data from table
     * @param query as a string
     * @param args for each ? add the specified argument as string
     * @return ResultSet
     */
    public static ResultSet get(String query, String... args)
    {
        try {

            DBM.connect();

            pst = con.prepareStatement(query);

            int i = 1;
            for (String arg : args) {
                if (Helper.isDate(arg)) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    pst.setDate(i, Date.valueOf(arg));
                } else if (Helper.isFloat(arg)) {
                    pst.setFloat(i, Float.parseFloat(arg));
                } else if (Helper.isInt(arg)) {
                    pst.setInt(i, Integer.parseInt(arg));
                } else {// String
                    pst.setString(i, arg);
                }

                i++;
            }
            
            rs = pst.executeQuery();
            DBM.close();
            
            

        } catch (SQLException ex) {
            Logger.getLogger(DBM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    /**
     * Execute any query like : insert, update, delete
     * @param query as String
     * @param args as String
     * @return success as boolean
     */
    public static boolean exec(String query, String... args) {
        boolean success = false;
        try {
            
            DBM.connect();

            pst = con.prepareStatement(query);

            int i = 1;
            for (String arg : args) {
                if (Helper.isDate(arg)) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    pst.setDate(i, Date.valueOf(arg));
                } else if (Helper.isFloat(arg)) {
                    pst.setFloat(i, Float.parseFloat(arg));
                } else if (Helper.isInt(arg)) {
                    pst.setInt(i, Integer.parseInt(arg));
                } else {// String
                    pst.setString(i, arg);
                }

                i++;
            }

            success = pst.execute();
            
            DBM.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    
    
}