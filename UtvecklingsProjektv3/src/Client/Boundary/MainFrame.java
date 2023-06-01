package Client.Boundary;

import Client.Controller.Client;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private Client client;

    public MainFrame(Client client) {
        super("Chatt");
        this.client = client;
        this.setResizable(true);
        this.setSize(1000, 650);
        this.mainPanel = new MainPanel(800, 450, this);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        new MainFrame(new Client());
    }

    public MainPanel getMainPanel(){
        return mainPanel;
    }
    public void buttonPressed(ButtonType pressedButton){
        //client.buttonPressed(pressedButton);
    }
}
