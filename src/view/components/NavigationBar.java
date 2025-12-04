package view.components;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class NavigationBar extends JPanel {
    public JButton homeBtn, catalogueBtn, genreBtn, authorsBtn, featuredBtn, cartBtn, loginBtn, searchBtn;
    public JTextField searchField;

    public NavigationBar() {
        // EXACT COLOR from your screenshot - Solid light warm brown/beige
        setBackground(new Color(240, 230, 220)); // EXACT solid color
        
        setPreferredSize(new Dimension(1200, 60));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 5, 0, 5);

        // --- Logo ---
        JLabel logo = new JLabel("📚 Bookify");
        logo.setFont(new Font("Arial", Font.BOLD, 26));
        logo.setForeground(new Color(80, 60, 40)); // Dark brown text
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0;
        add(logo, gbc);

        // --- Left spacer to push menu to center ---
        JPanel leftSpacer = new JPanel();
        leftSpacer.setOpaque(false);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(leftSpacer, gbc);

        // --- Main buttons (centered) ---
        homeBtn = new JButton("Home");
        catalogueBtn = new JButton("Catalogue");
        genreBtn = new JButton("Genre");
        authorsBtn = new JButton("Authors");
        featuredBtn = new JButton("Featured");

        JButton[] mainBtns = {homeBtn, catalogueBtn, genreBtn, authorsBtn, featuredBtn};
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        centerPanel.setOpaque(false);
        for (JButton btn : mainBtns) styleButton(btn);
        for (JButton btn : mainBtns) centerPanel.add(btn);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(centerPanel, gbc);

        // --- Right spacer to push right buttons to the right edge ---
        JPanel rightSpacer = new JPanel();
        rightSpacer.setOpaque(false);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(rightSpacer, gbc);

        // --- Right buttons: search, cart, login ---
        // Search field
        searchField = new JTextField(12);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 150, 120), 2),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        searchField.setBackground(new Color(255, 250, 245)); // Light cream
        searchField.setForeground(new Color(80, 70, 60));
        searchField.setCaretColor(new Color(80, 70, 60));

        // Search button
        searchBtn = new JButton("🔍");
        searchBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        searchBtn.setContentAreaFilled(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setFocusPainted(false);
        searchBtn.setForeground(new Color(80, 70, 60));

        // Cart button
        cartBtn = new JButton("🛒");
        cartBtn.setFont(new Font("SansSerif", Font.PLAIN, 20));
        cartBtn.setContentAreaFilled(false);
        cartBtn.setBorderPainted(false);
        cartBtn.setFocusPainted(false);
        cartBtn.setForeground(new Color(80, 70, 60));

        // Login button
        loginBtn = new JButton("Login");
        loginBtn.setContentAreaFilled(true);
        loginBtn.setBackground(new Color(200, 180, 160)); // Medium beige
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginBtn.setForeground(new Color(80, 60, 40));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(searchField);
        rightPanel.add(searchBtn);
        rightPanel.add(cartBtn);
        rightPanel.add(loginBtn);

        gbc.gridx = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(rightPanel, gbc);
    }
    
    private void styleButton(JButton btn) {
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(80, 60, 40));
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(110, 80, 50));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(80, 60, 40));
            }
        });
    }
    
    // No paintComponent needed - using solid color
    
    // --- Getters ---
    public JButton getHomeButton() { return homeBtn; }
    public JButton getCatalogueButton() { return catalogueBtn; }
    public JButton getGenreButton() { return genreBtn; }
    public JButton getAuthorsButton() { return authorsBtn; }
    public JButton getFeaturedButton() { return featuredBtn; }
    public JButton getCartButton() { return cartBtn; }
    public JButton getLoginButton() { return loginBtn; }
    public void updateFavoriteCount(int count) { /* no-op: favorites removed */ }
    public void updateCartCount(int count) {
        cartBtn.setText("🛒 " + count);
    }

    private static Icon createHeartIcon(Color color, int w, int h, boolean filled) {
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double W = w;
        double H = h;

        Ellipse2D left = new Ellipse2D.Double(W * 0.05, H * 0.18, W * 0.45, H * 0.45);
        Ellipse2D right = new Ellipse2D.Double(W * 0.5, H * 0.18, W * 0.45, H * 0.45);
        Polygon tri = new Polygon(new int[] {(int)(W*0.5), (int)(W*0.95), (int)(W*0.05)}, new int[] {(int)(H*0.92), (int)(H*0.55), (int)(H*0.55)}, 3);

        java.awt.geom.Area area = new java.awt.geom.Area(left);
        area.add(new java.awt.geom.Area(right));
        area.add(new java.awt.geom.Area(tri));

        g.setColor(color);
        if (filled) {
            g.fill(area);
        } else {
            float stroke = Math.max(1f, w / 14f);
            g.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(area);
        }

        g.dispose();
        return new ImageIcon(img);
    }
}