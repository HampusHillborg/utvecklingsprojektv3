package Client.Boundary;

import Client.Controller.Client;
import Entity.Message;
import Entity.ServerUpdate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private LPanel lPanel;
    private RPanel rPanel;
    private MPanel mPanel;

    public MainPanel(int width, int height, MainFrame mainFrame) {
        super(null);
        this.setSize(width, height);
        lPanel = new LPanel(width / 2 +60, height, mainFrame);
        add(lPanel);

        rPanel = new RPanel(width / 2 +70, height, mainFrame);
        add(rPanel);

        mPanel = new MPanel(width / 2 +70, height, mainFrame);
        add(mPanel);

    }

    public void displayMessage(Message message){
        lPanel.displayMessage(message);
    }
    protected LPanel getLeftPanel() {
        return lPanel;
    }

    protected RPanel getRightPanel() {
        return rPanel;
    }

    protected MPanel getmPanel(){
        return mPanel;
    }
    public void setContacts(ArrayList<String> contacts) {
        rPanel.setContacts(contacts);
    }
    /*
    public void serverUpdate(ServerUpdate update) {
        rPanel.serverUpdate(update);
    }

     */

}
