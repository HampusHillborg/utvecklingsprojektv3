package Entity;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Message implements Serializable {
    private String text;
    private ImageIcon icon;
    private User sender;
    private String hourTime;
    private String dateTime;
    private ArrayList<User> recipients;

    public Message(){

        LocalDateTime currTime = LocalDateTime.now();
        int year = currTime.getYear();
        int month = currTime.getMonthValue();
        int day = currTime.getDayOfMonth();
        int hour = currTime.getHour();
        int minute = currTime.getMinute();
        int second = currTime.getSecond();
        this.dateTime = String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
        this.hourTime = String.format("%02d:%02d", hour, minute);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getSender() {
        return sender.getUsername();
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ArrayList<User> getRecipients() {return recipients;}

    public void setRecipients(ArrayList<User> recipients) {
        this.recipients = recipients;
    }

    public String getHourTime() {
        return hourTime;
    }

    public String getDateTime() {
        return dateTime;
    }
}
