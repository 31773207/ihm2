package controller;

import model.Book;
import service.WishlistService;
import view.frames.MainFrame;

public class WishlistController {
    private static WishlistController instance;
    private MainFrame frame;

    private WishlistController() {}

    public static WishlistController getInstance() {
        if (instance == null) instance = new WishlistController();
        return instance;
    }

    public void setFrame(MainFrame f) { this.frame = f; }

    public void toggle(Book b) {
        if (WishlistService.getInstance().contains(b)) WishlistService.getInstance().remove(b);
        else WishlistService.getInstance().add(b);
        if (frame != null) frame.getNavigationBar().updateFavoriteCount(WishlistService.getInstance().size());
    }

    public void add(Book b) {
        WishlistService.getInstance().add(b);
        if (frame != null) frame.getNavigationBar().updateFavoriteCount(WishlistService.getInstance().size());
    }

    public void remove(Book b) {
        WishlistService.getInstance().remove(b);
        if (frame != null) frame.getNavigationBar().updateFavoriteCount(WishlistService.getInstance().size());
    }

    public java.util.Set<Book> getFavorites() { return WishlistService.getInstance().getFavorites(); }
}
