package Client.Boundary;

import Client.Controller.Client;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LPanel extends JPanel {
    private Client client;
    private JLabel messageText;
    private RPanel rPanel;
    private JTextArea messageWindow;
    private JTextArea messageField;
    private JButton sendButton;
    private JButton chooseImage;
    private String imagePath;
    private JLabel userImage;
    private JLabel messageImage;
    private JLabel username;
    private JLabel chattUsers;
    private MainFrame mainFrame;
    private int width;
    private int height;


    public LPanel(int width, int height, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        setLocation(0, 0);
        setUp();
    }

    private void setUp(){
        messageWindow = new JTextArea();
        messageWindow.setLocation(120, 40);
        messageWindow.setSize(500, 400);
        messageWindow.setBorder(BorderFactory.createLoweredBevelBorder());
        messageWindow.setBackground(new Color(255, 255, 255));
        messageWindow.setEditable(false);
        add(messageWindow);

        chooseImage = new JButton("Add Image");
        chooseImage.setSize(220, 40);
        chooseImage.setLocation(250, 545);

        chooseImage.addActionListener( e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setCurrentDirectory(new java.io.File("./images"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg & png", "png", "jpg");
            fileChooser.setFileFilter(filter);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });

        add(chooseImage);
        messageField = new JTextArea();
        messageField.setLocation(120, 450);
        messageField.setSize(500, 90);
        messageField.setBorder(BorderFactory.createLoweredBevelBorder());
        messageField.setBackground(new Color(255, 255, 255));
        messageField.setEditable(true);
        add(messageField);

        sendButton = new JButton("Send");
        sendButton.setSize(150, 40);
        sendButton.setLocation(470,545);
        add(sendButton);

        ImageIcon imageIcon = new ImageIcon("images/angry.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        userImage = new JLabel(imageIcon);
        userImage.setSize(40,40);
        userImage.setLocation(40, 100);
        add(userImage);

        ImageIcon imageIcon2 = new ImageIcon("images/angry.png"); // load the image to a imageIcon
        Image image2 = imageIcon2.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon2 = new ImageIcon(newimg2);  // transform it back

        userImage = new JLabel(imageIcon2);
        userImage.setSize(60,60);
        userImage.setLocation(40, 480);
        add(userImage);

        username = new JLabel("");
        username.setSize(90, 30);
        username.setLocation(40, 450);
        add(username);

        chattUsers = new JLabel();
        chattUsers.setSize(90, 30);
        chattUsers.setLocation(40, 70);
        add(chattUsers);

        addListeners();
    }
    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        sendButton.addActionListener(listener);
    }

    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == sendButton) {
                //client.sendMessage(messageField.getText(), rPanel.getSelectedRecipients());
                messageField.setText("");

            }
        }
    }

}

