package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AccountStatus extends JFrame implements ActionListener {

    private JPanel sideUI, mainUI;
    private JLabel user;
    private JButton btnHome, btnVote, btnAccount;

    private JPanel verifiedUI, notVerifiedUI;
    private JLabel title, status, notVerifiedTitle, description;
    private JButton btnVoteOpt, btnViewProfile, btnLogout, btnCandidacy;
    private JButton verifyButton;

    AccountStatus() {

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

        verifiedUI = new JPanel();
        verifiedUI.setLayout(null);
        verifiedUI.setBounds(0, 0, 700, 600);

        title = new JLabel("Account Status", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBounds(100, 30, 500, 50);
        verifiedUI.add(title);

        status = new JLabel("Your account is verified!", SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 20));
        status.setBounds(100, 85, 500, 40);
        verifiedUI.add(status);

        btnVoteOpt = new JButton("Vote");
        btnVoteOpt.setFont(new Font("Arial", Font.BOLD, 18));
        btnVoteOpt.setBounds(60, 180, 250, 100);
        verifiedUI.add(btnVoteOpt);

        btnViewProfile = new JButton("View Profile");
        btnViewProfile.setFont(new Font("Arial", Font.BOLD, 18));
        btnViewProfile.setBounds(360, 180, 250, 100);
        verifiedUI.add(btnViewProfile);

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogout.setBounds(60, 320, 250, 100);
        verifiedUI.add(btnLogout);

        btnCandidacy = new JButton("File a Candidacy");
        btnCandidacy.setFont(new Font("Arial", Font.BOLD, 18));
        btnCandidacy.setBounds(360, 320, 250, 100);
        verifiedUI.add(btnCandidacy);

        mainUI.add(verifiedUI);

        notVerifiedUI = new JPanel();
        notVerifiedUI.setLayout(null);
        notVerifiedUI.setBounds(0, 0, 700, 600);

        notVerifiedTitle = new JLabel("Account Not Verified Yet", SwingConstants.CENTER);
        notVerifiedTitle.setFont(new Font("Arial", Font.BOLD, 32));
        notVerifiedTitle.setBounds(50, 150, 600, 60);
        notVerifiedUI.add(notVerifiedTitle);

        description = new JLabel("Please verify your account to access voting features.", SwingConstants.CENTER);
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setBounds(50, 220, 600, 40);
        notVerifiedUI.add(description);

        verifyButton = new JButton("Verify Account");
        verifyButton.setFont(new Font("Arial", Font.BOLD, 18));
        verifyButton.setBounds(225, 300, 250, 60);
        notVerifiedUI.add(verifyButton);

        mainUI.add(notVerifiedUI);

        boolean verified = UserRegistry.currentUser != null
                && UserRegistry.isVerified(UserRegistry.currentUser);
        verifiedUI.setVisible(verified);
        notVerifiedUI.setVisible(!verified);

        //Add to ActionListener
        btnHome.addActionListener(this);
        btnVote.addActionListener(this);
        btnAccount.addActionListener(this);
        btnVoteOpt.addActionListener(this);
        btnViewProfile.addActionListener(this);
        btnLogout.addActionListener(this);
        btnCandidacy.addActionListener(this);
        verifyButton.addActionListener(this);

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
        } else if (e.getSource() == btnVoteOpt) {
            dispose();
            new Vote(UserRegistry.currentUser);
        } else if (e.getSource() == btnViewProfile) {
            String voterId = "unknown";
            if (UserRegistry.currentUser != null) {
                voterId = UserRegistry.currentUser;
            }
            Voter voter = UserRegistry.getVoter(voterId);
            String name = "Not set";
            if (voter != null) {
                name = voter.getName();
            }
            String verifyStatus = "Not Verified";
            if (UserRegistry.isVerified(voterId)) {
                verifyStatus = "Verified";
            }
            String candidacyStatus = CandidateRegistry.getCandidacyStatus(voterId);
            JOptionPane.showMessageDialog(this,
                    "Voter ID: " + voterId + "\n"
                    + "Name: " + name + "\n"
                    + "Verification: " + verifyStatus + "\n"
                    + "Candidacy: " + candidacyStatus,
                    "Voter Profile", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == btnLogout) {
            dispose();
            UserRegistry.currentUser = null;
            new LogIn();
        } else if (e.getSource() == btnCandidacy) {
            dispose();
            new CandidacyPage();
        } else if (e.getSource() == verifyButton) {
            dispose();
            new registerPage();
        }
    }
}
