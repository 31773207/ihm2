package model;

import java.util.ArrayList;
import java.util.List;

public class AuthorStore {
    public static List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        
        // Add authors with full data
        authors.add(new Author(
            "Jane Austen",
            "Classic Literature",
            "Master of wit and social commentary, known for Pride and Prejudice and Emma.",
            "Romance,Drama"
        ));
        
        authors.add(new Author(
            "Alex Michaelides",
            "Psychological Thriller",
            "Contemporary master of psychological suspense and mind-bending mysteries.",
            "Thriller,Mystery"
        ));
        
        authors.add(new Author(
            "Taylor Jenkins Reid",
            "Contemporary Fiction",
            "Bestselling author known for emotionally rich stories and complex characters.",
            "Romance,Drama"
        ));
        
        authors.add(new Author(
            "Andy Weir",
            "Science Fiction",
            "Engineer turned novelist, famous for The Martian and Project Hall Mary.",
            "Sci-Fi,Adventure"
        ));
        
        return authors;
    }
}

