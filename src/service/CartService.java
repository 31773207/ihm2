package service;

import model.Book;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    private static CartService instance;
    private final List<Book> cartItems;

    private CartService() {
        cartItems = new ArrayList<>();
    }

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public void addBook(Book book) {
        cartItems.add(book);
    }

    // remove a single occurrence (one unit) of the book
    public void removeOne(Book book) {
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getTitle().equals(book.getTitle())) {
                cartItems.remove(i);
                return;
            }
        }
    }

    // remove all occurrences of the book
    public void removeAll(Book book) {
        cartItems.removeIf(b -> b.getTitle().equals(book.getTitle()));
    }

    public int getQuantity(Book book) {
        return (int) cartItems.stream().filter(b -> b.getTitle().equals(book.getTitle())).count();
    }

    public List<Book> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }
    // In CartService.java
public void addBook(Book book) {
    System.out.println("CartService.addBook() - Adding: " + book.getTitle());
    cartItems.add(book);
    System.out.println("Total items in cart: " + cartItems.size());
}

public List<Book> getCartItems() {
    System.out.println("CartService.getCartItems() - Returning " + cartItems.size() + " items");
    return new ArrayList<>(cartItems);
}
}
