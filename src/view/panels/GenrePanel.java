package view.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Genre;
import model.GenreStore;
import view.components.GenreCard;

public class GenrePanel extends JPanel {

    public GenrePanel() {
        setPreferredSize(new Dimension(1000, 600)); // Increased size for 6 cards
        setBackground(new Color(245, 205, 175));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ===== TITLE =====
        JLabel mainTitle = new JLabel("Popular Genres", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Serif", Font.BOLD, 40));
        mainTitle.setForeground(new Color(110, 60, 16));
        mainTitle.setAlignmentX(CENTER_ALIGNMENT);
        mainTitle.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));
        add(mainTitle);

        // ===== SUBTITLE =====
        JLabel subtitle = new JLabel(
            "Explore our vast collection organized by genres to find exactly what ignites your passion for reading.",
            SwingConstants.CENTER
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitle.setForeground(new Color(110, 60, 16));
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 30, 100));
        add(subtitle);

        // ===== GRID OF 6 CARDS (2 rows x 3 columns) =====
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 25, 25)); // 2 rows, 3 columns
        gridPanel.setBackground(new Color(245, 205, 175));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 40, 50));

        List<Genre> genres = GenreStore.getAllGenres();
        
        // Show exactly 6 cards as in your image
        int cardCount = Math.min(6, genres.size());
        
        for (int i = 0; i < cardCount; i++) {
            GenreCard card = new GenreCard(genres.get(i));
            gridPanel.add(card);
        }

        // If you don't have 6 genres in GenreStore, you need to ensure your GenreStore has:
        // 1. Classic Literature
        // 2. Romance  
        // 3. Fantasy & Magic
        // 4. Science Fiction
        // 5. Mystery & Thriller
        // 6. Drama & Poetry
        
        // If GenreStore doesn't have these, you might need to add them or create cards manually

        add(gridPanel);
    }
}