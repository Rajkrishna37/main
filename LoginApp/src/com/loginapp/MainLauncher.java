package com.loginapp;

import javax.swing.*;
import java.awt.*;

public class MainLauncher extends JFrame {

    private String username;

    public MainLauncher(String username) {
        this.username = username;

        setTitle("Campus Forge - Main Launcher");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Navigation buttons
        JPanel navPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JButton searchBtn = new JButton("Search");
        JButton profileBtn = new JButton("Profile");

        navPanel.add(homeBtn);
        navPanel.add(searchBtn);
        navPanel.add(profileBtn);

        add(navPanel, BorderLayout.NORTH);

        // Panel holder
        JPanel container = new JPanel(new CardLayout());
        add(container, BorderLayout.CENTER);

        // Create tabs
        HomePanel homePanel = new HomePanel(username);
        SearchPanel searchPanel = new SearchPanel(username);
        ProfilePanel profilePanel = new ProfilePanel(username);

        container.add(homePanel, "Home");
        container.add(searchPanel, "Search");
        container.add(profilePanel, "Profile");

        // Navigation actions
        homeBtn.addActionListener(e -> switchPanel(container, "Home"));
        searchBtn.addActionListener(e -> switchPanel(container, "Search"));
        profileBtn.addActionListener(e -> switchPanel(container, "Profile"));

        setVisible(true);
    }

    private void switchPanel(JPanel container, String name) {
        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainLauncher("adara")); // example username
    }
}
