/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automated_services_3;

import lists.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rewoly
 * 
 * Класс для опросов, пинг запросы на сервера
 */
public class Pingstation extends TimerTask{
    public void getRead() throws FileNotFoundException, IOException {
        String[] splitLine;
        File file = new File("servers.txt");
        FileReader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);
        String line;
        while((line = reader.readLine()) != null) {
            String serverName = "";
            splitLine = line.split("\\s");
            for(int i = 1; i < splitLine.length; i++) serverName = serverName + splitLine[i] + " ";
            Lists.server.put(serverName, splitLine[0]); 
        }
    }
    
    
    public void getPing() throws IOException {
        
      for (String serverName: Lists.server.keySet()) {
        try {
            String ipAddress = Lists.server.get(serverName);
            InetAddress inet = InetAddress.getByName(ipAddress);   
            if (!inet.isReachable(1000)) {
              if (!Lists.serverStatusIsFailed.contains(serverName)) {
                      Lists.serverStatusIsFailed.add(serverName);
              }
            } else {
              if (Lists.serverStatusIsFailed.contains(serverName))
                      Lists.serverStatusIsFailed.remove(serverName); 
            }
        } catch (UnknownHostException ex) {
            System.out.println("Exception:" + ex.getMessage());
        }
      }
    }
            
    @Override
    public void run() {
        try {
            getRead();
            Thread.sleep(2000);
            getPing();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Pingstation.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
}
