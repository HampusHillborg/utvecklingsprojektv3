package Client.Boundary;

import Client.Controller.Client;
import Entity.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JTextArea messageArea;
    private JTextField messageField;
    private JPanel contactPanel;
    private JList<String> contacts;
    private DefaultListModel<String> contactList;
    private Client client;

    public MainFrame(Client client) {
        setTitle("Chat Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        contactList = new DefaultListModel<>();
        contacts = new JList<>(contactList);
        this.client = client;

        // Create the message display area
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create the message input field and send button
        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Create the contact panel
        contactPanel = new JPanel();
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
        JScrollPane contactScrollPane = new JScrollPane(contactPanel);
        contactScrollPane.setPreferredSize(new Dimension(200, getHeight()));
        add(contactScrollPane, BorderLayout.EAST);

        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    public void displayMessage(Message message) {
        messageArea.append(message.getText() + "\n");
    }

    public String getMessage() {
        return messageField.getText();
    }

    public void setContacts(ArrayList<String> listOfContacts){
        String[] list = new String[listOfContacts.size()];
        for(int i = 0; i < listOfContacts.size(); i++){
            list[i] = listOfContacts.get(i);
        }
        contacts.setListData(list);
    }

    public void addUserToContactList(String username) {
        JButton userButton = new JButton(username);
        contactPanel.add(userButton);
        revalidate();
        repaint();
    }

    public void sendMessage() {

        client.sendMessage(getMessage(), contactList);
        messageField.setText("");
    }

    public void sendImage(ImageIcon imageIcon) {
        // Handle sending the image here
        // Example: displayMessage("You sent an image: " + imageIcon.getDescription());
    }

    public static void main(String[] args) {

    }
}
