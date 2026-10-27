//package Pages;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class gaboresults extends JFrame implements ActionListener {
//
//    private final Color BAR_COLOR = new Color(50, 100, 200);
//
//    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
//    private final String DB_USER = "root";
//    private final String DB_PASSWORD = "";
//
//    private JPanel sideUI, mainUI, resultsPanel;
//    private JLabel user, title, totalVotesLabel, winnerLabel, noCandidatesLabel;
//    private JButton btnDashboard, btnCandidates, btnElection, btnLogout, refreshButton;
//    private JScrollPane scrollPane;
//
//    public gaboresults() {
//
//        setTitle("E-Vote Admin - Results");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(1000, 600);
//setResizable(false);
//        setLayout(null);
//setLocationRelativeTo(null);
//
//        sideUI = new JPanel();
//sideUI.setBackground(Color.BLACK);
//        sideUI.setLayout(null);
//        sideUI.setBounds(0, 0, 300, 600);
//
//        user = new JLabel("Admin Panel", SwingConstants.CENTER);
//        user.setFont(new Font("Arial", Font.BOLD, 24));
//user.setForeground(Color.WHITE);
//        user.setBounds(0, 0, 300, 80);
//        sideUI.add(user);
//
//        btnDashboard = new JButton("Dashboard");
//        btnDashboard.setFont(new Font("Arial", Font.BOLD, 20));
//        btnDashboard.setBounds(25, 100, 250, 75);
//        sideUI.add(btnDashboard);
//
//        btnCandidates = new JButton("Candidates");
//        btnCandidates.setFont(new Font("Arial", Font.BOLD, 20));
//        btnCandidates.setBounds(25, 200, 250, 75);
//        sideUI.add(btnCandidates);
//
//        btnElection = new JButton("Election");
//        btnElection.setFont(new Font("Arial", Font.BOLD, 20));
//        btnElection.setBounds(25, 300, 250, 75);
//        sideUI.add(btnElection);
//
//        btnLogout = new JButton("Logout");
//        btnLogout.setFont(new Font("Arial", Font.BOLD, 20));
//        btnLogout.setBounds(25, 400, 250, 75);
//        sideUI.add(btnLogout);
//
//        add(sideUI);
//
//        mainUI = new JPanel();
//        mainUI.setLayout(null);
//        mainUI.setBounds(300, 0, 700, 600);
//        add(mainUI);
//
//        title = new JLabel("Election Results");
//        title.setFont(new Font("Arial", Font.BOLD, 28));
//        title.setBounds(35, 20, 450, 35);
//        mainUI.add(title);
//
//        refreshButton = new JButton("Refresh");
//        refreshButton.setBounds(535, 25, 120, 35);
//        mainUI.add(refreshButton);
//
//        totalVotesLabel = new JLabel("Total Votes: 0");
//        totalVotesLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        totalVotesLabel.setBounds(35, 65, 300, 30);
//        mainUI.add(totalVotesLabel);
//
//        winnerLabel = new JLabel("");
//        winnerLabel.setFont(new Font("Arial", Font.BOLD, 18));
//        winnerLabel.setBounds(35, 100, 630, 35);
//        mainUI.add(winnerLabel);
//
//        resultsPanel = new JPanel();
//        resultsPanel.setLayout(null);
//
//        scrollPane = new JScrollPane(resultsPanel);
//        scrollPane.setBounds(25, 150, 650, 420);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//        mainUI.add(scrollPane);
//
//        loadResults();
//
//        //Add to ActionListener
//        btnDashboard.addActionListener(this);
//        btnCandidates.addActionListener(this);
//        btnElection.addActionListener(this);
//        btnLogout.addActionListener(this);
//        refreshButton.addActionListener(this);
//
//        setVisible(true);
//    }
//
//    private void loadResults() {
//
//        resultsPanel.removeAll();
//
//        String sql = "SELECT c.name, c.party, COUNT(v.id) AS vote_count "
//                + "FROM candidates c LEFT JOIN votes v ON v.candidate_id = c.id "
//                + "GROUP BY c.id, c.name, c.party ORDER BY vote_count DESC";
//
//        int totalVotes = 0;
//        int maxVotes = 0;
//        String winnerName = "";
//        String winnerParty = "";
//        List<Object[]> results = new ArrayList<>();
//
//        try {
//            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//            PreparedStatement stmt = con.prepareStatement(sql);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String party = rs.getString("party");
//                int votes = rs.getInt("vote_count");
//                results.add(new Object[]{name, party, votes});
//                totalVotes += votes;
//                if (votes > maxVotes) {
//                    maxVotes = votes;
//                    winnerName = name;
//                    winnerParty = party;
//                }
//            }
//            con.close();
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Unable to load results. Check database connection.",
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//            return;
//        }
//
//        totalVotesLabel.setText("Total Votes: " + totalVotes);
//
//        if (totalVotes == 0) {
//            winnerLabel.setText("No votes have been cast yet.");
//        } else {
//            winnerLabel.setText("Winner: " + winnerName + " - " + winnerParty + " (" + maxVotes + " votes)");
//        }
//
//        int cardH = 110;
//        int gap = 10;
//        int y = 10;
//
//        if (results.isEmpty()) {
//            noCandidatesLabel = new JLabel("No candidates in the election.", SwingConstants.CENTER);
//            noCandidatesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
//            noCandidatesLabel.setBounds(0, 150, 630, 30);
//            resultsPanel.add(noCandidatesLabel);
//            y = 400;
//        } else {
//            for (Object[] row : results) {
//                String name = (String) row[0];
//                String party = (String) row[1];
//                int votes = (int) row[2];
//                double pct = 0;
//                if (totalVotes > 0) {
//                    pct = (double) votes / totalVotes * 100;
//                }
//
//                JPanel card = new JPanel();
//                card.setLayout(null);
//                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
//                card.setBounds(10, y, 615, cardH);
//
//                JLabel lblName = new JLabel(name);
//                lblName.setFont(new Font("Arial", Font.BOLD, 19));
//                lblName.setBounds(15, 10, 350, 28);
//                card.add(lblName);
//
//                JLabel lblParty = new JLabel("Party: " + party);
//                lblParty.setFont(new Font("Arial", Font.PLAIN, 14));
//                lblParty.setBounds(15, 38, 350, 22);
//                card.add(lblParty);
//
//                JLabel lblVotes = new JLabel(votes + " votes", SwingConstants.RIGHT);
//                lblVotes.setFont(new Font("Arial", Font.BOLD, 16));
//                lblVotes.setBounds(440, 10, 150, 28);
//                card.add(lblVotes);
//
//                JLabel lblPct = new JLabel(String.format("%.1f%%", pct), SwingConstants.RIGHT);
//                lblPct.setFont(new Font("Arial", Font.PLAIN, 14));
//                lblPct.setBounds(440, 38, 150, 22);
//                card.add(lblPct);
//
//                JPanel barContainer = new JPanel();
//                barContainer.setLayout(null);
//                barContainer.setBackground(new Color(230, 230, 230));
//                barContainer.setBounds(15, 70, 575, 25);
//
//                int barWidth = 0;
//                if (totalVotes > 0) {
//                    barWidth = (int) ((double) votes / totalVotes * 575);
//                }
//                if (barWidth > 0) {
//                    JPanel barFill = new JPanel();
//                    barFill.setLayout(null);
//                    barFill.setBackground(BAR_COLOR);
//                    barFill.setBounds(0, 0, barWidth, 25);
//                    barContainer.add(barFill);
//                }
//
//                card.add(barContainer);
//                resultsPanel.add(card);
//                y += cardH + gap;
//            }
//        }
//
//        resultsPanel.setPreferredSize(new Dimension(630, y + 10));
//        resultsPanel.revalidate();
//        resultsPanel.repaint();
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnDashboard) {
//            dispose();
//            new AdminPanel();
//        } else if (e.getSource() == btnCandidates) {
//            dispose();
//            new AdminPanel();
//        } else if (e.getSource() == btnElection) {
//            dispose();
//            new AdminPanel();
//        } else if (e.getSource() == btnLogout) {
//            dispose();
//            UserRegistry.currentUser = null;
//            new LogIn();
//        } else if (e.getSource() == refreshButton) {
//            loadResults();
//        }
//    }
//}
