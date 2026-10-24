package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class gaboresults extends JFrame implements ActionListener {

    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color SUCCESS = new Color(0, 130, 0);
    private final Color BAR_COLOR = new Color(50, 100, 200);

    private final String sideBtn[] = {"Dashboard", "Candidates", "Election", "Logout"};
    private final String DB_URL = "jdbc:mysql://localhost:3306/swing_demo";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "";

    private JPanel sideUI;
    private JLabel user;

    private JPanel mainUI;
    private JLabel title, totalVotesLabel, winnerLabel;
    private JButton refreshButton;
    private JScrollPane scrollPane;
    private JPanel resultsPanel;

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

    public gaboresults() {

        setTitle("E-Vote Admin - Results");
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

        title = new JLabel("Election Results");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(BRAND);
        title.setBounds(35, 20, 450, 35);
        mainUI.add(title);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(535, 25, 120, 35);
        refreshButton.setBackground(BRAND);
        refreshButton.setForeground(ALT_FONTS);
        refreshButton.setFocusable(false);
        mainUI.add(refreshButton);

        totalVotesLabel = new JLabel("Total Votes: 0");
        totalVotesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalVotesLabel.setForeground(BRAND);
        totalVotesLabel.setBounds(35, 65, 300, 30);
        mainUI.add(totalVotesLabel);

        winnerLabel = new JLabel("");
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        winnerLabel.setForeground(SUCCESS);
        winnerLabel.setBounds(35, 100, 630, 35);
        mainUI.add(winnerLabel);

        resultsPanel = new JPanel();
        resultsPanel.setBackground(ALT_FONTS);
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBounds(25, 150, 650, 420);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainUI.add(scrollPane);

        loadResults();

        // Button listeners
        refreshButton.addActionListener(this);

        setVisible(true);
    }

    private void loadResults() {

        resultsPanel.removeAll();

        String sql = "SELECT c.name, c.party, COUNT(v.id) AS vote_count "
                + "FROM candidates c LEFT JOIN votes v ON v.candidate_id = c.id "
                + "GROUP BY c.id, c.name, c.party ORDER BY vote_count DESC";

        int totalVotes = 0;
        int maxVotes = 0;
        String winnerName = "";
        String winnerParty = "";
        List<Object[]> results = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String party = rs.getString("party");
                int votes = rs.getInt("vote_count");
                results.add(new Object[]{name, party, votes});
                totalVotes += votes;
                if (votes > maxVotes) {
                    maxVotes = votes;
                    winnerName = name;
                    winnerParty = party;
                }
            }

        } catch (SQLException e) {
            JLabel err = new JLabel("Unable to load results. Check database connection.",
                    SwingConstants.CENTER);
            err.setFont(new Font("Arial", Font.PLAIN, 18));
            err.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultsPanel.add(Box.createVerticalStrut(40));
            resultsPanel.add(err);
            e.printStackTrace();
            resultsPanel.revalidate();
            resultsPanel.repaint();
            return;
        }

        totalVotesLabel.setText("Total Votes: " + totalVotes);

        if (totalVotes == 0) {
            winnerLabel.setText("No votes have been cast yet.");
            winnerLabel.setForeground(HOVER);
        } else {
            winnerLabel.setText("Winner: " + winnerName + " - " + winnerParty
                    + " (" + maxVotes + " votes)");
            winnerLabel.setForeground(SUCCESS);
        }

        if (results.isEmpty()) {
            JLabel none = new JLabel("No candidates in the election.", SwingConstants.CENTER);
            none.setFont(new Font("Arial", Font.PLAIN, 18));
            none.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultsPanel.add(Box.createVerticalStrut(40));
            resultsPanel.add(none);
        } else {
            for (Object[] row : results) {
                String name = (String) row[0];
                String party = (String) row[1];
                int votes = (int) row[2];
                double pct = totalVotes > 0 ? (double) votes / totalVotes * 100 : 0;

                JPanel card = new JPanel();
                card.setLayout(null);
                card.setBackground(ALT_FONTS);
                card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                card.setMaximumSize(new Dimension(610, 110));
                card.setPreferredSize(new Dimension(610, 110));
                card.setMinimumSize(new Dimension(610, 110));

                JLabel lblName = new JLabel(name);
                lblName.setFont(new Font("Arial", Font.BOLD, 19));
                lblName.setBounds(15, 10, 350, 28);
                card.add(lblName);

                JLabel lblParty = new JLabel("Party: " + party);
                lblParty.setFont(new Font("Arial", Font.PLAIN, 14));
                lblParty.setBounds(15, 38, 350, 22);
                card.add(lblParty);

                JLabel lblVotes = new JLabel(votes + " votes", SwingConstants.RIGHT);
                lblVotes.setFont(new Font("Arial", Font.BOLD, 16));
                lblVotes.setBounds(440, 10, 150, 28);
                card.add(lblVotes);

                JLabel lblPct = new JLabel(String.format("%.1f%%", pct), SwingConstants.RIGHT);
                lblPct.setFont(new Font("Arial", Font.PLAIN, 14));
                lblPct.setBounds(440, 38, 150, 22);
                card.add(lblPct);

                JPanel barContainer = new JPanel();
                barContainer.setLayout(null);
                barContainer.setBackground(new Color(230, 230, 230));
                barContainer.setBounds(15, 70, 575, 25);
                card.add(barContainer);

                int barWidth = totalVotes > 0 ? (int) ((double) votes / totalVotes * 575) : 0;
                if (barWidth > 0) {
                    JPanel barFill = new JPanel();
                    barFill.setBackground(BAR_COLOR);
                    barFill.setBounds(0, 0, barWidth, 25);
                    barContainer.add(barFill);
                }

                card.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(card);
                resultsPanel.add(Box.createVerticalStrut(10));
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Dashboard":
            case "Candidates":
            case "Election":
                dispose();
                new AdminPanel();
                break;
            case "Logout":
                dispose();
                UserRegistry.currentUser = null;
                new LogIn();
                break;
            case "Refresh":
                loadResults();
                break;
        }
    }
}
