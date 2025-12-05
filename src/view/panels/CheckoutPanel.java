package view.panels;

import java.awt.*;
import javax.swing.*;
import service.CartService;
import service.OrderService;
import model.Book;
import model.Order;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CheckoutPanel extends JPanel {

    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextArea addressArea;
    private JTextField cityField;
    private JTextField postalField;
    private JButton submitBtn;
    private JButton cancelBtn;
    
    // For cart display
    private JLabel orderSummaryLabel;
    private JLabel totalAmountLabel;
    private JPanel cartItemsPanel;
    
    private CartService cartService;

    public CheckoutPanel() {
        this.cartService = CartService.getInstance();
        setLayout(new BorderLayout());
        setOpaque(false);
        initForm();
        updateCartDisplay();
    }
    
    private void updateCartDisplay() {
        List<Book> cartItems = cartService.getCartItems();
        double totalAmount = cartService.getTotalPrice();
        
        // Group books by title and count quantities
        LinkedHashMap<String, Integer> qtyMap = new LinkedHashMap<>();
        LinkedHashMap<String, Book> bookMap = new LinkedHashMap<>();
        for (Book b : cartItems) {
            String key = b.getTitle();
            qtyMap.put(key, qtyMap.getOrDefault(key, 0) + 1);
            if (!bookMap.containsKey(key)) bookMap.put(key, b);
        }
        
        // Update order summary
        if (orderSummaryLabel != null) {
            if (bookMap.isEmpty()) {
                orderSummaryLabel.setText("Your cart is empty");
            } else {
                StringBuilder summary = new StringBuilder();
                for (String key : bookMap.keySet()) {
                    Book b = bookMap.get(key);
                    int qty = qtyMap.get(key);
                    summary.append(b.getTitle())
                          .append(" (")
                          .append(qty)
                          .append("x)");
                    // Add comma if not last item
                    if (!key.equals(bookMap.keySet().toArray()[bookMap.size() - 1])) {
                        summary.append(", ");
                    }
                }
                orderSummaryLabel.setText(summary.toString());
            }
        }
        
        // Update total amount
        if (totalAmountLabel != null) {
            totalAmountLabel.setText(String.format("%.2f DZD", totalAmount));
        }
        
        // Update cart items panel
        if (cartItemsPanel != null) {
            cartItemsPanel.removeAll();
            cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
            
            if (bookMap.isEmpty()) {
                JLabel emptyLabel = new JLabel("Your cart is empty");
                emptyLabel.setFont(new Font("Arial", Font.ITALIC, 19));
                emptyLabel.setForeground(Color.GRAY);
                cartItemsPanel.add(emptyLabel);
            } else {
                for (String key : bookMap.keySet()) {
                    Book book = bookMap.get(key);
                    int qty = qtyMap.get(key);
                    JPanel itemPanel = createCartItemPanel(book, qty);
                    cartItemsPanel.add(itemPanel);
                    cartItemsPanel.add(Box.createVerticalStrut(5));
                }
                
                // Add cart total
                JPanel cartTotalPanel = new JPanel(new BorderLayout());
                cartTotalPanel.setOpaque(false);
                
                JLabel totalText = new JLabel("Total:");
                totalText.setFont(new Font("Arial", Font.BOLD, 19));
                
                JLabel cartTotalLabel = new JLabel(String.format("%.2f DZD", totalAmount));
                cartTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
                
                cartTotalPanel.add(totalText, BorderLayout.WEST);
                cartTotalPanel.add(cartTotalLabel, BorderLayout.EAST);
                cartTotalPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
                
                cartItemsPanel.add(cartTotalPanel);
            }
            
            cartItemsPanel.revalidate();
            cartItemsPanel.repaint();
        }
    }
    
    private JPanel createCartItemPanel(Book book, int quantity) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(book.getTitle());
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel priceLabel = new JLabel(String.format("%.2f DZD each", book.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(Color.GRAY);
        
        // Note: In checkout, you might want to disable removal or handle it differently
        JLabel removeLabel = new JLabel("Remove");
        removeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        removeLabel.setForeground(new Color(200, 0, 0));
        removeLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        removeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int response = JOptionPane.showConfirmDialog(
                    CheckoutPanel.this,
                    "Remove " + book.getTitle() + " from cart?",
                    "Remove Item",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (response == JOptionPane.YES_OPTION) {
                    // Remove one instance of the book from cart
                    cartService.removeFromCart(book);
                    updateCartDisplay();
                }
            }
        });
        
        itemPanel.add(titleLabel, BorderLayout.WEST);
        itemPanel.add(priceLabel, BorderLayout.CENTER);
        itemPanel.add(removeLabel, BorderLayout.EAST);
        
        return itemPanel;
    }

    // Helper method to create colored section panels
    private JPanel createColoredSection(String title, Color bgColor) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(bgColor);
        section.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        if (title != null && !title.isEmpty()) {
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(new Color(110, 60, 16));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            section.add(titleLabel);
            section.add(Box.createVerticalStrut(15));
        }
        
        return section;
    }

    private void initForm() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // === PERSONAL INFORMATION SECTION ===
        JPanel personalSection = createColoredSection("Personal Information", new Color(255, 248, 240)); // Light peach
        
        // Name fields in a row
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 2, 15, 0));
        namePanel.setOpaque(false);
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // First Name
        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.Y_AXIS));
        firstNamePanel.setOpaque(false);
        
        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        firstNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        firstNameField = new JTextField();
        firstNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        firstNameField.setBorder(new javax.swing.border.LineBorder(new Color(255,218,154), 2, true));

        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(Box.createVerticalStrut(5));
        firstNamePanel.add(firstNameField);
        
        // Last Name
        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.Y_AXIS));
        lastNamePanel.setOpaque(false);
        
        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lastNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        lastNameField = new JTextField();
        lastNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        lastNameField.setBorder(new javax.swing.border.LineBorder(new Color(255,218,154), 2, true));

        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(Box.createVerticalStrut(5));
        lastNamePanel.add(lastNameField);
        
        namePanel.add(firstNamePanel);
        namePanel.add(lastNamePanel);
        
        personalSection.add(namePanel);
        personalSection.add(Box.createVerticalStrut(20));
        
        // Contact fields in a row
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new GridLayout(1, 2, 15, 0));
        contactPanel.setOpaque(false);
        contactPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Phone
        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.Y_AXIS));
        phonePanel.setOpaque(false);
        
        JLabel phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        phoneField = new JTextField();
        phoneField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
                phoneField.setBorder(new javax.swing.border.LineBorder(new Color(255,218,154), 2, true));

        phonePanel.add(phoneLabel);
        phonePanel.add(Box.createVerticalStrut(5));
        phonePanel.add(phoneField);
        
        // Email
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setOpaque(false);
        
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        emailField.setBorder(new javax.swing.border.LineBorder(new Color(255,218,154), 2, true));

        emailPanel.add(emailLabel);
        emailPanel.add(Box.createVerticalStrut(5));
        emailPanel.add(emailField);
        
        contactPanel.add(phonePanel);
        contactPanel.add(emailPanel);
        
        personalSection.add(contactPanel);
        
        mainPanel.add(personalSection);
        mainPanel.add(Box.createVerticalStrut(20));

        // === ADDRESS SECTION ===
        JPanel addressSection = createColoredSection("Address", new Color(240, 248, 255)); // Light blue
        
        // Full Address
        JLabel addressLabel = new JLabel("Full Address");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        addressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        addressArea = new JTextArea(1, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        addressArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane addressScroll = new JScrollPane(addressArea);
        addressScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
                addressArea.setBorder(new javax.swing.border.LineBorder(new Color(255,218,154), 2, true));

        addressSection.add(addressLabel);
        addressSection.add(Box.createVerticalStrut(5));
        addressSection.add(addressScroll);
        addressSection.add(Box.createVerticalStrut(15));
        
        // City and Postal Code in a row
        JPanel cityPostalPanel = new JPanel();
        cityPostalPanel.setLayout(new GridLayout(1, 2, 15, 0));
        cityPostalPanel.setOpaque(false);
        cityPostalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // City
        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
        cityPanel.setOpaque(false);
        
        JLabel cityLabel = new JLabel("City");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cityField = new JTextField();
        cityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
                cityField.setBorder(new javax.swing.border.LineBorder(new Color(255,218,154), 2, true));

        cityPanel.add(cityLabel);
        cityPanel.add(Box.createVerticalStrut(5));
        cityPanel.add(cityField);
        
        // Postal Code
        JPanel postalPanel = new JPanel();
        postalPanel.setLayout(new BoxLayout(postalPanel, BoxLayout.Y_AXIS));
        postalPanel.setOpaque(false);
        
        JLabel postalLabel = new JLabel("Postal Code");
        postalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        postalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        postalField = new JTextField();
        postalField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
                postalField.setBorder(new javax.swing.border.LineBorder(new Color(255,218,154), 2, true));

        postalPanel.add(postalLabel);
        postalPanel.add(Box.createVerticalStrut(5));
        postalPanel.add(postalField);
        
        cityPostalPanel.add(cityPanel);
        cityPostalPanel.add(postalPanel);
        
        addressSection.add(cityPostalPanel);
        
        mainPanel.add(addressSection);
        mainPanel.add(Box.createVerticalStrut(20));

        // === ORDER SUMMARY & SHOPPING CART SECTION (Same Color) ===
        Color cartSummaryColor = new Color(240, 255, 240); // Light green
        
        // ORDER SUMMARY SECTION
        JPanel summarySection = createColoredSection("Order Summary", cartSummaryColor);
        
        // Order summary label
        orderSummaryLabel = new JLabel("Loading...");
        orderSummaryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        orderSummaryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        summarySection.add(orderSummaryLabel);
        summarySection.add(Box.createVerticalStrut(20));
        
        // Total amount
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setOpaque(false);
        totalPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel totalText = new JLabel("Total Amount");
        totalText.setFont(new Font("Arial", Font.BOLD, 16));
        
        totalAmountLabel = new JLabel("0.00 DZD");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmountLabel.setForeground(new Color(110, 60, 16));
        
        totalPanel.add(totalText, BorderLayout.WEST);
        totalPanel.add(totalAmountLabel, BorderLayout.EAST);
        
        summarySection.add(totalPanel);
        
        mainPanel.add(summarySection);
        mainPanel.add(Box.createVerticalStrut(20));

        // SHOPPING CART SECTION (Same color as Order Summary)
        JPanel cartSection = new JPanel();
        cartSection.setLayout(new BoxLayout(cartSection, BoxLayout.Y_AXIS));
        cartSection.setBackground(cartSummaryColor);
        cartSection.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        cartSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel cartTitle = new JLabel("Shopping Cart");
        cartTitle.setFont(new Font("Arial", Font.BOLD, 20));
        cartTitle.setForeground(new Color(110, 60, 16));
        cartTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        cartSection.add(cartTitle);
        cartSection.add(Box.createVerticalStrut(15));
        
        // Cart items panel
        cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new BoxLayout(cartItemsPanel, BoxLayout.Y_AXIS));
        cartItemsPanel.setOpaque(false);
        cartItemsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cartItemsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        cartSection.add(cartItemsPanel);
        
        mainPanel.add(cartSection);

        // SCROLL PANE for the entire form
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        add(scrollPane, BorderLayout.CENTER);

        // === BUTTONS SECTION ===
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Use BoxLayout for better control
        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setLayout(new BoxLayout(buttonsContainer, BoxLayout.X_AXIS));
        buttonsContainer.setOpaque(false);
        
        // Add space on left
        buttonsContainer.add(Box.createHorizontalGlue());

        // CANCEL BUTTON - Fixed size
        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 16));
        cancelBtn.setBackground(new Color(240, 240, 240));
        cancelBtn.setForeground(new Color(80, 80, 80));
        cancelBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelBtn.setPreferredSize(new Dimension(120, 45));
        cancelBtn.setMinimumSize(new Dimension(120, 45));
        cancelBtn.setMaximumSize(new Dimension(120, 45));

        // Add hover effect for Cancel button
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelBtn.setBackground(new Color(230, 230, 230));
                cancelBtn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                    BorderFactory.createEmptyBorder(10, 25, 10, 25)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelBtn.setBackground(new Color(240, 240, 240));
                cancelBtn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 25, 10, 25)
                ));
            }
        });

        cancelBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(CheckoutPanel.this);
            if (window != null) {
                window.dispose();
            }
        });

        buttonsContainer.add(cancelBtn);
        buttonsContainer.add(Box.createHorizontalStrut(15));

        // SUBMIT BUTTON - Fixed size
        submitBtn = new JButton("Confirm Order");
        submitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        submitBtn.setBackground(new Color(110, 60, 16));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitBtn.setPreferredSize(new Dimension(200, 45));
        submitBtn.setMinimumSize(new Dimension(200, 45));
        submitBtn.setMaximumSize(new Dimension(200, 45));

        // Add hover effect for Submit button
        submitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitBtn.setBackground(new Color(130, 80, 36));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitBtn.setBackground(new Color(110, 60, 16));
            }
        });

        submitBtn.addActionListener(e -> submitOrder());

        buttonsContainer.add(submitBtn);
        
        // Add space on right
        buttonsContainer.add(Box.createHorizontalGlue());

        // Add container to button panel
        btnPanel.add(buttonsContainer);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void submitOrder() {
    String firstName = firstNameField.getText().trim();
    String lastName = lastNameField.getText().trim();
    String email = emailField.getText().trim();
    String phone = phoneField.getText().trim();
    String address = addressArea.getText().trim();
    String city = cityField.getText().trim();
    String postal = postalField.getText().trim();

    // 1️⃣ Validate inputs
    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
        phone.isEmpty() || address.isEmpty() || city.isEmpty() || postal.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Please fill all fields!",
                "Missing Information",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 2️⃣ Validate cart
    if (cartService.getCartItems().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Your cart is empty!",
                "Empty Cart",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 3️⃣ Save current cart items in a separate list BEFORE clearing
    List<Book> itemsToSave = new ArrayList<>(cartService.getCartItems());
    double totalAmount = cartService.getTotalPrice();

    // 4️⃣ Create Order object with cart items
    Order order = new Order(
        firstName, lastName, email, phone,
        address, city, postal, totalAmount,
        itemsToSave
    );

    // 5️⃣ Save order to database
    OrderService.getInstance().saveOrder(order);

    // 6️⃣ Clear the cart
    cartService.clearCart();

    // 7️⃣ Update UI
    updateCartDisplay();

    // 8️⃣ Show success message with all order details
    JOptionPane.showMessageDialog(this,
        order.toString(),
        "Order Saved Successfully",
        JOptionPane.INFORMATION_MESSAGE);

    // 9️⃣ Close the checkout dialog
    SwingUtilities.getWindowAncestor(this).dispose();
}

    

    // Method to refresh cart display if needed
    public void refreshCart() {
        updateCartDisplay();
    }
}