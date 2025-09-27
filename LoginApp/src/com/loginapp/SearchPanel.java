package com.loginapp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SearchPanel extends JPanel {

    private String username;
    private DefaultListModel<String> searchResultsModel;

    public SearchPanel(String username) {
        this.username = username;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel lbl = new JLabel("Search by Skill:");
        JTextField searchField = new JTextField(15);
        JButton searchBtn = new JButton("Search");

        topPanel.add(lbl);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        searchResultsModel = new DefaultListModel<>();
        JList<String> resultsList = new JList<>(searchResultsModel);
        add(new JScrollPane(resultsList), BorderLayout.CENTER);

        searchBtn.addActionListener(e -> {
            String query = searchField.getText().trim();
            searchResultsModel.clear();

            if(!query.isEmpty()){
                try {
                    Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/loginapp","root","root");

                    // Find users with this skill
                    PreparedStatement ps = conn.prepareStatement(
                        "SELECT u.id, u.username, s.skill_name FROM users u " +
                        "JOIN user_skills s ON u.id = s.user_id WHERE s.skill_name LIKE ?");
                    ps.setString(1,"%"+query+"%");
                    ResultSet rs = ps.executeQuery();
                    boolean found=false;

                    while(rs.next()){
                        int userId = rs.getInt("id");
                        String uname = rs.getString("username");
                        String skillName = rs.getString("skill_name");

                        // Add skill info
                        StringBuilder resultText = new StringBuilder(uname + " - Skill: " + skillName);

                        // Fetch certificates for this user
                        PreparedStatement certPs = conn.prepareStatement(
                            "SELECT cert_name FROM user_certificates WHERE user_id=?");
                        certPs.setInt(1, userId);
                        ResultSet certRs = certPs.executeQuery();
                        StringBuilder certs = new StringBuilder();
                        while(certRs.next()) {
                            certs.append(certRs.getString("cert_name")).append(", ");
                        }
                        if(certs.length() > 0){
                            certs.setLength(certs.length()-2); // remove trailing comma
                            resultText.append(" | Certificates Gained: ").append(certs);
                        }

                        searchResultsModel.addElement(resultText.toString());
                        found = true;
                    }

                    if(!found) searchResultsModel.addElement("No users found with skill: "+query);
                    conn.close();
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(this,"Search Error: "+ex.getMessage());
                }
            } else JOptionPane.showMessageDialog(this,"Enter a skill to search!");
        });
    }
}
