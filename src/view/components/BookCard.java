/*package view.components; // ou panels selon structure dyalek

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import model.Book;

public class BookCard extends JPanel {

    public BookCard(Book book) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new MatteBorder(2, 2, 2, 2, Color.white));
        setBackground(new Color(238, 195, 162));
        setPreferredSize(new Dimension(300, 520));
        setMaximumSize(new Dimension(300, 520));
        setOpaque(true);

        // ======================== Image ===================================
        JLabel bookImage = new JLabel();
        bookImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookImage.setBorder(new MatteBorder(2, 2, 2, 2, Color.white));

        java.net.URL imgURL = getClass().getResource(book.getImagePath());
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH);
            bookImage.setIcon(new ImageIcon(img));
            bookImage.setPreferredSize(new Dimension(250, 600));
            bookImage.setMaximumSize(new Dimension(250, 600));
        } else {
            bookImage.setText("No Image");
            bookImage.setHorizontalAlignment(SwingConstants.CENTER);
            bookImage.setPreferredSize(new Dimension(250, 600));
            bookImage.setMaximumSize(new Dimension(250, 600));
        }
 

        // =================== Title ==================
        JLabel bookTitle = new JLabel(book.getTitle());
        bookTitle.setFont(new Font("Arial", Font.BOLD, 18));
        bookTitle.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        bookTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===================== Author ===================
        JLabel author = new JLabel(book.getAuthor());
        author.setFont(new Font("Arial", Font.ITALIC, 15));
        author.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ========================= Price =========================
        JLabel price = new JLabel("$" + book.getPrice());
        price.setFont(new Font("Arial", Font.BOLD, 20));
        price.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ==================== Description ======================
        JTextArea description = new JTextArea(book.getDescription());
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        description.setOpaque(false);
        description.setBackground(new Color(0,0,0,0));
        // limit description so it doesn't push the button out of the visible area
        description.setMaximumSize(new Dimension(260, 96));
        description.setPreferredSize(new Dimension(260, 96));

        // Use a JLayeredPane so the favorite heart is absolutely positioned
        // over the image without affecting the layout or preferred sizes.
        JLayeredPane imageLayer = new JLayeredPane();
        imageLayer.setPreferredSize(new Dimension(250, 300));
        imageLayer.setMaximumSize(new Dimension(250, 300));
        imageLayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // place the image at 0,0 inside the layered pane
        bookImage.setBounds(0, 0, 250, 300);
        imageLayer.add(bookImage, JLayeredPane.DEFAULT_LAYER);

        // no favorites overlay (feature removed) — only the image is added to the layered pane

        // =========================== Button =============================
        JButton addToCart = new JButton("Add to Cart");
        addToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCart.setFont(new Font("SansSerif", Font.BOLD, 16));
        addToCart.setBackground(new Color(110, 60, 16));
        addToCart.setForeground(Color.WHITE);
        addToCart.setFocusPainted(false);
        addToCart.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addToCart.setPreferredSize(new Dimension(200, 40));
        addToCart.setMaximumSize(new Dimension(220, 40));
        addToCart.addActionListener(e -> {
            controller.CartController.getInstance().addBook(book);
            Window owner = SwingUtilities.getWindowAncestor(this);
            showAddToCartToast(owner, book.getTitle() + " added to cart!");
        });


        // ============================ Add all components ===================
        add(imageLayer);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(bookTitle);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(author);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(description);
        add(price);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(addToCart);
    }

    private void showAddToCartToast(Window owner, String message) {
        if (owner == null) owner = JOptionPane.getRootFrame();

        final JWindow toast = new JWindow(owner);

        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 197, 94));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        content.setOpaque(false);
        content.setLayout(new BorderLayout(10, 0));
        content.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 18));

        // create a simple white check icon
        int iconSize = 18;
        BufferedImage iconImg = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig = iconImg.createGraphics();
        ig.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ig.setColor(Color.white);
        ig.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        ig.drawLine(4, iconSize/2, iconSize/2 - 1, iconSize - 5);
        ig.drawLine(iconSize/2 - 1, iconSize - 5, iconSize - 3, 5);
        ig.dispose();

        JLabel iconLabel = new JLabel(new ImageIcon(iconImg));
        iconLabel.setOpaque(false);

        JLabel text = new JLabel(message);
        text.setForeground(Color.white);
        text.setFont(new Font("SansSerif", Font.BOLD, 14));

        content.add(iconLabel, BorderLayout.WEST);
        content.add(text, BorderLayout.CENTER);

        toast.getContentPane().add(content);
        toast.pack();

        int toastW = toast.getWidth();
        int toastH = toast.getHeight();
        Point ownerLoc = owner.getLocationOnScreen();
        int x = ownerLoc.x + owner.getWidth() - toastW - 20;
        int y = ownerLoc.y + 60;
        toast.setLocation(x, y);
        toast.setAlwaysOnTop(true);
        toast.setVisible(true);

        // auto-hide after 2 seconds
        new javax.swing.Timer(2000, ev -> {
            toast.setVisible(false);
            toast.dispose();
        }) {{ setRepeats(false); }}.start();
    }
}
/* 
package view.components;

import java.awt.*;
import javax.swing.*;
import model.Book;

public class BookCard extends JPanel {

    public BookCard(Book book) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12) // Less padding for more image space
        ));
        setPreferredSize(new Dimension(300, 620)); // Taller for bigger image
        setMaximumSize(new Dimension(300, 620));
        setOpaque(true);

        // ======================== LARGER BOOK COVER IMAGE ===================================
        JLabel bookImage = new JLabel();
        bookImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookImage.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        bookImage.setBackground(new Color(245, 245, 245));
        bookImage.setOpaque(true);
        bookImage.setHorizontalAlignment(SwingConstants.CENTER);
        bookImage.setVerticalAlignment(SwingConstants.CENTER);

        // Load image from resources
        try {
            java.net.URL imgURL = getClass().getResource(book.getImagePath());
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                // LARGER image - takes more space!
                Image img = icon.getImage().getScaledInstance(350, 320, Image.SCALE_SMOOTH); // BIGGER!
                bookImage.setIcon(new ImageIcon(img));
            } else {
                // Image not found - show larger placeholder
                bookImage.setText("<html><div style='text-align:center;color:#999;font-size:14px;padding:40px'>"
                    + "BOOK COVER<br/><br/>" + book.getTitle() + "</div></html>");
            }
        } catch (Exception e) {
            bookImage.setText("<html><div style='text-align:center;color:#999;font-size:14px;padding:40px'>"
                + "IMAGE<br/>NOT FOUND</div></html>");
        }
        
        // LARGER image container
       // Use full card width
Image img = icon.getImage().getScaledInstance(276, 360, Image.SCALE_SMOOTH); // 300 - 12*2 = 276
bookImage.setPreferredSize(new Dimension(276, 360));
bookImage.setMaximumSize(new Dimension(276, 360));
bookImage.setMinimumSize(new Dimension(276, 360));

        // ======================== TITLE ===================================
        String cleanTitle = book.getTitle().replaceAll("<[^>]*>", "");
        JLabel bookTitle = new JLabel("<html><div style='text-align:center;width:260px;font-weight:bold;font-size:16px;color:#000000;margin-top:10px'>" + 
                                      cleanTitle + "</div></html>");
        bookTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 6, 0));

        // ===================== AUTHOR ===================
        String cleanAuthor = book.getAuthor().replaceAll("<[^>]*>", "");
        JLabel author = new JLabel("<html><div style='text-align:center;width:260px;font-size:13px;color:#666666;font-style:italic;'>" + 
                                   cleanAuthor + "</div></html>");
        author.setAlignmentX(Component.CENTER_ALIGNMENT);
        author.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // ==================== DESCRIPTION ======================
        String cleanDesc = book.getDescription().replaceAll("<[^>]*>", "");
        if (cleanDesc.length() > 60) { // Shorter to make room for bigger image
            cleanDesc = cleanDesc.substring(0, 60) + "...";
        }
        JLabel description = new JLabel("<html><div style='text-align:center;width:260px;color:#666666;font-size:12px;line-height:1.3;'>" + 
                                       cleanDesc + "</div></html>");
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // ==================== PRICE AND RATING ROW ======================
        JPanel priceRatingRow = new JPanel();
        priceRatingRow.setLayout(new BoxLayout(priceRatingRow, BoxLayout.X_AXIS));
        priceRatingRow.setBackground(Color.WHITE);
        priceRatingRow.setMaximumSize(new Dimension(260, 25));
        priceRatingRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceRatingRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        // ========= LEFT: Price =========
        JLabel price = new JLabel("$" + String.format("%.2f", book.getPrice()));
        price.setFont(new Font("Arial", Font.BOLD, 17));
        price.setForeground(new Color(110, 60, 16)); // Dark brown
        price.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ========= RIGHT: Rating Stars =========
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        ratingPanel.setBackground(Color.WHITE);
        
        double rating = 4.5;
        
        // Create star rating
        for (int i = 0; i < 5; i++) {
            JLabel star = new JLabel("★");
            star.setFont(new Font("Arial", Font.PLAIN, 15));
            if (i < Math.floor(rating)) {
                star.setForeground(Color.BLACK);
            } else {
                star.setForeground(Color.LIGHT_GRAY);
            }
            ratingPanel.add(star);
        }
        
        // Add rating number
        JLabel ratingText = new JLabel("(" + rating + ")");
        ratingText.setFont(new Font("Arial", Font.PLAIN, 13));
        ratingText.setForeground(Color.DARK_GRAY);
        ratingPanel.add(ratingText);
        
        // Add components to priceRatingRow
        priceRatingRow.add(price);
        priceRatingRow.add(Box.createHorizontalGlue());
        priceRatingRow.add(ratingPanel);

        // =========================== BUTTON =============================
        JButton addToCart = new JButton("Add to Cart");
        addToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCart.setFont(new Font("Arial", Font.BOLD, 14));
        addToCart.setBackground(new Color(110, 60, 16)); // Dark brown
        addToCart.setForeground(Color.WHITE);
        addToCart.setFocusPainted(false);
        addToCart.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        addToCart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addToCart.setPreferredSize(new Dimension(150, 40));
        addToCart.setMaximumSize(new Dimension(150, 40));

        addToCart.addActionListener(e -> {
            // Your cart controller code
            // controller.CartController.getInstance().addBook(book);
            showAddToCartToast(SwingUtilities.getWindowAncestor(this), 
                             cleanTitle + " added to cart!");
        });

        // ============================ Add all components ===================
        add(bookImage);       // BIG IMAGE at top (takes most space)
        add(bookTitle);       // Title
        add(author);          // Author
        add(description);     // Short description
        add(priceRatingRow);  // Price and rating
        add(addToCart);       // Button
        add(Box.createVerticalGlue());
    }

    private void showAddToCartToast(Window owner, String message) {
        if (owner == null) owner = JOptionPane.getRootFrame();

        final JWindow toast = new JWindow(owner);

        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 197, 94));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(messageLabel);
        
        toast.add(content);
        toast.pack();
        
        Point ownerLoc = owner.getLocationOnScreen();
        int x = ownerLoc.x + (owner.getWidth() - toast.getWidth()) / 2;
        int y = ownerLoc.y + 100;
        toast.setLocation(x, y);
        
        toast.setVisible(true);

        new Timer(2000, e -> toast.dispose()).start();
    }
}
}*/
package view.components;

