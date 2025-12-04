package view.components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import model.Author;

public class AuthorCard extends JPanel {

    private static final int ARC = 18;

    public AuthorCard(Author author) {
        setOpaque(false);
        setPreferredSize(new Dimension(240, 280));
        setLayout(new BorderLayout());

        String name = author.getName();

        // ===== CENTER CONTAINER =====
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // ===== Circular Avatar =====
        JPanel avatarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Avatar background - color based on genre
                Color avatarColor = getAvatarColorForAuthor(name);
                g2.setColor(avatarColor);
                g2.fillOval(0, 0, getWidth(), getHeight());

                // Initials
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 28));
                String initials = getInitials(name);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(initials)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(initials, x, y);

                g2.dispose();
            }
        };
        avatarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        avatarPanel.setPreferredSize(new Dimension(90, 90));
        avatarPanel.setOpaque(false);
        avatarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        // ===== Name =====
        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        nameLabel.setForeground(new Color(95, 50, 24));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        // ===== Genre =====
        String genre = getGenreFromName(name);
        JLabel genreLabel = new JLabel(genre, SwingConstants.CENTER);
        genreLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        genreLabel.setForeground(new Color(150, 100, 60));
        genreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        genreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // ===== Description =====
        String description = getDescriptionFromName(name);
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descArea.setForeground(new Color(100, 80, 60));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setOpaque(false);
        descArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Tags as inline colored labels =====
        JPanel tagsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        tagsPanel.setOpaque(false);
        tagsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        String[] tags = getTagsFromName(name);
        for (String tag : tags) {
            JLabel tagLabel = new JLabel(tag, SwingConstants.CENTER);
            tagLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
            tagLabel.setForeground(Color.WHITE);
            tagLabel.setBackground(new Color(100, 150, 200)); // Default blue
            tagLabel.setOpaque(true);
            tagLabel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

            // Color by tag type
            if (tag.equals("Romance") || tag.equals("Drama")) {
                tagLabel.setBackground(new Color(255, 100, 150));
            } else if (tag.equals("Thriller") || tag.equals("Mystery")) {
                tagLabel.setBackground(new Color(100, 150, 200));
            } else if (tag.equals("Sci-Fi") || tag.equals("Adventure")) {
                tagLabel.setBackground(new Color(100, 180, 100));
            }

            tagsPanel.add(tagLabel);
        }

        // ===== "View Books →" Link =====
        JLabel viewBooksLink = new JLabel("<html><u>View Books →</u></html>", SwingConstants.CENTER);
        viewBooksLink.setFont(new Font("SansSerif", Font.PLAIN, 12));
        viewBooksLink.setForeground(new Color(200, 120, 50));
        viewBooksLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewBooksLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        viewBooksLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewBooksLink.setForeground(new Color(180, 100, 30));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewBooksLink.setForeground(new Color(200, 120, 50));
            }
        });

        center.add(avatarPanel);
        center.add(nameLabel);
        center.add(genreLabel);
        center.add(descArea);
        center.add(tagsPanel);
        center.add(Box.createVerticalGlue());
        center.add(viewBooksLink);

        add(center, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // shadow
        g2.setColor(new Color(0, 0, 0, 18));
        RoundRectangle2D shadow = new RoundRectangle2D.Double(4, 6, w - 8, h - 12, ARC, ARC);
        g2.fill(shadow);

        // card background gradient
        GradientPaint gp = new GradientPaint(0, 0, new Color(255, 245, 230), 0, h, new Color(255, 237, 210));
        g2.setPaint(gp);
        RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, w - 8, h - 12, ARC, ARC);
        g2.fill(rect);

        g2.dispose();
        super.paintComponent(g);
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

    private Color getAvatarColorForAuthor(String name) {
        switch (name) {
            case "Jane Austen":
                return new Color(184, 134, 11); // Dark goldenrod
            case "Alex Michaelides":
                return new Color(0, 102, 204); // Blue
            case "Taylor Jenkins Reid":
                return new Color(147, 51, 234); // Purple
            case "Andy Weir":
                return new Color(34, 139, 34); // Forest green
            default:
                return new Color(100, 100, 100);
        }
    }

    private String getGenreFromName(String name) {
        switch (name) {
            case "Jane Austen":
                return "Classic Literature";
            case "Alex Michaelides":
                return "Psychological Thriller";
            case "Taylor Jenkins Reid":
                return "Contemporary Fiction";
            case "Andy Weir":
                return "Science Fiction";
            default:
                return "Author";
        }
    }

    private String getDescriptionFromName(String name) {
        switch (name) {
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
        switch (name) {
            case "Jane Austen":
                return new String[]{"Romance", "Drama"};
            case "Alex Michaelides":
                return new String[]{"Thriller", "Mystery"};
            case "Taylor Jenkins Reid":
                return new String[]{"Romance", "Drama"};
            case "Andy Weir":
                return new String[]{"Sci-Fi", "Adventure"};
            default:
                return new String[]{"Fiction", "Literature"};
        }
    }
}