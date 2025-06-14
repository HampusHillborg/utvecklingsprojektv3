package Client.Boundary;

import Client.Controller.Client;
import Entity.Message;
import Entity.User;


import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private Client controller;
    private final int width = 1000;
    private final int hight = 660;

    public MainFrame(Client controller) {
        super("Chat Client");
        this.setResizable(false);
        this.setSize(width, hight);
        this.mainPanel = new MainPanel(controller, width, hight);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.controller = controller;   //Skapar basfönster

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.clientDisconnecting();
                        System.out.println("Client vill avsluta");
            }
        });
    }
    public MainPanel getMainPanel(){
        return mainPanel;
    }
    public void displayNewMessage(Message msg) {
       mainPanel.displayMessage(msg);
    }
    public void setUser(User login) {
        mainPanel.setUser(login);
    }

    public void setImageMessage(ImageIcon icon){
        mainPanel.setImageMessage(icon);
    }
}

