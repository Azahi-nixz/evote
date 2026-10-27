package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CandidacyPage extends JFrame implements ActionListener {

    private String[] parties = {
            "Select Party", "Dilawan", "Pink",
            "Blue ribbon", "Red ribbon", "Independent", "Other"
    };

    private JPanel sideUI, mainUI, formCard, upSeparator, downSeparator;
    private JLabel user, titlelbl, subtitlelbl, candidateName, partylbl;
    private JTextField candidateNameField;
    private JComboBox<String> partyBox;
    private JButton btnHome, btnVote, btnAccount, backBtn, submitBtn;

    public CandidacyPage() {

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

        formCard = new JPanel();
        formCard.setLayout(null);
        formCard.setBounds(35, 25, 630, 520);
        formCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainUI.add(formCard);

        titlelbl = new JLabel("File a Candidacy");
        titlelbl.setBounds(40, 15, 400, 40);
        titlelbl.setFont(new Font("Arial", Font.BOLD, 28));
        formCard.add(titlelbl);

        subtitlelbl = new JLabel("Submit your candidacy application for approval");
        subtitlelbl.setBounds(40, 55, 450, 25);
        subtitlelbl.setFont(new Font("Arial", Font.PLAIN, 14));
        formCard.add(subtitlelbl);

        upSeparator = new JPanel();
        upSeparator.setBackground(Color.BLACK);
        upSeparator.setBounds(40, 90, 550, 1);
        formCard.add(upSeparator);

        candidateName = new JLabel("Candidate Name");
        candidateName.setBounds(40, 120, 200, 25);
        candidateName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(candidateName);

        candidateNameField = new JTextField();
        candidateNameField.setBounds(40, 148, 550, 38);
        candidateNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(candidateNameField);

        partylbl = new JLabel("Party");
        partylbl.setBounds(40, 210, 200, 25);
        partylbl.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(partylbl);

        partyBox = new JComboBox<>(parties);
        partyBox.setBounds(40, 238, 550, 38);
        partyBox.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(partyBox);

        downSeparator = new JPanel();
        downSeparator.setBackground(Color.BLACK);
        downSeparator.setBounds(40, 300, 550, 1);
        formCard.add(downSeparator);

        backBtn = new JButton("Back");
        backBtn.setBounds(300, 340, 130, 42);
        backBtn.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(backBtn);

        submitBtn = new JButton("Submit Candidacy");
        submitBtn.setBounds(445, 340, 145, 42);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        formCard.add(submitBtn);

        //Add to ActionListener
        btnHome.addActionListener(this);
        btnVote.addActionListener(this);
        btnAccount.addActionListener(this);
        backBtn.addActionListener(this);
        submitBtn.addActionListener(this);

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
        } else if (e.getSource() == btnAccount || e.getSource() == backBtn) {
            dispose();
            new AccountStatus();
        } else if (e.getSource() == submitBtn) {

            if (candidateNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Candidate name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (partyBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Please select a party.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = candidateNameField.getText().trim();
            String party = (String) partyBox.getSelectedItem();

            if (CandidateRegistry.hasUserApplied(UserRegistry.currentUser)) {
                JOptionPane.showMessageDialog(this, "You have already filed a candidacy.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

//            if (CandidateRegistry.candidateExists(name)) {
//                JOptionPane.showMessageDialog(this, "A candidacy with this name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }

            Candidate candidate = new Candidate("CAND-" + System.currentTimeMillis(), name, party);

            if (CandidateRegistry.addCandidate(candidate, UserRegistry.currentUser)) {
                JOptionPane.showMessageDialog(this, "Your candidacy has been submitted for approval.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new AccountStatus();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit candidacy. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
