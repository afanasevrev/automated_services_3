/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automated_services_3;

import lists.*;
import serversocket.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 *
 * @author rewoly
 */
public class Automated_services_3 {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) throws TelegramApiException, IOException, InterruptedException {
        // Запускаем модуль по считыванию из файлов конфиги
        ServerCommand serverCommand = new ServerCommand();
        // Запускаем модуль по пингам
        Pingstation pingstation = new Pingstation();
        
        // Запускаем вышестоящие модули периодически
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(serverCommand, 0, 10*6000);
        timer.scheduleAtFixedRate(pingstation, 1000, 10*6000);
        
        // Регаем и запускаем телеграм бота
        TelegramBotsApi telegramBotsApi =  new TelegramBotsApi(DefaultBotSession.class);
        //регаем бота
        try {
            TelegramBot telegramBot = new TelegramBot();
            telegramBotsApi.registerBot(telegramBot);       
            System.out.println("Телеграм бот запущен!");
        } catch (TelegramApiException e){
        }
        
        // Приостанавливаем выполнение программы на 1 секунду, чтобы модуль serverCommand успел считать порт
        Thread.sleep(1000);
        
        System.out.println("Порту сервера приёма сообщений присвоен номер = " + Lists.port);
        
        // Ещё на секунду задержимся перед запуском сервера для приёма удалённых сообщений
        Thread.sleep(1000);
        
        // Запускаем сервер для приёма удаленных сообщений
        ServerSocket serverSocket = new ServerSocket(Lists.port);
        System.out.println("Сервер приёма сообщений от удалённых объектов запущен!");
        while (true) {
            Socket socket = serverSocket.accept();
            try {
            Lists.remoteConnections.add(new RemoteConnection(socket));
            System.out.println("Клиент " + socket.getInetAddress().toString().replace("/", "") + " подключен");
            } catch (IOException e) {
                socket.close();
            }
        }   
    }  
}
