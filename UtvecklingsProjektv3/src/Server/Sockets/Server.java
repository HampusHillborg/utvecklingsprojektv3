package Server.Sockets;

import Entity.*;
import Server.Boundary.TrafficViewer;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server extends Thread{

    private TrafficViewer trafficViewer;
    private int port = 12345;
    private HashMap<User, ClientHandler> onlineUsers;
    private HashMap<User, ArrayList<String>> contactList;
    private ClientHandler clientHandler;
    private StoredMessages storedMessages;
    private ClientHandler latestClient;
    private ServerUpdate serverUpdate = null;

    public Server(){
        this.trafficViewer = new TrafficViewer();
        onlineUsers = new HashMap<>();
        contactList = new HashMap<>();
        this.storedMessages = new StoredMessages();
        start();
    }

    @Override
    public void run() {
        System.out.println("Server started");
        Socket clientSocket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            int count = 0;
            while (true) {
                clientSocket = serverSocket.accept();
                clientHandler = new ClientHandler(clientSocket, this);
                count++;
                System.out.println("Client connected: " + count);
            }
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }

    public void clientConnected(ClientHandler clientHandler) {
        User user = clientHandler.getUser();
        onlineUsers.put(user, clientHandler);

        if(storedMessages.contains(user)){
            ArrayList<Message> msgsForClient = storedMessages.get(user);
            for(int i = 0; i < msgsForClient.size(); i++){
                clientHandler.addMessage(msgsForClient.get(i));
            }
        }
        if(contactList.containsKey(user)){
            ContactList updatedContactList = new ContactList(user, contactList.get(user));
            clientHandler.newServerUpdate(updatedContactList);
        }
        latestClient = clientHandler;
        createAndSendServerUpdate();
    }

    private ServerUpdate getUpdate() {
        return serverUpdate;
    }

    private void createAndSendServerUpdate(){
        ServerUpdate serverUpdate = new ServerUpdate();

        ArrayList<User> connectedList = new ArrayList<>();
        onlineUsers.forEach(((user, clientConnection) -> connectedList.add(user)));

        serverUpdate.setConnectedList(connectedList);
        serverUpdate.setNewUserConnected(latestClient.getUser());

        onlineUsers.forEach(((user, clientConnection) -> clientConnection.newServerUpdate(serverUpdate)));
    }

    public void clientDisconnected(User user) {
        onlineUsers.remove(user);
        System.out.println(user.getUsername() + " disconnected.. :(");
        createAndSendServerUpdate();
    }

    public void sendMessage(Message msg) {
        ArrayList<User> recipients = msg.getRecipients();
        System.out.println("Skickar msg:s");

        for(int i = 0; i < recipients.size(); i++){
            User receiver = recipients.get(i);
            if(onlineUsers.containsKey(receiver)){
                onlineUsers.get(receiver).addMessage(msg);

            }else{
                storedMessages.put(receiver, msg);
            }
        }
    }

    public void updateActiveClientUser(User user, ClientHandler clientHandler) {
        System.out.println("Uppdaterade kontakterna för " + user.getUsername() + " med kontakterna " + user.getContacts());
        onlineUsers.put(user, clientHandler);
        contactList.put(user, user.getContacts());
    }

    public static void main(String[] args) {
        new Server();
    }
}
