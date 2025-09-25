package prog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class File extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public File() {
        // Frame settings
        setTitle("Login Page");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use a layout manager for the content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label + field
        gbc.gridx = 0; gbc.gridy = 0;
        contentPane.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        contentPane.add(usernameField, gbc);

        // Password label + field
        gbc.gridx = 0; gbc.gridy = 1;
        contentPane.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        contentPane.add(passwordField, gbc);

        // Login button
        gbc.gridx = 1; gbc.gridy = 2;
        loginButton = new JButton("Login");
        contentPane.add(loginButton, gbc);

        // Message label
        gbc.gridx = 1; gbc.gridy = 3;
        messageLabel = new JLabel("", SwingConstants.CENTER);
        contentPane.add(messageLabel, gbc);

        // Button action
        loginButton.addActionListener(new Act
