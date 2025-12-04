package controller;

import model.Book;
import service.CartService;
import view.frames.MainFrame;
import view.panels.CartPanel;

public class CartController {

    private static CartController instance;
    private MainFrame frame;
    private CartPanel panel;

    private CartController() {
    }

    public static CartController getInstance() {
        if (instance == null) instance = new CartController();
        return instance;
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    public void setCartPanel(CartPanel panel) {
        this.panel = panel;
    }

    public void addBook(Book b) {
        CartService.getInstance().addBook(b);
        updateCartIcon();
        if (panel != null) panel.refreshCart();
    }

    public void removeBook(Book b) {
        CartService.getInstance().removeOne(b);
        updateCartIcon();
        if (panel != null) panel.refreshCart();
    }

    // remove all occurrences of the given book (by title match)
    public void removeAll(Book b) {
        CartService.getInstance().removeAll(b);
        updateCartIcon();
        if (panel != null) panel.refreshCart();
    }

    public void updateCartIcon() {
        int count = CartService.getInstance().getCartItems().size();
        if (frame != null) frame.getNavigationBar().updateCartCount(count);
    }
public void addBook(Book b) {
    System.out.println("CartController.addBook() called for: " + b.getTitle());
    CartService.getInstance().addBook(b);
    System.out.println("CartService now has " + CartService.getInstance().getCartItems().size() + " items");
    updateCartIcon();
    if (panel != null) {
        System.out.println("Refreshing cart panel...");
        panel.refreshCart();
    } else {
        System.out.println("Cart panel is null!");
    }
}

}