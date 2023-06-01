package Client.Controller;

import Client.Boundary.ButtonType;
import Client.Boundary.MainFrame;
import Client.Boundary.UserRegistrationDialog;
import Entity.Message;
import Entity.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Client {
    private MainFrame view;
    private ServerConnection serverConnection;
    private UserRegistrationDialog loginWindow;
    private User login;

    public Client() {
        loginWindow = new UserRegistrationDialog(this);
        loginWindow.setVisible(true);

    }

    public void createUser(String username, ImageIcon avatar){
        login = new User(username, avatar);
    }

    public static void main(String[] args) {
        new Client();
    }

/*
    public void clientDisconnecting() {
        serverConnection.clientDisconnecting();
    }

    public void sendMessage(String text, ArrayList<String> recipients) {
        Message msg = new Message();
        msg.setText(text);
        msg.setSender(serverConnection.getUser());
        msg.setRecipients(serverConnection.getUser().getUsersFromString(recipients));
        serverConnection.sendMessage(msg);
        view.displayNewMessage(msg);
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void addContact(String selectedContactToAdd) {
        serverConnection.getUser().addContact(selectedContactToAdd);
        serverConnection.addContacts();
        serverConnection.sendUser();
    }

    public void removeContact(String selectedContactToRemove){
        serverConnection.getUser().removeContact(selectedContactToRemove);
        serverConnection.sendUser();
    }


 */

    /*
    public void buttonPressed(ButtonType button){

        switch (button) {
            case Send:
                sendMessage();
                break;

            case AddContact:
                addContact();
                break;

            case AddImage:
                addImage();
                break;

            case RemoveContact:
                removeContact();
                break;

        }
    }


    public void sendMessage(String text, ArrayList<String> recipients){
        Message msg = new Message();
        msg.setText(text);
        msg.setSender(ClientHandler.getUser());
        msg.setRecipients(clientHandler.getUser().getUsersFromString(recipients));
        clientHandler.sendMessage(msg);
        view.displayNewMessage(msg);
    }
    public void addContact(){

    }
    public void addImage(){

    }
    public void removeContact(String selectedContactToRemove){
        clientHandler.getUser().removeContact(selectedContactToRemove);
        clientHandler.sendUser();
    }
    public ClientHandler getClientHandler() {
        return clientHandler;
    }
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

     */

}
