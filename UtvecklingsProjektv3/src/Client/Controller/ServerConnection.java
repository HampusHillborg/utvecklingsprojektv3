package Client.Controller;

import Client.Boundary.MainFrame;
import Client.Boundary.MainPanel;
import Entity.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerConnection {

    private Client client;
    private Socket socket;
    private Buffer<Object> messageBuffer;
    private User user;
    private MainPanel view;

    public ServerConnection(String ip, int port, Client client, User user, MainPanel view) throws IOException {
        this.client = client;
        this.view = view;
        this.user = user;
        messageBuffer = new Buffer<Object>();
        socket = new Socket(ip, port);

        new ClientOutput().start();
        new ClientInput().start();
    }


    private void saveContacts() {
        try {
            File file = new File("contact_list.txt");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(new ContactList(user, user.getContacts()));
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(Message message){
        messageBuffer.put(message);
    }

    public void clientDisconnecting() {
        Message msg = new Message();
        msg.setText("//disconnect");
        messageBuffer.put(msg);
    }

    public void sendUser(){
        messageBuffer.put(user);
    }
    public User getUser(){
        return user;
    }
    public void addContacts(String contact){
        this.user.addContact(contact);
        saveContacts();
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
                        view.serverUpdate(update);

                    }
                    if (obj instanceof ContactList) {
                        // Client received the contact list
                        System.out.println("Client received a contact list update!");
                        ContactList update = (ContactList) obj;
                        System.out.println(update.getContacts());
                        // Make sure the list of contacts is unique
                        ArrayList<String> uniqueContacts = new ArrayList<>(new HashSet<>(update.getContacts()));
                        user.setContacts(uniqueContacts);
                        view.setContacts(uniqueContacts);
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
