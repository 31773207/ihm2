// FooterPanel.java
package view.panels;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;

public class FooterPanel extends JPanel {
    public FooterPanel() {
        setOpaque(false);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 320));

        // Top: Newsletter section
        JPanel newsletter = new JPanel();
        newsletter.setOpaque(false);
        newsletter.setLayout(new BoxLayout(newsletter, BoxLayout.Y_AXIS));
        newsletter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Stay Updated with Latest Releases");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setForeground(new Color(83, 33, 33));
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Subscribe to our newsletter to get the latest books and special offers");
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setForeground(new Color(120, 70, 70));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel inputRow = new JPanel();
        inputRow.setOpaque(false);
        inputRow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JTextField emailField = new JTextField("Enter your email address", 28);
        emailField.setForeground(new Color(130, 100, 100));
        emailField.setPreferredSize(new Dimension(300, 34));
        emailField.setBackground(Color.WHITE);
        emailField.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 180), 2));
        // simple placeholder behavior
        emailField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Enter your email address")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (emailField.getText().trim().isEmpty()) {
                    emailField.setText("Enter your email address");
                    emailField.setForeground(new Color(130,100,100));
                }
            }
        });

        JButton subscribe = new JButton("Subscribe");
        subscribe.setBackground(new Color(133, 68, 29));
        subscribe.setForeground(Color.WHITE);
        subscribe.setFocusPainted(false);
        subscribe.setPreferredSize(new Dimension(110, 34));

        inputRow.add(emailField);
        inputRow.add(subscribe);

        newsletter.add(title);
        newsletter.add(Box.createRigidArea(new Dimension(0,6)));
        newsletter.add(subtitle);
        newsletter.add(Box.createRigidArea(new Dimension(0,12)));
        newsletter.add(inputRow);

        // Middle: three columns
        JPanel columns = new JPanel(new GridLayout(1, 3));
        columns.setOpaque(false);
        columns.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Left column: Brand + description + social
        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JLabel brand = new JLabel("Bookify");
        brand.setFont(new Font("SansSerif", Font.BOLD, 20));
        brand.setForeground(new Color(83, 33, 33));
        JLabel desc = new JLabel("Your premier destination to discover, explore and savor the finest literary works.");
        desc.setForeground(new Color(120, 70, 70));
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setBorder(BorderFactory.createEmptyBorder(8,0,8,0));
        JPanel socials = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        socials.setOpaque(false);
        JButton t = makeSocialButton("@", new Color(255,240,230));
        JButton tw = makeSocialButton("T", new Color(255,240,230));
        JButton w = makeSocialButton("W", new Color(255,240,230));
        socials.add(t); socials.add(tw); socials.add(w);
        left.add(brand);
        left.add(desc);
        left.add(socials);

        // Center column: Navigation
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        JLabel navTitle = new JLabel("Navigation");
        navTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        navTitle.setForeground(new Color(83, 33, 33));
        JLabel links = new JLabel("Home   |   Catalogue   |   Genres   |   Authors   |   Featured");
        links.setForeground(new Color(120, 70, 70));
        links.setFont(new Font("SansSerif", Font.PLAIN, 13));
        center.add(navTitle);
        center.add(Box.createRigidArea(new Dimension(0,8)));
        center.add(links);

        // Right column: Contact
        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JLabel contactTitle = new JLabel("Contact");
        contactTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        contactTitle.setForeground(new Color(83, 33, 33));
        JLabel contactInfo = new JLabel("Email: hello@bookify.com");
        contactInfo.setForeground(new Color(120, 70, 70));
        contactInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JLabel phone = new JLabel("Phone: +1 (555) 123-4567");
        phone.setForeground(new Color(120, 70, 70));
        phone.setFont(new Font("SansSerif", Font.PLAIN, 13));
        right.add(contactTitle);
        right.add(Box.createRigidArea(new Dimension(0,8)));
        right.add(contactInfo);
        right.add(phone);

        columns.add(left);
        columns.add(center);
        columns.add(right);

        // Bottom legal line
        JLabel legal = new JLabel("Cultivating the love of reading together   |   © 2025 Bookify - All rights reserved.", SwingConstants.CENTER);
        legal.setForeground(new Color(110, 80, 80));
        legal.setFont(new Font("SansSerif", Font.PLAIN, 12));
        legal.setBorder(BorderFactory.createEmptyBorder(8, 0, 12, 0));

        // Compose main panel (vertical)
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(newsletter);
        content.add(columns);
        content.add(legal);

        add(content, BorderLayout.CENTER);
    }

    private JButton makeSocialButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(34, 34));
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(220,180,160)));
        return b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();
        Color c1 = new Color(255, 230, 230);
        Color c2 = new Color(240, 240, 255);
        GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        g2.dispose();
    }
}
