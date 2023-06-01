package Client.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplay extends JPanel {
    private JLabel imageLabel;
    private JLabel textLabel;

    public ImageDisplay() {
       setUp();
    }

    private void setUp(){
        setSize(90, 90);
        imageLabel = new JLabel();
        imageLabel.setSize(90, 20);
        add(imageLabel);

        textLabel = new JLabel();
        add(textLabel);
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

    public void setImage(ImageIcon image) {
        imageLabel.setIcon(image);
    }
}
