/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automated_services_3;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lists.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author rewoly
 * Класс для взаимодействия c клиентами в мессенджере Telegram
 */
public class TelegramBot extends TelegramLongPollingBot {

   @Override
    public String getBotToken() {
        return "token1";
    }
    
    @Override
    public String getBotUsername() {
        return "automatedservices_bot";
    }
    
    public synchronized void setButtons(SendMessage sendMessage) {
// Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

// Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

// Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        
// Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Мой \uD83C\uDD94"));

// Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        
// Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Инструкция\uD83D\uDCC3"));

// Третья строчка клавиатуры 
        KeyboardRow keyboardThirdRow = new KeyboardRow();  
        
// Добавляем кнопки в третью строчку клавиатуры
        keyboardThirdRow.add(new KeyboardButton("Список серверов\uD83D\uDE80"));
        
// Четвертая строчка клавиатуры 
        KeyboardRow keyboardFourthRow = new KeyboardRow();  
        
// Добавляем кнопки в четвертую строчку клавиатуры
        keyboardFourthRow.add(new KeyboardButton("Администраторы\uD83D\uDE0E"));  
        
// Пятая строчка клавиатуры 
        KeyboardRow keyboardFiveRow = new KeyboardRow();  
        
// Добавляем кнопки в пятую строчку клавиатуры
        keyboardFourthRow.add(new KeyboardButton("Пользователи\uD83D\uDC7B"));  
        
// Шестая строчка клавиатуры 
        KeyboardRow keyboardSixRow = new KeyboardRow();  
        
// Добавляем кнопки в шестую строчку клавиатуры
        keyboardSixRow.add(new KeyboardButton("Недоступные серверы\u26D4"));

// Седьмая строчка клавиатуры 
        KeyboardRow keyboardSevenRow = new KeyboardRow();  
        
// Добавляем кнопки в седьмую строчку клавиатуры
        keyboardSevenRow.add(new KeyboardButton("Недоступные устройства\uD83D\uDCF7"));    
        
// Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);  
        keyboard.add(keyboardFiveRow);
        keyboard.add(keyboardSixRow);
        keyboard.add(keyboardSevenRow);
// и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
    
    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
		SendMessage s = new SendMessage();
                s.setText(text); 
		s.setChatId(msg.getChatId().toString());
                setButtons(s);
                try {
                execute(s);
                } catch (TelegramApiException e) {}
                // Боту может писать не один человек, и поэтому 
                //чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять       
    }
        
