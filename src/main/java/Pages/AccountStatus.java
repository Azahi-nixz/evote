package Pages;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class AccountStatus extends JFrame implements ActionListener {

    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);

    // SIDE UI AND MAIN UI
    private JPanel sideUI;
    private JPanel mainUI;

    // ACCOUNT STATUS PANELS
    private JPanel verifiedUI;
    private JPanel notVerifiedUI;

    private JLabel user, title, status;

    private final String sideBtn[] = {"Home", "Vote", "Account"};

    // FUNCTION TO CREATE SIDE PANEL
    public void sidePanel() {

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
            button.addActionListener(this);
            button.setFocusable(false);

            button.setBorder(
                    BorderFactory.createLineBorder(ALT_FONTS, 1)
            );
            button.setBorderPainted(true);

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
        user.setBackground(BRAND);
        user.setBounds(0, 0, 300, 120);

        sideUI.add(user);
    }


    // VERIFIED ACCOUNT PAGE
    public void isVerifiedPage() {

        verifiedUI = new JPanel();

        verifiedUI.setBackground(ALT_FONTS);
        verifiedUI.setLayout(null);
        verifiedUI.setBounds(0, 0, 700, 600);

        // TITLE
        title = new JLabel("Account Status", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(BRAND);
        title.setBounds(100, 30, 500, 50);

        verifiedUI.add(title);

        // VERIFIED STATUS
        status = new JLabel(
                "Your account is verified!",
                SwingConstants.CENTER
        );

        status.setFont(new Font("Arial", Font.BOLD, 20));
        status.setForeground(new Color(0, 150, 0));
        status.setBounds(100, 85, 500, 40);

        verifiedUI.add(status);

        // BUTTON OPTIONS
        String options[] = {
                "Vote",
                "View Profile",
                "Info",
                "File a Candidacy"
        };

        for (int i = 0; i < options.length; i++) {

            JButton button = new JButton(options[i]);

            button.setBackground(BRAND);
            button.setForeground(ALT_FONTS);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setFocusable(false);

            button.setBorder(
                    BorderFactory.createLineBorder(BRAND, 1)
            );
            button.setBorderPainted(true);

            // BUTTON POSITIONS
            if (i < 2) {
                button.setBounds(
                        60 + (i * 300), 180, 250, 100
                );
            } else {
                button.setBounds(
                        60 + ((i - 2) * 300), 320, 250, 100
                );
            }

            // HOVER EFFECT
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

            button.addActionListener(this);

            verifiedUI.add(button);
        }

        mainUI.add(verifiedUI);
    }


    // NON-VERIFIED ACCOUNT PAGE
    public void notVerifiedPage() {

        notVerifiedUI = new JPanel();

        notVerifiedUI.setBackground(ALT_FONTS);
        notVerifiedUI.setLayout(null);
        notVerifiedUI.setBounds(0, 0, 700, 600);

        // TITLE
        JLabel title = new JLabel(
                "Account Not Verified Yet",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(BRAND);
        title.setBounds(50, 150, 600, 60);

        notVerifiedUI.add(title);

        // DESCRIPTION
        JLabel description = new JLabel(
                "Please verify your account to access voting features.",
                SwingConstants.CENTER
        );

        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setForeground(BRAND);
        description.setBounds(50, 220, 600, 40);

        notVerifiedUI.add(description);

        // VERIFY BUTTON
        JButton verifyButton = new JButton("Verify Account");

        verifyButton.setBackground(BRAND);
        verifyButton.setForeground(ALT_FONTS);
        verifyButton.setFont(new Font("Arial", Font.BOLD, 18));
        verifyButton.setBounds(225, 300, 250, 60);
        verifyButton.setFocusable(false);

        verifyButton.setBorder(
                BorderFactory.createLineBorder(BRAND, 1)
        );
        verifyButton.setBorderPainted(true);

        // HOVER EFFECT
        verifyButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                verifyButton.setBackground(HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                verifyButton.setBackground(BRAND);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                verifyButton.setBackground(HOVER);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                verifyButton.setBackground(HOVER);
            }
        });

        verifyButton.addActionListener(this);

        notVerifiedUI.add(verifyButton);

        mainUI.add(notVerifiedUI);
    }


    // CHANGE ACCOUNT PAGE VISIBILITY
    public void showVerifiedPage(boolean verified) {

        verifiedUI.setVisible(verified);
        notVerifiedUI.setVisible(!verified);

        mainUI.revalidate();
        mainUI.repaint();
    }


    AccountStatus() {

        // MAIN FRAME
        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setBackground(ALT_FONTS);
        setLayout(null);
        setLocationRelativeTo(null);

        // SIDE PANEL
        sidePanel();
        add(sideUI);

        // MAIN PANEL
        mainUI = new JPanel();

        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);

        add(mainUI);


        isVerifiedPage();
        notVerifiedPage();


        showVerifiedPage(false);

        setVisible(true);
    }


    // BUTTON ACTIONS
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
                break;

            case "View Profile":
                System.out.println("View Profile clicked");
                break;

            case "Info":
                System.out.println("Info clicked");
                break;

            case "File a Candidacy":
                System.out.println("File a Candidacy clicked");
                break;

            case "Verify Account":
                dispose();
                new registerPage();
        }
    }
}

