package view.components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import model.Genre;

public class GenreCard extends JPanel {

    private static final int ARC = 18;

    public GenreCard(Genre genre) {
        setOpaque(false);
        setPreferredSize(new Dimension(300, 220));
        setLayout(new BorderLayout());

        String genreName = "Unknown Genre";
        try {
            if (genre != null && genre.getName() != null) genreName = genre.getName();
        } catch (Exception e) {
            genreName = "Unknown Genre";
        }

        // center container
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        // Icon / thumbnail - try to use genre image if available
        JLabel iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Icon icon = createGenreIcon(genreName, 72, 72);
        iconLabel.setIcon(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        // Title
        JLabel titleLabel = new JLabel(genreName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setForeground(new Color(95, 50, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // Description
        String description = getDescriptionForGenre(genreName);
        JTextArea desc = new JTextArea(description);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setForeground(new Color(100, 55, 25));
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setBorder(BorderFactory.createEmptyBorder(0, 10, 12, 10));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Count badge
        String countText = getBookCountForGenre(genreName);
        JLabel count = new JLabel(countText, SwingConstants.CENTER);
        count.setFont(new Font("SansSerif", Font.BOLD, 13));
        count.setForeground(new Color(90, 50, 20));
        count.setOpaque(true);
        count.setBackground(new Color(255, 245, 230));
        count.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        count.setAlignmentX(Component.CENTER_ALIGNMENT);
        count.setMaximumSize(new Dimension(140, 30));

        center.add(iconLabel);
        center.add(titleLabel);
        center.add(desc);
        center.add(count);

        add(center, BorderLayout.CENTER);
    }

    private Icon createGenreIcon(String genreName, int w, int h) {
        // Try to load genre-specific book image
        String imageName = getImagePathForGenre(genreName);
        try {
            // Try different possible paths
            String[] possiblePaths = {
                "src/images/" + imageName,
                "images/" + imageName,
                "/images/" + imageName
            };
            
            java.io.File imageFile = null;
            for (String path : possiblePaths) {
                java.io.File file = new java.io.File(path);
                if (file.exists()) {
                    imageFile = file;
                    break;
                }
            }
            
            // Also try as resource
            if (imageFile == null) {
                java.net.URL imageUrl = getClass().getResource("/images/" + imageName);
                if (imageUrl != null) {
                    ImageIcon icon = new ImageIcon(imageUrl);
                    Image scaledImage = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImage);
                }
            }
            
            // If file found, load it
            if (imageFile != null && imageFile.exists()) {
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                Image scaledImage = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
            
        } catch (Exception e) {
            System.err.println("Failed to load image for genre: " + genreName);
            e.printStackTrace();
        }
        
        // Fallback: create a colored placeholder if image not found
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fill = new Color(255, 250, 245);
        Color accent = new Color(220, 180, 140);
        g2.setPaint(new GradientPaint(0, 0, fill, 0, h, new Color(255, 240, 220)));
        g2.fillRoundRect(0, 0, w, h, 14, 14);

        // draw placeholder book icon (simple rectangle)
        g2.setColor(accent.darker());
        int pad = 12;
        g2.fillRoundRect(pad/2, pad/2, w - pad, h - pad, 8, 8);

        g2.dispose();
        return new ImageIcon(img);
    }
    
    private String getImagePathForGenre(String genreName) {
        if (genreName == null) return "1.jpg";
        
        switch(genreName) {
            case "Fantasy": return "5.jpg";
case "Romance": return "3.jpg";
case "Horror": return "book12.jpg"; // Horror
case "Sci-Fi": return "2.jpg";
case "Mystery": return "1.jpg";

            default: return "3.jpg";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // shadow
        g2.setColor(new Color(0, 0, 0, 18));
        RoundRectangle2D shadow = new RoundRectangle2D.Double(6, 8, w - 12, h - 12, ARC, ARC);
        g2.fill(shadow);

        // card background gradient
        GradientPaint gp = new GradientPaint(0, 0, new Color(255, 245, 230), 0, h, new Color(255, 237, 210));
        g2.setPaint(gp);
        RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, w - 12, h - 16, ARC, ARC);
        g2.fill(rect);

        g2.dispose();
        super.paintComponent(g);
    }
    
    private String getDescriptionForGenre(String genreName) {
        if (genreName == null) return "No description available.";
        
        switch(genreName) {
            case "Classic Literature":
                return "Discover the masterpieces that have shaped the history of world literature and continue to inspire.";
            case "Romance":
                return "Experience passionate and heartwarming love stories that celebrate the beauty of human connection.";
            case "Fantasy & Magic":
                return "Escape into magical realms filled with extraordinary creatures, epic quests, and boundless imagination.";
            case "Science Fiction":
                return "Explore infinite possibilities of the future, advanced technology, and the mysteries of the universe.";
            case "Mystery & Thriller":
                return "Dive into captivating plots filled with suspense, intrigue, and unexpected twists that keep you guessing.";
            case "Drama & Poetry":
                return "Savor the beauty of words in their purest form, where language becomes art and emotion flows freely.";
            default:
                return "Explore our collection of " + genreName + " books.";
        }
    }
    
    private String getBookCountForGenre(String genreName) {
        if (genreName == null) return "0 books";
        
        switch(genreName) {
            case "Classic Literature": return "2,847 books";
            case "Romance": return "2,134 books";
            case "Fantasy & Magic": return "1,923 books";
            case "Science Fiction": return "1,678 books";
            case "Mystery & Thriller": return "1,456 books";
            case "Drama & Poetry": return "892 books";
            default: return "1,000 books";
        }
    }
    
    private Color getColorForGenre(String genreName) {
        if (genreName == null) return new Color(110, 60, 16);
        
        switch(genreName) {
            case "Classic Literature": return new Color(139, 69, 19);    // Brown
            case "Romance": return new Color(178, 34, 34);               // Red
            case "Fantasy & Magic": return new Color(72, 61, 139);       // Purple
            case "Science Fiction": return new Color(30, 144, 255);      // Blue
            case "Mystery & Thriller": return new Color(46, 139, 87);    // Green
            case "Drama & Poetry": return new Color(148, 0, 211);        // Violet
            default: return new Color(110, 60, 16);
        }
    }
    
    private Color getCountColorForGenre(String genreName) {
        if (genreName == null) return new Color(150, 80, 20);
        
        switch(genreName) {
            case "Classic Literature": return new Color(160, 82, 45);
            case "Romance": return new Color(205, 92, 92);
            case "Fantasy & Magic": return new Color(106, 90, 205);
            case "Science Fiction": return new Color(65, 105, 225);
            case "Mystery & Thriller": return new Color(60, 179, 113);
            case "Drama & Poetry": return new Color(186, 85, 211);
            default: return new Color(150, 80, 20);
        }
    }
}