    @Override
    public void onUpdateReceived(Update update) 
    {
        Message msg = update.getMessage(); // Это нам понадобится
	String txt = msg.getText();
        System.out.println(txt);
                //--------------------------------------------------------------
                //Команды для админов. Вторая сессия.
                //Удалить сервер
                if (Lists.serverDelete && Lists.adminsCreate.contains(msg.getChatId())) try {
                        ServerCommand.setDelete(txt, "servers.txt");
                        Lists.serverDelete = false;
                        Lists.adminsCreate.remove(msg.getChatId());
                        sendMsg(msg, Lists.serverCommandIsSuccesfully);
                    } catch (IOException ex) {
                        Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //Добавить сервер 
                if (Lists.serverAdd && Lists.adminsCreate.contains(msg.getChatId())) try {
                    if (Inspection.getInspection(Inspection.PATTERN_FOR_SERVERS, txt)) {
                        ServerCommand.setWrite(txt, "servers.txt");
                        sendMsg(msg, Lists.serverCommandIsSuccesfully);
                    } else sendMsg(msg, Lists.validation);
                        Lists.serverAdd = false;
                        Lists.adminsCreate.remove(msg.getChatId());
                    } catch (IOException ex) {
                        Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //Удалить пользователя
                if (Lists.userDelete && Lists.adminsCreate.contains(msg.getChatId())) try {
                        ServerCommand.setDelete(txt, "users.txt");
                        Lists.userDelete = false;
                        Lists.adminsCreate.remove(msg.getChatId());
                        sendMsg(msg, Lists.serverCommandIsSuccesfully);
                    } catch (IOException ex) {
                        Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //Добавить пользователя 
                if (Lists.userAdd && Lists.adminsCreate.contains(msg.getChatId())) try {
                    if (Inspection.getInspection(Inspection.PATTERN_FOR_USERS, txt)) {
                        ServerCommand.setWrite(txt, "users.txt");
                        sendMsg(msg, Lists.serverCommandIsSuccesfully);
                    } else sendMsg(msg, Lists.validation);
                        Lists.userAdd = false;
                        Lists.adminsCreate.remove(msg.getChatId());
                    } catch (IOException ex) {
                        Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //Удалить админа
                if (Lists.adminDelete && Lists.adminsCreate.contains(msg.getChatId())) try {
                        ServerCommand.setDelete(txt, "admins.txt");
                        Lists.adminDelete = false;
                        Lists.adminsCreate.remove(msg.getChatId());
                        sendMsg(msg, Lists.serverCommandIsSuccesfully);
                    } catch (IOException ex) {
                        Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //Добавить админа
                if (Lists.adminAdd && Lists.adminsCreate.contains(msg.getChatId())) try {
                    if (Inspection.getInspection(Inspection.PATTERN_FOR_ADMINS, txt) || Inspection.getInspection(Inspection.PATTERN_FOR_SUPERADMINS, txt)){
                        ServerCommand.setWrite(txt, "admins.txt");
                        sendMsg(msg, Lists.serverCommandIsSuccesfully);
                    } else sendMsg(msg, Lists.validation);
                        Lists.adminAdd = false;
                        Lists.adminsCreate.remove(msg.getChatId());
                        
                    } catch (IOException ex) {
                        Logger.getLogger(TelegramBot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //--------------------------------------------------------------
                //Пользовательские команды
		if (txt.equals("/start")) 
			sendMsg(msg, "Привет, я бот СТ 'Алмазавтоматика' ММНУ ЦОИСТБ");
                if (txt.equals("Мой \uD83C\uDD94"))
                        sendMsg(msg, msg.getChatId().toString());
                if (txt.equals("Инструкция\uD83D\uDCC3")){
                    if (!Lists.usersID.contains(msg.getChatId()) && !Lists.adminsID.contains(msg.getChatId()))
                        sendMsg(msg, Lists.manuals);
                    else if (Lists.usersID.contains(msg.getChatId()) && !Lists.adminsID.contains(msg.getChatId()))
                        sendMsg(msg, Lists.manualsForUsers);
                    else if (Lists.usersID.contains(msg.getChatId()) && Lists.adminsID.contains(msg.getChatId()))
                        sendMsg(msg, Lists.manualsForAdmins);
                }
                if (txt.equals("Список серверов\uD83D\uDE80")){
                    if (Lists.usersID.contains(msg.getChatId()))
                        sendMsg(msg, Lists.servers);
                    else sendMsg(msg, "Вам эта опция не доступна, так как не являетесь пользователем.");
                }
                if (txt.equals("Пользователи\uD83D\uDC7B")) {
                    if (Lists.adminsID.contains(msg.getChatId()))
                        sendMsg(msg, Lists.users);
                    else sendMsg(msg, "Вам эта опция не доступна, так как не являетесь админом.");
                }
                if (txt.equals("Администраторы\uD83D\uDE0E"))
                        sendMsg(msg, Lists.admins);
                if (txt.equals("Недоступные серверы\u26D4")) {
                    if (Lists.usersID.contains(msg.getChatId())) {
                        for (String text : Lists.serverStatusIsFailed)
                        sendMsg(msg, text);
                    } else sendMsg(msg, "Вам эта опция не доступна, так как не являетесь пользователем.");
                }
                if (txt.equals("Недоступные устройства\uD83D\uDCF7")) {
                    if (Lists.usersID.contains(msg.getChatId())) {
                        for (String text: Lists.equipmentsForSendsUsers)
                        sendMsg(msg, text);
                    } else sendMsg(msg, "Вам эта опция не доступна, так как не являетесь пользователем.");
                }
                //--------------------------------------------------------------
                //Команды для админов. Первая сессия.
                //Добавить пользователя
                if (txt.equals(Direction.ADD + " " + Direction.USER) && Lists.adminsID.contains(msg.getChatId())) {
                        sendMsg(msg, "Добавьте нового пользователя, одним сообщением введите ID и ФИ");
                        Lists.userAdd = true;
                        Lists.adminsCreate.add(msg.getChatId());
                }  
                //Добавить админа, если добавляющий обладает правами супер-админа
                if (txt.equals(Direction.ADD + " " + Direction.ADMIN) && Lists.adminsID.contains(msg.getChatId())) {
                    if (Lists.adminIsRoot.get(msg.getChatId())) {
                        sendMsg(msg, "Добавьте нового админа, одним сообщением введите ID и логин");
                        Lists.adminAdd = true;
                        Lists.adminsCreate.add(msg.getChatId());
                    } else sendMsg(msg, "Вы не обладаете правами супер-админа");
                }
                //Удалить пользоввтеля
                if (txt.equals(Direction.DELETE + " " + Direction.USER) && Lists.adminsID.contains(msg.getChatId())) {
                        sendMsg(msg, "Удалите пользователя из списка. Одним сообщением введите ID и ФИ");
                        sendMsg(msg, Lists.usersForAdmins);
                        Lists.userDelete = true;
                        Lists.adminsCreate.add(msg.getChatId());
                }
                //Удалить админа, если удаляющий обладает правами супер-админа
                if (txt.equals(Direction.DELETE + " " + Direction.ADMIN) && Lists.adminsID.contains(msg.getChatId())) {
                    if (Lists.adminIsRoot.get(msg.getChatId())) {
                        sendMsg(msg, "Удалите админа из списка. Одним сообщением введите ID и логин");
                        sendMsg(msg, Lists.adminsForAdmins);
                        Lists.adminDelete = true;
                        Lists.adminsCreate.add(msg.getChatId());
                    } else sendMsg(msg, "Вы не обладаете правами супер-админа");
                }
                //Добавить сервер
                if (txt.equals(Direction.ADD + " " + Direction.SERVER) && Lists.adminsID.contains(msg.getChatId())) {
                        sendMsg(msg, "Добавьте новый сервер, одним сообщением введите IP и название");
                        Lists.serverAdd = true;
                        Lists.adminsCreate.add(msg.getChatId());
                }  
                //Удалить сервер
                if (txt.equals(Direction.DELETE + " " + Direction.SERVER) && Lists.adminsID.contains(msg.getChatId())) {
                        sendMsg(msg, "Удалите сервер из списка. Одним сообщением введите IP и название");
                        sendMsg(msg, Lists.serversForAdmins);
                        Lists.serverDelete = true;
                        Lists.adminsCreate.add(msg.getChatId());
                }
                //--------------------------------------------------------------
    }

    @Override
    public void onUpdatesReceived(List<Update> list) {
        super.onUpdatesReceived(list); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onRegister() {
        super.onRegister(); //To change body of generated methods, choose Tools | Templates.
    }

    private void setDelete(String txt, String userstxt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
