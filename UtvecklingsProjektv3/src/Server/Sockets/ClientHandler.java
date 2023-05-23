package Server.Sockets;

import Entity.Buffer;
import Entity.Message;
import Entity.User;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private Buffer<Object> objectBuffer;
    private User user;
    private Server server;

    public ClientHandler(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }


    private class MessageReciever extends Thread{

        private Server server;
        private ObjectInputStream ois;
        private ClientHandler clientHandler;

        public MessageReciever(ClientHandler clientHandler){
            this.clientHandler = clientHandler;
        }

        public void run(){
            try{
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                //Första objektet som servern får ifrån Client är alltid ett user objekt.
                user = (User) ois.readObject();
                System.out.println(user.getUsername() + " anslöt sig.");
                server.clientConnected(clientHandler);

                //Sedan kan klient bara skicka Message objekt.
                while(true){
                    Object obj = ois.readObject();

                    if(obj instanceof Message){
                        Message msg = (Message) obj;
                        if(msg.getText().equals("//disconnect")){
                            server.clientDisconnected(user);
                            System.out.println("En klient disconnetar!");

                        }else{
                            server.sendMessage(msg);
                        }
                    }

                    if(obj instanceof User){
                        //Jämför namnet med aktiva klienter. Där det matchar uppdatera den klientens kontakter.
                        User user = (User) obj;
                        System.out.println(user.getContacts());
                        server.updateActiveClientUser(user, clientHandler);
                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
