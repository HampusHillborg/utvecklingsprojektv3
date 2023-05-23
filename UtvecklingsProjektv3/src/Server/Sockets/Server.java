package Server.Sockets;

import Server.GUI.ServerFrame;
import Entity.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Server extends Thread{

    private ServerFrame serverFrame;
    private int port = 12345;
    private HashMap<User, ClientHandler> onlineUsers;
    private HashMap<User, ArrayList<String>> contactList;
    private ClientHandler clientHandler;

    public Server(){
        this.serverFrame = new ServerFrame();
        onlineUsers = new HashMap<>();
        contactList = new HashMap<>();
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
}
