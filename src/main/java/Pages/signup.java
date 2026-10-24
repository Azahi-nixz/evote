package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class signup extends JFrame implements ActionListener {

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

    private JPanel sidePane;
    private JSeparator separator;
    private JLabel brandTitle, brandSubtitle;
    private JLabel title, subtitle;
    private JLabel usernameLabel, passwordLabel, confirmPasswordLabel, regionLabel;
    private JLabel usernameError, passwordError, confirmPasswordError, regionError;
    private JLabel loginNav;
    private JTextField username;
    private JPasswordField password, confirmPassword;
    private JComboBox<String> region;
    private JButton signup, passwordEye, confirmPasswordEye;

    private void createSideUI() {

        sidePane = new JPanel();
        sidePane.setBackground(BRAND);
        sidePane.setBounds(0, 0, 300, 600);
        sidePane.setLayout(null);

        brandTitle = new JLabel("E-VOTE", JLabel.CENTER);
        brandTitle.setFont(new Font("Arial", Font.BOLD, 42));
        brandTitle.setForeground(ALT_FONTS);
        brandTitle.setBounds(0, 70, 300, 50);
        sidePane.add(brandTitle);

        brandSubtitle = new JLabel("Fast. Secure. Efficient", JLabel.CENTER);
        brandSubtitle.setFont(new Font("Arial", Font.ITALIC, 14));
        brandSubtitle.setForeground(ALT_FONTS);
        brandSubtitle.setBounds(0, 110, 300, 50);
        sidePane.add(brandSubtitle);

        separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(ALT_FONTS);
        separator.setBounds(50, 160, 200, 10);
        sidePane.add(separator);

        for (int i = 0; i < points.length; i++) {
            JLabel l = new JLabel("-" + points[i], SwingConstants.CENTER);
            l.setFont(new Font("Arial", Font.BOLD, 14));
            l.setForeground(ALT_FONTS);
            l.setBounds(0, 180 + (i * 30), 300, 50);
            sidePane.add(l);
        }
    }

    private void resetBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BRAND, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    private void errorBorder(JTextField field) {
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ERROR, 2),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)));
    }

    public signup() {

        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setBackground(ALT_FONTS);
        setLayout(null);

        createSideUI();
        add(sidePane);

        title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(BRAND);
        title.setBounds(350, 30, 300, 50);
        add(title);

        subtitle = new JLabel("Register your account to participate in elections.", JLabel.LEFT);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(HOVER);
        subtitle.setBounds(350, 70, 600, 40);
        add(subtitle);

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(BRAND);
        usernameLabel.setBounds(350, 115, 100, 30);
        add(usernameLabel);

        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setForeground(BRAND);
        username.setBounds(350, 145, 450, 40);
        username.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BRAND, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        username.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (!usernameError.isVisible()) resetBorder(username);
            }
            public void focusLost(FocusEvent e) {
                if (!usernameError.isVisible()) resetBorder(username);
            }
        });
        add(username);

        usernameError = new JLabel("");
        usernameError.setFont(new Font("Arial", Font.PLAIN, 11));
        usernameError.setForeground(ERROR);
        usernameError.setBounds(350, 185, 450, 20);
        usernameError.setVisible(false);
        add(usernameError);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(BRAND);
        passwordLabel.setBounds(350, 205, 100, 30);
        add(passwordLabel);

        password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setForeground(BRAND);
        password.setBounds(350, 235, 410, 40);
        password.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BRAND, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        password.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (!passwordError.isVisible()) resetBorder(password);
            }
            public void focusLost(FocusEvent e) {
                if (!passwordError.isVisible()) resetBorder(password);
            }
        });
        add(password);

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

        passwordError = new JLabel("");
        passwordError.setFont(new Font("Arial", Font.PLAIN, 11));
        passwordError.setForeground(ERROR);
        passwordError.setBounds(350, 275, 450, 20);
        passwordError.setVisible(false);
        add(passwordError);

        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setForeground(BRAND);
        confirmPasswordLabel.setBounds(350, 295, 150, 30);
        add(confirmPasswordLabel);

        confirmPassword = new JPasswordField();
        confirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPassword.setForeground(BRAND);
        confirmPassword.setBounds(350, 325, 410, 40);
        confirmPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BRAND, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        confirmPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (!confirmPasswordError.isVisible()) resetBorder(confirmPassword);
            }
            public void focusLost(FocusEvent e) {
                if (!confirmPasswordError.isVisible()) resetBorder(confirmPassword);
            }
        });
        add(confirmPassword);

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

        confirmPasswordError = new JLabel("");
        confirmPasswordError.setFont(new Font("Arial", Font.PLAIN, 11));
        confirmPasswordError.setForeground(ERROR);
        confirmPasswordError.setBounds(350, 365, 450, 20);
        confirmPasswordError.setVisible(false);
        add(confirmPasswordError);

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

        regionError = new JLabel("");
        regionError.setFont(new Font("Arial", Font.PLAIN, 11));
        regionError.setForeground(ERROR);
        regionError.setBounds(350, 455, 450, 20);
        regionError.setVisible(false);
        add(regionError);

        signup = new JButton("Create Account");
        signup.setFont(new Font("Arial", Font.BOLD, 14));
        signup.setBackground(BRAND);
        signup.setForeground(ALT_FONTS);
        signup.setBorderPainted(false);
        signup.setFocusPainted(false);
        signup.setBounds(350, 470, 450, 45);
        signup.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { signup.setBackground(HOVER); }
            public void mouseExited(MouseEvent e) { signup.setBackground(BRAND); }
        });
        add(signup);

        loginNav = new JLabel("Already have an account? Click here to login");
        loginNav.setFont(new Font("Arial", Font.PLAIN, 10));
        loginNav.setForeground(HOVER);
        loginNav.setBounds(350, 520, 450, 30);
        loginNav.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginNav.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { loginNav.setForeground(Color.BLUE); }
            public void mouseExited(MouseEvent e) { loginNav.setForeground(HOVER); }
            public void mouseClicked(MouseEvent e) { new LogIn(); dispose(); }
        });
        add(loginNav);

        // Button listeners
        signup.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() != signup) return;

        usernameError.setVisible(false);
        passwordError.setVisible(false);
        confirmPasswordError.setVisible(false);
        regionError.setVisible(false);

        resetBorder(username);
        resetBorder(password);
        resetBorder(confirmPassword);

        if (username.getText().isEmpty()) {
            usernameError.setText("Username is required.");
            usernameError.setVisible(true);
            errorBorder(username);
        } else if (username.getText().length() < 6) {
            usernameError.setText("Username should be at least 6 characters.");
            usernameError.setVisible(true);
            errorBorder(username);
        }

        if (password.getPassword().length == 0) {
            passwordError.setText("Password is required.");
            passwordError.setVisible(true);
            errorBorder(password);
        } else if (password.getPassword().length < 6) {
            passwordError.setText("Password should be at least 6 characters.");
            passwordError.setVisible(true);
            errorBorder(password);
        }

        if (confirmPassword.getPassword().length == 0) {
            confirmPasswordError.setText("Please confirm your password.");
            confirmPasswordError.setVisible(true);
            errorBorder(confirmPassword);
        } else if (!String.valueOf(password.getPassword()).equals(String.valueOf(confirmPassword.getPassword()))) {
            confirmPasswordError.setText("Passwords do not match.");
            confirmPasswordError.setVisible(true);
            errorBorder(confirmPassword);
        }

        if (region.getSelectedIndex() == 0) {
            regionError.setText("Please select your region.");
            regionError.setVisible(true);
        }

        if (usernameError.isVisible() || passwordError.isVisible()
                || confirmPasswordError.isVisible() || regionError.isVisible()) return;

        String usernameValue = username.getText().trim();
        String passwordValue = String.valueOf(password.getPassword());
        String regionValue = (String) region.getSelectedItem();

        try {
            if (UserRegistry.userExists(usernameValue)) {
                usernameError.setText("Username already exists.");
                usernameError.setVisible(true);
                errorBorder(username);
                return;
            }

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/swing_demo", "root", "");

            PreparedStatement check = connection.prepareStatement(
                    "SELECT username FROM users WHERE username = ?");
            check.setString(1, usernameValue);
            ResultSet result = check.executeQuery();

            if (result.next()) {
                usernameError.setText("Username already exists.");
                usernameError.setVisible(true);
                errorBorder(username);
                UserRegistry.addUser(usernameValue, false);
            } else {
                PreparedStatement info = connection.prepareStatement(
                        "INSERT INTO users (username, password, region) VALUES (?, ?, ?)");
                info.setString(1, usernameValue);
                info.setString(2, passwordValue);
                info.setString(3, regionValue);
                int rows = info.executeUpdate();

                if (rows > 0) {
                    UserRegistry.addUser(usernameValue, false);
                    dispose();
                    new LogIn();
                }
            }

            connection.close();

        } catch (SQLException sql) {
            usernameError.setText("Internal server error. Please try again later.");
            usernameError.setVisible(true);
            passwordError.setText("Internal server error. Please try again later.");
            passwordError.setVisible(true);
            sql.printStackTrace();
        }
    }
}
