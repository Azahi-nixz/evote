package Pages;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class AccountStatus extends JFrame implements ActionListener {

    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);

    private final String sideBtn[] = {"Home", "Vote", "Account"};

    private JPanel sideUI, mainUI;
    private JPanel verifiedUI, notVerifiedUI;
    private JLabel user, title, status;
    private JButton verifyButton;

    private void sidePanel() {

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
            button.setBorderPainted(true);
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
        user.setBackground(BRAND);
        user.setBounds(0, 0, 300, 120);
        sideUI.add(user);
    }

    private void buildVerifiedPage() {

        verifiedUI = new JPanel();
        verifiedUI.setBackground(ALT_FONTS);
        verifiedUI.setLayout(null);
        verifiedUI.setBounds(0, 0, 700, 600);

        title = new JLabel("Account Status", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(BRAND);
        title.setBounds(100, 30, 500, 50);
        verifiedUI.add(title);

        status = new JLabel("Your account is verified!", SwingConstants.CENTER);
        status.setFont(new Font("Arial", Font.BOLD, 20));
        status.setForeground(new Color(0, 150, 0));
        status.setBounds(100, 85, 500, 40);
        verifiedUI.add(status);

        String options[] = {"Vote", "View Profile", "Logout", "File a Candidacy"};

        for (int i = 0; i < options.length; i++) {
            JButton button = new JButton(options[i]);
            button.setBackground(BRAND);
            button.setForeground(ALT_FONTS);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setFocusable(false);
            button.setBorder(BorderFactory.createLineBorder(BRAND, 1));
            button.setBorderPainted(true);
            button.setBounds(i < 2 ? 60 + (i * 300) : 60 + ((i - 2) * 300),
                             i < 2 ? 180 : 320, 250, 100);
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { button.setBackground(HOVER); }
                public void mouseExited(MouseEvent e) { button.setBackground(BRAND); }
                public void mousePressed(MouseEvent e) { button.setBackground(HOVER); }
                public void mouseReleased(MouseEvent e) { button.setBackground(HOVER); }
            });
            button.addActionListener(this);
            verifiedUI.add(button);
        }

        mainUI.add(verifiedUI);
    }

    private void buildNotVerifiedPage() {

        notVerifiedUI = new JPanel();
        notVerifiedUI.setBackground(ALT_FONTS);
        notVerifiedUI.setLayout(null);
        notVerifiedUI.setBounds(0, 0, 700, 600);

        JLabel notVerifiedTitle = new JLabel("Account Not Verified Yet", SwingConstants.CENTER);
        notVerifiedTitle.setFont(new Font("Arial", Font.BOLD, 32));
        notVerifiedTitle.setForeground(BRAND);
        notVerifiedTitle.setBounds(50, 150, 600, 60);
        notVerifiedUI.add(notVerifiedTitle);

        JLabel description = new JLabel(
                "Please verify your account to access voting features.",
                SwingConstants.CENTER);
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setForeground(BRAND);
        description.setBounds(50, 220, 600, 40);
        notVerifiedUI.add(description);

        verifyButton = new JButton("Verify Account");
        verifyButton.setBackground(BRAND);
        verifyButton.setForeground(ALT_FONTS);
        verifyButton.setFont(new Font("Arial", Font.BOLD, 18));
        verifyButton.setBounds(225, 300, 250, 60);
        verifyButton.setFocusable(false);
        verifyButton.setBorder(BorderFactory.createLineBorder(BRAND, 1));
        verifyButton.setBorderPainted(true);
        verifyButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { verifyButton.setBackground(HOVER); }
            public void mouseExited(MouseEvent e) { verifyButton.setBackground(BRAND); }
            public void mousePressed(MouseEvent e) { verifyButton.setBackground(HOVER); }
            public void mouseReleased(MouseEvent e) { verifyButton.setBackground(HOVER); }
        });
        notVerifiedUI.add(verifyButton);

        mainUI.add(notVerifiedUI);
    }

    AccountStatus() {

        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setBackground(ALT_FONTS);
        setLayout(null);
        setLocationRelativeTo(null);

        sidePanel();
        add(sideUI);

        mainUI = new JPanel();
        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);
        add(mainUI);

        buildVerifiedPage();
        buildNotVerifiedPage();

        boolean verified = UserRegistry.currentUser != null
                && UserRegistry.isVerified(UserRegistry.currentUser);
        verifiedUI.setVisible(verified);
        notVerifiedUI.setVisible(!verified);

        // Button listeners
        verifyButton.addActionListener(this);

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
                break;

            case "View Profile":
                String voterId = UserRegistry.currentUser != null
                        ? UserRegistry.currentUser : "unknown";
                Voter voter = UserRegistry.getVoter(voterId);
                String name = voter != null ? voter.getName() : "Not set";
                String verifyStatus = UserRegistry.isVerified(voterId) ? "Verified" : "Not Verified";
                String candidacyStatus = CandidateRegistry.getCandidacyStatus(voterId);

                JOptionPane.showMessageDialog(this,
                        "Voter ID: " + voterId + "\n"
                        + "Name: " + name + "\n"
                        + "Verification: " + verifyStatus + "\n"
                        + "Candidacy: " + candidacyStatus,
                        "Voter Profile",
                        JOptionPane.INFORMATION_MESSAGE);
                break;

            case "Logout":
                dispose();
                UserRegistry.currentUser = null;
                new LogIn();
                break;

            case "File a Candidacy":
                dispose();
                new CandidacyPage();
                break;

            case "Verify Account":
                dispose();
                new registerPage();
                break;
        }
    }
}