import controller.CartController;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import model.Book;
public class BookCard extends JPanel {

    public BookCard(Book book) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // ========== IMPORTANT: Set to transparent so gradient shows ==========
        setOpaque(false); // Changed from true to false
        /*setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));*/
setBorder(new RoundedBorder(25, new Color(230, 230, 230), 2));

        setPreferredSize(new Dimension(300, 600));
        setMaximumSize(new Dimension(300, 600));

        // ======================== LARGER BOOK COVER IMAGE ===================================
       JLabel bookImage = new JLabel() {
    @Override
    protected void paintComponent(Graphics g) {
        // Create a rounded clip
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int arc = 25; // Match your border radius
        
        // Set rounded clipping area
        Shape clip = new RoundRectangle2D.Double(0, 0, width, height, arc, arc);
        g2.setClip(clip);
        
        // Let JLabel paint normally (icon/text) within the clip
        super.paintComponent(g2);
        
        g2.dispose();
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        // Paint the rounded border
        super.paintBorder(g);
    }
};

bookImage.setAlignmentX(Component.CENTER_ALIGNMENT);
bookImage.setBackground(new Color(245, 245, 245));
bookImage.setOpaque(true);
bookImage.setHorizontalAlignment(SwingConstants.CENTER);
bookImage.setVerticalAlignment(SwingConstants.CENTER);
bookImage.setBorder(new RoundedBorder(25, new Color(253, 229, 193), 2));

// Load image from resources
try {
    java.net.URL imgURL = getClass().getResource(book.getImagePath());
    if (imgURL != null) {
        ImageIcon icon = new ImageIcon(imgURL);
        // Scale to fit the rounded area (slightly smaller to account for border)
        Image img = icon.getImage().getScaledInstance(256, 316, Image.SCALE_SMOOTH); // 260-4, 320-4
        bookImage.setIcon(new ImageIcon(img));
        bookImage.setText("");
    } else {
        bookImage.setText("<html><div style='text-align:center;color:#999;font-size:14px;padding:40px'>"
            + "BOOK COVER<br/><br/>" + book.getTitle() + "</div></html>");
    }
} catch (Exception e) {
    bookImage.setText("<html><div style='text-align:center;color:#999;font-size:14px;padding:40px'>"
        + "IMAGE<br/>NOT FOUND</div></html>");
}

bookImage.setPreferredSize(new Dimension(260, 320));
bookImage.setMaximumSize(new Dimension(260, 320));
bookImage.setMinimumSize(new Dimension(260, 320));

