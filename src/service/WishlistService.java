package service;

import model.Book;
import java.util.LinkedHashSet;
import java.util.Set;

public class WishlistService {
    private static WishlistService instance;
    private final Set<Book> favorites;

    private WishlistService() { favorites = new LinkedHashSet<>(); }

    public static WishlistService getInstance() {
        if (instance == null) instance = new WishlistService();
        return instance;
    }

    public void add(Book b) { favorites.add(b); }
    public void remove(Book b) { favorites.remove(b); }
    public boolean contains(Book b) { return favorites.stream().anyMatch(x -> x.getTitle().equals(b.getTitle())); }
    public Set<Book> getFavorites() { return java.util.Collections.unmodifiableSet(favorites); }
    public int size() { return favorites.size(); }
}
