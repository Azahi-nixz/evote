
package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vote extends JFrame implements ActionListener {

    // COLORS
    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);
    private final Color SUCCESS = new Color(0, 130, 0);

    private final String sideBtn[] = {"Home", "Vote", "Account"};

    private final String DB_URL =
            "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private final String loggedInUsername;

    // SIDE UI COMPONENTS
    private JPanel sideUI;
    private JLabel user;
    private JButton sideButtons[];

    // MAIN UI COMPONENTS
    private JPanel mainUI;
    private JPanel candidatePanel;
    private JPanel waitingPanel;
    private JScrollPane scrollPane;

    private JLabel title;
    private JLabel statusLabel;
    private JLabel waitingLabel;
    private JLabel noCandidatesLabel;

    private JButton refreshButton;

    // CONSTRUCTOR
    public Vote(String username) {

        this.loggedInUsername = username;

        setTitle("E-VOTE");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // FIX FRAME LAYOUT
        getContentPane().setLayout(null);

        sidePanel();
        mainPanel();

        getContentPane().add(sideUI);
        getContentPane().add(mainUI);

        refreshVotingPage();

        setVisible(true);
    }

    // SIDE PANEL
    public void sidePanel() {

        sideUI = new JPanel();
        sideUI.setBackground(BRAND);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 300, 600);

        sideButtons = new JButton[sideBtn.length];

        // SIDE BUTTONS
        for (int i = 0; i < sideBtn.length; i++) {

            sideButtons[i] = new JButton(sideBtn[i]);

            sideButtons[i].setBackground(BRAND);
            sideButtons[i].setForeground(ALT_FONTS);
            sideButtons[i].setFont(
                    new Font("Arial", Font.BOLD, 20)
            );

            sideButtons[i].setBounds(
                    25, 160 + (i * 100), 250, 75
            );

            sideButtons[i].addActionListener(this);
            sideButtons[i].setFocusable(false);

            sideButtons[i].setBorder(
                    BorderFactory.createLineBorder(ALT_FONTS, 1)
            );

            sideButtons[i].setBorderPainted(true);

            sideButtons[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    ((JButton) e.getSource()).setBackground(HOVER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ((JButton) e.getSource()).setBackground(BRAND);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    ((JButton) e.getSource()).setBackground(HOVER);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    ((JButton) e.getSource()).setBackground(HOVER);
                }
            });

            sideUI.add(sideButtons[i]);
        }

        // USER NAME
        user = new JLabel(
                "Hello " + loggedInUsername + "!",
                SwingConstants.CENTER
        );

        user.setFont(new Font("Arial", Font.BOLD, 22));
        user.setForeground(ALT_FONTS);
        user.setBounds(0, 0, 300, 120);

        sideUI.add(user);
    }

    // MAIN PANEL
    public void mainPanel() {

        mainUI = new JPanel();
        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);

        // MAIN PANEL STARTS AFTER SIDEBAR
        mainUI.setBounds(300, 0, 700, 600);

        // TITLE
        title = new JLabel("ELECTION VOTING");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(BRAND);
        title.setBounds(35, 20, 450, 45);

        mainUI.add(title);

        // VOTING STATUS
        statusLabel = new JLabel("Checking election status...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(HOVER);
        statusLabel.setBounds(35, 75, 470, 35);

        mainUI.add(statusLabel);

        // REFRESH BUTTON
        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(535, 25, 120, 35);
        refreshButton.setBackground(BRAND);
        refreshButton.setForeground(ALT_FONTS);
        refreshButton.setFocusable(false);
        refreshButton.addActionListener(e -> refreshVotingPage());

        mainUI.add(refreshButton);

        // CANDIDATE PANEL
        candidatePanel = new JPanel();
        candidatePanel.setBackground(ALT_FONTS);
        candidatePanel.setLayout(
                new BoxLayout(candidatePanel, BoxLayout.Y_AXIS)
        );

        // WAITING MESSAGE PANEL
        waitingPanel = new JPanel(new BorderLayout());
        waitingPanel.setBackground(ALT_FONTS);
        waitingPanel.setPreferredSize(new Dimension(630, 300));
        waitingPanel.setMaximumSize(new Dimension(630, 300));

        waitingLabel = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "<h2>Voting Hasn't Started Yet</h2>"
                        + "<p>Please wait until the administrator "
                        + "starts the election.</p>"
                        + "</div></html>",
                SwingConstants.CENTER
        );

        waitingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        waitingPanel.add(waitingLabel, BorderLayout.CENTER);

        // NO CANDIDATES MESSAGE
        noCandidatesLabel = new JLabel(
                "No candidates have been added yet.",
                SwingConstants.CENTER
        );

        noCandidatesLabel.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        // SCROLL PANE
        scrollPane = new JScrollPane(candidatePanel);
        scrollPane.setBounds(25, 125, 650, 425);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)
        );

        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainUI.add(scrollPane);
    }

    // CHECK ELECTION STATUS
    private boolean isVotingStarted() throws SQLException {

        String sql =
                "SELECT is_started FROM election_settings WHERE id = 1";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            if (result.next()) {
                return result.getBoolean("is_started");
            }
        }

        return false;
    }

    // REFRESH VOTING PAGE
    private void refreshVotingPage() {

        candidatePanel.removeAll();

        try {

            if (!isVotingStarted()) {

                statusLabel.setText(
                        "Voting has not started yet."
                );

                statusLabel.setForeground(ERROR);

                // SHOW WAITING MESSAGE INSIDE MAIN PANEL
                candidatePanel.add(waitingPanel);

            } else {

                statusLabel.setText("Voting is currently OPEN.");
                statusLabel.setForeground(SUCCESS);

                displayCandidates();
            }

        } catch (SQLException e) {

            statusLabel.setText(
                    "Unable to load election status."
            );

            statusLabel.setForeground(ERROR);

            JLabel errorLabel = new JLabel(
                    "Please check your database connection.",
                    SwingConstants.CENTER
            );

            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            candidatePanel.add(Box.createVerticalStrut(30));
            candidatePanel.add(errorLabel);

            e.printStackTrace();
        }

        candidatePanel.revalidate();
        candidatePanel.repaint();
    }

    // DISPLAY CANDIDATES
    private void displayCandidates() throws SQLException {

        String sql =
                "SELECT id, name, party, position FROM candidates";

        List<Integer> candidateIds = new ArrayList<>();

        // CHECK IF USER HAS ALREADY VOTED
        boolean alreadyVoted = hasAlreadyVoted();

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {

                int candidateId = result.getInt("id");
                String name = result.getString("name");
                String party = result.getString("party");
                String position = result.getString("position");

                candidateIds.add(candidateId);

                // CANDIDATE CARD
                JPanel card = new JPanel();
                card.setLayout(null);
                card.setBackground(ALT_FONTS);

                card.setBorder(
                        BorderFactory.createLineBorder(
                                Color.LIGHT_GRAY, 1
                        )
                );

                card.setMaximumSize(new Dimension(610, 115));
                card.setPreferredSize(new Dimension(610, 115));
                card.setMinimumSize(new Dimension(610, 115));

                // CANDIDATE NAME
                JLabel candidateName = new JLabel(name);

                candidateName.setFont(
                        new Font("Arial", Font.BOLD, 19)
                );

                candidateName.setBounds(15, 10, 390, 28);

                // CANDIDATE PARTY
                JLabel candidateParty = new JLabel(
                        "Party: " + party
                );

                candidateParty.setFont(
                        new Font("Arial", Font.PLAIN, 14)
                );

                candidateParty.setBounds(15, 42, 390, 22);

                // CANDIDATE POSITION
                JLabel candidatePosition = new JLabel(
                        "Position: " + position
                );

                candidatePosition.setFont(
                        new Font("Arial", Font.PLAIN, 14)
                );

                candidatePosition.setBounds(15, 68, 390, 22);

                // VOTE BUTTON
                JButton voteButton = new JButton("Vote");

                voteButton.setBounds(455, 35, 120, 40);
                voteButton.setBackground(BRAND);
                voteButton.setForeground(ALT_FONTS);
                voteButton.setFocusable(false);

                // PREVENT VOTING AGAIN THROUGH THE UI
                voteButton.setEnabled(!alreadyVoted);

                voteButton.addActionListener(e ->
                        castVote(candidateId, voteButton)
                );

                card.add(candidateName);
                card.add(candidateParty);
                card.add(candidatePosition);
                card.add(voteButton);

                card.setAlignmentX(Component.CENTER_ALIGNMENT);

                candidatePanel.add(card);
                candidatePanel.add(Box.createVerticalStrut(10));
            }
        }

        // NO CANDIDATES
        if (candidateIds.isEmpty()) {

            noCandidatesLabel.setAlignmentX(
                    Component.CENTER_ALIGNMENT
            );

            candidatePanel.add(Box.createVerticalStrut(40));
            candidatePanel.add(noCandidatesLabel);
        }

        // UPDATE STATUS IF USER ALREADY VOTED
        if (alreadyVoted) {

            statusLabel.setText("You have already voted.");
            statusLabel.setForeground(HOVER);
        }
    }

    // CHECK IF USER HAS ALREADY VOTED
    private boolean hasAlreadyVoted() throws SQLException {

        String sql =
                "SELECT id FROM votes WHERE username = ? LIMIT 1";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, loggedInUsername);

            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        }
    }

    // CAST VOTE
    private void castVote(int candidateId, JButton voteButton) {

        // QUICK HASHMAP VERIFICATION CHECK
        if (!UserRegistry.isVerified(loggedInUsername)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your account is not verified.",
                    "Verification Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to vote for this candidate?",
                "Confirm Vote",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "INSERT INTO votes (username, candidate_id) "
                        + "SELECT ?, ? "
                        + "FROM election_settings "
                        + "WHERE id = 1 AND is_started = TRUE "
                        + "AND EXISTS ("
                        + "SELECT 1 FROM users "
                        + "WHERE username = ? AND is_verified = TRUE"
                        + ")";

        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, loggedInUsername);
            statement.setInt(2, candidateId);
            statement.setString(3, loggedInUsername);

            int rows = statement.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Your vote has been recorded successfully!",
                        "Vote Submitted",
                        JOptionPane.INFORMATION_MESSAGE
                );

                refreshVotingPage();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Your vote was not recorded. "
                                + "Voting may have closed, your account "
                                + "may not be verified, or you may have "
                                + "already voted.",
                        "Vote Unsuccessful",
                        JOptionPane.WARNING_MESSAGE
                );

                refreshVotingPage();
            }

        } catch (SQLIntegrityConstraintViolationException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "You have already voted.",
                    "Duplicate Vote",
                    JOptionPane.WARNING_MESSAGE
            );

            refreshVotingPage();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to submit your vote. Please try again.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // SIDEBAR BUTTON ACTIONS
    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();
        String selected = button.getText();

        switch (selected) {

            case "Home":
                dispose();
                new UserInterface();
                break;

            case "Vote":
                refreshVotingPage();
                break;

            case "Account":
                dispose();
                new AccountStatus();
                break;
        }
    }
}
