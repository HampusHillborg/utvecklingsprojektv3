package Client.Boundary;

import Client.Controller.Client;
import Entity.Message;
import Entity.ServerUpdate;
import Entity.User;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private Client controller;
    private Message msg;
    private int width;
    private int height;

    private LPanel leftPanel; //Kontakter, användare online etc.
    private RPanel rightPanel; //Chatt och möjlighet att skriva.
    private ImageDisplay imageDisplay;

    public MainPanel(Client controller, int width, int height) {
        this.width = width;
        this.height = height;
        this.controller = controller;

        this.setSize(width, height);
        this.setLayout(null);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        setUp();
    }

    public void setUp(){
        leftPanel = new LPanel(controller, width / 3, height);
        leftPanel.setLocation(0,0);
        add(leftPanel);

        rightPanel = new RPanel(controller, leftPanel,  700, height);
        rightPanel.setLocation(width/3, 0);
        add(rightPanel);




    }

    public void serverUpdate(ServerUpdate update) {
        leftPanel.serverUpdate(update);
    }

    public void displayMessage(Message msg) {
        rightPanel.newMessage(msg);
    }

    public void setUser(User login) {
        rightPanel.setUser(login);
    }

    public void setContacts(ArrayList<String> contacts) {
        leftPanel.setContacts(contacts);
    }

    public void setImageMessage(ImageIcon icon){
        imageDisplay.setImage(icon);
    }

}
