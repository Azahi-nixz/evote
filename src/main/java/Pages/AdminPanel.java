package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame implements ActionListener {

    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JPanel sideUI, mainUI;
    private JLabel user;
    private JButton btnDashboard, btnCandidates, btnElection, btnLogout;

    private JPanel dashboardPanel;
    private JLabel dashboardTitle;
    private JPanel pendingCard, approvedCard, electionCard;
    private JLabel pendingCardTitle, pendingCardValue;
    private JLabel approvedCardTitle, approvedCardValue;
    private JLabel electionCardTitle, electionCardValue;
    private JLabel quickActions;
    private JButton goToCandidatesBtn, goToElectionBtn;

    private JPanel candidatePanel;
    private JLabel candidateTitle, pendingSectionLabel, approvedSectionLabel;
    private DefaultListModel<String> pendingListModel, approvedListModel;
    private JList<String> pendingJList, approvedJList;
    private JScrollPane pendingScrollPane, approvedScrollPane;
    private JButton approveBtn, rejectBtn;

    private JPanel electionPanel;
    private JLabel electionTitle, electionStatusLabel, voteCountLabel, candidateCountLabel;
    private JPanel separator;
    private JLabel statsTitle;
    private JButton startBtn, stopBtn, viewResultsBtn;

    public AdminPanel() {

        setTitle("E-Vote Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
setResizable(false);
        setLayout(null);
setLocationRelativeTo(null);

        sideUI = new JPanel();
sideUI.setBackground(Color.BLACK);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 300, 600);

        user = new JLabel("Admin Panel", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 24));
user.setForeground(Color.WHITE);
        user.setBounds(0, 0, 300, 80);
        sideUI.add(user);

        btnDashboard = new JButton("Dashboard");
        btnDashboard.setFont(new Font("Arial", Font.BOLD, 20));
        btnDashboard.setBounds(25, 100, 250, 75);
        sideUI.add(btnDashboard);

        btnCandidates = new JButton("Candidates");
        btnCandidates.setFont(new Font("Arial", Font.BOLD, 20));
        btnCandidates.setBounds(25, 200, 250, 75);
        sideUI.add(btnCandidates);

        btnElection = new JButton("Election");
        btnElection.setFont(new Font("Arial", Font.BOLD, 20));
        btnElection.setBounds(25, 300, 250, 75);
        sideUI.add(btnElection);

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 20));
        btnLogout.setBounds(25, 400, 250, 75);
        sideUI.add(btnLogout);

        add(sideUI);

        mainUI = new JPanel();
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);
        add(mainUI);

        // Dashboard panel
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(null);
        dashboardPanel.setBounds(0, 0, 700, 600);

        dashboardTitle = new JLabel("Admin Dashboard");
        dashboardTitle.setFont(new Font("Arial", Font.BOLD, 32));
        dashboardTitle.setBounds(40, 30, 500, 45);
        dashboardPanel.add(dashboardTitle);

        pendingCard = new JPanel();
        pendingCard.setLayout(null);
pendingCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pendingCard.setBounds(40, 100, 190, 125);
        pendingCardTitle = new JLabel("Pending", SwingConstants.CENTER);
        pendingCardTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        pendingCardTitle.setBounds(0, 15, 190, 30);
        pendingCardValue = new JLabel("0", SwingConstants.CENTER);
        pendingCardValue.setFont(new Font("Arial", Font.BOLD, 32));
        pendingCardValue.setBounds(0, 50, 190, 50);
        pendingCard.add(pendingCardTitle);
        pendingCard.add(pendingCardValue);
        dashboardPanel.add(pendingCard);

        approvedCard = new JPanel();
        approvedCard.setLayout(null);
approvedCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        approvedCard.setBounds(255, 100, 190, 125);
        approvedCardTitle = new JLabel("Approved", SwingConstants.CENTER);
        approvedCardTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        approvedCardTitle.setBounds(0, 15, 190, 30);
        approvedCardValue = new JLabel("0", SwingConstants.CENTER);
        approvedCardValue.setFont(new Font("Arial", Font.BOLD, 32));
        approvedCardValue.setBounds(0, 50, 190, 50);
        approvedCard.add(approvedCardTitle);
        approvedCard.add(approvedCardValue);
        dashboardPanel.add(approvedCard);

        electionCard = new JPanel();
        electionCard.setLayout(null);
electionCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        electionCard.setBounds(470, 100, 190, 125);
        electionCardTitle = new JLabel("Election", SwingConstants.CENTER);
        electionCardTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        electionCardTitle.setBounds(0, 15, 190, 30);
        electionCardValue = new JLabel("CLOSE", SwingConstants.CENTER);
        electionCardValue.setFont(new Font("Arial", Font.BOLD, 28));
        electionCardValue.setBounds(0, 50, 190, 50);
        electionCard.add(electionCardTitle);
        electionCard.add(electionCardValue);
        dashboardPanel.add(electionCard);

        quickActions = new JLabel("Quick Actions");
        quickActions.setFont(new Font("Arial", Font.BOLD, 22));
        quickActions.setBounds(40, 260, 300, 30);
        dashboardPanel.add(quickActions);

        goToCandidatesBtn = new JButton("Manage Candidates");
        goToCandidatesBtn.setFont(new Font("Arial", Font.BOLD, 16));
        goToCandidatesBtn.setBounds(40, 310, 280, 55);
        dashboardPanel.add(goToCandidatesBtn);

        goToElectionBtn = new JButton("Election Control");
        goToElectionBtn.setFont(new Font("Arial", Font.BOLD, 16));
        goToElectionBtn.setBounds(350, 310, 280, 55);
        dashboardPanel.add(goToElectionBtn);

        mainUI.add(dashboardPanel);

        // Candidate panel
        candidatePanel = new JPanel();
        candidatePanel.setLayout(null);
        candidatePanel.setBounds(0, 0, 700, 600);

        candidateTitle = new JLabel("Manage Candidates");
        candidateTitle.setFont(new Font("Arial", Font.BOLD, 28));
        candidateTitle.setBounds(35, 15, 450, 35);
        candidatePanel.add(candidateTitle);

        pendingSectionLabel = new JLabel("Pending Applications");
        pendingSectionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        pendingSectionLabel.setBounds(35, 60, 300, 25);
        candidatePanel.add(pendingSectionLabel);

        //BE
        pendingListModel = new DefaultListModel<>();
        //FE
        pendingJList = new JList<>(pendingListModel);
        pendingJList.setFont(new Font("Arial", Font.PLAIN, 15));
        pendingJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pendingScrollPane = new JScrollPane(pendingJList);
        pendingScrollPane.setBounds(35, 90, 630, 170);
        pendingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        candidatePanel.add(pendingScrollPane);

        approveBtn = new JButton("Approve");
        approveBtn.setFont(new Font("Arial", Font.BOLD, 14));
        approveBtn.setBounds(335, 275, 150, 40);
        candidatePanel.add(approveBtn);

        rejectBtn = new JButton("Reject");
        rejectBtn.setFont(new Font("Arial", Font.BOLD, 14));
        rejectBtn.setBounds(495, 275, 150, 40);
        candidatePanel.add(rejectBtn);

        approvedSectionLabel = new JLabel("Approved Candidates");
        approvedSectionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        approvedSectionLabel.setBounds(35, 335, 300, 25);
        candidatePanel.add(approvedSectionLabel);

        //BE
        approvedListModel = new DefaultListModel<>();
        //FE
        approvedJList = new JList<>(approvedListModel);
        approvedJList.setFont(new Font("Arial", Font.PLAIN, 15));
        approvedJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        approvedScrollPane = new JScrollPane(approvedJList);
        approvedScrollPane.setBounds(35, 365, 630, 185);
        approvedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        candidatePanel.add(approvedScrollPane);

        mainUI.add(candidatePanel);

        // Election panel
        electionPanel = new JPanel();
        electionPanel.setLayout(null);
        electionPanel.setBounds(0, 0, 700, 600);

        electionTitle = new JLabel("Election Control");
        electionTitle.setFont(new Font("Arial", Font.BOLD, 28));
        electionTitle.setBounds(35, 30, 450, 35);
        electionPanel.add(electionTitle);

        electionStatusLabel = new JLabel("Checking election status...");
        electionStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        electionStatusLabel.setBounds(35, 80, 400, 35);
        electionPanel.add(electionStatusLabel);

        startBtn = new JButton("Start Election");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        startBtn.setBounds(100, 145, 350, 55);
        electionPanel.add(startBtn);

        stopBtn = new JButton("Stop Election");
        stopBtn.setFont(new Font("Arial", Font.BOLD, 16));
        stopBtn.setBounds(100, 215, 350, 55);
        electionPanel.add(stopBtn);

        viewResultsBtn = new JButton("View Results");
        viewResultsBtn.setFont(new Font("Arial", Font.BOLD, 16));
        viewResultsBtn.setBounds(100, 285, 350, 45);
        electionPanel.add(viewResultsBtn);

        separator = new JPanel();
