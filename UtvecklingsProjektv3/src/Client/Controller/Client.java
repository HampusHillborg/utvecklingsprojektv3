package Client.Controller;

import Client.Boundary.MainFrame;
import Client.Boundary.UserRegistrationDialog;
import Entity.Message;
import Entity.User;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Client {
    private MainFrame view;
    private ServerConnection serverConnection;
    private UserRegistrationDialog loginWindow;
    private User login;

    public Client() throws IOException {
        loginWindow = new UserRegistrationDialog(this);

        while(!loginWindow.isLoggedIn()){

        }
        view.setUser(login);
    }

    public void createUser(String username, ImageIcon avatar){
        login = new User(username, avatar);
        try {
            view = new MainFrame(this);
            serverConnection = new ServerConnection("localhost", 12345, this, login, view.getMainPanel());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

<<<<<<< Updated upstream
   /* public void sendMessage(String text, ArrayList<String> recipients) {
        Message msg = new Message();
        msg.setText(text);
        msg.setSender(serverConnection.getUser());
        msg.setRecipients(serverConnection.getUser().getUsersFromString(recipients));
        serverConnection.sendMessage(msg);
        view.displayMessage(msg);
    }*/

    public static void main(String[] args) {
        new Client();
    }


=======
>>>>>>> Stashed changes
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
        serverConnection.addContacts(selectedContactToAdd);
        serverConnection.sendUser();
    }

    public void removeContact(String selectedContactToRemove){
        serverConnection.getUser().removeContact(selectedContactToRemove);
        serverConnection.sendUser();
    }

<<<<<<< Updated upstream



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

=======
    public static void main(String[] args) throws IOException {
        new Client();
        new Client();
    }


>>>>>>> Stashed changes
}
