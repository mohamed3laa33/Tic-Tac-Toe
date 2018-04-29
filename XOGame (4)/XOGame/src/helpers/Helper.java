/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author marei
 */
public class Helper {
    
   public static boolean isInt(String str){
       try {
           Integer.parseInt(str);
           return true;
       } catch (NumberFormatException e) {
       }
        return false;
    }
    
    public static boolean isFloat(String str) {
        try {
            if (Helper.isInt(str)) {
                return false;
            }else{
                Float.parseFloat(str);
                return true;
            }
        } catch (NumberFormatException e) {
        }
        return false;
    }
    
    public static boolean isDate(String str) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            df.parse(str);
            return true;
        } catch (Exception e) {
            //not a date
        }
        return false;
    }
    
    public static boolean isString(String str) {
        if (Helper.isDate(str)) {
            return false;
        }else if(Helper.isFloat(str)){
            return false;
        }else if (Helper.isInt(str)) {
            return false;
        }
        return true;
    }
}
