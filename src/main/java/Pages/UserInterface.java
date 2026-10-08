
package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame implements ActionListener {

    private final Color ALT_FONTS = new Color(255, 255, 255);
    private final Color BRAND = new Color(0, 0, 0);
    private final Color HOVER = new Color(105, 105, 105);
    private final Color ERROR = new Color(220, 0, 0);

    private final String sideBtn[] = {"Home", "Vote", "Account"};

    // SIDE UI
    private JPanel sideUI;
    private JLabel user;

    // MAIN UI
    private JPanel mainUI;

    // HOME UI
    private JLabel welcome;
    private JLabel subtitle;

    private JPanel electionCard;
    private JLabel electionTitle;
    private JLabel electionValue;

    private JPanel statusCard;
    private JLabel statusTitle;
    private JLabel status;

    private JPanel accountCard;
    private JLabel accountTitle;
    private JLabel accountValue;

    private JLabel currentElection;

    private JPanel electionPanel;
    private JLabel electionName;
    private JLabel electionDescription;
    private JButton viewButton;


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

            button.setFocusable(false);

            button.setBorder(BorderFactory.createLineBorder(ALT_FONTS, 1));
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

        // NAME
        user = new JLabel("Hello user!", SwingConstants.CENTER);
        user.setFont(new Font("Arial", Font.BOLD, 24));
        user.setForeground(ALT_FONTS);
        user.setBackground(BRAND);
        user.setBounds(0, 0, 300, 120);

        sideUI.add(user);
    }


    UserInterface() {

        // MAIN FRAME
        setTitle("E-Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setBackground(ALT_FONTS);
        setLayout(null);
        setLocationRelativeTo(null);

        sidePanel();

        add(sideUI);

        // MAIN PANEL
        mainUI = new JPanel();

        mainUI.setBackground(ALT_FONTS);
        mainUI.setLayout(null);
        mainUI.setBounds(300, 0, 700, 600);

        add(mainUI);


        // WELCOME TITLE
        welcome = new JLabel("Welcome to E-VOTE");
        welcome.setFont(new Font("Arial", Font.BOLD, 32));
        welcome.setForeground(BRAND);
        welcome.setBounds(40, 40, 600, 45);

        mainUI.add(welcome);


        // SUBTITLE
        subtitle = new JLabel("Fast. Secure. Efficient.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setBounds(42, 85, 500, 30);

        mainUI.add(subtitle);


        // ELECTIONS CARD
        electionCard = new JPanel();

        electionCard.setBackground(ALT_FONTS);
        electionCard.setLayout(null);
        electionCard.setBorder(
                BorderFactory.createLineBorder(BRAND, 1)
        );
        electionCard.setBounds(40, 150, 190, 130);

        electionTitle = new JLabel(
                "Elections",
                SwingConstants.CENTER
        );
        electionTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        electionTitle.setBounds(0, 15, 190, 30);

        electionValue = new JLabel(
                "0",
                SwingConstants.CENTER
        );
        electionValue.setFont(new Font("Arial", Font.BOLD, 32));
        electionValue.setBounds(0, 50, 190, 50);

        electionCard.add(electionTitle);
        electionCard.add(electionValue);

        mainUI.add(electionCard);


        // STATUS CARD
        statusCard = new JPanel();

        statusCard.setBackground(ALT_FONTS);
        statusCard.setLayout(null);
        statusCard.setBorder(
                BorderFactory.createLineBorder(BRAND, 1)
        );
        statusCard.setBounds(255, 150, 190, 130);

        statusTitle = new JLabel(
                "Voting Status",
                SwingConstants.CENTER
        );
        statusTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        statusTitle.setBounds(0, 15, 190, 30);

        status = new JLabel(
                "CLOSE",
                SwingConstants.CENTER
        );
        status.setFont(new Font("Arial", Font.BOLD, 28));
        status.setBounds(0, 50, 190, 50);

        statusCard.add(statusTitle);
        statusCard.add(status);

        mainUI.add(statusCard);


        // ACCOUNT CARD
        accountCard = new JPanel();

        accountCard.setBackground(ALT_FONTS);
        accountCard.setLayout(null);
        accountCard.setBorder(
                BorderFactory.createLineBorder(BRAND, 1)
        );
        accountCard.setBounds(470, 150, 190, 130);

        accountTitle = new JLabel(
                "Account",
                SwingConstants.CENTER
        );
        accountTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        accountTitle.setBounds(0, 15, 190, 30);

        accountValue = new JLabel(
                "NOT VERIFIED",
                SwingConstants.CENTER
        );
        accountValue.setFont(new Font("Arial", Font.BOLD, 22));
        accountValue.setBounds(0, 50, 190, 50);

        accountCard.add(accountTitle);
        accountCard.add(accountValue);

        mainUI.add(accountCard);


        // CURRENT ELECTION TITLE
        currentElection = new JLabel("Current Election");
        currentElection.setFont(new Font("Arial", Font.BOLD, 24));
        currentElection.setForeground(BRAND);
        currentElection.setBounds(40, 330, 400, 35);

        mainUI.add(currentElection);


        // CURRENT ELECTION PANEL
        electionPanel = new JPanel();

        electionPanel.setBackground(ALT_FONTS);
        electionPanel.setLayout(null);
        electionPanel.setBorder(
                BorderFactory.createLineBorder(BRAND, 1)
        );
        electionPanel.setBounds(40, 380, 620, 100);

        electionName = new JLabel(
                "Student Council Election"
        );
        electionName.setFont(
                new Font("Arial", Font.BOLD, 18)
        );
        electionName.setBounds(20, 15, 350, 30);

        electionDescription = new JLabel(
                "Not available yet."
        );
        electionDescription.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        electionDescription.setForeground(Color.DARK_GRAY);
        electionDescription.setBounds(20, 50, 300, 25);

        viewButton = new JButton("View Candidates");

        viewButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );
        viewButton.setEnabled(false);
        viewButton.setBackground(BRAND);
        viewButton.setForeground(ALT_FONTS);
        viewButton.setFocusable(false);
        viewButton.setBorder(
                BorderFactory.createLineBorder(BRAND, 1)
        );
        viewButton.setBounds(430, 25, 165, 45);

        electionPanel.add(electionName);
        electionPanel.add(electionDescription);
        electionPanel.add(viewButton);

        mainUI.add(electionPanel);


        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}