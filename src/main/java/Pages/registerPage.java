package Pages;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class registerPage extends JFrame implements ActionListener {

    private final Color ALT_FONTS = Color.WHITE;
    private final Color BRAND = Color.BLACK;
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);
    private final Color NORMAL_BORDER = new Color(200, 200, 200);

    private final String sideBtn[] = {"Home", "Vote", "Account"};

    private JPanel sideUI, mainUI, formCard, upSeparator, downSeparator;
    private JLabel user, fName, lName, mName, registerlbl, subtitlelbl;
    private JLabel birthDate, gender;
    private JLabel fNameError, lNameError, birthDateError, genderError;
    private JTextField fNameField, lNameField, mNameField;
    private JSpinner birthDateSpinner;
    private JComboBox<String> genderBox;
    private JButton backBtn, createBtn;

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
        lbl.setText(hasError ? "Field text required" : "");
    }

    private void setDateError(boolean hasError) {
        JTextField dateField = ((JSpinner.DateEditor) birthDateSpinner.getEditor()).getTextField();
        if (hasError) {
            dateField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ERROR, 2),
                    BorderFactory.createEmptyBorder(4, 9, 4, 9)));
            birthDateError.setText("Field text required");
        } else {
            dateField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(NORMAL_BORDER),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));
            birthDateError.setText("");
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        boolean fnMissing = fNameField.getText().isEmpty();
        setFieldError(fNameField, fNameError, fnMissing);
        if (fnMissing) valid = false;

        boolean lnMissing = lNameField.getText().isEmpty();
        setFieldError(lNameField, lNameError, lnMissing);
        if (lnMissing) valid = false;

        JTextField dateField = ((JSpinner.DateEditor) birthDateSpinner.getEditor()).getTextField();
        boolean dateMissing = dateField.getText().isEmpty();
        if (!dateMissing) {
            try {
                birthDateSpinner.commitEdit();
                if (!(birthDateSpinner.getValue() instanceof Date)) dateMissing = true;
            } catch (Exception ex) {
                dateMissing = true;
            }
        }
        setDateError(dateMissing);
        if (dateMissing) valid = false;

        boolean genderMissing = genderBox.getSelectedIndex() == 0;
        setComponentError(genderBox, genderError, genderMissing,
                BorderFactory.createLineBorder(NORMAL_BORDER));
        if (genderMissing) valid = false;

        return valid;
    }

    public registerPage() {

        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

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

        registerlbl = new JLabel("Verify Account");
        registerlbl.setBounds(40, 15, 400, 40);
        registerlbl.setFont(new Font("Arial", Font.BOLD, 28));
        registerlbl.setForeground(Color.BLACK);
        formCard.add(registerlbl);

        subtitlelbl = new JLabel("Please fill in your personal information");
        subtitlelbl.setBounds(40, 55, 450, 25);
        subtitlelbl.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitlelbl.setForeground(new Color(110, 110, 110));
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
        fNameField.setBorder(normalBorder());
        formCard.add(fNameField);

        fNameError = errorLabel(40, 177);
        formCard.add(fNameError);

        lName = new JLabel("Last Name");
        lName.setBounds(320, 110, 200, 25);
        lName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(lName);

        lNameField = new JTextField();
        lNameField.setBounds(320, 138, 270, 38);
        lNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        lNameField.setBorder(normalBorder());
        formCard.add(lNameField);

        lNameError = errorLabel(320, 177);
        formCard.add(lNameError);

        mName = new JLabel("Middle Name (Optional)");
        mName.setBounds(40, 190, 250, 25);
        mName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(mName);

        mNameField = new JTextField();
        mNameField.setBounds(40, 218, 250, 38);
        mNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        mNameField.setBorder(normalBorder());
        formCard.add(mNameField);

        birthDate = new JLabel("Birth Date");
        birthDate.setBounds(320, 190, 200, 25);
        birthDate.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(birthDate);

        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "MM/dd/yyyy"));
        birthDateSpinner.setBounds(320, 218, 270, 38);
        birthDateSpinner.setFont(new Font("Arial", Font.PLAIN, 15));

        JSpinner.DateEditor dateEditor = (JSpinner.DateEditor) birthDateSpinner.getEditor();
        dateEditor.getTextField().setText("");
        dateEditor.getTextField().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(NORMAL_BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formCard.add(birthDateSpinner);

        birthDateError = errorLabel(320, 257);
        formCard.add(birthDateError);

        gender = new JLabel("Gender");
        gender.setBounds(40, 270, 200, 25);
        gender.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(gender);

        String[] genders = {"Select Gender", "Male", "Female", "Prefer not to say"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(40, 298, 250, 38);
        genderBox.setFont(new Font("Arial", Font.PLAIN, 15));
        genderBox.setBackground(Color.WHITE);
        genderBox.setBorder(BorderFactory.createLineBorder(NORMAL_BORDER));
        formCard.add(genderBox);

        genderError = errorLabel(40, 337);
        formCard.add(genderError);

        downSeparator = new JPanel();
        downSeparator.setBackground(Color.BLACK);
        downSeparator.setBounds(40, 365, 550, 1);
        formCard.add(downSeparator);

        backBtn = new JButton("Back");
        backBtn.setBounds(300, 405, 130, 42);
        backBtn.setFont(new Font("Arial", Font.BOLD, 15));
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(Color.BLACK);
        backBtn.setFocusable(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formCard.add(backBtn);

        createBtn = new JButton("Verify Account");
        createBtn.setBounds(445, 405, 145, 42);
        createBtn.setFont(new Font("Arial", Font.BOLD, 14));
        createBtn.setBackground(Color.BLACK);
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusable(false);
        createBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createBtn.setBorderPainted(false);
        formCard.add(createBtn);

        fNameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!fNameField.getText().isEmpty()) setFieldError(fNameField, fNameError, false);
            }
        });

        lNameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!lNameField.getText().isEmpty()) setFieldError(lNameField, lNameError, false);
            }
        });

        dateEditor.getTextField().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!dateEditor.getTextField().getText().isEmpty()) setDateError(false);
            }
        });

        genderBox.addActionListener(e -> {
            if (genderBox.getSelectedIndex() != 0)
                setComponentError(genderBox, genderError, false,
                        BorderFactory.createLineBorder(NORMAL_BORDER));
        });

        // Button listeners
        backBtn.addActionListener(this);
        createBtn.addActionListener(this);

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

            case "Verify Account":
                if (validateForm()) {
                    String fullName = fNameField.getText().trim();
                    if (!mNameField.getText().isEmpty())
                        fullName += " " + mNameField.getText().trim();
                    fullName += " " + lNameField.getText().trim();

                    String voterId = UserRegistry.currentUser != null
                            ? UserRegistry.currentUser : "unknown";

                    UserRegistry.registerVoter(new Voter(voterId, fullName, true));
                    UserRegistry.updateUser(voterId, true);

                    dispose();
                    new AccountStatus();
                }
                break;
        }
    }
}
