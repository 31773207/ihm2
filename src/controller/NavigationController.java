package controller;

// Handles menu clicks and screen changes
import java.awt.*;
import javax.swing.*;
import view.components.NavigationBar;
import view.frames.MainFrame;
import view.dialogs.AccountDialog;
import service.UserService;

public class NavigationController {

    private MainFrame frame;
    private NavigationBar navBar;
    private JDialog cartDialog;

    public NavigationController(MainFrame frame, NavigationBar navBar) {
        this.frame = frame;
        this.navBar = navBar;

        // add listeners
        addListeners();
    }

    private void addListeners() {
        navBar.getHomeButton().addActionListener(e -> frame.scrollToPanel(frame.getHomePanel()));
        navBar.getFeaturedButton().addActionListener(e -> frame.scrollToPanel(frame.getFeaturedPanel()));
        navBar.getCatalogueButton().addActionListener(e -> frame.scrollToPanel(frame.getCatalogPanel()));
        navBar.getGenreButton().addActionListener(e -> frame.scrollToPanel(frame.getGenresPanel()));
        navBar.getAuthorsButton().addActionListener(e -> frame.scrollToPanel(frame.getAuthorsPanel()));
        // Show login/account popup when login button is clicked
        navBar.getLoginButton().addActionListener(e -> handleLoginAccountButtonClick());
            // Show cart popup when cart button is clicked
            navBar.getCartButton().addActionListener(e -> toggleCartPopup());
    }

    private void handleLoginAccountButtonClick() {
        if (UserService.getInstance().isLoggedIn()) {
            // User is logged in - show account dialog
            showAccountPopup();
        } else {
            // User is not logged in - show login dialog
            showLoginPopup();
        }
    }

    private void toggleCartPopup() {
        if (cartDialog != null && cartDialog.isShowing()) {
            cartDialog.dispose();
            cartDialog = null;
        } else {
            createCartDialog();
            cartDialog.setVisible(true);
        }
    }

    private void createCartDialog() {
        Color navBg = new Color(255, 255, 255);
        cartDialog = new JDialog(frame);
        cartDialog.setUndecorated(true);
        cartDialog.setSize(380, frame.getHeight());
        cartDialog.setLayout(new BorderLayout());

        // Position to the right edge of the main frame
        Point loc = frame.getLocationOnScreen();
        cartDialog.setLocation(loc.x + frame.getWidth() - cartDialog.getWidth(), loc.y);

        // Main container with border (simulate shadow)
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(220, 210, 205)));
        container.setBackground(navBg);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navBg);
        header.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JLabel title = new JLabel("Shopping Cart");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(60, 30, 30));
        header.add(title, BorderLayout.WEST);

        JButton close = new JButton("✕");
        close.setBorderPainted(false);
        close.setContentAreaFilled(false);
        close.setFont(new Font("SansSerif", Font.BOLD, 16));
        close.setForeground(new Color(130, 80, 60));
        close.addActionListener(e -> { cartDialog.dispose(); cartDialog = null; });
        header.add(close, BorderLayout.EAST);

        container.add(header, BorderLayout.NORTH);

        // Body: reuse CartPanel from frame if available
        JPanel cartPanel = null;
        try {
            cartPanel = (JPanel) frame.getCartPanel();
        } catch (Exception ex) { cartPanel = null; }
        if (cartPanel == null) cartPanel = new view.panels.CartPanel(controller.CartController.getInstance());

        cartPanel.setPreferredSize(new Dimension(360, frame.getHeight() - 75));

        // CartPanel already contains its own scroll for items; add it directly so
        // the bottom total / checkout bar stays fixed at the bottom of the panel.
        container.add(cartPanel, BorderLayout.CENTER);

        cartDialog.getContentPane().add(container, BorderLayout.CENTER);
        cartDialog.pack();
    }

    private void showAccountPopup() {
        AccountDialog accountDialog = new AccountDialog(frame);
        accountDialog.setVisible(true);
    }

    private void showLoginPopup() {
        JDialog dialog = new JDialog(frame, "Login", true);
        dialog.setSize(520, 520);
        dialog.setUndecorated(true);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(0, 0, 0, 0));

        // Theme colors
        Color accent = new Color(110, 60, 16);
        Color btnBg = new Color(198, 175, 158);

        // Rounded card panel
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(420, 420));

        // Header with icon
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel icon = new JLabel("🔒", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        icon.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        header.add(icon, BorderLayout.NORTH);

        JLabel title = new JLabel("Sign in to your account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(accent);
        title.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));
        header.add(title, BorderLayout.CENTER);

        card.add(header, BorderLayout.NORTH);

        // Form area
        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        styleInput(emailField, new Color(245, 245, 245), accent);
        emailField.setToolTipText("Email");

        JPasswordField passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        styleInput(passField, new Color(245, 245, 245), accent);
        passField.setToolTipText("Password");

        JLabel forgot = new JLabel("Forgot password?", SwingConstants.RIGHT);
        forgot.setForeground(accent);
        forgot.setFont(new Font("Arial", Font.PLAIN, 12));
        forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(dialog, "Password recovery is not implemented yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        form.add(Box.createVerticalStrut(6));
        form.add(emailField);
        form.add(Box.createVerticalStrut(12));
        form.add(passField);
        form.add(Box.createVerticalStrut(6));
        form.add(forgot);
        form.add(Box.createVerticalStrut(16));

        // Buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actions.setOpaque(false);

        JButton close = new JButton("Close");
        close.setBackground(btnBg);
        close.setForeground(accent);
        close.setFocusPainted(false);
        close.setPreferredSize(new Dimension(110, 40));

        JButton loginBtn = new JButton("Sign In");
        loginBtn.setBackground(accent);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setPreferredSize(new Dimension(140, 40));

        actions.add(close);
        actions.add(loginBtn);

        form.add(actions);

        card.add(form, BorderLayout.CENTER);

        // Footer: create account link
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JLabel signup = new JLabel("Don't have an account? Create one", SwingConstants.CENTER);
        signup.setForeground(accent);
        signup.setFont(new Font("Arial", Font.PLAIN, 12));
        signup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signup.setBorder(BorderFactory.createEmptyBorder(8, 12, 16, 12));
        footer.add(signup, BorderLayout.CENTER);
        card.add(footer, BorderLayout.SOUTH);

        dialog.add(card);

        // Behavior
        close.addActionListener(e -> dialog.dispose());
        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passField.getPassword());
            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter email and password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Extract name from email (part before @)
            String name = email.split("@")[0];
            
            // Login the user
            UserService.getInstance().login(email, name);
            
            // Update navigation bar button
            navBar.updateLoginButton(true);
            
            JOptionPane.showMessageDialog(dialog, "Signed in successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    // Helper to style text inputs consistently
    private void styleInput(JComponent comp, Color bg, Color accent) {
        comp.setBackground(bg);
        comp.setForeground(accent);
        comp.setFont(new Font("Arial", Font.PLAIN, 14));
        comp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 160), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }
}
