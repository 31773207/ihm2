// main window
package view.frames;

import controller.BookController;
import controller.NavigationController;
import service.SearchController;
import controller.CartController;
import java.awt.*; // for colors .BorderLayout
import javax.swing.*;
import view.components.NavigationBar;
import view.panels.*;



public class MainFrame extends JFrame {
    private JPanel mainPanel;
    // Panels
    private HomePanel homePanel;
    private FeaturedPanel featuredPanel;
    private CatalogPanel catalogPanel;
    private GenrePanel genresPanel;
    private AuthorsPanel authorsPanel;
    private LoginPanel loginPanel;
    private CartPanel cartPanel; // Custom navigation bar component
    private FooterPanel footerPanel; // Custom navigation bar component
    private NavigationBar navBar;

    // Constructor
    public MainFrame() {
        setTitle("Bookify");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

      
        // ========================== Navigation Bar =======================
        navBar = new NavigationBar();
        add(navBar, BorderLayout.NORTH);

        //====================== Main Panel ====================================
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // ... initialize panels
        homePanel = new HomePanel(this);
        featuredPanel = new FeaturedPanel();
        catalogPanel = new CatalogPanel();
        genresPanel = new GenrePanel();
        authorsPanel = new AuthorsPanel();
        // Cart panel (uses the singleton CartController)
        cartPanel = new CartPanel(CartController.getInstance());
        // Let the controller know about the cart panel so it can refresh it
        CartController.getInstance().setCartPanel(cartPanel);

        mainPanel.add(homePanel);
        mainPanel.add(featuredPanel);
        mainPanel.add(genresPanel);
        mainPanel.add(authorsPanel);
        mainPanel.add(catalogPanel);
        // Cart panel is created and registered with controller, but not added
        // to the main scroll content to avoid showing the persistent checkout bar.

        footerPanel = new FooterPanel();

        // Optional: if you have BookController class
        new BookController(catalogPanel);

        // JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null);

        add(scrollPane);
        // add footer at the end of mainPanel (scrollable)
        FooterPanel footer = new FooterPanel();
        mainPanel.add(footer);

        // link the buttons with Controller
        new NavigationController(this, navBar);

        // Make CartController aware of the main frame so it can update the nav bar
        CartController.getInstance().setFrame(this);

        setVisible(true);
    }

    // this method takes a specific panel and scrolls the page to make it visible.
    public void scrollToPanel(JPanel targetPanel) {
        SwingUtilities.invokeLater(() -> {
            Rectangle rect = targetPanel.getBounds();
            mainPanel.scrollRectToVisible(rect);
        });
    }

    // 🟢 Getters for panels
    public JPanel getHomePanel() { return homePanel; }
    public JPanel getFeaturedPanel() { return featuredPanel; }
    public JPanel getCatalogPanel() { return catalogPanel; }
    public JPanel getGenresPanel() { return genresPanel; }
    public JPanel getAuthorsPanel() { return authorsPanel; }
    public JPanel getLoginPanel() { return loginPanel; }
    public JPanel getCartPanel() { return cartPanel; }
    public FooterPanel getFooterPanel() { return footerPanel; }

    public NavigationBar getNavigationBar() { return navBar; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            // scroll to top by default
            JScrollPane scrollPane = (JScrollPane) frame.getContentPane().getComponent(1); // 0 = navBar, 1 = scrollPane
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
}
