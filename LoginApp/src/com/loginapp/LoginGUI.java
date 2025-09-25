package com.loginapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("Login Form");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Labels and fields
        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);

        loginButton = new JButton("Login");

        // Add components
        gbc.gridx = 0; gbc.gridy = 0; add(userLabel, gbc);
        gbc.gridx = 1; add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(passLabel, gbc);
        gbc.gridx = 1; add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 2; add(loginButton, gbc);

        // Action listener
        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }
}
