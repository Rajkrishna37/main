package test;
import javax.swing.*;
import java.awt.*;

public class hello {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Label & TextField Example");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new FlowLayout()); // simple left-to-right layout

        // Label
        JLabel label = new JLabel("Enter your name:");

        // Text field
        JTextField textField = new JTextField(15); // 15 columns wide

        // Add to frame
        frame.add(label);
        frame.add(textField);

        frame.setVisible(true);
    }
}
