package com.loginapp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class HomePanel extends JPanel {

    private String username;

    public HomePanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("🏠 Welcome to Campus Forge, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        contentPanel.add(new JScrollPane(infoArea), BorderLayout.NORTH);

        JPanel certPanel = new JPanel();
        certPanel.setLayout(new BoxLayout(certPanel, BoxLayout.Y_AXIS));
        contentPanel.add(new JScrollPane(certPanel), BorderLayout.CENTER);

        fetchUserData(infoArea, certPanel);
    }

    private void fetchUserData(JTextArea infoArea, JPanel certPanel) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/loginapp", "root", "root");

            // Get user id
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            int userId = 0;
            if (rs.next()) userId = rs.getInt("id");

            // Fetch skills
            ps = conn.prepareStatement("SELECT skill_name FROM user_skills WHERE user_id=?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            StringBuilder sb = new StringBuilder("Your Skills:\n");
            while (rs.next()) sb.append("- ").append(rs.getString("skill_name")).append("\n");
            infoArea.setText(sb.toString());

            // Fetch certificates
            ps = conn.prepareStatement("SELECT cert_id, cert_name, cert_file FROM user_certificates WHERE user_id=?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            certPanel.removeAll();
            while (rs.next()) {
                int certId = rs.getInt("cert_id");
                String certName = rs.getString("cert_name");
                byte[] certFile = rs.getBytes("cert_file");

                JPanel singleCert = new JPanel(new FlowLayout(FlowLayout.LEFT));
                JLabel certLabel = new JLabel("Certificates Gained: " + certName);
                JButton showBtn = new JButton("Show");

                showBtn.addActionListener(e -> {
                    try {
                        File tempFile = File.createTempFile("certificate_" + certId, ".pdf");
                        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                            fos.write(certFile);
                        }
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(tempFile);
                        } else {
                            JOptionPane.showMessageDialog(this, "Cannot open PDF on this system.");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error opening certificate: " + ex.getMessage());
                    }
                });

                singleCert.add(certLabel);
                singleCert.add(showBtn);
                certPanel.add(singleCert);
            }

            certPanel.revalidate();
            certPanel.repaint();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}
