package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vote extends JFrame implements ActionListener {

    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);
    private final Color SUCCESS = new Color(0, 130, 0);

    private final String sideBtn[] = {"Home", "Vote", "Account"};
    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";
    private final String loggedInUsername;

    private JPanel sideUI;
    private JLabel user;
    private JButton sideButtons[];

    private JPanel mainUI;
    private JPanel candidatePanel;
    private JPanel waitingPanel;
    private JScrollPane scrollPane;
    private JLabel title, statusLabel, waitingLabel, noCandidatesLabel;
    private JButton refreshButton;

    public Vote(String username) {

        this.loggedInUsername = username;

        setTitle("E-VOTE");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);

        buildSidePanel();
        buildMainPanel();

        getContentPane().add(sideUI);
        getContentPane().add(mainUI);

        refreshVotingPage();
        setVisible(true);
    }

    private void buildSidePanel() {

        sideUI = new JPanel();
        sideUI.setBackground(BRAND);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 300, 600);

        sideButtons = new JButton[sideBtn.length];

        for (int i = 0; i < sideBtn.length; i++) {
            sideButtons[i] = new JButton(sideBtn[i]);
            sideButtons[i].setBackground(BRAND);
            sideButtons[i].setForeground(ALT_FONTS);
            sideButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            sideButtons[i].setBounds(25, 160 + (i * 100), 250, 75);
            sideButtons[i].setFocusable(false);
            sideButtons[i].setBorder(BorderFactory.createLineBorder(ALT_FONTS, 1));
            sideButtons[i].setBorderPainted(true);
            sideButtons[i].addActionListener(this);
            sideButtons[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { ((JButton) e.getSource()).setBackground(HOVER); }
                public void mouseExited(MouseEvent e) { ((JButton) e.getSource()).setBackground(BRAND); }
                public void mousePressed(MouseEvent e) { ((JButton) e.getSource()).setBackground(HOVER); }
                public void mouseReleased(MouseEvent e) { ((JButton) e.getSource()).setBackground(HOVER); }
            });
            sideUI.add(sideButtons[i]);
        }

        user = new JLabel("Hello " + loggedInUsername + "!", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 22));
        user.setForeground(ALT_FONTS);
        user.setBounds(0, 0, 300, 120);
        sideUI.add(user);
    }

    private void buildMainPanel() {

        mainUI = new JPanel();
        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);

        title = new JLabel("ELECTION VOTING");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(BRAND);
        title.setBounds(35, 20, 450, 45);
        mainUI.add(title);

        statusLabel = new JLabel("Checking election status...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(HOVER);
        statusLabel.setBounds(35, 75, 470, 35);
        mainUI.add(statusLabel);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(535, 25, 120, 35);
        refreshButton.setBackground(BRAND);
        refreshButton.setForeground(ALT_FONTS);
        refreshButton.setFocusable(false);
        refreshButton.addActionListener(e -> refreshVotingPage());
        mainUI.add(refreshButton);

        candidatePanel = new JPanel();
        candidatePanel.setBackground(ALT_FONTS);
        candidatePanel.setLayout(new BoxLayout(candidatePanel, BoxLayout.Y_AXIS));

        waitingPanel = new JPanel(new BorderLayout());
        waitingPanel.setBackground(ALT_FONTS);
        waitingPanel.setPreferredSize(new Dimension(630, 300));
        waitingPanel.setMaximumSize(new Dimension(630, 300));

        waitingLabel = new JLabel(
                "Voting hasn't started yet. Please wait for the administrator.",
                SwingConstants.CENTER);
        waitingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        waitingPanel.add(waitingLabel, BorderLayout.CENTER);

        noCandidatesLabel = new JLabel("No candidates have been added yet.", SwingConstants.CENTER);
        noCandidatesLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        scrollPane = new JScrollPane(candidatePanel);
        scrollPane.setBounds(25, 125, 650, 425);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainUI.add(scrollPane);
    }

    private boolean isVotingStarted() throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT is_started FROM election_settings WHERE id = 1");
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getBoolean("is_started");
        }
    }

    private void refreshVotingPage() {

        candidatePanel.removeAll();

        try {
            if (!isVotingStarted()) {
                statusLabel.setText("Voting has not started yet.");
                statusLabel.setForeground(ERROR);
                candidatePanel.add(waitingPanel);
            } else {
                statusLabel.setText("Voting is currently OPEN.");
                statusLabel.setForeground(SUCCESS);
                displayCandidates();
            }
        } catch (SQLException e) {
            statusLabel.setText("Unable to load election status.");
            statusLabel.setForeground(ERROR);
            JLabel err = new JLabel("Please check your database connection.", SwingConstants.CENTER);
            err.setAlignmentX(Component.CENTER_ALIGNMENT);
            candidatePanel.add(Box.createVerticalStrut(30));
            candidatePanel.add(err);
            e.printStackTrace();
        }

        candidatePanel.revalidate();
        candidatePanel.repaint();
    }

    private void displayCandidates() throws SQLException {

        List<Integer> ids = new ArrayList<>();
        boolean alreadyVoted = hasAlreadyVoted();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT id, name, party, position FROM candidates");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int candidateId = rs.getInt("id");
                String cName = rs.getString("name");
                String party = rs.getString("party");
                String position = rs.getString("position");

                ids.add(candidateId);

                JPanel card = new JPanel();
                card.setLayout(null);
                card.setBackground(ALT_FONTS);
                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                card.setMaximumSize(new Dimension(610, 115));
                card.setPreferredSize(new Dimension(610, 115));
                card.setMinimumSize(new Dimension(610, 115));

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
                voteBtn.setBackground(BRAND);
                voteBtn.setForeground(ALT_FONTS);
                voteBtn.setFocusable(false);
                voteBtn.setEnabled(!alreadyVoted);
                voteBtn.addActionListener(ev -> castVote(candidateId, voteBtn));

                card.add(lblName);
                card.add(lblParty);
                card.add(lblPosition);
                card.add(voteBtn);
                card.setAlignmentX(Component.CENTER_ALIGNMENT);

                candidatePanel.add(card);
                candidatePanel.add(Box.createVerticalStrut(10));
            }
        }

        if (ids.isEmpty()) {
            noCandidatesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            candidatePanel.add(Box.createVerticalStrut(40));
            candidatePanel.add(noCandidatesLabel);
        }

        if (alreadyVoted) {
            statusLabel.setText("You have already voted.");
            statusLabel.setForeground(HOVER);
        }
    }

    private boolean hasAlreadyVoted() throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT id FROM votes WHERE username = ? LIMIT 1")) {
            stmt.setString(1, loggedInUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void castVote(int candidateId, JButton voteBtn) {

        if (!UserRegistry.isVerified(loggedInUsername)) {
            JOptionPane.showMessageDialog(this,
                    "Your account is not verified.",
                    "Verification Required",
                    JOptionPane.WARNING_MESSAGE);
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

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, loggedInUsername);
            stmt.setInt(2, candidateId);
            stmt.setString(3, loggedInUsername);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                        "Your vote has been recorded successfully!",
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
            JOptionPane.showMessageDialog(this,
                    "Unable to submit your vote. Please try again.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (((JButton) e.getSource()).getText()) {
            case "Home": dispose(); new UserInterface(); break;
            case "Vote": refreshVotingPage(); break;
            case "Account": dispose(); new AccountStatus(); break;
        }
    }
}
