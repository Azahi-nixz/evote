package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame implements ActionListener {

    private JPanel sideUI, mainUI;
    private JLabel user;
    private JButton btnHome, btnVote, btnAccount;

    private JLabel welcome, subtitle;
    private JPanel electionCard, statusCard, accountCard;
    private JLabel electionTitle, electionValue;
    private JLabel statusTitle, status;
    private JLabel accountTitle, accountValue;
    private JLabel currentElection;
    private JPanel electionPanel;
    private JLabel electionName, electionDescription;
    private JButton viewButton;

    public UserInterface() {

        setTitle("E-Vote");
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

        welcome = new JLabel("Welcome to E-VOTE");
        welcome.setFont(new Font("Arial", Font.BOLD, 32));
        welcome.setBounds(40, 40, 600, 45);
        mainUI.add(welcome);

        subtitle = new JLabel("Fast. Secure. Efficient.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(42, 85, 500, 30);
        mainUI.add(subtitle);

        electionCard = new JPanel();
        electionCard.setLayout(null);
        electionCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        electionCard.setBounds(40, 150, 190, 130);
        electionTitle = new JLabel("Elections", SwingConstants.CENTER);
        electionTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        electionTitle.setBounds(0, 15, 190, 30);
        electionValue = new JLabel("0", SwingConstants.CENTER);
        electionValue.setFont(new Font("Arial", Font.BOLD, 32));
        electionValue.setBounds(0, 50, 190, 50);
        electionCard.add(electionTitle);
        electionCard.add(electionValue);
        mainUI.add(electionCard);

        statusCard = new JPanel();
        statusCard.setLayout(null);
        statusCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        statusCard.setBounds(255, 150, 190, 130);
        statusTitle = new JLabel("Voting Status", SwingConstants.CENTER);
        statusTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        statusTitle.setBounds(0, 15, 190, 30);
        status = new JLabel("CLOSE", SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 28));
        status.setBounds(0, 50, 190, 50);
        statusCard.add(statusTitle);
        statusCard.add(status);
        mainUI.add(statusCard);

        accountCard = new JPanel();
        accountCard.setLayout(null);
        accountCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        accountCard.setBounds(470, 150, 190, 130);
        accountTitle = new JLabel("Account", SwingConstants.CENTER);
        accountTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        accountTitle.setBounds(0, 15, 190, 30);
        accountValue = new JLabel("NOT VERIFIED", SwingConstants.CENTER);
        accountValue.setFont(new Font("Arial", Font.BOLD, 22));
        accountValue.setBounds(0, 50, 190, 50);
        accountCard.add(accountTitle);
        accountCard.add(accountValue);
        mainUI.add(accountCard);

        currentElection = new JLabel("Current Election");
        currentElection.setFont(new Font("Arial", Font.BOLD, 24));
        currentElection.setBounds(40, 330, 400, 35);
        mainUI.add(currentElection);

        electionPanel = new JPanel();
        electionPanel.setLayout(null);
        electionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        electionPanel.setBounds(40, 380, 620, 100);
        electionName = new JLabel("Election");
        electionName.setFont(new Font("Arial", Font.BOLD, 18));
        electionName.setBounds(20, 15, 350, 30);
        electionDescription = new JLabel("Not available yet.");
        electionDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        electionDescription.setBounds(20, 50, 300, 25);
        viewButton = new JButton("View Candidates");
        viewButton.setFont(new Font("Arial", Font.BOLD, 14));
        viewButton.setEnabled(false);
        viewButton.setBounds(430, 25, 165, 45);
        electionPanel.add(electionName);
        electionPanel.add(electionDescription);
        electionPanel.add(viewButton);
        mainUI.add(electionPanel);

        //Add to ActionListener
        btnHome.addActionListener(this);
        btnVote.addActionListener(this);
        btnAccount.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVote) {
            dispose();
            new Vote(UserRegistry.currentUser);
        } else if (e.getSource() == btnAccount) {
            dispose();
            new AccountStatus();
        }
    }
}
