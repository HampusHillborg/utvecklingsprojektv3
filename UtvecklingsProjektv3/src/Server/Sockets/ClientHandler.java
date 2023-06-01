package Server.Sockets;
import Entity.Buffer;
import Entity.Message;
import Entity.User;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private Buffer<Object> objectBuffer;
    private User user;
    private Server server;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        objectBuffer = new Buffer<Object>();
        new MessageReceiver(this).start();
        new MessageSender().start();
    }

    public User getUser() {
        return user;
    }

    public void addMessage(Message message) {
        objectBuffer.put(message);
    }

    public void newServerUpdate(Object update) {
        objectBuffer.put(update);
    }

    private class MessageReceiver extends Thread {
        private ObjectInputStream ois;
        private ClientHandler clientHandler;

        public MessageReceiver(ClientHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

        public void run() {
            try {
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                user = (User) ois.readObject();
                System.out.println(user.getUsername() + " anslöt sig.");
                server.clientConnected(clientHandler);

                while (true) {
                    Object obj = ois.readObject();

                    if (obj instanceof Message) {
                        Message msg = (Message) obj;
                        if (msg.getText().equals("//disconnect")) {
                            server.clientDisconnected(user);
                            System.out.println("En klient disconnetar!");
                        } else {
                            server.sendMessage(msg);
                        }
                    }

                    if (obj instanceof User) {
                        User user = (User) obj;
                        System.out.println(user.getContacts());
                        server.updateActiveClientUser(user, clientHandler);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class MessageSender extends Thread {
        private ObjectOutputStream oos;

        public void run() {
            try {
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                while (true) {
                    Object obj = objectBuffer.get();
                    if (obj != null) {
                        oos.writeObject(obj);
                        oos.flush();
                        System.out.println("Skickat något till klient");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

