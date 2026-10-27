package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class registerPage extends JFrame implements ActionListener {

    private JPanel sideUI, mainUI, formCard, upSeparator, downSeparator;
    private JLabel user, fName, lName, mName, registerlbl, subtitlelbl;
    private JLabel birthDate, gender;
    private JTextField fNameField, lNameField, mNameField;
    private JSpinner birthDateSpinner;
    private JComboBox<String> genderBox;
    private JButton btnHome, btnVote, btnAccount, backBtn, createBtn;

    public registerPage() {

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

        registerlbl = new JLabel("Verify Account");
        registerlbl.setBounds(40, 15, 400, 40);
        registerlbl.setFont(new Font("Arial", Font.BOLD, 28));
        formCard.add(registerlbl);

        subtitlelbl = new JLabel("Please fill in your personal information");
        subtitlelbl.setBounds(40, 55, 450, 25);
        subtitlelbl.setFont(new Font("Arial", Font.PLAIN, 14));
        formCard.add(subtitlelbl);

        upSeparator = new JPanel();
upSeparator.setBackground(Color.BLACK);
        upSeparator.setBounds(40, 90, 550, 1);
        formCard.add(upSeparator);

        fName = new JLabel("First Name");
        fName.setBounds(40, 110, 200, 25);
        fName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(fName);

        fNameField = new JTextField();
        fNameField.setBounds(40, 138, 250, 38);
        fNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(fNameField);

        lName = new JLabel("Last Name");
        lName.setBounds(320, 110, 200, 25);
        lName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(lName);

        lNameField = new JTextField();
        lNameField.setBounds(320, 138, 270, 38);
        lNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(lNameField);

        mName = new JLabel("Middle Name (Optional)");
        mName.setBounds(40, 190, 250, 25);
        mName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(mName);

        mNameField = new JTextField();
        mNameField.setBounds(40, 218, 250, 38);
        mNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(mNameField);

        birthDate = new JLabel("Birth Date");
        birthDate.setBounds(320, 190, 200, 25);
        birthDate.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(birthDate);

        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "MM/dd/yyyy"));
        birthDateSpinner.setBounds(320, 218, 270, 38);
        birthDateSpinner.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(birthDateSpinner);

        gender = new JLabel("Gender");
        gender.setBounds(40, 270, 200, 25);
        gender.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(gender);

        String[] genders = {"Select Gender", "Male", "Female", "Prefer not to say"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(40, 298, 250, 38);
        genderBox.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(genderBox);

        downSeparator = new JPanel();
        downSeparator.setBackground(Color.BLACK);
        downSeparator.setBounds(40, 355, 550, 1);
        formCard.add(downSeparator);

        backBtn = new JButton("Back");
        backBtn.setBounds(300, 390, 130, 42);
        backBtn.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(backBtn);

        createBtn = new JButton("Verify Account");
        createBtn.setBounds(445, 390, 145, 42);
        createBtn.setFont(new Font("Arial", Font.BOLD, 14));
        formCard.add(createBtn);

        //Add to ActionListener
        btnHome.addActionListener(this);
        btnVote.addActionListener(this);
        btnAccount.addActionListener(this);
        backBtn.addActionListener(this);
        createBtn.addActionListener(this);

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
        } else if (e.getSource() == createBtn) {

            if (fNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "First name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (lNameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Last name is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField dateField = ((JSpinner.DateEditor) birthDateSpinner.getEditor()).getTextField();
            if (dateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Birth date is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (genderBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Please select a gender.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fullName = fNameField.getText().trim();
            if (!mNameField.getText().isEmpty()) {
                fullName += " " + mNameField.getText().trim();
            }
            fullName += " " + lNameField.getText().trim();

            String voterId = "unknown";
            if (UserRegistry.currentUser != null) {
                voterId = UserRegistry.currentUser;
            }

            UserRegistry.registerVoter(new Voter(voterId, fullName, true));
            UserRegistry.updateUser(voterId, true);

            JOptionPane.showMessageDialog(this, "Account verified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new AccountStatus();
        }
    }
}
