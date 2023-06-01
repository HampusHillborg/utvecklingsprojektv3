package Client.Boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplay extends JPanel {
    private JLabel imageLabel;
    private JLabel textLabel;

    public ImageDisplay() {
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        textLabel = new JLabel();
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(textLabel, BorderLayout.NORTH);
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

    public void setImage(ImageIcon image) {
        imageLabel.setIcon(image);
    }
}
