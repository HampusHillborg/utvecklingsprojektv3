/**
 * The `ClientHandler` class represents a handler for a client connected to the server.
 * It manages the communication between the client and the server.
 */
package Server.Sockets;

import Entity.Buffer;
import Entity.ContactList;
import Entity.Message;
import Entity.User;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private Buffer<Object> objectBuffer;
    private User user;
    private Server server;

    /**
     * Constructs a new `ClientHandler` object.
     *
     * @param socket the socket associated with the client connection
     * @param server the server instance
     */
    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        objectBuffer = new Buffer<Object>();
        new MessageReceiver(this).start();
        new MessageSender().start();
    }

    private ContactList loadContacts() {
        ContactList contactList = null;
        try {
            File file = new File("contact_list.txt");
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                contactList = (ContactList) ois.readObject();
                ois.close();
                return contactList;

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    /**
     * Returns the user associated with this client handler.
     *
     * @return the user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Adds a new message to the object buffer to be sent to the client.
     *
     * @param message the message to be sent
     */
    public void addMessage(Message message) {
        objectBuffer.put(message);
    }

    /**
     * Notifies the client handler about a new server update.
     *
     * @param update the server update object
     */
    public void newServerUpdate(Object update) {
        objectBuffer.put(update);
    }

    /**
     * The `MessageReceiver` class is responsible for receiving messages from the client.
     */
    private class MessageReceiver extends Thread {
        private ObjectInputStream ois;
        private ClientHandler clientHandler;

        /**
         * Constructs a new `MessageReceiver` object.
         *
         * @param clientHandler the client handler object
         */
        public MessageReceiver(ClientHandler clientHandler) {
            this.clientHandler = clientHandler;
        }

        /**
         * Starts the message receiving process.
         */
        public void run() {
            try {
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

                // Read the User object sent by the client
                user = (User) ois.readObject();

                // Notify the server that a client has connected
                System.out.println(user.getUsername() + " connected.");
                server.clientConnected(clientHandler);

                while (true) {
                    // Receive the next object from the client
                    Object obj = ois.readObject();

                    if (obj instanceof Message) {
                        // If the received object is a Message, process it accordingly
                        Message msg = (Message) obj;

                        if (msg.getText().equals("//disconnect")) {
                            // If the message text is "//disconnect", the client wants to disconnect
                            server.clientDisconnected(user);
                            System.out.println("A client disconnected!");
                        } else {
                            // Otherwise, it's a regular message to be sent to other clients
                            server.sendMessage(msg);
                            saveMessageToFile(msg);
                        }
                    }

                    if (obj instanceof User) {
                        // If the received object is a User, update the active client user in the server
                        User user = (User) obj;
                        user.setContacts(loadContacts().getContacts());
                        System.out.println(user.getContacts());
                        server.updateActiveClientUser(user, clientHandler);
                    }
                }
            } catch (IOException e) {
                // Handle IO exceptions
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // Handle class not found exceptions
                e.printStackTrace();
            }
        }

        /**
         * Saves the received message to a file.
         *
         * @param message the message to be saved
         */
        private void saveMessageToFile(Message message) {
            try (FileWriter writer = new FileWriter("messages.txt", true)) {
                String formattedMessage = String.format("[%s] %s: %s\n", message.getDateTime(), message.getSender(), message.getText());
                writer.write(formattedMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The `MessageSender` class is responsible for sending messages to the client.
     */
    private class MessageSender extends Thread {
        private ObjectOutputStream oos;

        /**
         * Starts the message sending process.
         */
        public void run() {
            try {
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                while (true) {
                    // Retrieve the next object from the object buffer
                    Object obj = objectBuffer.get();
                    // If an object is available, send it to the client
                    if (obj != null) {
                        oos.writeObject(obj);
                        oos.flush();
                        System.out.println("Sent something to the client");
                    }
                }
                // Handle IO exceptions
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
