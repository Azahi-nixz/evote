package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.LinkedList;

public class ElectionResults extends JFrame implements ActionListener {

    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

   
    private LinkedList<String[]> resultList;

    private JPanel sideUI, mainUI, resultsPanel;
    private JLabel user, title, totalVotesLabel, winnerLabel, noCandidatesLabel;
    private JButton btnHome, btnVote, btnAccount;
    private JScrollPane scrollPane;

    public ElectionResults() {

        setTitle("E-Vote - Election Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        sideUI = new JPanel();
        sideUI.setBackground(Color.BLACK);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 300, 600);

        String displayName = "user";
        if (UserRegistry.currentUser != null) {
            displayName = UserRegistry.currentUser;
        }
        user = new JLabel("Hello " + displayName + "!", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 24));
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

        add(sideUI);

        mainUI = new JPanel();
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);
        add(mainUI);

        title = new JLabel("Election Results");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(35, 20, 450, 35);
        mainUI.add(title);

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
        btnHome.addActionListener(this);
        btnVote.addActionListener(this);
        btnAccount.addActionListener(this);

        loadResults();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHome) {
            dispose();
            new UserInterface();
        } else if (e.getSource() == btnVote) {
            dispose();
            new Vote(UserRegistry.currentUser);
        } else if (e.getSource() == btnAccount) {
            dispose();
            new AccountStatus();
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
