package model;

import java.util.List;
import java.util.stream.Collectors;

public class FilterLogic {

    public static List<Book> filterBooks(List<Book> books,
                                         String title,
                                         String genre,
                                         double maxPrice) {

        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(b -> genre.equals("All") || b.getGenre().equalsIgnoreCase(genre))
                .filter(b -> b.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
