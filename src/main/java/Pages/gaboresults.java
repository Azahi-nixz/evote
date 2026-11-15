package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.LinkedList;

public class gaboresults extends JFrame implements ActionListener {

    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    // BE - LinkedList of result rows
    private LinkedList<String[]> resultList;

    private JPanel sideUI, mainUI, resultsPanel;
    private JLabel user, title, totalVotesLabel, winnerLabel, noCandidatesLabel;
    private JButton btnDashboard, btnCandidates, btnElection, btnLogout, refreshButton;
    private JScrollPane scrollPane;

    public gaboresults() {

        setTitle("E-Vote Admin - Results");
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

        title = new JLabel("Election Results");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(35, 20, 450, 35);
        mainUI.add(title);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(535, 25, 120, 35);
        mainUI.add(refreshButton);

        totalVotesLabel = new JLabel("Total Votes: 0");
        totalVotesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalVotesLabel.setBounds(35, 65, 300, 30);
        mainUI.add(totalVotesLabel);

        winnerLabel = new JLabel("");
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        winnerLabel.setBounds(35, 100, 630, 35);
        mainUI.add(winnerLabel);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(null);

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBounds(25, 150, 650, 420);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainUI.add(scrollPane);

        //Add to ActionListener
        btnDashboard.addActionListener(this);
        btnCandidates.addActionListener(this);
        btnElection.addActionListener(this);
        btnLogout.addActionListener(this);
        refreshButton.addActionListener(this);

        loadResults();
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
        }
    }

    private void loadResults() {

        resultsPanel.removeAll();

        // BE - LinkedList stores raw rows from DB
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
                winnerLabel.setText("Winner: " + winner[0] + " - " + winner[1]
                        + " (" + winner[2] + " votes)");
            }
        }

        int cardH = 80;
        int gap = 8;
        int y = 10;

        if (resultList.isEmpty()) {
            noCandidatesLabel = new JLabel("No candidates in the election.", SwingConstants.CENTER);
            noCandidatesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noCandidatesLabel.setBounds(0, 150, 630, 30);
            resultsPanel.add(noCandidatesLabel);
            y = 400;
        } else {
            int rank = 1;
            for (String[] row : resultList) {
                String name = row[0];
                String party = row[1];
                int votes = Integer.parseInt(row[2]);

                JPanel card = new JPanel();
                card.setLayout(null);
                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                card.setBounds(10, y, 615, cardH);

                JLabel lblRank = new JLabel("#" + rank);
                lblRank.setFont(new Font("Arial", Font.BOLD, 18));
                lblRank.setBounds(15, 10, 40, 60);
                card.add(lblRank);

                JLabel lblName = new JLabel(name);
                lblName.setFont(new Font("Arial", Font.BOLD, 16));
                lblName.setBounds(65, 10, 300, 28);
                card.add(lblName);

                JLabel lblParty = new JLabel("Party: " + party);
                lblParty.setFont(new Font("Arial", Font.PLAIN, 13));
                lblParty.setBounds(65, 40, 300, 22);
                card.add(lblParty);

                JLabel lblVotes = new JLabel(votes + " votes", SwingConstants.RIGHT);
                lblVotes.setFont(new Font("Arial", Font.BOLD, 16));
                lblVotes.setBounds(430, 10, 160, 28);
                card.add(lblVotes);

                resultsPanel.add(card);
                y += cardH + gap;
                rank++;
            }
        }

        resultsPanel.setPreferredSize(new Dimension(630, y + 10));
        resultsPanel.revalidate();
        resultsPanel.repaint();
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
