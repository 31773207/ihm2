package view.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Book;
import model.BookStore;
import view.components.BookCard;

public class FeaturedPanel extends JPanel {

    public FeaturedPanel() {
        setPreferredSize(new Dimension(800, 800));
        
        // IMPORTANT: Set transparent so gradient shows
        setOpaque(false);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // ================= TITLE ==================
        JLabel mainTitle = new JLabel("✨ Featured Books ✨", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Serif", Font.BOLD, 40));
        mainTitle.setForeground(new Color(152, 77, 35));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(mainTitle);

        // ================= SUBTITLE ==================
        JLabel subtitle = new JLabel("Handpicked selection from our curators");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 20));
        subtitle.setForeground(new Color(152, 77, 35));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(subtitle);

        // ================= ONE SINGLE ROW ==================
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        row.setOpaque(false); // Make row transparent

        List<Book> books = BookStore.getAllBooks();
        int maxBooks = Math.min(4, books.size());

        for (int i = 0; i < maxBooks; i++) {
            BookCard card = new BookCard(books.get(i));
            row.add(card);
        }

        add(row);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        // COLORS FROM YOUR IMAGE:
        Color leftColor = new Color(240,215,221);  // Warm beige/cream (left)
        Color rightColor = new Color(223, 224, 255); // Light pink/peach (right)
        
        // HORIZONTAL gradient from left to right
        GradientPaint gp = new GradientPaint(0, 0, leftColor, w, 0, rightColor);

        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }
}