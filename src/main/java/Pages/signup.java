package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class signup extends JFrame implements ActionListener {

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
    private JLabel point1, point2, point3, point4;
    private JLabel title, subtitle;
    private JLabel usernameLabel, passwordLabel, confirmPasswordLabel, regionLabel;
    private JLabel loginNav;
    private JTextField username;
    private JPasswordField password, confirmPassword;
    private JComboBox<String> region;
    private JButton signup;

    public signup() {

        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
setResizable(false);
        setLayout(null);

        sidePane = new JPanel();
sidePane.setBackground(Color.BLACK);
        sidePane.setBounds(0, 0, 300, 600);
        sidePane.setLayout(null);

        brandTitle = new JLabel("E-VOTE", JLabel.CENTER);
        brandTitle.setFont(new Font("Arial", Font.BOLD, 42));
brandTitle.setForeground(Color.WHITE);
        brandTitle.setBounds(0, 70, 300, 50);
        sidePane.add(brandTitle);

        brandSubtitle = new JLabel("Fast. Secure. Efficient", JLabel.CENTER);
        brandSubtitle.setFont(new Font("Arial", Font.ITALIC, 14));
brandSubtitle.setForeground(Color.WHITE);
        brandSubtitle.setBounds(0, 110, 300, 50);
        sidePane.add(brandSubtitle);

separator = new JSeparator(JSeparator.HORIZONTAL);
separator.setForeground(Color.WHITE);
        separator.setBounds(50, 160, 200, 10);
        sidePane.add(separator);

        point1 = new JLabel("-Leading E-Voting System in the country", SwingConstants.CENTER);
        point1.setFont(new Font("Arial", Font.BOLD, 14));
point1.setForeground(Color.WHITE);
        point1.setBounds(0, 180, 300, 50);
        sidePane.add(point1);

        point2 = new JLabel("-Easy to use", SwingConstants.CENTER);
        point2.setFont(new Font("Arial", Font.BOLD, 14));
point2.setForeground(Color.WHITE);
        point2.setBounds(0, 210, 300, 50);
        sidePane.add(point2);

        point3 = new JLabel("-Secure and trustworthy", SwingConstants.CENTER);
        point3.setFont(new Font("Arial", Font.BOLD, 14));
point3.setForeground(Color.WHITE);
        point3.setBounds(0, 240, 300, 50);
        sidePane.add(point3);

        point4 = new JLabel("-Fast performance", SwingConstants.CENTER);
        point4.setFont(new Font("Arial", Font.BOLD, 14));
point4.setForeground(Color.WHITE);
        point4.setBounds(0, 270, 300, 50);
        sidePane.add(point4);

        add(sidePane);

        title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(350, 30, 300, 50);
        add(title);

        subtitle = new JLabel("Register your account to participate in elections.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setBounds(350, 70, 600, 30);
        add(subtitle);

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(350, 110, 100, 30);
        add(usernameLabel);

        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setBounds(350, 140, 450, 40);
        add(username);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(350, 195, 100, 30);
        add(passwordLabel);

        password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setBounds(350, 225, 450, 40);
        add(password);

        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordLabel.setBounds(350, 280, 150, 30);
        add(confirmPasswordLabel);

        confirmPassword = new JPasswordField();
        confirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPassword.setBounds(350, 310, 450, 40);
        add(confirmPassword);

        regionLabel = new JLabel("Region");
        regionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        regionLabel.setBounds(350, 365, 100, 30);
        add(regionLabel);

        region = new JComboBox<>(regions);
        region.setFont(new Font("Arial", Font.PLAIN, 14));
        region.setBounds(350, 395, 450, 40);
        add(region);

        signup = new JButton("Create Account");
        signup.setFont(new Font("Arial", Font.BOLD, 14));
        signup.setBounds(350, 455, 450, 45);
        add(signup);

        loginNav = new JLabel("Already have an account? Click here to login");
        loginNav.setFont(new Font("Arial", Font.PLAIN, 10));
        loginNav.setBounds(350, 510, 450, 30);
loginNav.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginNav.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new LogIn(); dispose(); }
        });
        add(loginNav);

        //Add to ActionListener
        signup.addActionListener(this);

setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == signup) {

            String usernameValue = username.getText().trim();
            String passwordValue = String.valueOf(password.getPassword());
            String confirmPasswordValue = String.valueOf(confirmPassword.getPassword());
            String regionValue = (String) region.getSelectedItem();

            if (usernameValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usernameValue.length() < 6) {
                JOptionPane.showMessageDialog(this, "Username should be at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (passwordValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (passwordValue.length() < 6) {
                JOptionPane.showMessageDialog(this, "Password should be at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (confirmPasswordValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please confirm your password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!passwordValue.equals(confirmPasswordValue)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (region.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Please select your region.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (UserRegistry.userExists(usernameValue)) {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swing_demo", "root", "");

                PreparedStatement check = connection.prepareStatement(
                        "SELECT username FROM users WHERE username = ?");
                check.setString(1, usernameValue);
                ResultSet result = check.executeQuery();

                if (result.next()) {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
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
                        JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new LogIn();
                    }
                }

                connection.close();

            } catch (SQLException sql) {
                JOptionPane.showMessageDialog(this, "Internal server error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                sql.printStackTrace();
            }
        }
    }
}