separator.setBackground(Color.BLACK);
        separator.setBounds(35, 350, 630, 1);
        electionPanel.add(separator);

        statsTitle = new JLabel("Election Statistics");
        statsTitle.setFont(new Font("Arial", Font.BOLD, 22));
        statsTitle.setBounds(35, 370, 400, 35);
        electionPanel.add(statsTitle);

        voteCountLabel = new JLabel("Total Votes: 0");
        voteCountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        voteCountLabel.setBounds(35, 420, 300, 30);
        electionPanel.add(voteCountLabel);

        candidateCountLabel = new JLabel("Total Candidates: 0");
        candidateCountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        candidateCountLabel.setBounds(35, 455, 300, 30);
        electionPanel.add(candidateCountLabel);

        mainUI.add(electionPanel);

        showPanel("Dashboard");

        //Add to ActionListener
        btnDashboard.addActionListener(this);
        btnCandidates.addActionListener(this);
        btnElection.addActionListener(this);
        btnLogout.addActionListener(this);
        goToCandidatesBtn.addActionListener(this);
        goToElectionBtn.addActionListener(this);
        approveBtn.addActionListener(this);
        rejectBtn.addActionListener(this);
        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);
        viewResultsBtn.addActionListener(this);

        setVisible(true);
    }

    public void showPanel(String panelName) {
        dashboardPanel.setVisible(false);
        candidatePanel.setVisible(false);
        electionPanel.setVisible(false);
        if (panelName.equals("Dashboard")) {
            dashboardPanel.setVisible(true);
        } else if (panelName.equals("Candidates")) {
            candidatePanel.setVisible(true);
            refreshCandidateList();
        } else if (panelName.equals("Election")) {
            electionPanel.setVisible(true);
            refreshElectionStatus();
        }
    }

