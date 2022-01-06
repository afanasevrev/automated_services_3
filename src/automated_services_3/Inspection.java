/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automated_services_3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rewoly
 * 
 * Класс в котором будет проверяться правильность заданных значений
 */

public class Inspection {
    public static String PATTERN_FOR_USERS = "^(\\d{1,})\\s(\\S{1,})\\s(\\S{1,})$";
    public static String PATTERN_FOR_ADMINS = "^(\\d{1,})\\s(\\@\\S{1,})$";
    public static String PATTERN_FOR_SUPERADMINS = "^(\\d{1,})\\s(\\@\\S{1,})\\s(root)$";
    public static String PATTERN_FOR_SERVERS =   "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\s\\D{1,}$";                          
     
    public static boolean getInspection(String patternByInspection, String text){
        Pattern pattern = Pattern.compile(patternByInspection);
        Matcher matcher = pattern.matcher(text);
    return matcher.find();
    }       
}
