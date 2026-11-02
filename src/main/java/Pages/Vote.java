package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vote extends JFrame implements ActionListener {

    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";
    private final String loggedInUsername;

    private JPanel sideUI, mainUI, candidatePanel;
    private JLabel user, title, statusLabel, waitingLabel, errLabel, noCandidatesLabel;
    private JButton btnHome, btnVote, btnAccount, refreshButton;
    private JScrollPane scrollPane;

    public Vote(String username) {

        this.loggedInUsername = username;

        setTitle("E-VOTE");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        sideUI = new JPanel();
        sideUI.setBackground(Color.BLACK);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 300, 600);

        user = new JLabel("Hello " + loggedInUsername + "!", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 22));
        user.setForeground(Color.WHITE);
        user.setBounds(0, 0, 300, 120);
        sideUI.add(user);

        btnHome = new JButton("Home");
        btnHome.setFont(new Font("Arial", Font.BOLD, 20));
        btnHome.setBounds(25, 160, 250, 75);
        sideUI.add(btnHome);

        btnVote = new JButton("Vote");
        btnVote.setFont(new Font("Arial", Font.BOLD, 20));
        btnVote.setBounds(25, 260, 250, 75);
        sideUI.add(btnVote);

        btnAccount = new JButton("Account");
        btnAccount.setFont(new Font("Arial", Font.BOLD, 20));
        btnAccount.setBounds(25, 360, 250, 75);
        sideUI.add(btnAccount);

        mainUI = new JPanel();
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);

        title = new JLabel("ELECTION VOTING");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(35, 20, 450, 45);
        mainUI.add(title);

        statusLabel = new JLabel("Checking election status...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setBounds(35, 75, 470, 35);
        mainUI.add(statusLabel);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(535, 25, 120, 35);
        mainUI.add(refreshButton);

        candidatePanel = new JPanel();
        candidatePanel.setLayout(null);

        scrollPane = new JScrollPane(candidatePanel);
        scrollPane.setBounds(25, 125, 650, 425);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainUI.add(scrollPane);

        getContentPane().add(sideUI);
        getContentPane().add(mainUI);

        //Add to ActionListener
        btnHome.addActionListener(this);
        btnVote.addActionListener(this);
        btnAccount.addActionListener(this);
        refreshButton.addActionListener(this);

        refreshVotingPage();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHome) {
            dispose();
            new UserInterface();
        } else if (e.getSource() == btnVote) {
            refreshVotingPage();
        } else if (e.getSource() == btnAccount) {
            dispose();
            new AccountStatus();
        } else if (e.getSource() == refreshButton) {
            refreshVotingPage();
        }
    }

    private boolean isVotingStarted() {
        try {
            Connection con = dbconnection.connect();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT is_started FROM election_settings WHERE id = 1");
            ResultSet rs = stmt.executeQuery();
            boolean started = false;
            if (rs.next()) {
                started = rs.getBoolean("is_started");
            }
            con.close();
            return started;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void refreshVotingPage() {

        candidatePanel.removeAll();

        if (!isVotingStarted()) {
            statusLabel.setText("Voting has not started yet.");
            waitingLabel = new JLabel(
                    "Voting hasn't started yet. Please wait for the administrator.",
                    SwingConstants.CENTER);
            waitingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            waitingLabel.setBounds(0, 150, 630, 30);
            candidatePanel.add(waitingLabel);
            candidatePanel.setPreferredSize(new Dimension(630, 400));
        } else {
            statusLabel.setText("Voting is currently OPEN.");

        }

    }

}


