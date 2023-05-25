package Client.Boundary;

import Client.Controller.Client;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private Client client;

    public MainFrame(int width, int height, Client client) {
        super("Chatt");
        this.client = client;
        this.setResizable(false);
        this.setSize(width, height);
        this.mainPanel = new MainPanel(width, height, this);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    public void buttonPressed(ButtonType pressedButton){
        client.buttonPressed(pressedButton);
    }
}
