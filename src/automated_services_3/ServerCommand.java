/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automated_services_3;

import lists.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rewoly
 * Класс для команд от клиентов.
 */
public class ServerCommand extends TimerTask{
    
    public void getRead() throws FileNotFoundException, IOException {
        //----------------------------------------------------------------------
        //В начале цикла чистим все массивы с информацией
        Lists.servers = "";
        Lists.users = "";
        Lists.manuals = "";
        Lists.admins = "";
        Lists.usersForAdmins = "";
        Lists.adminsForAdmins = "";
        Lists.serversForAdmins = "";
        Lists.manualsForAdmins = "";
        Lists.manualsForUsers = "";
        
        Lists.adminsID.clear();
        Lists.usersID.clear();
        
        String line;
        String text;
        String[] splitLine;
        
        //----------------------------------------------------------------------
        File fileServers = new File("servers.txt");
        FileReader filereaderServers = new FileReader(fileServers);
        BufferedReader readerServers = new BufferedReader(filereaderServers);
       
        // Цикл для Servers
        while((line = readerServers.readLine()) != null) {
            Lists.serversForAdmins = Lists.serversForAdmins + line + "\n";
            text = "";
            splitLine = line.split("\\s+");
            for (int i = 1; i < splitLine.length; i++){
                text = text + " " + splitLine[i];
            }
            Lists.servers = Lists.servers + text + "\n";  
        }
        //----------------------------------------------------------------------
        
        File fileUsers = new File("users.txt");
        FileReader filereaderUsers = new FileReader(fileUsers);
        BufferedReader readerUsers = new BufferedReader(filereaderUsers);
        
        // Цикл для Users
        while((line = readerUsers.readLine()) != null) {
            Lists.usersForAdmins = Lists.usersForAdmins + line + "\n";
            text = "";
            splitLine = line.split("\\s+");
            for (int i = 1; i < splitLine.length; i++){
                text = text + " " + splitLine[i];
            }
            Lists.users = Lists.users + text + "\n";
            Lists.usersID.add(Long.parseLong(splitLine[0]));
        }
        //----------------------------------------------------------------------
        File fileAdmins = new File("admins.txt");
        FileReader filereaderAdmins = new FileReader(fileAdmins);
        BufferedReader readerAdmins = new BufferedReader(filereaderAdmins);
        
        // Цикл для Admins
        while((line = readerAdmins.readLine()) != null) {
            Lists.adminsForAdmins = Lists.adminsForAdmins + line + "\n";
            text = "";
            splitLine = line.split("\\s+");
            text = splitLine[1];
            Lists.admins = Lists.admins + text + "\n"; 
            Lists.adminsID.add(Long.parseLong(splitLine[0]));
            if (splitLine.length > 2 && splitLine[2].equals("root")) 
                Lists.adminIsRoot.put(Long.parseLong(splitLine[0]), Boolean.TRUE);
            else Lists.adminIsRoot.put(Long.parseLong(splitLine[0]), Boolean.FALSE);
        }
        //----------------------------------------------------------------------
        File fileManuals = new File("manuals.txt");
        FileReader filereaderManuals = new FileReader(fileManuals);
        BufferedReader readerManuals = new BufferedReader(filereaderManuals);
        
        // Цикл для Manuals
        while((line = readerManuals.readLine()) != null) {  
            Lists.manuals = Lists.manuals + line;
        }
        //----------------------------------------------------------------------
        File fileManualsForUsers = new File("manualsforusers.txt");
        FileReader filereaderManualsForUsers = new FileReader(fileManualsForUsers);
        BufferedReader readerManualsForUsers = new BufferedReader(filereaderManualsForUsers);
        
        // Цикл для ManualsForUSers
        while((line = readerManualsForUsers.readLine()) != null) {  
            Lists.manualsForUsers = Lists.manualsForUsers + line + "\n";
        }
        //----------------------------------------------------------------------
        File fileManualsForAdmins = new File("manualsforadmins.txt");
        FileReader filereaderManualsForAdmins = new FileReader(fileManualsForAdmins);
        BufferedReader readerManualsForAdmins = new BufferedReader(filereaderManualsForAdmins);
        
        // Цикл для ManualsForAdmins
        while((line = readerManualsForAdmins.readLine()) != null) {  
            Lists.manualsForAdmins = Lists.manualsForAdmins + line + "\n";
        }
        //----------------------------------------------------------------------
        
        // Считываем из файла serverport.txt номер порта
        File file = new File("serverport.txt");
        FileReader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);
        // Цикл для считывания порта
        while((line = reader.readLine()) != null) {
            splitLine = line.split("=");
            Lists.port = Integer.parseInt(splitLine[1]);
        }
        
        // Готовим сообщение отправки клиентам о состоянии удаленных объектов
        Lists.equipmentsForSendsUsers.clear();
        if (Lists.equipmentsIsFault.isEmpty()) Lists.equipmentsForSendsUsers.add("В настоящее время клиенты не подключены");
        else {
            String equipment;
            for (String equipmentName: Lists.equipmentsIsFault.keySet()) {
                equipment = equipmentName + ":" + "\n" + Lists.equipmentsIsFault.get(equipmentName).replace(":", "\n");
                Lists.equipmentsForSendsUsers.add(equipment);
            }
        }
    }
    
    public synchronized static void setWrite(String text, String filename) throws IOException {
        //Вытаскиваем всё содержимое из текстового файла во временный массив
        ArrayList<String> txt = new ArrayList<>();
        File file = new File(filename);
        FileReader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);
        String line;
        while((line = reader.readLine()) != null) {
            txt.add(line);  
        }
        
        //Записываем в текстовый файл наш массив с новым содержимым - text
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false));
        String lineSeparator = System.getProperty("line.separator");
        for (String text1: txt) {
            text1 = text1 + lineSeparator;
            bw.write(text1);
        }
        if (!txt.contains(text)) {
            text = text + lineSeparator;
            bw.write(text);
        }
        bw.flush();
    }
    
    public synchronized static void setDelete(String text, String filename) throws IOException {
        //Вытаскиваем всё содержимое из текстового файла во временный массив
        ArrayList<String> txt = new ArrayList<>();
        File file = new File(filename);
        FileReader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);
        String line;
        while((line = reader.readLine()) != null) {
            txt.add(line);  
        }
        
        //Записываем в текстовый файл наш массив с новым содержимым - text
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false));
        String lineSeparator = System.getProperty("line.separator");
        
        if (txt.contains(text)) txt.remove(text);
            
        for (String text1: txt) {
            text1 = text1 + lineSeparator;
            bw.write(text1);
        }
        
        bw.flush();
    }
    
    @Override
    public void run() {
        try {
            getRead();
        } catch (IOException ex) {
            Logger.getLogger(ServerCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