//    private void refreshDashboard() {
//        CandidateRegistry.loadPendingFromDB();
//        pendingCardValue.setText(String.valueOf(CandidateRegistry.getPendingCount()));
//        CandidateRegistry.loadApprovedFromDB();
//        approvedCardValue.setText(String.valueOf(CandidateRegistry.getApprovedCount()));
//        try {
//            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            PreparedStatement stmt = con.prepareStatement(
//                    "SELECT is_started FROM election_settings WHERE id = 1");
//            ResultSet rs = stmt.executeQuery();
//            boolean started = false;
//            if (rs.next()) {
//                started = rs.getBoolean("is_started");
//            }
//            con.close();
//            if (started) {
//                electionCardValue.setText("OPEN");
//            } else {
//                electionCardValue.setText("CLOSE");
//            }
//        } catch (SQLException e) {
//            electionCardValue.setText("ERROR");
//            e.printStackTrace();
//        }
//    }

    private void refreshCandidateList() {
        CandidateRegistry.loadPendingFromDB();
        pendingListModel.clear(); //FE
        for (Candidate c : CandidateRegistry.pendingCandidates) { //BE
            pendingListModel.addElement(c.getName() + " - " + c.getParty()); //FE
        }

        CandidateRegistry.loadApprovedFromDB();
        approvedListModel.clear(); //FE
        for (Candidate c : CandidateRegistry.approvedCandidates) { //BE
            approvedListModel.addElement(c.getName() + " - " + c.getParty()); //FE
        }

        if (pendingListModel.size() > 0) {
            pendingJList.setSelectedIndex(0);
        }
    }

    private void refreshElectionStatus() {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT is_started FROM election_settings WHERE id = 1");
            ResultSet rs = stmt.executeQuery();
            boolean started = false;
            if (rs.next()) {
                started = rs.getBoolean("is_started");
            }
            con.close();

            if (started) {
                electionStatusLabel.setText("Election is currently OPEN.");
            } else {
                electionStatusLabel.setText("Election is currently CLOSED.");
            }

            Connection con2 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt2 = con2.prepareStatement("SELECT COUNT(*) AS total FROM votes");
            ResultSet rs2 = stmt2.executeQuery();
            int votes = 0;
            if (rs2.next()) {
                votes = rs2.getInt("total");
            }
            con2.close();
            voteCountLabel.setText("Total Votes: " + votes);

            Connection con3 = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt3 = con3.prepareStatement("SELECT COUNT(*) AS total FROM candidates");
            ResultSet rs3 = stmt3.executeQuery();
            int candidates = 0;
            if (rs3.next()) {
                candidates = rs3.getInt("total");
            }
            candidateCountLabel.setText("Total Candidates: " + candidates);

        } catch (SQLException e) {
            electionStatusLabel.setText("Unable to load election status.");
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDashboard) {
            showPanel("Dashboard");
        } else if (e.getSource() == btnCandidates) {
            showPanel("Candidates");
        } else if (e.getSource() == btnElection) {
            showPanel("Election");
        } else if (e.getSource() == btnLogout) {
            dispose();
            UserRegistry.currentUser = null;
            new LogIn();
        } else if (e.getSource() == goToCandidatesBtn) {
            showPanel("Candidates");
        } else if (e.getSource() == goToElectionBtn) {
            showPanel("Election");
        } else if (e.getSource() == approveBtn) {
            int index = pendingJList.getSelectedIndex();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Please select a candidate to approve.",
                        "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Candidate candidate = CandidateRegistry.pendingCandidates.get(index); //BE
            int choice = JOptionPane.showConfirmDialog(this,
                    "Approve " + candidate.getName() + " - " + candidate.getParty() + "?",
                    "Confirm Approval", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) return;
            if (CandidateRegistry.approveCandidate(candidate)) { //BE
                refreshCandidateList();
                JOptionPane.showMessageDialog(this, "Candidate approved successfully.",
                        "Approved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to approve. Check database connection.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == rejectBtn) {
            int index = pendingJList.getSelectedIndex();
            if (index < 0) {
                JOptionPane.showMessageDialog(this, "Please select a candidate to reject.",
                        "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Candidate candidate = CandidateRegistry.pendingCandidates.get(index); //BE
            int choice = JOptionPane.showConfirmDialog(this,
                    "Reject " + candidate.getName() + " - " + candidate.getParty() + "?",
                    "Confirm Rejection", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) return;
            if (CandidateRegistry.rejectCandidate(candidate)) { //BE
                refreshCandidateList();
                JOptionPane.showMessageDialog(this, "Candidate rejected.",
                        "Rejected", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to reject. Check database connection.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == startBtn) {
            try {
                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = con.prepareStatement(
                        "UPDATE election_settings SET is_started = TRUE WHERE id = 1");
                stmt.executeUpdate();
                refreshElectionStatus();
                JOptionPane.showMessageDialog(this, "Election started. Voting is now open.",
                        "Election Started", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Failed to start election.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (e.getSource() == stopBtn) {
            try {
                Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = con.prepareStatement(
                        "UPDATE election_settings SET is_started = FALSE WHERE id = 1");
                stmt.executeUpdate();
                refreshElectionStatus();
                JOptionPane.showMessageDialog(this, "Election stopped. Voting is now closed.",
                        "Election Stopped", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Failed to stop election.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (e.getSource() == viewResultsBtn) {
            dispose();
           // new gaboresults();
        }
    }
}
