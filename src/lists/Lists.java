/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import serversocket.RemoteConnection;

/**
 *
 * @author rewoly
 * 
 * Класс для хранения временных данных в массивы
 */
public class Lists {
    // Переменные для админов
    public static String admins = new String();
    public static ArrayList<Long> adminsID = new ArrayList();
    public static ArrayList<Long> adminsCreate = new ArrayList();
    public static HashMap<Long, Boolean> adminIsRoot = new HashMap<>();
    public static String adminsForAdmins = new String();
    
    // Переменные для пользователей
    public static String users = new String();
    public static ArrayList<Long> usersID = new ArrayList();
    public static String usersForAdmins = new String();
    
    // Переменные для серверов
    public static String servers = new String(); 
    public static HashMap<String, String> server = new HashMap<>();
    public static ArrayList<String> serverStatusIsFailed = new ArrayList();
    public static String serversForAdmins = new String();
    
    // Переменные для удаленных объектов
    public static HashMap<String, String> equipmentsIsFault = new HashMap<>();
    public static ArrayList<String> equipmentsForSendsUsers = new ArrayList<>();
    
    // Переменные для инструкций 
    public static String manuals = new String();
    public static String manualsForAdmins = new String();
    public static String manualsForUsers = new String();
    
    //Переменные для сокета
    public static LinkedList<RemoteConnection> remoteConnections = new LinkedList<>();
    public static int port = 0;
    
    // Переменные для уведомлений
    public static String serverCommandIsSuccesfully = "Операция выполнена успешно.";
    public static String validation = "Введенный вами текст не прошёл валидацию, повторите снова.";
    
    // Переменные для вторых сессий в классе для команд
    public static boolean userAdd = false;
    public static boolean userDelete = false;
    public static boolean adminAdd = false;
    public static boolean adminDelete = false;
    public static boolean serverAdd = false;
    public static boolean serverDelete = false;
}
