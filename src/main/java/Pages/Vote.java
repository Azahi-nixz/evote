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
        setLocationRelativeTo(null);
        setResizable(false);
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

    private boolean isVotingStarted() throws SQLException {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement stmt = con.prepareStatement(
                "SELECT is_started FROM election_settings WHERE id = 1");
        ResultSet rs = stmt.executeQuery();
        boolean started = false;
        if (rs.next()) {
            started = rs.getBoolean("is_started");
        }
        con.close();
        return started;
    }

    private void refreshVotingPage() {

        candidatePanel.removeAll();

        try {
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
                displayCandidates();
            }
        } catch (SQLException e) {
            statusLabel.setText("Unable to load election status.");
            errLabel = new JLabel("Please check your database connection.", SwingConstants.CENTER);
            errLabel.setBounds(0, 150, 630, 30);
            candidatePanel.add(errLabel);
            candidatePanel.setPreferredSize(new Dimension(630, 400));
            e.printStackTrace();
        }

        candidatePanel.revalidate();
        candidatePanel.repaint();
    }

    private void displayCandidates() throws SQLException {

        List<Integer> ids = new ArrayList<>();
        boolean alreadyVoted = hasAlreadyVoted();
        int cardH = 115;
        int gap = 10;
        int y = 10;

        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement stmt = con.prepareStatement(
                "SELECT id, name, party, position FROM candidates");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int candidateId = rs.getInt("id");
            String cName = rs.getString("name");
            String party = rs.getString("party");
            String position = rs.getString("position");

            ids.add(candidateId);

            JPanel card = new JPanel();
            card.setLayout(null);
card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            card.setBounds(10, y, 615, cardH);

            JLabel lblName = new JLabel(cName);
            lblName.setFont(new Font("Arial", Font.BOLD, 19));
            lblName.setBounds(15, 10, 390, 28);

            JLabel lblParty = new JLabel("Party: " + party);
            lblParty.setFont(new Font("Arial", Font.PLAIN, 14));
            lblParty.setBounds(15, 42, 390, 22);

            JLabel lblPosition = new JLabel("Position: " + position);
            lblPosition.setFont(new Font("Arial", Font.PLAIN, 14));
            lblPosition.setBounds(15, 68, 390, 22);

            JButton voteBtn = new JButton("Vote");
            voteBtn.setBounds(455, 35, 120, 40);
            voteBtn.setEnabled(!alreadyVoted);
            voteBtn.addActionListener(ev -> castVote(candidateId, voteBtn));

            card.add(lblName);
            card.add(lblParty);
            card.add(lblPosition);
            card.add(voteBtn);

            candidatePanel.add(card);
            y += cardH + gap;
        }


        if (ids.isEmpty()) {
            noCandidatesLabel = new JLabel("No candidates have been added yet.", SwingConstants.CENTER);
            noCandidatesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noCandidatesLabel.setBounds(0, 150, 630, 30);
            candidatePanel.add(noCandidatesLabel);
            y = 400;
        }

        candidatePanel.setPreferredSize(new Dimension(630, y + 10));

        if (alreadyVoted) {
            statusLabel.setText("You have already voted.");
        }
    }

    private boolean hasAlreadyVoted() throws SQLException {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        PreparedStatement stmt = con.prepareStatement(
                "SELECT id FROM votes WHERE username = ? LIMIT 1");
        stmt.setString(1, loggedInUsername);
        ResultSet rs = stmt.executeQuery();
        boolean voted = rs.next();
        con.close();
        return voted;
    }

    private void castVote(int candidateId, JButton voteBtn) {

        if (!UserRegistry.isVerified(loggedInUsername)) {
            JOptionPane.showMessageDialog(this, "Your account is not verified.",
                    "Verification Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to vote for this candidate?",
                "Confirm Vote", JOptionPane.YES_NO_OPTION);

        if (choice != JOptionPane.YES_OPTION) return;

        String sql = "INSERT INTO votes (username, candidate_id) "
                + "SELECT ?, ? FROM election_settings "
                + "WHERE id = 1 AND is_started = TRUE "
                + "AND EXISTS (SELECT 1 FROM users WHERE username = ? AND is_verified = TRUE)";

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, loggedInUsername);
            stmt.setInt(2, candidateId);
            stmt.setString(3, loggedInUsername);
            int rows = stmt.executeUpdate();
            con.close();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Your vote has been recorded successfully!",
                        "Vote Submitted", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Your vote was not recorded. Voting may have closed or you already voted.",
                        "Vote Unsuccessful", JOptionPane.WARNING_MESSAGE);
            }
            refreshVotingPage();

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "You have already voted.",
                    "Duplicate Vote", JOptionPane.WARNING_MESSAGE);
            refreshVotingPage();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Unable to submit your vote. Please try again.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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
}
