package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.Map;

public class gaboresults extends JFrame implements ActionListener {

    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    // BE - LinkedList of result rows
    private LinkedList<String[]> resultList;

    private JPanel sideUI, mainUI;
    private JLabel user, title, totalVotesLabel, winnerLabel, noCandidatesLabel;
    private JButton btnDashboard, btnCandidates, btnElection, btnLogout, refreshButton;

    // Results section
    private JPanel resultsPanel;
    private JScrollPane scrollPane;

    // Graph view section
    private JLabel graphTitle;
    private JPanel graphListPanel;
    private JScrollPane graphScrollPane;

    public gaboresults() {

        setTitle("E-Vote Admin - Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 620);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        sideUI = new JPanel();
        sideUI.setBackground(Color.BLACK);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 220, 620);

        user = new JLabel("Admin Panel", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 20));
        user.setForeground(Color.WHITE);
        user.setBounds(0, 0, 220, 70);
        sideUI.add(user);

        btnDashboard = new JButton("Dashboard");
        btnDashboard.setFont(new Font("Arial", Font.BOLD, 16));
        btnDashboard.setBounds(15, 80, 190, 60);
        sideUI.add(btnDashboard);

        btnCandidates = new JButton("Candidates");
        btnCandidates.setFont(new Font("Arial", Font.BOLD, 16));
        btnCandidates.setBounds(15, 160, 190, 60);
        sideUI.add(btnCandidates);

        btnElection = new JButton("Election");
        btnElection.setFont(new Font("Arial", Font.BOLD, 16));
        btnElection.setBounds(15, 240, 190, 60);
        sideUI.add(btnElection);

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogout.setBounds(15, 320, 190, 60);
        sideUI.add(btnLogout);

        add(sideUI);

        mainUI = new JPanel();
        mainUI.setLayout(null);
        mainUI.setBounds(220, 0, 980, 620);
        add(mainUI);

        // ---- Results section (left half) ----
        title = new JLabel("Election Results");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(10, 15, 350, 30);
        mainUI.add(title);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(380, 15, 100, 30);
        mainUI.add(refreshButton);

        totalVotesLabel = new JLabel("Total Votes: 0");
        totalVotesLabel.setFont(new Font("Arial", Font.BOLD, 15));
        totalVotesLabel.setBounds(10, 50, 280, 25);
        mainUI.add(totalVotesLabel);

        winnerLabel = new JLabel("");
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        winnerLabel.setBounds(10, 78, 480, 25);
        mainUI.add(winnerLabel);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(null);

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBounds(10, 110, 480, 480);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainUI.add(scrollPane);

        
        graphTitle = new JLabel("Voter - Candidate Graph");
        graphTitle.setFont(new Font("Arial", Font.BOLD, 18));
        graphTitle.setBounds(500, 15, 460, 30);
        mainUI.add(graphTitle);

        graphListPanel = new JPanel();
        graphListPanel.setLayout(null);

        graphScrollPane = new JScrollPane(graphListPanel);
        graphScrollPane.setBounds(500, 50, 470, 540);
        graphScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        graphScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainUI.add(graphScrollPane);

        //Add to ActionListener
        btnDashboard.addActionListener(this);
        btnCandidates.addActionListener(this);
        btnElection.addActionListener(this);
        btnLogout.addActionListener(this);
        refreshButton.addActionListener(this);

