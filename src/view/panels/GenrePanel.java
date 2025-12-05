package view.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Genre;
import model.GenreStore;
import view.components.GenreCard;

public class GenrePanel extends JPanel {

    public GenrePanel() {
        setPreferredSize(new Dimension(1100, 700));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        // ===== TITLE =====
        JLabel mainTitle = new JLabel("Popular Genres", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Serif", Font.BOLD, 40));
        mainTitle.setForeground(new Color(152, 77, 35));
        mainTitle.setAlignmentX(CENTER_ALIGNMENT);
        mainTitle.setBorder(BorderFactory.createEmptyBorder(30, 0, 15, 0));
        add(mainTitle);

        // ===== SUBTITLE =====
        JLabel subtitle = new JLabel(
            "Explore our vast collection organized by genres to find exactly what ignites your passion for reading.",
            SwingConstants.CENTER
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitle.setForeground(new Color(152, 77, 35));
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 30, 100));
        add(subtitle);

        // ===== GRID OF 6 CARDS (2 rows x 3 columns) =====
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 30, 30)); // 2 rows, 3 columns
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 60, 60));

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

   /*  @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        // soft vertical gradient background
        GradientPaint gp = new GradientPaint(0, 0, new Color(246, 231, 250), 0, h, new Color(214, 209, 246));
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }*/
      @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        // EXACTLY YOUR 2 COLORS:
        Color c1 = new Color(246, 231, 250); // Light pink
        Color c2 = new Color(214, 209, 246); // Light blue
        GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);

        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }
}
