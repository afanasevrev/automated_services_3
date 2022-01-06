/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lists.Lists;

/**
 *
 * @author rewoly
 * Класс для приёма сообщений о состоянии объектов от удаленных серверов
 */
public class RemoteConnection extends Thread {
    private final BufferedReader in;
    private final Socket socket;
    
    public RemoteConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }
    
    @Override
    public void run() {
        String word;
        while (true) {
            try {
                word = in.readLine();
                Lists.equipmentsIsFault.put(word.split("=")[0], word.split("=")[1]);
            } catch (IOException ex) {
                System.out.println("Клиент отключен");
                stop();
            }
        }
    }
}
