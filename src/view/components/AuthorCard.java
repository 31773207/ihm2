package view.components;

import java.awt.*;
import javax.swing.*;
import model.Author;

public class AuthorCard extends JPanel {

    public AuthorCard(Author author) {
        setLayout(new BorderLayout(20, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240), 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        setPreferredSize(new Dimension(380, 220));
        setMaximumSize(new Dimension(380, 220));
        setOpaque(true);

        // ===== LEFT: Circular Avatar =====
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(249, 211, 153)); // F9D399
                g2.fillOval(0, 0, getWidth(), getHeight());
                
                g2.setColor(new Color(110, 60, 16));
                g2.setFont(new Font("Arial", Font.BOLD, 24));
                String initials = getInitials(author.getName());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(initials)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(initials, x, y);
                
                g2.dispose();
            }
        };
        avatarPanel.setPreferredSize(new Dimension(80, 80));
        avatarPanel.setOpaque(false);

        // ===== RIGHT: Content =====
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        // ===== Name and Genre =====
        // Create HTML to have bold name and plain genre
        String name = author.getName();
        String genre = getGenreFromName(name);
        String nameGenreHtml = "<html><div style='width:230px;'>" +
                             "<span style='font-weight:bold;font-size:18px;color:#2C1810;'>" + name + "</span>" +
                             "<span style='color:#888888;font-size:16px;'> • " + genre + "</span>" +
                             "</div></html>";
        
        JLabel nameGenreLabel = new JLabel(nameGenreHtml);
        nameGenreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameGenreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        // ===== Description =====
        String description = getDescriptionFromName(name);
        JLabel descLabel = new JLabel("<html><div style='width:230px;color:#666666;font-size:14px;line-height:1.5;margin-bottom:10px;'>" + 
                                      description + "</div></html>");
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        // ===== Tags (plain text, no boxes, separated by space) =====
        String[] tags = getTagsFromName(name);
        String tagsText = String.join("   ", tags); // Three spaces between tags
        JLabel tagsLabel = new JLabel("<html><div style='width:230px;color:#666666;font-size:14px;'>" + 
                                      tagsText + "</div></html>");
        tagsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        tagsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // ===== "View Books →" Button =====
        JButton viewBooksBtn = new JButton("<html><span style='font-size:14px;'>View Books →</span></html>");
        viewBooksBtn.setFont(new Font("Arial", Font.BOLD, 14));
        viewBooksBtn.setBackground(Color.WHITE);
        viewBooksBtn.setForeground(new Color(110, 60, 16));
        viewBooksBtn.setFocusPainted(false);
        viewBooksBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(110, 60, 16), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        viewBooksBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewBooksBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Hover effect
        viewBooksBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewBooksBtn.setBackground(new Color(249, 211, 153));
                viewBooksBtn.setForeground(new Color(110, 60, 16));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewBooksBtn.setBackground(Color.WHITE);
                viewBooksBtn.setForeground(new Color(110, 60, 16));
            }
        });

        // ===== Add to content panel =====
        contentPanel.add(nameGenreLabel);
        contentPanel.add(descLabel);
        contentPanel.add(tagsLabel);
        contentPanel.add(viewBooksBtn);
        contentPanel.add(Box.createVerticalGlue());

        // ===== Add to main panel =====
        add(avatarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "??";
        
        String[] parts = name.trim().split("\\s+");
        if (parts.length == 1) {
            return parts[0].substring(0, Math.min(2, parts[0].length())).toUpperCase();
        } else {
            StringBuilder initials = new StringBuilder();
            for (String part : parts) {
                if (!part.isEmpty()) {
                    initials.append(part.charAt(0));
                }
            }
            return initials.toString().toUpperCase();
        }
    }
    
    private String getGenreFromName(String name) {
        switch(name) {
            case "Jane Austen": return "Classic Literature";
            case "Alex Michaelides": return "Psychological Thriller";
            case "Taylor Jenkins Reid": return "Contemporary Fiction";
            case "Andy Weir": return "Science Fiction";
            default: return "Author";
        }
    }
    
    private String getDescriptionFromName(String name) {
        switch(name) {
            case "Jane Austen": 
                return "Master of wit and social commentary, known for Pride and Prejudice and Emma.";
            case "Alex Michaelides": 
                return "Contemporary master of psychological suspense and mind-bending mysteries.";
            case "Taylor Jenkins Reid": 
                return "Bestselling author known for emotionally rich stories and complex characters.";
            case "Andy Weir": 
                return "Engineer turned novelist, famous for The Martian and Project Hail Mary.";
            default: 
                return "Renowned author with multiple acclaimed works.";
        }
    }
    
    private String[] getTagsFromName(String name) {
        switch(name) {
            case "Jane Austen": return new String[]{"Romance", "Drama"};
            case "Alex Michaelides": return new String[]{"Thriller", "Mystery"};
            case "Taylor Jenkins Reid": return new String[]{"Romance", "Drama"};
            case "Andy Weir": return new String[]{"Sci-Fi", "Adventure"};
            default: return new String[]{"Fiction", "Literature"};
        }
    }
}