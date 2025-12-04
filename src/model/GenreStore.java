package model;

import java.util.ArrayList;
import java.util.List;

public class GenreStore {

    public static List<Genre> getAllGenres() {
        List<Genre> list = new ArrayList<>();
        list.add(new Genre("Fantasy", "/images/fantasy.png"));
        list.add(new Genre("Romance", "/images/romance.png"));
        list.add(new Genre("Horror", "/images/horror.png"));
        list.add(new Genre("Sci-Fi", "/images/scifi.png"));
        list.add(new Genre("Mystery", "/images/mystery.png"));
        return list;
    }
}

