package Client.Boundary;

import Client.Controller.ClientHandler;

import javax.swing.*;

public class MPanel extends JPanel{
    private MainFrame mainFrame;
    private JList<String> connectedUsers;
    private JButton addContact;
    private DefaultListModel<String> contactList;
    private JLabel lblTitle;
    private int width;
    private int height;
    private ClientHandler clientHandler;
    public MPanel(int width, int height, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width / 3, height);
        setLocation(width, 0);
        setUp();
    }
    private void setUp() {
        lblTitle = new JLabel("Connected Users");
        lblTitle.setLocation(2, 0);
        lblTitle.setSize((width / 2)-100, 20);
        this.add(lblTitle);

        connectedUsers = new JList<>();
        connectedUsers.setLocation(0, 23);
        connectedUsers.setSize(width, height - 100);
        this.add(connectedUsers);

    }
    public void serverUpdate(){
        connectedUsers.setListData(clientHandler.);
    }
}
