package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame implements ActionListener {

    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);
    private final Color SUCCESS = new Color(0, 130, 0);

    private final String sideBtn[] = {"Dashboard", "Candidates", "Election", "Logout"};
    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JPanel sideUI;
    private JLabel user;

    private JPanel mainUI;

    private JPanel dashboardPanel;
    private JLabel dashboardTitle;
    private JLabel pendingCardTitle, pendingCardValue;
    private JLabel approvedCardTitle, approvedCardValue;
    private JLabel electionCardTitle, electionCardValue;
    private JButton goToCandidatesBtn, goToElectionBtn;

    private JPanel candidatePanel;
    private JLabel candidateTitle, pendingSectionLabel, approvedSectionLabel;
    private DefaultListModel<String> pendingListModel, approvedListModel;
    private JList<String> pendingJList, approvedJList;
    private JScrollPane pendingScrollPane, approvedScrollPane;
    private JButton approveBtn, rejectBtn;

    private JPanel electionPanel;
    private JLabel electionTitle, electionStatusLabel, voteCountLabel, candidateCountLabel;
    private JButton startBtn, stopBtn, viewResultsBtn;

    private void buildSidePanel() {

        sideUI = new JPanel();
        sideUI.setBackground(BRAND);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 300, 600);

        for (int i = 0; i < sideBtn.length; i++) {
            JButton button = new JButton(sideBtn[i]);
            button.setBackground(BRAND);
            button.setForeground(ALT_FONTS);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBounds(25, 100 + (i * 100), 250, 75);
            button.setFocusable(false);
            button.setBorder(BorderFactory.createLineBorder(ALT_FONTS, 1));
            button.setBorderPainted(true);
            button.addActionListener(this);
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { button.setBackground(HOVER); }
                public void mouseExited(MouseEvent e) { button.setBackground(BRAND); }
                public void mousePressed(MouseEvent e) { button.setBackground(HOVER); }
                public void mouseReleased(MouseEvent e) { button.setBackground(HOVER); }
            });
            sideUI.add(button);
        }

        user = new JLabel("Admin Panel", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 24));
        user.setForeground(ALT_FONTS);
        user.setBounds(0, 0, 300, 80);
        sideUI.add(user);
    }

    private JPanel buildStatCard(int x, int y, String labelText) {
        JPanel card = new JPanel();
        card.setBackground(ALT_FONTS);
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        card.setBounds(x, y, 190, 125);
        return card;
    }

    private void buildDashboardPanel() {

        dashboardPanel = new JPanel();
        dashboardPanel.setBackground(ALT_FONTS);
        dashboardPanel.setLayout(null);
        dashboardPanel.setBounds(0, 0, 700, 600);

        dashboardTitle = new JLabel("Admin Dashboard");
        dashboardTitle.setFont(new Font("Arial", Font.BOLD, 32));
        dashboardTitle.setForeground(BRAND);
        dashboardTitle.setBounds(40, 30, 500, 45);
        dashboardPanel.add(dashboardTitle);

        JPanel pendingCard = buildStatCard(40, 100, "Pending");
        pendingCardTitle = new JLabel("Pending", SwingConstants.CENTER);
        pendingCardTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        pendingCardTitle.setBounds(0, 15, 190, 30);
        pendingCardValue = new JLabel("0", SwingConstants.CENTER);
        pendingCardValue.setFont(new Font("Arial", Font.BOLD, 32));
        pendingCardValue.setBounds(0, 50, 190, 50);
        pendingCard.add(pendingCardTitle);
        pendingCard.add(pendingCardValue);
        dashboardPanel.add(pendingCard);

        JPanel approvedCard = buildStatCard(255, 100, "Approved");
        approvedCardTitle = new JLabel("Approved", SwingConstants.CENTER);
        approvedCardTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        approvedCardTitle.setBounds(0, 15, 190, 30);
        approvedCardValue = new JLabel("0", SwingConstants.CENTER);
        approvedCardValue.setFont(new Font("Arial", Font.BOLD, 32));
        approvedCardValue.setBounds(0, 50, 190, 50);
        approvedCard.add(approvedCardTitle);
        approvedCard.add(approvedCardValue);
        dashboardPanel.add(approvedCard);

        JPanel electionCard = buildStatCard(470, 100, "Election");
        electionCardTitle = new JLabel("Election", SwingConstants.CENTER);
        electionCardTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        electionCardTitle.setBounds(0, 15, 190, 30);
        electionCardValue = new JLabel("CLOSE", SwingConstants.CENTER);
        electionCardValue.setFont(new Font("Arial", Font.BOLD, 28));
        electionCardValue.setBounds(0, 50, 190, 50);
        electionCard.add(electionCardTitle);
        electionCard.add(electionCardValue);
        dashboardPanel.add(electionCard);

        JLabel quickActions = new JLabel("Quick Actions");
        quickActions.setFont(new Font("Arial", Font.BOLD, 22));
        quickActions.setForeground(BRAND);
        quickActions.setBounds(40, 260, 300, 30);
        dashboardPanel.add(quickActions);

        goToCandidatesBtn = new JButton("Manage Candidates");
        goToCandidatesBtn.setFont(new Font("Arial", Font.BOLD, 16));
        goToCandidatesBtn.setBackground(BRAND);
        goToCandidatesBtn.setForeground(ALT_FONTS);
        goToCandidatesBtn.setFocusable(false);
        goToCandidatesBtn.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        goToCandidatesBtn.setBounds(40, 310, 280, 55);
        dashboardPanel.add(goToCandidatesBtn);

        goToElectionBtn = new JButton("Election Control");
        goToElectionBtn.setFont(new Font("Arial", Font.BOLD, 16));
        goToElectionBtn.setBackground(BRAND);
        goToElectionBtn.setForeground(ALT_FONTS);
        goToElectionBtn.setFocusable(false);
        goToElectionBtn.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        goToElectionBtn.setBounds(350, 310, 280, 55);
        dashboardPanel.add(goToElectionBtn);

        mainUI.add(dashboardPanel);
    }

    private void buildCandidatePanel() {

        candidatePanel = new JPanel();
        candidatePanel.setBackground(ALT_FONTS);
        candidatePanel.setLayout(null);
        candidatePanel.setBounds(0, 0, 700, 600);

        candidateTitle = new JLabel("Manage Candidates");
        candidateTitle.setFont(new Font("Arial", Font.BOLD, 28));
        candidateTitle.setForeground(BRAND);
        candidateTitle.setBounds(35, 15, 450, 35);
        candidatePanel.add(candidateTitle);

        pendingSectionLabel = new JLabel("Pending Applications");
        pendingSectionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pendingSectionLabel.setForeground(BRAND);
        pendingSectionLabel.setBounds(35, 60, 300, 25);
        candidatePanel.add(pendingSectionLabel);

        pendingListModel = new DefaultListModel<>();
        pendingJList = new JList<>(pendingListModel);
        pendingJList.setFont(new Font("Arial", Font.PLAIN, 15));
        pendingJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pendingScrollPane = new JScrollPane(pendingJList);
        pendingScrollPane.setBounds(35, 90, 630, 170);
        pendingScrollPane.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        pendingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        candidatePanel.add(pendingScrollPane);

        approveBtn = new JButton("Approve");
        approveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        approveBtn.setBackground(BRAND);
        approveBtn.setForeground(ALT_FONTS);
        approveBtn.setFocusable(false);
        approveBtn.setBorderPainted(false);
        approveBtn.setBounds(335, 275, 150, 40);
        candidatePanel.add(approveBtn);

        rejectBtn = new JButton("Reject");
        rejectBtn.setFont(new Font("Arial", Font.BOLD, 14));
        rejectBtn.setBackground(Color.WHITE);
        rejectBtn.setForeground(BRAND);
        rejectBtn.setFocusable(false);
        rejectBtn.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        rejectBtn.setBounds(495, 275, 150, 40);
        candidatePanel.add(rejectBtn);

        approvedSectionLabel = new JLabel("Approved Candidates");
        approvedSectionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        approvedSectionLabel.setForeground(BRAND);
        approvedSectionLabel.setBounds(35, 335, 300, 25);
        candidatePanel.add(approvedSectionLabel);

        approvedListModel = new DefaultListModel<>();
        approvedJList = new JList<>(approvedListModel);
        approvedJList.setFont(new Font("Arial", Font.PLAIN, 15));
        approvedJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        approvedScrollPane = new JScrollPane(approvedJList);
        approvedScrollPane.setBounds(35, 365, 630, 185);
        approvedScrollPane.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        approvedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        candidatePanel.add(approvedScrollPane);

        mainUI.add(candidatePanel);
    }

    private void buildElectionPanel() {

        electionPanel = new JPanel();
        electionPanel.setBackground(ALT_FONTS);
        electionPanel.setLayout(null);
        electionPanel.setBounds(0, 0, 700, 600);

        electionTitle = new JLabel("Election Control");
        electionTitle.setFont(new Font("Arial", Font.BOLD, 28));
        electionTitle.setForeground(BRAND);
        electionTitle.setBounds(35, 30, 450, 35);
        electionPanel.add(electionTitle);

        electionStatusLabel = new JLabel("Checking election status...");
        electionStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        electionStatusLabel.setForeground(HOVER);
        electionStatusLabel.setBounds(35, 80, 400, 35);
        electionPanel.add(electionStatusLabel);

        startBtn = new JButton("Start Election");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        startBtn.setBackground(BRAND);
        startBtn.setForeground(ALT_FONTS);
        startBtn.setFocusable(false);
        startBtn.setBorderPainted(false);
        startBtn.setBounds(100, 145, 350, 55);
        electionPanel.add(startBtn);

        stopBtn = new JButton("Stop Election");
        stopBtn.setFont(new Font("Arial", Font.BOLD, 16));
        stopBtn.setBackground(Color.WHITE);
        stopBtn.setForeground(BRAND);
        stopBtn.setFocusable(false);
        stopBtn.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        stopBtn.setBounds(100, 215, 350, 55);
        electionPanel.add(stopBtn);

        viewResultsBtn = new JButton("View Results");
        viewResultsBtn.setFont(new Font("Arial", Font.BOLD, 16));
        viewResultsBtn.setBackground(BRAND);
        viewResultsBtn.setForeground(ALT_FONTS);
        viewResultsBtn.setFocusable(false);
        viewResultsBtn.setBorderPainted(false);
        viewResultsBtn.setBounds(100, 285, 350, 45);
        electionPanel.add(viewResultsBtn);

        JPanel separator = new JPanel();
        separator.setBackground(BRAND);
        separator.setBounds(35, 350, 630, 1);
        electionPanel.add(separator);

        JLabel statsTitle = new JLabel("Election Statistics");
        statsTitle.setFont(new Font("Arial", Font.BOLD, 22));
        statsTitle.setForeground(BRAND);
        statsTitle.setBounds(35, 370, 400, 35);
        electionPanel.add(statsTitle);

        voteCountLabel = new JLabel("Total Votes: 0");
        voteCountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        voteCountLabel.setForeground(BRAND);
        voteCountLabel.setBounds(35, 420, 300, 30);
        electionPanel.add(voteCountLabel);

        candidateCountLabel = new JLabel("Total Candidates: 0");
        candidateCountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        candidateCountLabel.setForeground(BRAND);
        candidateCountLabel.setBounds(35, 455, 300, 30);
        electionPanel.add(candidateCountLabel);

        mainUI.add(electionPanel);
    }

    public void showPanel(String panelName) {
        dashboardPanel.setVisible(false);
        candidatePanel.setVisible(false);
        electionPanel.setVisible(false);

        switch (panelName) {
            case "Dashboard": dashboardPanel.setVisible(true); refreshDashboard(); break;
            case "Candidates": candidatePanel.setVisible(true); refreshCandidateList(); break;
            case "Election": electionPanel.setVisible(true); refreshElectionStatus(); break;
        }
    }

    private void refreshDashboard() {
        CandidateRegistry.loadPendingFromDB();
        pendingCardValue.setText(String.valueOf(CandidateRegistry.getPendingCount()));

        CandidateRegistry.loadApprovedFromDB();
        approvedCardValue.setText(String.valueOf(CandidateRegistry.getApprovedCount()));

        try {
            boolean started = isVotingStarted();
            electionCardValue.setText(started ? "OPEN" : "CLOSE");
            electionCardValue.setForeground(started ? SUCCESS : ERROR);
        } catch (SQLException e) {
            electionCardValue.setText("ERROR");
            electionCardValue.setForeground(ERROR);
            e.printStackTrace();
        }
    }

    private void refreshCandidateList() {
        CandidateRegistry.loadPendingFromDB();
        pendingListModel.clear();
        for (Candidate c : CandidateRegistry.pendingCandidates)
            pendingListModel.addElement(c.getName() + " - " + c.getParty());

        CandidateRegistry.loadApprovedFromDB();
        approvedListModel.clear();
        for (Candidate c : CandidateRegistry.approvedCandidates)
            approvedListModel.addElement(c.getName() + " - " + c.getParty());

        if (pendingListModel.size() > 0)
            pendingJList.setSelectedIndex(0);
    }

    private void refreshElectionStatus() {
        try {
            boolean started = isVotingStarted();
            electionStatusLabel.setText(started ? "Election is currently OPEN." : "Election is currently CLOSED.");
            electionStatusLabel.setForeground(started ? SUCCESS : ERROR);
            voteCountLabel.setText("Total Votes: " + getVoteCount());
            candidateCountLabel.setText("Total Candidates: " + getDBCandidateCount());
        } catch (SQLException e) {
            electionStatusLabel.setText("Unable to load election status.");
            electionStatusLabel.setForeground(ERROR);
            e.printStackTrace();
        }
    }

    private boolean isVotingStarted() throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(
                     "SELECT is_started FROM election_settings WHERE id = 1");
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() && rs.getBoolean("is_started");
        }
    }

    private void setElectionState(boolean start) throws SQLException {
        String sql = "UPDATE election_settings SET is_started = " + start + " WHERE id = 1";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    private int getVoteCount() throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM votes");
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("total") : 0;
        }
    }

    private int getDBCandidateCount() throws SQLException {
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS total FROM candidates");
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt("total") : 0;
        }
    }

    public AdminPanel() {

        setTitle("E-Vote Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setBackground(ALT_FONTS);
        setLayout(null);
        setLocationRelativeTo(null);

        buildSidePanel();
        add(sideUI);

        mainUI = new JPanel();
        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);
        add(mainUI);

        buildDashboardPanel();
        buildCandidatePanel();
        buildElectionPanel();

        showPanel("Dashboard");

        // Button listeners
        goToCandidatesBtn.addActionListener(this);
        goToElectionBtn.addActionListener(this);
        approveBtn.addActionListener(this);
        rejectBtn.addActionListener(this);
        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);
        viewResultsBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Dashboard": showPanel("Dashboard"); break;
            case "Candidates": showPanel("Candidates"); break;
            case "Election": showPanel("Election"); break;
            case "Manage Candidates": showPanel("Candidates"); break;
            case "Election Control": showPanel("Election"); break;

            case "Logout":
                dispose();
                UserRegistry.currentUser = null;
                new LogIn();
                break;

            case "Approve": {
                int index = pendingJList.getSelectedIndex();
                if (index < 0) {
                    JOptionPane.showMessageDialog(this, "Please select a candidate to approve.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Candidate candidate = CandidateRegistry.pendingCandidates.get(index);
                int choice = JOptionPane.showConfirmDialog(this,
                        "Approve " + candidate.getName() + " - " + candidate.getParty() + "?",
                        "Confirm Approval", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) return;

                if (CandidateRegistry.approveCandidate(candidate)) {
                    refreshCandidateList();
                    JOptionPane.showMessageDialog(this, "Candidate approved successfully.",
                            "Approved", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to approve. Check database connection.",
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }

            case "Reject": {
                int index = pendingJList.getSelectedIndex();
                if (index < 0) {
                    JOptionPane.showMessageDialog(this, "Please select a candidate to reject.",
                            "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Candidate candidate = CandidateRegistry.pendingCandidates.get(index);
                int choice = JOptionPane.showConfirmDialog(this,
                        "Reject " + candidate.getName() + " - " + candidate.getParty() + "?",
                        "Confirm Rejection", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) return;

                if (CandidateRegistry.rejectCandidate(candidate)) {
                    refreshCandidateList();
                    JOptionPane.showMessageDialog(this, "Candidate rejected.",
                            "Rejected", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to reject. Check database connection.",
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }

            case "Start Election":
                try {
                    setElectionState(true);
                    refreshElectionStatus();
                    JOptionPane.showMessageDialog(this, "Election started. Voting is now open.",
                            "Election Started", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to start election. Check database connection.",
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                break;

            case "Stop Election":
                try {
                    setElectionState(false);
                    refreshElectionStatus();
                    JOptionPane.showMessageDialog(this, "Election stopped. Voting is now closed.",
                            "Election Stopped", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to stop election. Check database connection.",
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                break;

            case "View Results":
                dispose();
                new gaboresults();
                break;
        }
    }
}
