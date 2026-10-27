package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LogIn extends JFrame implements ActionListener {

    private JPanel sidePane;
    private JLabel brandTitle, brandSubtitle;
    private JLabel point1, point2, point3, point4;
    private JLabel title, subtitle;
    private JLabel usernameLabel, passwordLabel;
    private JLabel createNav;
    private JTextField username;
    private JPasswordField password;
    private JButton login;

    public LogIn() {

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

        title = new JLabel("Welcome Back!");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(350, 50, 300, 50);
        add(title);

        subtitle = new JLabel("Sign in to continue using your account.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setBounds(350, 90, 600, 30);
        add(subtitle);

        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(350, 150, 100, 30);
        add(usernameLabel);

        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setBounds(350, 180, 450, 40);
        add(username);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(350, 240, 100, 30);
        add(passwordLabel);

        password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setBounds(350, 270, 450, 40);
        add(password);

        login = new JButton("Login");
        login.setFont(new Font("Arial", Font.BOLD, 14));
        login.setBounds(350, 340, 450, 50);
        add(login);

        createNav = new JLabel("No account? Click here to create one");
        createNav.setFont(new Font("Arial", Font.PLAIN, 10));
        createNav.setBounds(350, 400, 450, 30);
        createNav.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createNav.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { new signup(); dispose(); }
        });
        add(createNav);

        //Add to ActionListener
        login.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login) {

            String usernameText = username.getText();
            String passwordText = new String(password.getPassword());

            if (usernameText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usernameText.length() < 6) {
                JOptionPane.showMessageDialog(this, "Username should be at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (passwordText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (passwordText.length() < 6) {
                JOptionPane.showMessageDialog(this, "Password should be at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/swing_demo", "root", "");
                PreparedStatement stmt = connection.prepareStatement(
                        "SELECT username, password FROM users WHERE username = ? AND password = ?");
                stmt.setString(1, usernameText);
                stmt.setString(2, passwordText);
                ResultSet data = stmt.executeQuery();

                if (data.next()) {
                    UserRegistry.setCurrentUser(usernameText);
                    dispose();
                    if (usernameText.equals("administrator")) {
                        new AdminPanel();
                    } else {
                        new UserInterface();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

                connection.close();

            } catch (SQLException sql) {
                JOptionPane.showMessageDialog(this, "Internal server error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                sql.printStackTrace();
            }
        }
    }
}
