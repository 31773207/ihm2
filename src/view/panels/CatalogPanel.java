/*package view.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Book;
import view.components.BookCard;

public class CatalogPanel extends JPanel {

    private JComboBox<String> genreBox; // dropdown to filter by genre

    public CatalogPanel() {
        setBackground(new Color(218, 168, 131));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===================== TITLE =====================
        JLabel title = new JLabel("Books Catalogue", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        title.setForeground(new Color(110, 60, 16));
        add(title);

        // ===================== DROPDOWN FILTER =====================
        genreBox = new JComboBox<>(new String[]{"All", "Romance", "Fantasy", "Drama", "Sci-Fi", "Horror"});
       

        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(new Color(218, 168, 131));
        filterPanel.add(new JLabel("Filter by Genre:"));
        filterPanel.add(genreBox);
        add(filterPanel);
    }

    // ==================== DISPLAY BOOKS (for controller) =====================
    public void displayBooks(List<Book> books) {
        // Remove previous book rows but keep title + filter panel
        Component[] components = getComponents();
        for (int i = components.length - 1; i >= 2; i--) { // 0=title, 1=filter panel
            remove(components[i]);
        }

        int booksPerRow = 4; // 4 cards per row
        for (int i = 0; i < books.size(); i += booksPerRow) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
            row.setBackground(new Color(218, 168, 131));
            for (int j = i; j < i + booksPerRow && j < books.size(); j++) {
                BookCard card = new BookCard(books.get(j));
                row.add(card);
            }
            add(row);
        }

        revalidate();
        repaint();
    }

    // Getter for controller
    public JComboBox<String> getGenreBox() {
        return genreBox;
    }
      @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();
        
        // WHITE background like your design image
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        
        g2.dispose();
    }
}

*/
package view.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Book;
import view.components.BookCard;

public class CatalogPanel extends JPanel {

    private JComboBox<String> genreBox;
    private JComboBox<String> sortBox;
    private JButton clearFiltersBtn;
    private JPanel booksGrid;

    public CatalogPanel() {
        // Set to transparent so gradient shows through
        setOpaque(false);
        setLayout(new BorderLayout());

        // ===================== MAIN PANEL =====================
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false); // Transparent to show gradient
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));
        
        // ===================== HEADER =====================
        JLabel title = new JLabel("Complete Catalogue");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(new Color(110, 60, 16)); // Dark brown text
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Explore our complete collection of over 10,000 carefully selected books");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(new Color(110, 60, 16)); // Dark brown text
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // ===================== FILTER BAR =====================
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        filterBar.setOpaque(false); // Transparent
        filterBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        // All Genres (styled as label + dropdown)
        JLabel allGenresLabel = new JLabel("All Genres");
        allGenresLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        allGenresLabel.setForeground(new Color(110, 60, 16)); // Dark brown text
        
        genreBox = new JComboBox<>(new String[]{"All", "Fantasy", "Romance", "Sci-Fi", "Mystery", "Horror", "Drama"});
        genreBox.setFont(new Font("Arial", Font.PLAIN, 20));
        genreBox.setPreferredSize(new Dimension(120, 40));
genreBox.setBorder(BorderFactory.createLineBorder(new Color(110, 60, 16)));
        genreBox.setForeground(new Color(110, 60, 16));
        
        // Sort by (label + dropdown)
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        sortLabel.setForeground(new Color(110, 60, 16)); // Dark brown text
        
        sortBox = new JComboBox<>(new String[]{"Title", "Price: Low to High", "Price: High to Low", "Author", "Rating"});
        sortBox.setFont(new Font("Arial", Font.PLAIN, 14));
        sortBox.setPreferredSize(new Dimension(150, 40));
        sortBox.setForeground(new Color(110, 60, 16));
        
        // Clear Filters button
        clearFiltersBtn = new JButton("Clear Filters");
clearFiltersBtn.setFont(new Font("Arial", Font.PLAIN, 16));
clearFiltersBtn.setBackground(new Color(255, 229, 204));
// clearFiltersBtn.setForeground(new Color(110, 60, 16)); // Dark brown text
clearFiltersBtn.setBorder(BorderFactory.createLineBorder(new Color(110, 60, 16)));
clearFiltersBtn.setFocusPainted(false);

// ========== ADD SIZE SETTINGS ==========
clearFiltersBtn.setPreferredSize(new Dimension(140, 40));
clearFiltersBtn.setMaximumSize(new Dimension(140, 40));
clearFiltersBtn.setMinimumSize(new Dimension(140, 40));
        // Add to filter bar
        filterBar.add(allGenresLabel);
        filterBar.add(genreBox);
        filterBar.add(Box.createRigidArea(new Dimension(20, 0)));
        //filterBar.add(allPricesLabel);
        filterBar.add(Box.createRigidArea(new Dimension(20, 0)));
        filterBar.add(sortLabel);
        filterBar.add(sortBox);
        filterBar.add(Box.createRigidArea(new Dimension(20, 0)));
        filterBar.add(clearFiltersBtn);
             
        // ===================== BOOKS GRID =====================
        booksGrid = new JPanel(new GridLayout(0, 4, 25, 35)); // 3 COLUMNS for larger images
        booksGrid.setOpaque(false); // Transparent to show gradient
        
        JScrollPane scrollPane = new JScrollPane(booksGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        // ===================== ASSEMBLE MAIN PANEL =====================
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(filterBar);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        //mainPanel.add(separator);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(scrollPane);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    // ==================== DISPLAY BOOKS =====================
    public void displayBooks(List<Book> books) {
        booksGrid.removeAll();
        
        for (Book book : books) {
            BookCard card = new BookCard(book);
            booksGrid.add(card);
        }
        
        // Add empty panels if needed to maintain grid layout
        int remainder = 3 - (books.size() % 3); // Changed from 4 to 3
        if (remainder != 3) { // Changed from 4 to 3
            for (int i = 0; i < remainder; i++) {
                JPanel empty = new JPanel();
                empty.setOpaque(false); // Transparent
                booksGrid.add(empty);
            }
        }
        
        booksGrid.revalidate();
        booksGrid.repaint();
    }

    // ==================== GETTERS =====================
    public JComboBox<String> getGenreBox() {
        return genreBox;
    }
    
    public JComboBox<String> getSortBox() {
        return sortBox;
    }
    
    public JButton getClearFiltersBtn() {
        return clearFiltersBtn;
    }
    
    public JPanel getBooksGrid() {
        return booksGrid;
    }
    
  @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g.create();
    int w = getWidth();
    int h = getHeight();

    // EXACTLY YOUR 2 COLORS:
    Color c1 = new Color(255, 240, 240); // Light pink on LEFT
    Color c2 = new Color(235, 235, 255); // Light blue on RIGHT
    
    // HORIZONTAL gradient from left to right
    GradientPaint gp = new GradientPaint(0, 0, c1, w, 0, c2);

    g2.setPaint(gp);
    g2.fillRect(0, 0, w, h);

    g2.dispose();
}
}