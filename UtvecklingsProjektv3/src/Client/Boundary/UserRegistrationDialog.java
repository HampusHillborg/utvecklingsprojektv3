package Client.Boundary;

import Client.Controller.Client;
import Entity.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UserRegistrationDialog extends JDialog {
    private JTextField usernameField;
    private JTextField hostField;
    private JTextField portField;
    private JButton chooseImageButton;
    private JLabel imageLabel;
    private ImageIcon icon;

    private User user;
    private String host;
    private int port;
    private boolean loggedIn = false;

    public UserRegistrationDialog(Client client) {
        setTitle("Chat Login");
        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(3, 2);
        inputPanel.setLayout(gridLayout);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);

        usernameField = new JTextField();
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);

        chooseImageButton = new JButton("Choose Image");
        inputPanel.add(new JLabel("Profile Picture:"));
        inputPanel.add(chooseImageButton);

        hostField = new JTextField("localhost");
        inputPanel.add(new JLabel("Host (IP/domain):"));
        inputPanel.add(hostField);

        portField = new JTextField("12345");
        inputPanel.add(new JLabel("Port:"));
        inputPanel.add(portField);

        JPanel registerPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        registerPanel.add(registerButton);

        JPanel imagePanel = new JPanel();
        imageLabel = new JLabel();
        imagePanel.add(imageLabel);

        add(inputPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(registerPanel, BorderLayout.SOUTH);

        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Files ending in .png", "png"));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Files ending in .jpeg", "jpeg"));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Files ending in .jpg", "jpg"));

                int result = fileChooser.showOpenDialog(UserRegistrationDialog.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    //icon = new ImageIcon(selectedFile.getPath());
                    if (icon.getIconHeight() > 256 || icon.getIconWidth() > 256) {
                        JOptionPane.showMessageDialog(UserRegistrationDialog.this, "Image is too large. Please choose a smaller image.");
                        return;
                    }
                    imageLabel.setIcon(icon);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip;
                icon = new ImageIcon("UtvecklingsProjektv3/koskesh.jpg");
                imageLabel.setIcon(icon);

                try {
                    ip = InetAddress.getByName(hostField.getText()).getHostAddress();
                } catch (UnknownHostException ex) {
                    ip = hostField.getText();
                    throw new RuntimeException(ex);
                }

                if (usernameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(UserRegistrationDialog.this, "Please enter a username.");
                    return;
                }

                if (imageLabel.getIcon() == null) {
                    JOptionPane.showMessageDialog(UserRegistrationDialog.this, "Please choose an image.");
                    return;
                }

                if (!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
                    JOptionPane.showMessageDialog(UserRegistrationDialog.this, "Please enter a valid host.");
                    return;
                }

                if (portField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(UserRegistrationDialog.this, "Please enter a port.");
                    return;
                }

                client.createUser(usernameField.getText(), icon);
                host = ip;
                port = Integer.parseInt(portField.getText());
                loggedIn = true;
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public User getUser() {
        return user;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isUserLoggedIn() {
        return loggedIn;
    }
    public static void main(String[] args){
        UserRegistrationDialog user = new UserRegistrationDialog(new Client());
    }
}