        loadResults();
        loadGraph();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDashboard) {
            dispose();
            new AdminPanel();
        } else if (e.getSource() == btnCandidates) {
            dispose();
            new AdminPanel();
        } else if (e.getSource() == btnElection) {
            dispose();
            new AdminPanel();
        } else if (e.getSource() == btnLogout) {
            dispose();
            UserRegistry.currentUser = null;
            new LogIn();
        } else if (e.getSource() == refreshButton) {
            loadResults();
            loadGraph();
        }
    }

    private void loadResults() {

        resultsPanel.removeAll();
        resultList = new LinkedList<>();
        int totalVotes = 0;

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT c.name, c.party, COUNT(v.id) AS vote_count "
                    + "FROM candidates c LEFT JOIN votes v ON v.candidate_id = c.id "
                    + "GROUP BY c.id, c.name, c.party");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String party = rs.getString("party");
                String votes = rs.getString("vote_count");
                resultList.add(new String[]{name, party, votes}); //BE
                totalVotes += Integer.parseInt(votes);
            }

            con.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Unable to load results. Check database connection.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        // Sorting algorithm - bubble sort by vote count DESC
        bubbleSort(resultList);

        totalVotesLabel.setText("Total Votes: " + totalVotes);

        if (totalVotes == 0) {
            winnerLabel.setText("No votes have been cast yet.");
        } else {
            if (!resultList.isEmpty()) {
                String[] winner = resultList.getFirst();
                winnerLabel.setText("Winner: " + winner[0] + " (" + winner[2] + " votes)");
            }
        }

        int cardH = 70;
        int gap = 6;
        int y = 8;

        if (resultList.isEmpty()) {
            noCandidatesLabel = new JLabel("No candidates in the election.", SwingConstants.CENTER);
            noCandidatesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noCandidatesLabel.setBounds(0, 120, 460, 30);
            resultsPanel.add(noCandidatesLabel);
            y = 300;
        } else {
            int rank = 1;
            for (String[] row : resultList) {
                String name = row[0];
                String party = row[1];
                int votes = Integer.parseInt(row[2]);

                JPanel card = new JPanel();
                card.setLayout(null);
                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                card.setBounds(5, y, 455, cardH);

                JLabel lblRank = new JLabel("#" + rank);
                lblRank.setFont(new Font("Arial", Font.BOLD, 16));
                lblRank.setBounds(10, 8, 35, 50);
                card.add(lblRank);

                JLabel lblName = new JLabel(name);
                lblName.setFont(new Font("Arial", Font.BOLD, 15));
                lblName.setBounds(55, 8, 250, 25);
                card.add(lblName);

                JLabel lblParty = new JLabel("Party: " + party);
                lblParty.setFont(new Font("Arial", Font.PLAIN, 12));
                lblParty.setBounds(55, 36, 250, 20);
                card.add(lblParty);

                JLabel lblVotes = new JLabel(votes + " votes", SwingConstants.RIGHT);
                lblVotes.setFont(new Font("Arial", Font.BOLD, 15));
                lblVotes.setBounds(330, 8, 110, 25);
                card.add(lblVotes);

                resultsPanel.add(card);
                y += cardH + gap;
                rank++;
            }
        }

        resultsPanel.setPreferredSize(new Dimension(460, y + 10));
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void loadGraph() {

        graphListPanel.removeAll();

        VoterCandidateGraph graph = UserRegistry.graph;
        Map<String, LinkedList<String>> edges = graph.getAdjacencyList();

        int y = 8;
        int rowH = 30;
        int gap = 4;

        if (edges.isEmpty()) {
            JLabel empty = new JLabel("No voter-candidate connections yet.", SwingConstants.CENTER);
            empty.setFont(new Font("Arial", Font.PLAIN, 14));
            empty.setBounds(0, 120, 455, 30);
            graphListPanel.add(empty);
            graphListPanel.setPreferredSize(new Dimension(455, 300));
            graphListPanel.revalidate();
            graphListPanel.repaint();
            return;
        }

        // Header row
        JLabel hdrVoter = new JLabel("Voter (Username)");
        hdrVoter.setFont(new Font("Arial", Font.BOLD, 13));
        hdrVoter.setBounds(10, y, 180, 22);
        graphListPanel.add(hdrVoter);

        JLabel hdrEdge = new JLabel("Age -> Candidate", SwingConstants.CENTER);
        hdrEdge.setFont(new Font("Arial", Font.BOLD, 13));
        hdrEdge.setBounds(195, y, 240, 22);
        graphListPanel.add(hdrEdge);

        y += rowH;

        JPanel divider = new JPanel();
        divider.setBackground(Color.BLACK);
        divider.setBounds(5, y, 450, 1);
        graphListPanel.add(divider);

        y += 6;

        for (Map.Entry<String, LinkedList<String>> entry : edges.entrySet()) {
            String voterId = entry.getKey();
            LinkedList<String> edgeLabels = entry.getValue();
            String edgeStr = String.join(", ", edgeLabels);

            JPanel row = new JPanel();
            row.setLayout(null);
            row.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            row.setBounds(5, y, 450, rowH);

            JLabel lblVoter = new JLabel(voterId);
            lblVoter.setFont(new Font("Arial", Font.PLAIN, 13));
            lblVoter.setBounds(8, 4, 180, 22);
            row.add(lblVoter);

            JLabel lblEdge = new JLabel(edgeStr);
            lblEdge.setFont(new Font("Arial", Font.PLAIN, 13));
            lblEdge.setBounds(195, 4, 245, 22);
            row.add(lblEdge);

            graphListPanel.add(row);
            y += rowH + gap;
        }

        graphListPanel.setPreferredSize(new Dimension(455, y + 10));
        graphListPanel.revalidate();
        graphListPanel.repaint();
    }

    // Bubble sort - sorts LinkedList by vote count descending
    private void bubbleSort(LinkedList<String[]> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int votes1 = Integer.parseInt(list.get(j)[2]);
                int votes2 = Integer.parseInt(list.get(j + 1)[2]);
                if (votes1 < votes2) {
                    String[] temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}
