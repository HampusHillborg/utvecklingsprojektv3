package Server.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrafficViewer extends JFrame {
    private JTextArea logTextArea;
    private JButton viewButton;
    private JTextField startTimeField;
    private JTextField endTimeField;

    public TrafficViewer() {
        super("Message Traffic Viewer");
        initializeComponents();
        setupListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        JLabel startTimeLabel = new JLabel("Start Time:");
        JLabel endTimeLabel = new JLabel("End Time:");
        startTimeField = new JTextField(15);
        endTimeField = new JTextField(15);
        viewButton = new JButton("View");

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(startTimeLabel);
        inputPanel.add(startTimeField);
        inputPanel.add(endTimeLabel);
        inputPanel.add(endTimeField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startTimeStr = startTimeField.getText();
                String endTimeStr = endTimeField.getText();

                Date startTime = parseDateTime(startTimeStr);
                Date endTime = parseDateTime(endTimeStr);
                String trafficLog = retrieveTrafficLog(startTime, endTime);

                logTextArea.setText(trafficLog);
            }
        });
    }

    private Date parseDateTime(String dateTimeStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return formatter.parse(dateTimeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String retrieveTrafficLog(Date startTime, Date endTime) {
        StringBuilder logBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String dateTimeStr = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date logDateTime = formatter.parse(dateTimeStr);
                if (logDateTime.compareTo(startTime) >= 0 && logDateTime.compareTo(endTime) <= 0) {
                    logBuilder.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logBuilder.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TrafficViewer();
            }
        });
    }
}
