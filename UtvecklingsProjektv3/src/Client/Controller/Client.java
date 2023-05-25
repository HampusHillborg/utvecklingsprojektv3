package Client.Controller;

import Client.Boundary.ButtonType;
import Client.Boundary.MainFrame;
import Client.Boundary.UserRegistrationDialog;
import Entity.Message;

import java.awt.*;
import java.util.ArrayList;

public class Client {
    private MainFrame view;
    private ClientHandler clientHandler;

    public Client() {
        UserRegistrationDialog userRegistrationDialog = new UserRegistrationDialog();
    }

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

}
