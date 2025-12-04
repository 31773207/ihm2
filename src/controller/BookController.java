//Manages book-related actions (search, filter)
package controller;

import java.util.List;
import model.Book;
import model.BookStore;
import view.panels.CatalogPanel;

public class BookController {

    private CatalogPanel view;

    public BookController(CatalogPanel view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // When genre dropdown changes → filter books
        view.getGenreBox().addActionListener(e -> applyFilter());

        // Show all books first time
        applyFilter();
    }

    private void applyFilter() {
        String selectedGenre = view.getGenreBox().getSelectedItem().toString();
        List<Book> books = BookStore.getAllBooks();

        if (!selectedGenre.equals("All")) {
            books = books.stream()
                         .filter(b -> b.getGenre().equalsIgnoreCase(selectedGenre))
                         .toList();
        }

        view.displayBooks(books); // tell view to redraw
    }
}