        // ======================== WISHLIST HEART OVERLAY ===================================
        JLayeredPane imageLayer = new JLayeredPane();
        imageLayer.setPreferredSize(new Dimension(280, 340));
        imageLayer.setMaximumSize(new Dimension(280, 340));
        imageLayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Place the image at (10,10) inside the layered pane
        bookImage.setBounds(10, 10, 260, 320);
        imageLayer.add(bookImage, JLayeredPane.DEFAULT_LAYER);

        // Add wishlist heart button overlay at top-right
        JButton wishlistBtn = new JButton("\u2661");
        wishlistBtn.setFont(new Font("Arial", Font.PLAIN, 24));
        wishlistBtn.setForeground(new Color(200, 200, 200));
        wishlistBtn.setContentAreaFilled(false);
        wishlistBtn.setBorderPainted(false);
        wishlistBtn.setFocusPainted(false);
        wishlistBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        wishlistBtn.setBounds(240, 10, 40, 40);
        
        wishlistBtn.addActionListener(e -> {
            if (wishlistBtn.getForeground().equals(new Color(200, 200, 200))) {
                wishlistBtn.setForeground(new Color(220, 0, 0)); // Red heart when added
            } else {
                wishlistBtn.setForeground(new Color(200, 200, 200)); // Gray when removed
            }
        });
        
