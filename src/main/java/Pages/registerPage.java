
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

    private JPanel sideUI, mainUI, upSeparator, downSeparator, formCard;

    private JLabel user, fName, mName, lName, registerlbl;
    private JLabel birthDate, gender, subtitlelbl;

    // Error labels
    private JLabel fNameError, lNameError;
    private JLabel birthDateError, genderError;

    private JTextField fNameField, lNameField, mNameField;
    private JSpinner birthDateSpinner;
    private JComboBox<String> genderBox;
    private JButton backBtn, createBtn;

    public registerPage() {

        // MAIN FRAME
        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);

        // CREATE SIDE PANEL
        sideUI = new JPanel();
        sideUI.setBackground(BRAND);
        sideUI.setLayout(null);
        sideUI.setBounds(0, 0, 300, 600);

        // SIDE BUTTONS
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
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(HOVER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(BRAND);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    button.setBackground(HOVER);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(HOVER);
                }
            });

            sideUI.add(button);
        }

        // USER NAME
        user = new JLabel("Hello user!", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 24));
        user.setForeground(ALT_FONTS);
        user.setBounds(0, 0, 300, 120);

        sideUI.add(user);
        add(sideUI);

        // MAIN PANEL
        mainUI = new JPanel();
        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);
        add(mainUI);

        // FORM CARD
        formCard = new JPanel();
        formCard.setBackground(Color.WHITE);
        formCard.setLayout(null);
        formCard.setBounds(35, 25, 630, 520);
        formCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mainUI.add(formCard);

        // FORM TITLE
        registerlbl = new JLabel("Create Account");
        registerlbl.setBounds(40, 15, 400, 40);
        registerlbl.setFont(new Font("Arial", Font.BOLD, 28));
        registerlbl.setForeground(Color.BLACK);
        formCard.add(registerlbl);

        // SUBTITLE
        subtitlelbl = new JLabel("Please fill in your personal information");
        subtitlelbl.setBounds(40, 55, 450, 25);
        subtitlelbl.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitlelbl.setForeground(new Color(110, 110, 110));
        formCard.add(subtitlelbl);

        // TOP SEPARATOR
        upSeparator = new JPanel();
        upSeparator.setBackground(Color.BLACK);
        upSeparator.setBounds(40, 90, 550, 1);
        formCard.add(upSeparator);

        // FIRST NAME
        fName = new JLabel("First Name");
        fName.setBounds(40, 110, 200, 25);
        fName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(fName);

        fNameField = new JTextField();
        fNameField.setBounds(40, 138, 250, 38);
        fNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        fNameField.setBorder(createNormalTextBorder());
        formCard.add(fNameField);

        fNameError = createErrorLabel(40, 177);
        formCard.add(fNameError);

        // LAST NAME
        lName = new JLabel("Last Name");
        lName.setBounds(320, 110, 200, 25);
        lName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(lName);

        lNameField = new JTextField();
        lNameField.setBounds(320, 138, 270, 38);
        lNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        lNameField.setBorder(createNormalTextBorder());
        formCard.add(lNameField);

        lNameError = createErrorLabel(320, 177);
        formCard.add(lNameError);

        // MIDDLE NAME - OPTIONAL
        mName = new JLabel("Middle Name (Optional)");
        mName.setBounds(40, 190, 250, 25);
        mName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(mName);

        mNameField = new JTextField();
        mNameField.setBounds(40, 218, 250, 38);
        mNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        mNameField.setBorder(createNormalTextBorder());
        formCard.add(mNameField);

        // BIRTH DATE
        birthDate = new JLabel("Birth Date");
        birthDate.setBounds(320, 190, 200, 25);
        birthDate.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(birthDate);

        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(
                new JSpinner.DateEditor(birthDateSpinner, "MM/dd/yyyy")
        );
        birthDateSpinner.setBounds(320, 218, 270, 38);
        birthDateSpinner.setFont(new Font("Arial", Font.PLAIN, 15));

        // Start with a blank date field so the user must enter a date.
        JSpinner.DateEditor dateEditor =
                (JSpinner.DateEditor) birthDateSpinner.getEditor();

        dateEditor.getTextField().setText("");
        dateEditor.getTextField().setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(NORMAL_BORDER),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        formCard.add(birthDateSpinner);

        birthDateError = createErrorLabel(320, 257);
        formCard.add(birthDateError);

        // GENDER
        gender = new JLabel("Gender");
        gender.setBounds(40, 270, 200, 25);
        gender.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(gender);

        String[] genders = {
                "Select Gender",
                "Male",
                "Female",
                "Prefer not to say"
        };

        // Use the class field, not a local variable.
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(40, 298, 250, 38);
        genderBox.setFont(new Font("Arial", Font.PLAIN, 15));
        genderBox.setBackground(Color.WHITE);
        genderBox.setBorder(BorderFactory.createLineBorder(NORMAL_BORDER));
        formCard.add(genderBox);

        genderError = createErrorLabel(40, 337);
        formCard.add(genderError);

        // BOTTOM SEPARATOR
        downSeparator = new JPanel();
        downSeparator.setBackground(Color.BLACK);
        downSeparator.setBounds(40, 365, 550, 1);
        formCard.add(downSeparator);

        // BACK BUTTON
        backBtn = new JButton("Back");
        backBtn.setBounds(300, 405, 130, 42);
        backBtn.setFont(new Font("Arial", Font.BOLD, 15));
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(Color.BLACK);
        backBtn.setFocusable(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        backBtn.addActionListener(this);
        formCard.add(backBtn);

        // VERIFY ACCOUNT BUTTON
        createBtn = new JButton("Verify Account");
        createBtn.setBounds(445, 405, 145, 42);
        createBtn.setFont(new Font("Arial", Font.BOLD, 14));
        createBtn.setBackground(Color.BLACK);
        createBtn.setForeground(Color.WHITE);
        createBtn.setFocusable(false);
        createBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createBtn.setBorderPainted(false);
        createBtn.addActionListener(this);
        formCard.add(createBtn);

        // CLEAR ERRORS WHEN FIELDS ARE CORRECTED
        fNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!fNameField.getText().isEmpty()) {
                    setTextFieldError(fNameField, fNameError, false);
                }
            }
        });

        lNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!lNameField.getText().isEmpty()) {
                    setTextFieldError(lNameField, lNameError, false);
                }
            }
        });

        dateEditor.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!dateEditor.getTextField().getText().isEmpty()) {
                    setDateError(false);
                }
            }
        });

        genderBox.addActionListener(e -> {
            if (genderBox.getSelectedIndex() != 0) {
                setComponentError(
                        genderBox, genderError, false,
                        BorderFactory.createLineBorder(NORMAL_BORDER)
                );
            }
        });

        setVisible(true);
    }

    // CREATE A NORMAL TEXT FIELD BORDER
    private Border createNormalTextBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(NORMAL_BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );
    }

    // CREATE AN ERROR LABEL
    private JLabel createErrorLabel(int x, int y) {
        JLabel label = new JLabel("");
        label.setBounds(x, y, 270, 18);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(ERROR);
        return label;
    }

    // VALIDATE FIRST NAME OR LAST NAME
    private void setTextFieldError(
            JTextField field, JLabel errorLabel, boolean hasError
    ) {
        if (hasError) {
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ERROR, 2),
                    BorderFactory.createEmptyBorder(4, 9, 4, 9)
            ));
            errorLabel.setText("Field text required");
        } else {
            field.setBorder(createNormalTextBorder());
            errorLabel.setText("");
        }

        field.revalidate();
        field.repaint();
        errorLabel.repaint();
    }

    // VALIDATE A COMPONENT AND ITS ERROR LABEL
    private void setComponentError(
            JComponent component,
            JLabel errorLabel,
            boolean hasError,
            Border normalBorder
    ) {
        if (hasError) {
            component.setBorder(BorderFactory.createLineBorder(ERROR, 2));
            errorLabel.setText("Field text required");
        } else {
            component.setBorder(normalBorder);
            errorLabel.setText("");
        }

        component.revalidate();
        component.repaint();
        errorLabel.repaint();
    }

    // VALIDATE BIRTH DATE
    private void setDateError(boolean hasError) {
        JSpinner.DateEditor editor =
                (JSpinner.DateEditor) birthDateSpinner.getEditor();

        JTextField dateField = editor.getTextField();

        if (hasError) {
            dateField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ERROR, 2),
                    BorderFactory.createEmptyBorder(4, 9, 4, 9)
            ));
            birthDateError.setText("Field text required");
        } else {
            dateField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(NORMAL_BORDER),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            birthDateError.setText("");
        }

        dateField.repaint();
        birthDateSpinner.repaint();
        birthDateError.repaint();
    }

    // CHECK ALL REQUIRED FIELDS
    private boolean validateForm() {
        boolean valid = true;

        // FIRST NAME
        boolean firstNameMissing =
                fNameField.getText().isEmpty();

        setTextFieldError(fNameField, fNameError, firstNameMissing);

        if (firstNameMissing) {
            valid = false;
        }

        // LAST NAME
        boolean lastNameMissing =
                lNameField.getText().isEmpty();

        setTextFieldError(lNameField, lNameError, lastNameMissing);

        if (lastNameMissing) {
            valid = false;
        }

        // BIRTH DATE
        JSpinner.DateEditor editor =
                (JSpinner.DateEditor) birthDateSpinner.getEditor();

        JTextField dateField = editor.getTextField();
        boolean birthDateMissing = dateField.getText().isEmpty();

        if (!birthDateMissing) {
            try {
                birthDateSpinner.commitEdit();

                Object value = birthDateSpinner.getValue();

                if (!(value instanceof Date)) {
                    birthDateMissing = true;
                }
            } catch (Exception ex) {
                birthDateMissing = true;
            }
        }

        setDateError(birthDateMissing);

        if (birthDateMissing) {
            valid = false;
        }

        // GENDER
        boolean genderMissing = genderBox.getSelectedIndex() == 0;

        setComponentError(
                genderBox,
                genderError,
                genderMissing,
                BorderFactory.createLineBorder(NORMAL_BORDER)
        );

        if (genderMissing) {
            valid = false;
        }

        // MIDDLE NAME IS OPTIONAL - NO VALIDATION NEEDED

        return valid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Home":
                dispose();
                new UserInterface();
                break;

            case "Vote":
                System.out.println("Vote clicked");
                break;

            case "Account":
                dispose();
                new AccountStatus();
                break;

            case "Back":
                dispose();
                new AccountStatus();
                break;

            case "Verify Account":
                if (validateForm()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "All required fields have been completed!",
                            "Validation Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                break;
        }
    }
}

