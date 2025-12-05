package view.dialogs;

import java.awt.*;
import javax.swing.*;

public class LoginDialog extends JDialog {

    public LoginDialog(Window parent) {
        super(parent, "Login", ModalityType.APPLICATION_MODAL);

        setSize(450, 520);
        setLayout(new BorderLayout());
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
        card.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        // Icon (User Avatar)
        JPanel iconPanel = new JPanel();
        iconPanel.setOpaque(false);
        iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel iconLabel = new JLabel("👤");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        iconPanel.add(iconLabel);
        card.add(iconPanel);

        card.add(Box.createVerticalStrut(15));

        // Title - "حسابي" (My Account)
        JLabel title = new JLabel("حسابي", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(110, 60, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        card.add(Box.createVerticalStrut(10));

        // Subtitle - معلومات الشخصية وتاريخ شرائك
        JLabel subtitle = new JLabel("معلومات الشخصية وتاريخ شرائك");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(200, 100, 50));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(subtitle);

        card.add(Box.createVerticalStrut(20));

        // Bookify Logo
        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel logo = new JLabel("📚");
        logo.setFont(new Font("Arial", Font.PLAIN, 50));
        logoPanel.add(logo);
        card.add(logoPanel);

        card.add(Box.createVerticalStrut(15));

        // Bookify text
        JLabel bookifyText = new JLabel("في Bookify!", SwingConstants.CENTER);
        bookifyText.setFont(new Font("Arial", Font.BOLD, 16));
        bookifyText.setForeground(new Color(110, 60, 16));
        bookifyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(bookifyText);

        card.add(Box.createVerticalStrut(10));

        // Description text
        JLabel description = new JLabel("الم تقم بعملية شراء بعد أبدا؟ ابدأ رحلتك الأدبية معنا");
        description.setFont(new Font("Arial", Font.PLAIN, 12));
        description.setForeground(new Color(100, 100, 100));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(description);

        card.add(Box.createVerticalStrut(25));

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // "استكشف المجموعة" button
        JButton exploreBtn = new JButton("استكشف المجموعة");
        exploreBtn.setFont(new Font("Arial", Font.BOLD, 13));
        exploreBtn.setBackground(new Color(165, 85, 30));
        exploreBtn.setForeground(Color.WHITE);
        exploreBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        exploreBtn.setFocusPainted(false);
        exploreBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exploreBtn.addActionListener(e -> {
            // Navigate to catalog
            dispose();
        });

        // "إغلاق" button
        JButton closeBtn = new JButton("إغلاق");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 13));
        closeBtn.setBackground(new Color(220, 220, 220));
        closeBtn.setForeground(new Color(80, 80, 80));
        closeBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());

        buttonsPanel.add(exploreBtn);
        buttonsPanel.add(closeBtn);

        card.add(buttonsPanel);

        // Main panel with background color
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 235, 230));
        mainPanel.add(card, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }
}
