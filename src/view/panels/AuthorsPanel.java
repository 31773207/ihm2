package view.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Author;
import model.AuthorStore;
import view.components.AuthorCard;

public class AuthorsPanel extends JPanel {

    public AuthorsPanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(1100, 750));

        // ===== TITLE =====
        JLabel mainTitle = new JLabel("Featured Authors", SwingConstants.CENTER);
        mainTitle.setFont(new Font("Georgia", Font.BOLD, 40));
        mainTitle.setForeground(new Color(152, 77, 35));
        mainTitle.setAlignmentX(CENTER_ALIGNMENT);
        mainTitle.setBorder(BorderFactory.createEmptyBorder(50, 0, 15, 0));
        add(mainTitle);

        // ===== SUBTITLE =====
        JLabel subtitle = new JLabel("Meet the brilliant minds behind the stories that have captivated readers worldwide.", SwingConstants.CENTER);
        subtitle.setFont(new Font("Georgia", Font.PLAIN, 18));
        subtitle.setForeground(new Color(152, 77, 35));
        subtitle.setAlignmentX(CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        add(subtitle);

        // ===== AUTHORS GRID (1x4 layout) =====
        JPanel authorsGrid = new JPanel(new GridLayout(1, 4, 25, 25));
        authorsGrid.setOpaque(false);
        authorsGrid.setMaximumSize(new Dimension(1000, 280));
        
        // Get all authors
        List<Author> authors = AuthorStore.getAllAuthors();
        int maxAuthors = Math.min(4, authors.size());

        // Create and add author cards
        for (int i = 0; i < maxAuthors; i++) {
            AuthorCard card = new AuthorCard(authors.get(i));
            authorsGrid.add(card);
        }
        
        // If we have less than 4 authors, add empty panels
        for (int i = maxAuthors; i < 4; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            authorsGrid.add(emptyPanel);
        }
        
        // Center the grid
        JPanel gridWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        gridWrapper.setOpaque(false);
        gridWrapper.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));
        gridWrapper.add(authorsGrid);
        gridWrapper.setAlignmentX(CENTER_ALIGNMENT);
        add(gridWrapper);

        // ===== VIEW ALL AUTHORS BUTTON =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 60, 0));
        
        JButton viewAllButton = new JButton("View All Authors");
        viewAllButton.setFont(new Font("Georgia", Font.BOLD, 18));
        viewAllButton.setBackground(new Color(152, 77, 35));
        viewAllButton.setForeground(Color.WHITE);
        viewAllButton.setFocusPainted(false);
        viewAllButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(152, 77, 35), 1),
            BorderFactory.createEmptyBorder(12, 40, 12, 40)
        ));
        viewAllButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        viewAllButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewAllButton.setBackground(new Color(30, 20, 10));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewAllButton.setBackground(new Color(152, 77, 35));
            }
        });
        
        buttonPanel.add(viewAllButton);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        add(buttonPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        // soft vertical gradient background
        GradientPaint gp = new GradientPaint(0, 0, new Color(235, 216, 228), 0, h, new Color(210, 199, 225));
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }
}