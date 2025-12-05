package view.panels;

import java.awt.*;
import javax.swing.*;
import view.frames.MainFrame;
public class HomePanel extends JPanel {

    public HomePanel(MainFrame mainFrame) {
        setPreferredSize(new Dimension(800, 750));
        // keep panel background, but gradient is painted in paintComponent
        setLayout(new GridLayout(1, 2)); // 2 columns : left / right

        // ===== Left Panel =====
        JPanel leftPanel = new JPanel();
        // make it non-opaque so the gradient from HomePanel shows through
        leftPanel.setOpaque(false);
        // you can keep the visual "background color" for components inside
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(120, 50, 50, 40));

        // Title
        JLabel title = new JLabel("<html>Discover Literary <br> Treasures</html>");
        title.setFont(new Font("SansSerif", Font.BOLD, 50));
        title.setForeground(new Color(110, 60, 16));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setOpaque(false);

        // Description
        JLabel desc = new JLabel("<html>Immerse yourself in a curated collection of timeless classics and contemporary masterpieces. "
                + "Every book opens a door to new worlds of knowledge and imagination. "
                + "Join thousands of readers who have found their literary sanctuary with us.</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 20));
        desc.setForeground(new Color(110, 60, 16));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        desc.setOpaque(false);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        buttonPanel.setOpaque(false);

        JButton startReading = new JButton("Start Reading");
        startReading.setFont(new Font("SansSerif", Font.BOLD, 20));
        startReading.setBackground(new Color(137, 61, 14));
        startReading.setForeground(Color.WHITE);
        startReading.setFocusPainted(false);
        startReading.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Button action
        startReading.addActionListener(e -> {
            mainFrame.scrollToPanel(mainFrame.getCatalogPanel());
        });

        JButton freeClassics = new JButton("Free Classics");
        freeClassics.setFont(new Font("SansSerif", Font.BOLD, 20));
        freeClassics.setBackground(new Color(234, 215, 228));
        freeClassics.setForeground(new Color(110, 60, 16));
        freeClassics.setFocusPainted(false);
        freeClassics.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(219, 192, 195), 1),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));

        buttonPanel.add(startReading);
        buttonPanel.add(freeClassics);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 15));
        statsPanel.setOpaque(false);
        statsPanel.add(createStat("📚 10,000+", "Books Available"));
        statsPanel.add(createStat("😊 25,000+", "Happy Readers"));
        statsPanel.add(createStat("📖 50+", "Genres"));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components to left panel
        leftPanel.add(title);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(desc);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(statsPanel);

// ===== Right Panel (Image) =====
JPanel rightPanel = new JPanel(new BorderLayout());
rightPanel.setOpaque(false);

// Load image
ImageIcon icon = new ImageIcon("resources/icons/book2.jpg");
Image img = icon.getImage();
Image newImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH); // Small size
final ImageIcon resizedIcon = new ImageIcon(newImg);

// Create image panel with rounded corners
JPanel imageContainer = new JPanel() {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int arc = 25;
        
        g2.setClip(new java.awt.geom.RoundRectangle2D.Double(0, 0, width, height, arc, arc));
        if (resizedIcon != null) {
            g2.drawImage(resizedIcon.getImage(), 0, 0, width, height, this);
        }
        
        g2.dispose();
    }
    
    @Override
    public void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int arc = 25;
        
        g2.setColor(new Color(253, 229, 193));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, width - 3, height - 3, arc, arc);
        
        g2.dispose();
    }
};

imageContainer.setPreferredSize(new Dimension(500, 500));
imageContainer.setOpaque(false);

// Create wrapper panel to center the image
// Create wrapper panel to center the image vertically and horizontally
// Create wrapper panel to center the image


// Create wrapper panel to center the image
JPanel wrapper = new JPanel();
wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS)); // Vertical layout
wrapper.setOpaque(false);
wrapper.add(Box.createVerticalGlue()); // Push down
wrapper.add(imageContainer);
wrapper.add(Box.createVerticalGlue()); // Push up

rightPanel.add(wrapper, BorderLayout.CENTER);

rightPanel.add(wrapper, BorderLayout.CENTER);
        // Add panels to main layout (HomePanel)
        add(leftPanel);
        add(rightPanel);
    }

    // ===== Stat Panel Generator =====
    private JPanel createStat(String value, String label) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        JLabel v = new JLabel(value);
        v.setFont(new Font("SansSerif", Font.BOLD, 35));
        v.setForeground(new Color(110, 60, 16));
        v.setAlignmentX(Component.CENTER_ALIGNMENT);
        v.setOpaque(false);

        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.PLAIN, 20));
        l.setForeground(new Color(110, 60, 16));
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        l.setOpaque(false);

        p.add(v);
        p.add(Box.createVerticalStrut(5));
        p.add(l);

        return p;
    }

    // ===== Background Gradient =====
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();

        // EXACTLY YOUR 2 COLORS:
        Color c1 = new Color(250, 231, 245); // Light pink
        Color c2 = new Color(221, 217, 253); // Light blue
        GradientPaint gp = new GradientPaint(0, 0, c1, 0, h, c2);

        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        g2.dispose();
    }
}