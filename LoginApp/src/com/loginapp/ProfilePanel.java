package com.loginapp;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;

public class ProfilePanel extends JPanel {

    private String username;
    private DefaultListModel<String> skillListModel;
    private DefaultListModel<String> certListModel;

    public ProfilePanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("👤 Profile Page - Add Your Skills & Certificates"));
        add(topPanel, BorderLayout.NORTH);

        // ----- Skills Section -----
        JPanel skillPanel = new JPanel();
        skillPanel.setBorder(BorderFactory.createTitledBorder("Add Skills"));

        String[] commonSkills = {"Java","Python","C++","SQL","HTML/CSS",
                "JavaScript","React","Spring Boot","Machine Learning","UI/UX Design"};

        JComboBox<String> skillBox = new JComboBox<>(commonSkills);
        JButton addSkillBtn = new JButton("Add Skill");
        skillListModel = new DefaultListModel<>();
        JList<String> skillList = new JList<>(skillListModel);

        // Load skills initially
        loadSkills();

        addSkillBtn.addActionListener(e -> {
            String skill = (String) skillBox.getSelectedItem();
            if (!skillListModel.contains(skill)) {
                try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/loginapp", "root", "root")) {

                    PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=?");
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();
                    int userId = 0;
                    if (rs.next()) userId = rs.getInt("id");

                    ps = conn.prepareStatement("INSERT INTO user_skills(user_id, skill_name) VALUES(?, ?)");
                    ps.setInt(1, userId);
                    ps.setString(2, skill);
                    ps.executeUpdate();

                    skillListModel.addElement(skill); // update UI
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error adding skill: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Skill already added!");
            }
        });

        skillPanel.add(skillBox);
        skillPanel.add(addSkillBtn);
        skillPanel.add(new JScrollPane(skillList));

        // ----- Certificates Section -----
        JPanel certPanel = new JPanel();
        certPanel.setBorder(BorderFactory.createTitledBorder("Upload Certificates"));
        JButton uploadBtn = new JButton("Upload Certificate");
        certListModel = new DefaultListModel<>();
        JList<String> certList = new JList<>(certListModel);

        // Load certificates initially
        loadCertificates();

        uploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int option = chooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try (Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/loginapp", "root", "root")) {

                    PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=?");
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();
                    int userId = 0;
                    if (rs.next()) userId = rs.getInt("id");

                    ps = conn.prepareStatement(
                            "INSERT INTO user_certificates(user_id, cert_name, cert_file) VALUES(?,?,?)");
                    ps.setInt(1, userId);
                    ps.setString(2, file.getName());
                    ps.setBytes(3, java.nio.file.Files.readAllBytes(file.toPath()));
                    ps.executeUpdate();

                    certListModel.addElement(file.getName()); // update UI
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error uploading certificate: " + ex.getMessage());
                }
            }
        });

        certPanel.add(uploadBtn);
        certPanel.add(new JScrollPane(certList));

        // Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, skillPanel, certPanel);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);
    }

    private void loadSkills() {
        skillListModel.clear();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loginapp", "root", "root")) {
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            int userId = 0;
            if (rs.next()) userId = rs.getInt("id");

            ps = conn.prepareStatement("SELECT skill_name FROM user_skills WHERE user_id=?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) skillListModel.addElement(rs.getString("skill_name"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading skills: " + e.getMessage());
        }
    }

    private void loadCertificates() {
        certListModel.clear();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loginapp", "root", "root")) {
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            int userId = 0;
            if (rs.next()) userId = rs.getInt("id");

            ps = conn.prepareStatement("SELECT cert_name FROM user_certificates WHERE user_id=?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) certListModel.addElement(rs.getString("cert_name"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading certificates: " + e.getMessage());
        }
    }
}
