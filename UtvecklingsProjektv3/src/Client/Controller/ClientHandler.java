package Client.Controller;

import Client.Boundary.MPanel;
import Client.Boundary.MainPanel;
import Entity.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {
    private Client client;
    private Socket socket;
    private User user;
    private MainPanel view;
    private Buffer<Object> messageBuffer;
    private MPanel mPanel;
    public ClientHandler(String ip, int port, Client client, User user, MainPanel view) throws IOException {
        this.client = client;
        this.view = view;
        this.user = user;
        messageBuffer = new Buffer<Object>();
        socket = new Socket(ip, port);
        new ClientOutput().start();
        new ClientInput().start();
    }
    public void sendMessage(Message message){
        messageBuffer.put(message);
    }
    public void sendUser(){
        messageBuffer.put(user);
    }
    public User getUser(){
        return user;
    }
    public void addContacts(){
        this.user.addContact("Johan");
    }
    ArrayList<User> users = update.getConnectedList();
    User thisUser = getUser();

    public void serverUpdate(ServerUpdate update) {

        System.out.println(update.getConnectedList());
        System.out.println(update.getNewUserConnected());

        String[] userList = new String[users.size()];
        for(int i = 0; i < users.size(); i++){
            if(!users.get(i).getUsername().equals(thisUser.getUsername())){
                userList[i] = users.get(i).getUsername();
            }
        }
        connectedUsers.setListData(userlist);
    }

    //Skickar meddelande till servern
    private class ClientOutput extends Thread{
        private ObjectOutputStream oos;

        public void run(){
            try {
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                oos.writeObject(user);
                oos.flush();
                System.out.println("Klient skickade sin användare till server");

                while(true){
                    //Väntar tills dess att tråden blir notifierad av bufferten.
                    //Den blir notifierad då det finns ett message i bufferten att hämta.
                    Object obj = messageBuffer.get();
                    if(obj instanceof Message){
                        Message msg = (Message) obj;
                        if(msg.getText().equals("//disconnect")){
                            oos.writeObject(msg);
                            oos.flush();
                            System.exit(0);
                        }
                    }
                    oos.writeObject(obj);
                    oos.flush();

                }


            } catch (Exception e){
                System.err.println(e);
            }
        }
    }
    private class ClientInput extends Thread{
        private ObjectInputStream ois;

        public void run(){
            try{
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                while(true){
                    Object obj = ois.readObject();
                    System.out.println("tog emot");

                    if(obj instanceof ServerUpdate){
                        //Ny serverupdate. Packa upp den och visa i view.
                        System.out.println("Clienten fick en serveruppdatering!");
                        ServerUpdate update = (ServerUpdate) obj;

                    }
                    if(obj instanceof ContactList){
                        //Clienten fick veta vilka contakter den har
                        System.out.println("Clienten fick en kontaktuppdatering!");
                        ContactList update = (ContactList) obj;
                        System.out.println(update.getContacts());
                        user.setContacts(update.getContacts());
                        view.setContacts(update.getContacts());
                    }

                    if(obj instanceof Message){
                        //Nytt meddelande. Displaya det i view.
                        System.out.println("Clienten fick ett nytt Message!");
                        Message msg = (Message) obj;
                        view.displayMessage(msg);
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
