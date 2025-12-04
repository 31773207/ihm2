package view.panels;

import controller.CartController;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import javax.swing.*;
import model.Book;
import service.CartService;
import view.components.CartItemCard;
import view.dialogs.CheckoutFormDialog;

public class CartPanel extends JPanel {

    private final CartController controller;
    private JPanel itemsPanel;
    private JLabel totalLabel;
    private JButton checkoutBtn;

    public CartPanel(CartController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initUI();
    }

    private void initUI() {
        // Main items panel with proper spacing
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(Color.WHITE);
        itemsPanel.setAlignmentX(LEFT_ALIGNMENT);
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Wrap items panel in a container with padding
        JPanel itemsContainer = new JPanel(new BorderLayout());
        itemsContainer.setBackground(Color.WHITE);
        itemsContainer.add(itemsPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(itemsContainer);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(Color.WHITE);

        add(scroll, BorderLayout.CENTER);

        // Bottom Bar
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        bottomRight.setBackground(Color.WHITE);

        totalLabel = new JLabel("Total: 0.00 DZD");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));

        checkoutBtn = new JButton("Checkout");
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutBtn.setBackground(new Color(110, 60, 16));
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        checkoutBtn.setFocusPainted(false);
        checkoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        checkoutBtn.addActionListener(e -> handleCheckout());

        bottomRight.add(totalLabel);
        bottomRight.add(checkoutBtn);

        bottom.add(bottomRight, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);
    }

    private void handleCheckout() {
        if (CartService.getInstance().getCartItems().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Cart is empty!",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Open checkout form
Window parentWindow = SwingUtilities.getWindowAncestor(this);
CheckoutFormDialog dialog = new CheckoutFormDialog(parentWindow);
dialog.setVisible(true);


        // After dialog closes, refresh cart and update icon
        refreshCart();
        controller.updateCartIcon();
    }

    public void refreshCart() {
        itemsPanel.removeAll();

        List<Book> list = CartService.getInstance().getCartItems();
        LinkedHashMap<String, Integer> qtyMap = new LinkedHashMap<>();
        LinkedHashMap<String, Book> bookMap = new LinkedHashMap<>();
        for (Book b : list) {
            String key = b.getTitle();
            qtyMap.put(key, qtyMap.getOrDefault(key, 0) + 1);
            if (!bookMap.containsKey(key)) bookMap.put(key, b);
        }

        if (bookMap.isEmpty()) {
            JLabel emptyLabel = new JLabel("<html><div style='text-align:center;color:#999;font-size:16px;padding:40px;'>"
                    + "Your cart is empty<br/>Add some books to get started!</div></html>");
            emptyLabel.setAlignmentX(CENTER_ALIGNMENT);
            itemsPanel.add(emptyLabel);
        } else {
            for (String key : bookMap.keySet()) {
                Book b = bookMap.get(key);
                CartItemCard card = new CartItemCard(b, controller);
                card.setAlignmentX(LEFT_ALIGNMENT);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
                card.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                itemsPanel.add(card);
                itemsPanel.add(Box.createVerticalStrut(8));
            }
        }

        double total = CartService.getInstance().getTotalPrice();
        totalLabel.setText(String.format("Total: %.2fDZD", total));

        checkoutBtn.setEnabled(total > 0);

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
}
