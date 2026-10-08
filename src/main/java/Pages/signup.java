
package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class signup extends JFrame implements ActionListener {

    // PRIMARY BRAND COLORS
    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);

    private String[] points = {
            "Leading E-Voting System in the country",
            "Easy to use",
            "Secure and trustworthy",
            "Fast performance"
    };

    // USER INPUTS
    private JTextField username;
    private JPasswordField password;
    private JPasswordField confirmPassword;

    // USER INFORMATION
    private JComboBox<String> region;

    // BUTTONS
    private JButton signup;
    private JButton passwordEye;
    private JButton confirmPasswordEye;

    // LABELS
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel regionLabel;
    private JLabel title;
    private JLabel subtitle;
    private JLabel brandTitle;
    private JLabel brandSubtitle;
    private JLabel loginNav;

    // ERROR LABELS
    private JLabel usernameError;
    private JLabel passwordError;
    private JLabel confirmPasswordError;
    private JLabel regionError;

    // PANELS
    private JPanel sidePane;

    private JSeparator separator;

    // REGIONS
    private String[] regions = {
            "Select your region",
            "National Capital Region (NCR)",
            "Cordillera Administrative Region (CAR)",
            "Region I - Ilocos Region",
            "Region II - Cagayan Valley",
            "Region III - Central Luzon",
            "Region IV-A - CALABARZON",
            "MIMAROPA Region",
            "Region V - Bicol Region",
            "Region VI - Western Visayas",
            "Negros Island Region (NIR)",
            "Region VII - Central Visayas",
            "Region VIII - Eastern Visayas",
            "Region IX - Zamboanga Peninsula",
            "Region X - Northern Mindanao",
            "Region XI - Davao Region",
            "Region XII - SOCCSKSARGEN",
            "Region XIII - Caraga",
            "Bangsamoro Autonomous Region in Muslim Mindanao (BARMM)"
    };

    // FUNCTION FOR CREATING SIDE PANEL UI
    private void createSideUI() {

        sidePane = new JPanel();
        sidePane.setBackground(BRAND);
        sidePane.setBounds(0, 0, 300, 600);
        sidePane.setLayout(null);

        // CREATE BRAND TITLE
        brandTitle = new JLabel("E-VOTE", JLabel.CENTER);
        brandTitle.setFont(new Font("Arial", Font.BOLD, 42));
        brandTitle.setForeground(ALT_FONTS);
        brandTitle.setBounds(0, 70, 300, 50);
        sidePane.add(brandTitle);

        // CREATE BRAND SUB-TITLE
        brandSubtitle = new JLabel("Fast. Secure. Efficient", JLabel.CENTER);
        brandSubtitle.setFont(new Font("Arial", Font.ITALIC, 14));
        brandSubtitle.setForeground(ALT_FONTS);
        brandSubtitle.setBounds(0, 110, 300, 50);
        sidePane.add(brandSubtitle);

        // CREATE LINE SEPARATOR
        separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(ALT_FONTS);
        separator.setBounds(50, 160, 200, 10);
        sidePane.add(separator);

        // CREATE POINTS
        for (int i = 0; i < points.length; i++) {

            JLabel l = new JLabel("-" + points[i], SwingConstants.CENTER);
            l.setFont(new Font("Arial", Font.BOLD, 14));
            l.setForeground(ALT_FONTS);
            l.setBounds(0, 180 + (i * 30), 300, 50);

            sidePane.add(l);
        }
    }


    // FUNCTION TO CREATE SIGNUP UI
    private void createUI() {

        // TITLE
        title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(BRAND);
        title.setBounds(350, 30, 300, 50);
        add(title);


        // SUBTITLE
        subtitle = new JLabel(
                "Register your account to participate in elections.",
                JLabel.LEFT
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(HOVER);
        subtitle.setBounds(350, 70, 600, 40);
        add(subtitle);


        // USERNAME
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(BRAND);
        usernameLabel.setBounds(350, 115, 100, 30);
        add(usernameLabel);


        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setForeground(BRAND);
        username.setBounds(350, 145, 450, 40);

        username.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BRAND, 1),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );

        username.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (!usernameError.isVisible()) {

                    username.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(BRAND, 2),
                                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                            )
                    );
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (!usernameError.isVisible()) {

                    username.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(BRAND, 1),
                                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                            )
                    );
                }
            }
        });

        add(username);


        // USERNAME ERROR
        usernameError = new JLabel("");
        usernameError.setFont(new Font("Arial", Font.PLAIN, 11));
        usernameError.setForeground(ERROR);
        usernameError.setBounds(350, 185, 450, 20);
        usernameError.setVisible(false);

        add(usernameError);


        // PASSWORD
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(BRAND);
        passwordLabel.setBounds(350, 205, 100, 30);
        add(passwordLabel);


        password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setForeground(BRAND);
        password.setBounds(350, 235, 410, 40);

        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BRAND, 1),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );

        password.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (!passwordError.isVisible()) {

                    password.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(BRAND, 2),
                                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                            )
                    );
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (!passwordError.isVisible()) {

                    password.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(BRAND, 1),
                                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                            )
                    );
                }
            }
        });

        add(password);


        // PASSWORD SHOW BUTTON
        passwordEye = new JButton("H");
        passwordEye.setFont(new Font("Arial", Font.PLAIN, 9));
        passwordEye.setBackground(BRAND);
        passwordEye.setForeground(ALT_FONTS);
        passwordEye.setBorderPainted(false);
        passwordEye.setFocusPainted(false);
        passwordEye.setBounds(760, 235, 40, 40);

        passwordEye.addActionListener(e -> {

            if (password.getEchoChar() == (char) 0) {

                password.setEchoChar('•');
                passwordEye.setText("S");

            } else {

                password.setEchoChar((char) 0);
                passwordEye.setText("H");
            }
        });

        add(passwordEye);


        // PASSWORD ERROR
        passwordError = new JLabel("");
        passwordError.setFont(new Font("Arial", Font.PLAIN, 11));
        passwordError.setForeground(ERROR);
        passwordError.setBounds(350, 275, 450, 20);
        passwordError.setVisible(false);

        add(passwordError);


        // CONFIRM PASSWORD
        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setForeground(BRAND);
        confirmPasswordLabel.setBounds(350, 295, 150, 30);
        add(confirmPasswordLabel);


        confirmPassword = new JPasswordField();
        confirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPassword.setForeground(BRAND);
        confirmPassword.setBounds(350, 325, 410, 40);

        confirmPassword.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BRAND, 1),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );

        confirmPassword.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (!confirmPasswordError.isVisible()) {

                    confirmPassword.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(BRAND, 2),
                                    BorderFactory.createEmptyBorder(7, 9, 7, 9)
                            )
                    );
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (!confirmPasswordError.isVisible()) {

                    confirmPassword.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(BRAND, 1),
                                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                            )
                    );
                }
            }
        });

        add(confirmPassword);


        // PASSWORD SHOW BUTTON
        confirmPasswordEye = new JButton("H");
        confirmPasswordEye.setFont(new Font("Arial", Font.PLAIN, 9));
        confirmPasswordEye.setBackground(BRAND);
        confirmPasswordEye.setForeground(ALT_FONTS);
        confirmPasswordEye.setBorderPainted(false);
        confirmPasswordEye.setFocusPainted(false);
        confirmPasswordEye.setBounds(760, 325, 40, 40);

        confirmPasswordEye.addActionListener(e -> {

            if (confirmPassword.getEchoChar() == (char) 0) {

                confirmPassword.setEchoChar('•');
                confirmPasswordEye.setText("S");

            } else {

                confirmPassword.setEchoChar((char) 0);
                confirmPasswordEye.setText("H");
            }
        });
        add(confirmPasswordEye);


        // CONFIRM PASSWORD ERROR
        confirmPasswordError = new JLabel("");
        confirmPasswordError.setFont(new Font("Arial", Font.PLAIN, 11));
        confirmPasswordError.setForeground(ERROR);
        confirmPasswordError.setBounds(350, 365, 450, 20);
        confirmPasswordError.setVisible(false);

        add(confirmPasswordError);


        // REGION
        regionLabel = new JLabel("Region");
        regionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        regionLabel.setForeground(BRAND);
        regionLabel.setBounds(350, 385, 100, 30);
        add(regionLabel);


        region = new JComboBox<>(regions);
        region.setFont(new Font("Arial", Font.PLAIN, 14));
        region.setBounds(350, 415, 450, 40);
        region.setBackground(ALT_FONTS);
        region.setForeground(BRAND);

        add(region);


        // REGION ERROR
        regionError = new JLabel("");
        regionError.setFont(new Font("Arial", Font.PLAIN, 11));
        regionError.setForeground(ERROR);
        regionError.setBounds(350, 455, 450, 20);
        regionError.setVisible(false);

        add(regionError);


        // SIGNUP BUTTON
        signup = new JButton("Create Account");
        signup.setFont(new Font("Arial", Font.BOLD, 14));
        signup.setBackground(BRAND);
        signup.setForeground(ALT_FONTS);
        signup.setBorderPainted(false);
        signup.setFocusPainted(false);
        signup.setBounds(350, 470, 450, 45);

        signup.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                signup.setBackground(HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signup.setBackground(BRAND);
            }
        });

        add(signup);


        // LOGIN HYPERLINK
        loginNav = new JLabel(
                "<html>Already have an account? <u>Click here to login</u></html>"
        );

        loginNav.setFont(new Font("Arial", Font.PLAIN, 10));
        loginNav.setForeground(HOVER);
        loginNav.setBounds(350, 520, 450, 30);
        loginNav.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginNav.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                loginNav.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginNav.setForeground(HOVER);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new LogIn();
                dispose();
            }
        });

        add(loginNav);
    }


    // FUNCTION FOR RESETTING FIELD BORDER
    private void resetBorder(JTextField field) {

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BRAND, 1),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                )
        );
    }


    // FUNCTION FOR SETTING ERROR BORDER
    private void errorBorder(JTextField field) {

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ERROR, 2),
                        BorderFactory.createEmptyBorder(7, 9, 7, 9)
                )
        );
    }

    // MAIN FRAME
    public signup() {

        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setBackground(ALT_FONTS);
        setLayout(null);

        createSideUI();
        add(sidePane);

        createUI();

        setLocationRelativeTo(null);
        setVisible(true);

        // BUTTONS
        signup.addActionListener(this);
    }


    // BUTTON ACTIONS
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == signup) {

            // RESET ERRORS
            usernameError.setVisible(false);
            passwordError.setVisible(false);
            confirmPasswordError.setVisible(false);
            regionError.setVisible(false);

            resetBorder(username);
            resetBorder(password);
            resetBorder(confirmPassword);


            // USERNAME VALIDATION
            if (username.getText().isEmpty()) {

                usernameError.setText("Username is required.");
                usernameError.setVisible(true);

                errorBorder(username);

            }

            else if (username.getText().length() < 6) {

                usernameError.setText(
                        "Username should be at least 6 characters."
                );

                usernameError.setVisible(true);

                errorBorder(username);
            }


            // PASSWORD VALIDATION
            if (password.getPassword().length == 0) {

                passwordError.setText("Password is required.");
                passwordError.setVisible(true);

                errorBorder(password);

            }

            else if (password.getPassword().length < 6) {

                passwordError.setText(
                        "Password should be at least 6 characters."
                );

                passwordError.setVisible(true);

                errorBorder(password);
            }


            // CONFIRM PASSWORD VALIDATION
            if (confirmPassword.getPassword().length == 0) {

                confirmPasswordError.setText(
                        "Please confirm your password."
                );

                confirmPasswordError.setVisible(true);

                errorBorder(confirmPassword);

            }

            else if (!String.valueOf(password.getPassword())
                    .equals(String.valueOf(confirmPassword.getPassword()))) {

                confirmPasswordError.setText(
                        "Passwords do not match."
                );

                confirmPasswordError.setVisible(true);

                errorBorder(confirmPassword);
            }


            // REGION VALIDATION
            if (region.getSelectedIndex() == 0) {

                regionError.setText("Please select your region.");
                regionError.setVisible(true);
            }


            // SUCCESS
            if (!usernameError.isVisible()
                    && !passwordError.isVisible()
                    && !confirmPasswordError.isVisible()
                    && !regionError.isVisible()) {

                String usernameValue = username.getText();
                String passwordValue = String.valueOf(password.getPassword());
                String regionValue = (String) region.getSelectedItem();

                try {

                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/swing_demo",
                            "root",
                            ""
                    );

                    // CHECK IF USERNAME ALREADY EXISTS
                    PreparedStatement check = connection.prepareStatement(
                            "SELECT username FROM users WHERE username = ?"
                    );

                    check.setString(1, usernameValue);

                    ResultSet result = check.executeQuery();

                    if (result.next()) {

                        // USERNAME ALREADY EXISTS
                        usernameError.setText("Username already exists.");
                        usernameError.setVisible(true);

                        errorBorder(username);

                    } else {

                        // CREATE ACCOUNT
                        PreparedStatement info = connection.prepareStatement(
                                "INSERT INTO users (username, password, region) VALUES (?, ?, ?)"
                        );

                        info.setString(1, usernameValue);
                        info.setString(2, passwordValue);
                        info.setString(3, regionValue);

                        int rows = info.executeUpdate();

                        if (rows > 0) {

                            dispose();
                            new LogIn();

                        }
                    }

                    result.close();
                    check.close();
                    connection.close();

                } catch (SQLException sql) {

                    passwordError.setText(
                            "Oops! Internal server error. Please try again later."
                    );

                    passwordError.setVisible(true);

                    usernameError.setText(
                            "Oops! Internal server error. Please try again later."
                    );

                    usernameError.setVisible(true);
                }
            }

        }

    }

}
