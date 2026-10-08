
package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LogIn extends JFrame implements ActionListener {

    // PRIMARY BRAND COLORS
    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);

    private JTextField username;
    private JPasswordField password;

    private JButton login;
    private JButton passwordEye;
    private JLabel usernameLabel, passwordLabel;
    private JLabel usernameError, passwordError;
    private JLabel title, subtitle, brandTitle, brandSubtitle, createNav;

    private JPanel sidePane;
    private JSeparator separator;

    private String[] points = {
            "Leading E-Voting System in the country",
            "Easy to use",
            "Secure and trustworthy",
            "Fast performance"
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


    // MAIN FRAME
    public LogIn() {

        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setBackground(ALT_FONTS);
        setLayout(null);

        createSideUI();

        add(sidePane);

        // TITLE
        title = new JLabel("Welcome Back!");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(BRAND);
        title.setBounds(350, 50, 300, 50);
        add(title);


        // SUBTITLE
        subtitle = new JLabel(
                "Sign in to continue using your account.",
                JLabel.LEFT
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(HOVER);
        subtitle.setBounds(350, 90, 700, 50);
        add(subtitle);


        // USERNAME
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(BRAND);
        usernameLabel.setBounds(350, 150, 100, 30);
        add(usernameLabel);


        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 14));
        username.setForeground(BRAND);
        username.setBounds(350, 180, 450, 40);

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



        // PASSWORD
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(BRAND);
        passwordLabel.setBounds(350, 245, 100, 30);
        add(passwordLabel);


        password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        password.setForeground(BRAND);
        password.setBounds(350, 275, 410, 40);

        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BRAND, 1),
                        BorderFactory.createEmptyBorder(8, 10, 8, 14)
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
        passwordEye = new JButton("S");
        passwordEye.setFont(new Font("Arial", Font.PLAIN, 9));
        passwordEye.setBackground(BRAND);
        passwordEye.setForeground(ALT_FONTS);
        passwordEye.setBorderPainted(false);
        passwordEye.setFocusPainted(false);
        passwordEye.setBounds(760, 275, 40, 40);

        passwordEye.addActionListener(e -> {

            if (password.getEchoChar() == (char) 0) {

                password.setEchoChar('•');
                passwordEye.setText("X");
            } else {

                password.setEchoChar((char) 0);
                passwordEye.setText("S");
            }
        });

        add(passwordEye);

        // LOG-IN BUTTON
        login = new JButton("Login");
        login.setFont(new Font("Arial", Font.BOLD, 14));
        login.setBackground(BRAND);
        login.setForeground(ALT_FONTS);
        login.setBorderPainted(false);
        login.setFocusPainted(false);
        login.setBounds(350, 355, 450, 50);

        login.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                login.setBackground(HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                login.setBackground(BRAND);
            }
        });

        add(login);


        // CREATE ACCOUNT HYPERLINK
        createNav = new JLabel(
                "<html>No account? <u>Click here to create one</u></html>"
        );

        createNav.setFont(new Font("Arial", Font.PLAIN, 10));
        createNav.setForeground(HOVER);
        createNav.setBounds(350, 410, 450, 30);
        createNav.setCursor(new Cursor(Cursor.HAND_CURSOR));

        createNav.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                createNav.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createNav.setForeground(HOVER);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new signup();
                dispose();
            }
        });

        add(createNav);

        // USERNAME ERROR
        usernameError = new JLabel("");
        usernameError.setFont(new Font("Arial", Font.PLAIN, 11));
        usernameError.setForeground(ERROR);
        usernameError.setBounds(350, 220, 450, 20);
        usernameError.setVisible(false);

        add(usernameError);


        // PASSWORD ERROR
        passwordError = new JLabel("");
        passwordError.setFont(new Font("Arial", Font.PLAIN, 11));
        passwordError.setForeground(ERROR);
        passwordError.setBounds(350, 315, 450, 20);
        passwordError.setVisible(false);

        add(passwordError);


        // BUTTONS
        login.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login) {

            // RESET ERRORS
            usernameError.setVisible(false);
            passwordError.setVisible(false);

            username.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BRAND, 1),
                            BorderFactory.createEmptyBorder(8, 10, 8, 10)
                    )
            );

            password.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BRAND, 1),
                            BorderFactory.createEmptyBorder(8, 10, 8, 10)
                    )
            );


            // USERNAME VALIDATION
            if (username.getText().isEmpty()) {

                usernameError.setText("Username is required.");
                usernameError.setVisible(true);

                username.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(ERROR, 2),
                                BorderFactory.createEmptyBorder(7, 9, 7, 9)
                        )
                );

            }

            else if (username.getText().length() < 6) {

                usernameError.setText(
                        "Username should be at least 6 characters."
                );

                usernameError.setVisible(true);

                username.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(ERROR, 2),
                                BorderFactory.createEmptyBorder(7, 9, 7, 9)
                        )
                );
            }


            // PASSWORD VALIDATION
            if (password.getPassword().length == 0) {

                passwordError.setText("Password is required.");
                passwordError.setVisible(true);

                password.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(ERROR, 2),
                                BorderFactory.createEmptyBorder(7, 9, 7, 9)
                        )
                );

            }

            else if (password.getPassword().length < 6) {

                passwordError.setText(
                        "Password should be at least 6 characters."
                );

                passwordError.setVisible(true);

                password.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(ERROR, 2),
                                BorderFactory.createEmptyBorder(7, 9, 7, 9)
                        )
                );
            }


            if (!usernameError.isVisible() && !passwordError.isVisible()) {

                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo",
                            "root", "");

                    PreparedStatement info = (PreparedStatement) connection.prepareStatement(
                            "SELECT username, password from users where username = ? and password = ?"
                    );

                    info.setString(1, username.getText());
                    info.setString(2, new String(password.getPassword()));
                    ResultSet data = info.executeQuery();

                    if (data.next()) {

                        dispose();
                        new UserInterface();

                    }

                    else {
                        passwordError.setText("Wrong username or password");
                        passwordError.setVisible(true);

                        usernameError.setText("Wrong username or password");
                        usernameError.setVisible(true);
                    }
                }
                catch (SQLException sql) {
                    passwordError.setText(
                            "Oops! Internal server error. Please try again later."
                    );
                    passwordError.setVisible(true);

                    usernameError.setText(
                            "Oops! Internal server error. Please try again later."
                    );
                    usernameError.setVisible(true);
                    sql.printStackTrace();
                }
            }
        }
    }
}