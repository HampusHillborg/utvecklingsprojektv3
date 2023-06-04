/**
 * The `Server` class represents a server that handles client connections and message communication.
 * It manages the online users, contact lists, and message sending/receiving.
 */
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

    /**
     * Constructs a new `Server` object.
     * Initializes the online user and contact list data structures.
     * Starts the server.
     */
    public Server(){
        this.trafficViewer = new TrafficViewer();
        onlineUsers = new HashMap<>();
        contactList = new HashMap<>();
        this.storedMessages = new StoredMessages();
        start();
    }

    /**
     * Starts the server and listens for client connections.
     * Creates a new `ClientHandler` for each connected client.
     */
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

    /**
     * Notifies the server that a client has connected.
     * Adds the client to the online users and sends stored messages and contact list updates.
     *
     * @param clientHandler the client handler associated with the connected client
     */
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

    /**
     * Returns the latest server update.
     *
     * @return the latest server update
     */
    private ServerUpdate getUpdate() {
        return serverUpdate;
    }

    /**
     * Creates a server update object and sends it to all connected clients.
     * The server update includes the list of connected users and the new user connected.
     */
    private void createAndSendServerUpdate(){
        ServerUpdate serverUpdate = new ServerUpdate();

        ArrayList<User> connectedList = new ArrayList<>();
        onlineUsers.forEach(((user, clientConnection) -> connectedList.add(user)));

        serverUpdate.setConnectedList(connectedList);
        serverUpdate.setNewUserConnected(latestClient.getUser());

        onlineUsers.forEach(((user, clientConnection) -> clientConnection.newServerUpdate(serverUpdate)));
    }

    /**
     * Notifies the server that a client has disconnected.
     * Removes the client from the online users and sends a server update to all connected clients.
     *
     * @param user the user associated with the disconnected client
     */
    public void clientDisconnected(User user) {
        onlineUsers.remove(user);
        System.out.println(user.getUsername() + " disconnected.. :(");
        createAndSendServerUpdate();
    }

    /**
     * Sends a message to the specified recipients.
     * If a recipient is online, the message is sent directly.
     * Otherwise, the message is stored for the recipient until they come online.
     *
     * @param msg the message to be sent
     */
    public void sendMessage(Message msg) {
        ArrayList<User> recipients = msg.getRecipients();
        System.out.println("Sending messages");

        for(int i = 0; i < recipients.size(); i++){
            User receiver = recipients.get(i);
            if(onlineUsers.containsKey(receiver)){
                onlineUsers.get(receiver).addMessage(msg);
            } else {
                storedMessages.put(receiver, msg);
            }
        }
    }

    /**
     * Updates the active client user with the provided user object and client handler.
     * Adds the user to the online users and updates the contact list for the user.
     *
     * @param user           the user to be updated
     * @param clientHandler  the client handler associated with the user
     */
    public void updateActiveClientUser(User user, ClientHandler clientHandler) {
        System.out.println("Updated contacts for " + user.getUsername() + " with contacts " + user.getContacts());
        onlineUsers.put(user, clientHandler);
        contactList.put(user, user.getContacts());
    }

    /**
     * The entry point of the server application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Server();
    }
}
