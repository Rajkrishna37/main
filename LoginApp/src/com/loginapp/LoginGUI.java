package com.loginapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, backButton, submitRegisterButton;

    private JPanel mainPanel; // container for switching screens

    public LoginGUI() {
        setTitle("Campus Forge");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(Color.BLACK);

        // Screens
        JPanel choicePanel = createChoicePanel();   // First screen (Login | Register choice)
        JPanel loginPanel = createLoginPanel();     // Login screen
        JPanel registerPanel = createRegisterPanel(); // Register screen

        mainPanel.add(choicePanel, "choice");
        mainPanel.add(loginPanel, "login");
        mainPanel.add(registerPanel, "register");

        setContentPane(mainPanel);

        showScreen("choice");

        setVisible(true);
    }

    // ---------- First Screen ----------
    private JPanel createChoicePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        JLabel title = new JLabel("🏫 Campus Forge");
        title.setFont(new Font("Serif", Font.BOLD, 48));
        title.setForeground(Color.WHITE);

        JButton goLogin = new JButton("Login");
        goLogin.setFont(new Font("Arial", Font.BOLD, 22));
        goLogin.setBackground(Color.DARK_GRAY);
        goLogin.setForeground(Color.WHITE);
        goLogin.addActionListener(e -> showScreen("login"));

        JButton goRegister = new JButton("Register");
        goRegister.setFont(new Font("Arial", Font.BOLD, 22));
        goRegister.setBackground(Color.DARK_GRAY);
        goRegister.setForeground(Color.WHITE);
        goRegister.addActionListener(e -> showScreen("register"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(title, gbc);

        gbc.gridy = 1;
        panel.add(goLogin, gbc);

        gbc.gridy = 2;
        panel.add(goRegister, gbc);

        return panel;
    }

    // ---------- Login Screen ----------
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);

        usernameField = new JTextField(15);
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passLabel.setForeground(Color.WHITE);

        passwordField = new JPasswordField(15);
        passwordField.setBackground(Color.BLACK);
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(e -> login());

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> showScreen("choice"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(backButton, gbc);
        gbc.gridx = 1;
        panel.add(loginButton, gbc);

        return panel;
    }

    // ---------- Register Screen ----------
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("New Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);

        JTextField newUserField = new JTextField(15);
        newUserField.setBackground(Color.BLACK);
        newUserField.setForeground(Color.WHITE);
        newUserField.setCaretColor(Color.WHITE);

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passLabel.setForeground(Color.WHITE);

        JPasswordField newPassField = new JPasswordField(15);
        newPassField.setBackground(Color.BLACK);
        newPassField.setForeground(Color.WHITE);
        newPassField.setCaretColor(Color.WHITE);

        submitRegisterButton = new JButton("Register");
        submitRegisterButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitRegisterButton.setBackground(Color.DARK_GRAY);
        submitRegisterButton.setForeground(Color.WHITE);

        submitRegisterButton.addActionListener(e -> {
            String user = newUserField.getText();
            String pass = new String(newPassField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields!");
                return;
            }

            try (Connection con = DBConnection.getConnection()) {
                String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, user);
                pst.setString(2, pass);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Registered Successfully! Now Login.");
                showScreen("login");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backBtn.setBackground(Color.DARK_GRAY);
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> showScreen("choice"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(newUserField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(newPassField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(backBtn, gbc);
        gbc.gridx = 1;
        panel.add(submitRegisterButton, gbc);

        return panel;
    }

    // ---------- Helper ----------
    private void showScreen(String name) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, name);
    }

    // ---------- Login Function ----------
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
                this.dispose(); // close login window
                new MainGUI(username); // open main app
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::new);
    }
}

// ---------------- MAIN APP ----------------
class MainGUI extends JFrame {
    public MainGUI(String username) {
        setTitle("Welcome, " + username + " - Campus Forge");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabbed pane
        JTabbedPane tabs = new JTabbedPane();

        // Home Tab
        JPanel homePanel = new JPanel();
        homePanel.add(new JLabel("🏠 Home Page"));

        // Search Tab
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("🔍 Search Page"));

        // Profile Tab
        JPanel profilePanel = new JPanel();
        profilePanel.add(new JLabel("👤 Profile Page for " + username));

        // Add tabs
        tabs.addTab("Home", homePanel);
        tabs.addTab("Search", searchPanel);
        tabs.addTab("Profile", profilePanel);

        add(tabs);

        setVisible(true);
    }
}
