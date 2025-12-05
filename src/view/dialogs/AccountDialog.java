package view.dialogs;

import java.awt.*;
import javax.swing.*;
import service.UserService;
import view.frames.MainFrame;

public class AccountDialog extends JDialog {
    private MainFrame mainFrame;

    public AccountDialog(Window parent) {
        super(parent, "My Account", ModalityType.APPLICATION_MODAL);
        
        if (parent instanceof MainFrame) {
            this.mainFrame = (MainFrame) parent;
        }

        setSize(450, 600);
        setUndecorated(true);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(0, 0, 0, 0));
        setLocationRelativeTo(parent);

        // Rounded card background
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        card.setPreferredSize(new Dimension(400, 550));

        // Icon (User Avatar)
        JPanel iconPanel = new JPanel();
        iconPanel.setOpaque(false);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel iconLabel = new JLabel("👤");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        iconPanel.add(iconLabel);
        card.add(iconPanel);

        card.add(Box.createVerticalStrut(20));

        // Title - "My Account"
        JLabel titleLabel = new JLabel("My Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(160, 82, 45)); // Brown color
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(titleLabel);

        card.add(Box.createVerticalStrut(10));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Your personal information and purchase history");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 130, 80));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitleLabel);

        card.add(Box.createVerticalStrut(30));

        // Books icon
        JPanel booksPanel = new JPanel();
        booksPanel.setOpaque(false);
        booksPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel booksLabel = new JLabel("📚");
        booksLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        booksPanel.add(booksLabel);
        card.add(booksPanel);

        card.add(Box.createVerticalStrut(15));

        // Welcome message
        String userName = UserService.getInstance().getCurrentUserName();
        if (userName == null || userName.isEmpty()) {
            userName = "User";
        }
        
        JLabel welcomeLabel = new JLabel("Welcome to Bookify, " + userName + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(new Color(160, 82, 45));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(welcomeLabel);

        card.add(Box.createVerticalStrut(10));

        // User email
        String userEmail = UserService.getInstance().getCurrentUserEmail();
        JLabel emailLabel = new JLabel("Email: " + (userEmail != null ? userEmail : "Not set"));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        emailLabel.setForeground(new Color(120, 120, 120));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(emailLabel);

        card.add(Box.createVerticalStrut(10));

        // Description
        JLabel descLabel = new JLabel("Haven't made a purchase yet? Start your literary journey with us");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(new Color(130, 130, 130));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(descLabel);

        card.add(Box.createVerticalStrut(35));

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // "Explore Collection" button
        JButton exploreBtn = new JButton("Explore Collection");
        exploreBtn.setFont(new Font("Arial", Font.BOLD, 13));
        exploreBtn.setBackground(new Color(165, 85, 30));
        exploreBtn.setForeground(Color.WHITE);
        exploreBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        exploreBtn.setContentAreaFilled(true);
        exploreBtn.setBorderPainted(false);
        exploreBtn.setFocusPainted(false);
        exploreBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exploreBtn.addActionListener(e -> {
            if (mainFrame != null) {
                mainFrame.scrollToPanel(mainFrame.getCatalogPanel());
            }
            dispose();
        });

        // "Logout" button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 13));
        logoutBtn.setBackground(new Color(220, 210, 200));
        logoutBtn.setForeground(new Color(160, 82, 45));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        logoutBtn.setContentAreaFilled(true);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            UserService.getInstance().logout();
            // Update navigation bar button
            Window parentWindow = (Window) SwingUtilities.getAncestorOfClass(Window.class, card);
            if (parentWindow instanceof javax.swing.JFrame) {
                javax.swing.JFrame frame = (javax.swing.JFrame) parentWindow;
                // Find and update the navigation bar
                Component[] components = frame.getContentPane().getComponents();
                for (Component comp : components) {
                    if (comp instanceof view.components.NavigationBar) {
                        ((view.components.NavigationBar) comp).updateLoginButton(false);
                        break;
                    }
                }
            }
            dispose();
        });

        buttonsPanel.add(exploreBtn);
        buttonsPanel.add(logoutBtn);

        card.add(buttonsPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);
    }
}
