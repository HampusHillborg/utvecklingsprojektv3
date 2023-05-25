package Client.Boundary;

import Client.Controller.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RPanel extends JPanel{

    private JList<String> contacts;
    private JButton removeContact;
    private int width;
    private int height;
    private MainFrame mainFrame;
    private JLabel lblTitle;
    public RPanel(int width, int height, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width / 3, height);
        setLocation(width+200, 0);
        setUp();
    }
    private void setUp() {
        lblTitle = new JLabel("ContactList");
        lblTitle.setLocation(2, 0);
        lblTitle.setSize((width / 2)-100, 20);
        this.add(lblTitle);

        contacts = new JList<>();
        contacts.setLocation(0, 23);
        contacts.setSize(width, height - 100);
        this.add(contacts);

        removeContact = new JButton("Add to Contactlist");
        removeContact.setEnabled(true);
        removeContact.setSize(width / 5, 30);
        removeContact.setLocation(0, height - 75);
        removeContact.addActionListener(l -> mainFrame.buttonPressed(ButtonType.RemoveContact));
        this.add(removeContact);


    }
    public void setContacts(ArrayList<String> listOfContacts){
        String[] list = new String[listOfContacts.size()];
        for(int i = 0; i < listOfContacts.size(); i++){
            list[i] = listOfContacts.get(i);
        }
        contacts.setListData(list);
    }


    public ArrayList<String> getSelectedRecipients(){
        ArrayList<String> selectedRecipients = new ArrayList<>();
        selectedRecipients.addAll(contacts.getSelectedValuesList());
        return selectedRecipients;
    }




}
