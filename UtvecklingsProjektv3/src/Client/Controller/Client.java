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

    public void clientDisconnecting() {
        serverConnection.clientDisconnecting();
    }

    public void sendMessage(Message message, ArrayList<String> recipients) {
        message.setSender(serverConnection.getUser());
        message.setRecipients(serverConnection.getUser().getUsersFromString(recipients));
        serverConnection.sendMessage(message);
        view.displayNewMessage(message);
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

    public static void main(String[] args) throws IOException {
        new Client();
        new Client();
        new Client();
    }


}