        imageLayer.add(wishlistBtn, JLayeredPane.PALETTE_LAYER);

        // ======================== TITLE ===================================
        String cleanTitle = book.getTitle().replaceAll("<[^>]*>", "");
        JLabel bookTitle = new JLabel("<html><div style='text-align:left;width:260px;font-weight:bold;font-size:14px;color:#6E3C10;margin-top:10px'>" + 
                                      cleanTitle + "</div></html>");
        bookTitle.setForeground(new Color(110, 60, 16));
        bookTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 6, 0));

        // ===================== AUTHOR ===================
        String cleanAuthor = book.getAuthor().replaceAll("<[^>]*>", "");
        JLabel author = new JLabel("<html><div style='text-align:left;width:260px;font-size:10px;color:#6E3C10;font-style:italic;'>" + 
                                   cleanAuthor + "</div></html>");
        author.setAlignmentX(Component.CENTER_ALIGNMENT);
        author.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        // ==================== DESCRIPTION ======================
        String cleanDesc = book.getDescription().replaceAll("<[^>]*>", "");
        if (cleanDesc.length() > 60) {
            cleanDesc = cleanDesc.substring(0, 60) + "...";
        }
        JLabel description = new JLabel("<html><div style='text-align:left;width:260px;color:#C87832;font-size:10px;line-height:1.3;'>" + 
                                       cleanDesc + "</div></html>");
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); 

        // ==================== PRICE AND RATING ROW ======================
        JPanel priceRatingRow = new JPanel();
        priceRatingRow.setLayout(new BoxLayout(priceRatingRow, BoxLayout.X_AXIS));
        // ========== Set to transparent so gradient shows through ==========
        priceRatingRow.setOpaque(false); // Changed from true to false
        priceRatingRow.setMaximumSize(new Dimension(260, 25));
        priceRatingRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceRatingRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        // ========= LEFT: Price =========
        JLabel price = new JLabel( String.format("%.2f DZD", book.getPrice()));
        price.setFont(new Font("Arial", Font.BOLD, 20));
        price.setForeground(new Color(152, 80, 32));
        price.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ========= RIGHT: Rating Stars =========
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        // ========== Set to transparent so gradient shows through ==========
        ratingPanel.setOpaque(false); // Changed from true to false
        
        double rating = 4.5;
        
        // Create star rating
        for (int i = 0; i < 5; i++) {
JLabel star = new JLabel("<html><font color='#FFD700'>★</font></html>");
            star.setFont(new Font("Arial", Font.PLAIN, 15));
            if (i < Math.floor(rating)) {
                star.setForeground(Color.BLACK);
            } else {
                star.setForeground(Color.LIGHT_GRAY);
            }
            ratingPanel.add(star);
        }
        
        // Add rating number
        JLabel ratingText = new JLabel("(" + rating + ")");
        ratingText.setFont(new Font("Arial", Font.PLAIN, 13));
        ratingText.setForeground(Color.DARK_GRAY);
        ratingPanel.add(ratingText);
        
        // Add components to priceRatingRow
        priceRatingRow.add(price);
        priceRatingRow.add(Box.createHorizontalGlue());
        priceRatingRow.add(ratingPanel);



    


        // =========================== BUTTON =============================
        JButton addToCart = new JButton("Add to Cart");
        addToCart.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToCart.setFont(new Font("Arial", Font.BOLD, 14));
        addToCart.setBackground(new Color(140, 63, 13));
        addToCart.setForeground(Color.WHITE);
        addToCart.setFocusPainted(false);
        addToCart.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        addToCart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addToCart.setPreferredSize(new Dimension(250, 40));
        addToCart.setMaximumSize(new Dimension(250, 40));

       addToCart.addActionListener(e -> {
    // Get the CartController instance and add the book
    CartController cartController = CartController.getInstance();
    cartController.addBook(book);
    
    // Show toast notification
    showAddToCartToast(SwingUtilities.getWindowAncestor(this), 
                     cleanTitle + " added to cart!");
});

        // ============================ Add all components ===================
        add(imageLayer);  // Use layered pane with image + wishlist button
        add(bookTitle);
        add(author);
        add(description);
        add(priceRatingRow);
        add(addToCart);
        add(Box.createVerticalGlue());
    }

  /*  @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g.create();
    int w = getWidth();
    int h = getHeight();
    
    // Create gradient from LIGHT COLOR (top) to F9D399 (bottom)
   // For example: light peach color
Color colorTop = new Color(255, 245, 235); // Light peach
Color colorBottom = new Color(249, 211, 153); // F9D399

    // Create vertical gradient - Light cream at top, F9D399 at bottom
    GradientPaint gradient = new GradientPaint(
        0, 0, colorTop,    // Very light cream at top
        0, h, colorBottom  // F9D399 at bottom
    );
    
    g2.setPaint(gradient);
    g2.fillRect(0, 0, w, h);
    
    // Add a subtle shadow effect
    g2.setColor(new Color(0, 0, 0, 10));
    g2.drawRect(0, 0, w - 1, h - 1);
    
    g2.dispose();

    } */
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    int w = getWidth();
    int h = getHeight();
    int arc = 50; // Match your RoundedBorder(50, ...)
    
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // JUST the gradient background - NO shadow
    //GradientPaint gp = new GradientPaint(0, 0, new Color(255, 245, 230), 0, h, new Color(253, 229, 193));
    GradientPaint gp = new GradientPaint(0, 0, new Color(254, 235, 211), 0, h, new Color(254, 218, 177));
    g2.setPaint(gp);
    g2.fillRoundRect(0, 0, w, h, arc, arc);

    // ADD THIS LINE FOR SMALL SHADOW:
    g2.setColor(new Color(0, 0, 0, 5));
    g2.drawRoundRect(1, 1, w - 2, h - 2, arc, arc);

    g2.dispose();
}


    /**
     * Set a click listener for the bookmark/free button
     */
    public void setFreeClickListener(java.awt.event.ActionListener listener) {
        // This method is a placeholder for future bookmark functionality
        // Current implementation doesn't have a separate free button
    }

    private void showAddToCartToast(Window owner, String message) {
        if (owner == null) owner = JOptionPane.getRootFrame();

        final JWindow toast = new JWindow(owner);

        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(34, 197, 94));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        content.add(messageLabel);
        
        toast.add(content);
        toast.pack();
        
        Point ownerLoc = owner.getLocationOnScreen();
        int x = ownerLoc.x + (owner.getWidth() - toast.getWidth()) / 2;
        int y = ownerLoc.y + 100;
        toast.setLocation(x, y);
        
        toast.setVisible(true);

        new Timer(2000, e -> toast.dispose()).start();
    }


}
