package Pages;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class CandidacyPage extends JFrame implements ActionListener {

    private final Color ALT_FONTS = Color.WHITE;
    private final Color BRAND = Color.BLACK;
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);
    private final Color NORMAL_BORDER = new Color(200, 200, 200);

    private final String sideBtn[] = {"Home", "Vote", "Account"};

    private String[] parties = {
            "Select Party", "Dilawan", "Pink",
            "Blue ribbon", "Red ribbon", "Independent", "Other"
    };

    private JPanel sideUI, mainUI, formCard, upSeparator, downSeparator;
    private JLabel user, titlelbl, subtitlelbl;
    private JLabel candidateName, partylbl;
    private JLabel candidateNameError, partyError;
    private JTextField candidateNameField;
    private JComboBox<String> partyBox;
    private JButton backBtn, submitBtn;

    private Border normalBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(NORMAL_BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private JLabel errorLabel(int x, int y) {
        JLabel label = new JLabel("");
        label.setBounds(x, y, 270, 18);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(ERROR);
        return label;
    }

    private void setFieldError(JTextField field, JLabel lbl, boolean hasError) {
        if (hasError) {
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ERROR, 2),
                    BorderFactory.createEmptyBorder(4, 9, 4, 9)));
            lbl.setText("Field text required");
        } else {
            field.setBorder(normalBorder());
            lbl.setText("");
        }
    }

    private void setComponentError(JComponent comp, JLabel lbl, boolean hasError, Border normal) {
        comp.setBorder(hasError ? BorderFactory.createLineBorder(ERROR, 2) : normal);
        lbl.setText(hasError ? "Please select an option" : "");
    }

    private boolean validateForm() {
        boolean valid = true;
        boolean nameMissing = candidateNameField.getText().isEmpty();
        setFieldError(candidateNameField, candidateNameError, nameMissing);
        if (nameMissing) valid = false;

        boolean partyMissing = partyBox.getSelectedIndex() == 0;
        setComponentError(partyBox, partyError, partyMissing,
                BorderFactory.createLineBorder(NORMAL_BORDER));
        if (partyMissing) valid = false;

        return valid;
    }

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
            button.setBounds(25, 160 + (i * 100), 250, 75);
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

        String displayName = UserRegistry.currentUser != null ? UserRegistry.currentUser : "user";
        user = new JLabel("Hello " + displayName + "!", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 24));
        user.setForeground(ALT_FONTS);
        user.setBounds(0, 0, 300, 120);
        sideUI.add(user);
    }

    public CandidacyPage() {

        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        buildSidePanel();
        add(sideUI);

        mainUI = new JPanel();
        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);
        add(mainUI);

        formCard = new JPanel();
        formCard.setBackground(Color.WHITE);
        formCard.setLayout(null);
        formCard.setBounds(35, 25, 630, 520);
        formCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainUI.add(formCard);

        titlelbl = new JLabel("File a Candidacy");
        titlelbl.setBounds(40, 15, 400, 40);
        titlelbl.setFont(new Font("Arial", Font.BOLD, 28));
        titlelbl.setForeground(Color.BLACK);
        formCard.add(titlelbl);

        subtitlelbl = new JLabel("Submit your candidacy application for approval");
        subtitlelbl.setBounds(40, 55, 450, 25);
        subtitlelbl.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitlelbl.setForeground(new Color(110, 110, 110));
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
        candidateNameField.setBorder(normalBorder());
        formCard.add(candidateNameField);

        candidateNameError = errorLabel(40, 187);
        formCard.add(candidateNameError);

        partylbl = new JLabel("Party");
        partylbl.setBounds(40, 220, 200, 25);
        partylbl.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(partylbl);

        partyBox = new JComboBox<>(parties);
        partyBox.setBounds(40, 248, 550, 38);
        partyBox.setFont(new Font("Arial", Font.PLAIN, 15));
        partyBox.setBackground(Color.WHITE);
        partyBox.setBorder(BorderFactory.createLineBorder(NORMAL_BORDER));
        formCard.add(partyBox);

        partyError = errorLabel(40, 287);
        formCard.add(partyError);

        downSeparator = new JPanel();
        downSeparator.setBackground(Color.BLACK);
        downSeparator.setBounds(40, 320, 550, 1);
        formCard.add(downSeparator);

        backBtn = new JButton("Back");
        backBtn.setBounds(300, 360, 130, 42);
        backBtn.setFont(new Font("Arial", Font.BOLD, 15));
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(Color.BLACK);
        backBtn.setFocusable(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formCard.add(backBtn);

        submitBtn = new JButton("Submit Candidacy");
        submitBtn.setBounds(445, 360, 145, 42);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setBackground(Color.BLACK);
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusable(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.setBorderPainted(false);
        formCard.add(submitBtn);

        candidateNameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!candidateNameField.getText().isEmpty())
                    setFieldError(candidateNameField, candidateNameError, false);
            }
        });

        partyBox.addActionListener(e -> {
            if (partyBox.getSelectedIndex() != 0)
                setComponentError(partyBox, partyError, false,
                        BorderFactory.createLineBorder(NORMAL_BORDER));
        });

        // Button listeners
        backBtn.addActionListener(this);
        submitBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Home":
                dispose();
                new UserInterface();
                break;

            case "Vote":
                dispose();
                new Vote(UserRegistry.currentUser);
                break;

            case "Account":
            case "Back":
                dispose();
                new AccountStatus();
                break;

            case "Submit Candidacy":
                if (!validateForm()) break;

                String name = candidateNameField.getText().trim();
                String party = (String) partyBox.getSelectedItem();

                if (CandidateRegistry.hasUserApplied(UserRegistry.currentUser)) {
                    JOptionPane.showMessageDialog(this, "You have already filed a candidacy.",
                            "Duplicate Candidacy", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                if (CandidateRegistry.candidateExists(name)) {
                    JOptionPane.showMessageDialog(this, "A candidacy with this name already exists.",
                            "Duplicate Candidacy", JOptionPane.WARNING_MESSAGE);
                    break;
                }

                Candidate candidate = new Candidate("CAND-" + System.currentTimeMillis(), name, party);

                if (CandidateRegistry.addCandidate(candidate, UserRegistry.currentUser)) {
                    JOptionPane.showMessageDialog(this, "Your candidacy has been submitted for approval.",
                            "Candidacy Submitted", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new AccountStatus();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit candidacy. Please try again.",
                            "Database Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
    }
}
