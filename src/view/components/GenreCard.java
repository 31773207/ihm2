package view.components;

import java.awt.*;
import javax.swing.*;
import model.Genre;

public class GenreCard extends JPanel {
    
    public GenreCard(Genre genre) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 150), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        setPreferredSize(new Dimension(250, 200));
        
        // Get genre name safely
        String genreName = "Unknown Genre";
        try {
            genreName = genre.getName(); // This should exist
        } catch (Exception e) {
            genreName = "Unknown Genre";
        }
        
        // Icon/Image at top
        JLabel iconLabel = new JLabel("📚"); // Simple emoji fallback for now
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        iconLabel.setForeground(getColorForGenre(genreName));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Genre title
        JLabel titleLabel = new JLabel(genreName);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setForeground(getColorForGenre(genreName));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Description - Use hardcoded descriptions based on genre name
        String description = getDescriptionForGenre(genreName);
        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descArea.setForeground(new Color(80, 50, 20));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(Color.WHITE);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        descArea.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Book count - Use hardcoded counts based on genre name
        String bookCountText = getBookCountForGenre(genreName);
        JLabel countLabel = new JLabel(bookCountText);
        countLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        countLabel.setForeground(getCountColorForGenre(genreName));
        countLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(iconLabel);
        add(titleLabel);
        add(descArea);
        add(Box.createVerticalGlue());
        add(countLabel);
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