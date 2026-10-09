package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class registerPage extends JFrame {

    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);

    private final String sideBtn[] = {"Home", "Vote", "Account"};

    private JPanel sideUI, mainUI,upSeparator, downSeparator, formCard;
    private JLabel user, fName, mName, lName, registerlbl, birthDate, gender, subtitlelbl;
    private JTextField fNameField, lNameField, mNameField;
    private JSpinner birthDateSpinner;
    private JComboBox<String> genderBox;
    private JButton backBtn, createBtn;
    
    // CONSTRUCTOR
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
            button.setBorder(
                BorderFactory.createLineBorder(ALT_FONTS, 1)
            );

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
        
        // SEPARATOR
        upSeparator = new JPanel();
        upSeparator.setBackground(Color.BLACK);
        upSeparator.setBounds(40, 90, 550, 1);
        formCard.add(upSeparator);

        
        //REGISTER FORM TITLE
        registerlbl = new JLabel("Register Form");
        registerlbl.setBounds(250,0,300,150);
        registerlbl.setFont(new Font("Arial", Font.BOLD, 24));
        mainUI.add(registerlbl);
        
        // FIRSTNAME AND BUTTON
        fName = new JLabel("First Name");
        fName.setBounds(40, 110, 200, 25);
        fName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(fName);
        
        fNameField = new JTextField();
        fNameField.setBounds(40, 138, 250, 38);
        fNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        fNameField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formCard.add(fNameField);
        
        // LASTNAME AND BUTTON
        lName = new JLabel("Last Name");
        lName.setBounds(320, 110, 200, 25);
        lName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(lName);

        lNameField = new JTextField();
        lNameField.setBounds(320, 138, 270, 38);
        lNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        lNameField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formCard.add(lNameField);
        
        //MIDDLE NAME AND BUTTON
        mName = new JLabel("Middle Name");
        mName.setBounds(40, 190, 200, 25);
        mName.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(mName);

        mNameField = new JTextField();
        mNameField.setBounds(40, 218, 250, 38);
        mNameField.setFont(new Font("Arial", Font.PLAIN, 15));
        mNameField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200)),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        formCard.add(mNameField);
        
        //BIRTHDATE LABEL 
        birthDate = new JLabel("Birth Date");
        birthDate.setBounds(320, 190, 200, 25);
        birthDate.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(birthDate);
        
        //CHOOSE BIRTHDATE
        birthDateSpinner = new JSpinner(new SpinnerDateModel());
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "MM/dd/yyyy"));
        birthDateSpinner.setBounds(320, 218, 270, 38);
        birthDateSpinner.setFont(new Font("Arial", Font.PLAIN, 15));
        formCard.add(birthDateSpinner);
        
        // GENDER LABEL  
        gender = new JLabel("Gender");
        gender.setBounds(40, 270, 200, 25);
        gender.setFont(new Font("Arial", Font.BOLD, 15));
        formCard.add(gender);
        
        //COMBOBOX FOR GENDER
        String[] genders = {"Select Gender","Male","Female","Prefer not to say"};

        JComboBox<String> genderBox = new JComboBox<>(genders);

        genderBox.setBounds(40, 298, 250, 38);
        genderBox.setFont(new Font("Arial", Font.PLAIN, 15));
        genderBox.setBackground(Color.WHITE);
        formCard.add(genderBox);
        
        // BOTTOM SEPARATOR
        downSeparator = new JPanel();
        downSeparator.setBackground(Color.BLACK);
        downSeparator.setBounds(40, 365, 550, 1);
        formCard.add(downSeparator);
        
        
        // BACK BUTTON
        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 405, 130, 42);
        backButton.setFont(new Font("Arial", Font.BOLD, 15));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusable(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        formCard.add(backButton); 
        
        
        // CREATE BUTTON
        JButton createButton = new JButton("Create Account");
        createButton.setBounds(445, 405, 145, 42);
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBackground(Color.BLACK);
        createButton.setForeground(Color.WHITE);
        createButton.setFocusable(false);
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createButton.setBorderPainted(false);
        formCard.add(createButton);
}
}