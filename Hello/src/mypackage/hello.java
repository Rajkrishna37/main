import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleWindow {
    public static void main(String[] args) {
        // Create a new frame (window)
        JFrame frame = new JFrame("My First Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        // Create a text field
        JTextField textField = new JTextField(20);

        // Create a button
        JButton button = new JButton("Show Text");

        // Create a label to display text
        JLabel label = new JLabel("Type something above and press the button.");

        // Add action when button is clicked
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                label.setText("You typed: " + input);
            }
        });

        // Add components to frame
        frame.add(textField);
        frame.add(button);
        frame.add(label);

        // Make the window visible
        frame.setVisible(true);
    }
}
