package model;

public class Book {
    private String title;
    private String author;
    private double price;
    private String description;
    private String imagePath; // <-- this
        private String genre;  // <-- add this


    public Book(String title, String author, double price, String description, String imagePath, String genre) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
                this.genre = genre;   // <-- set genre

    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; } // <-- return path
        public String getGenre() { return genre; }   // <-- add this

}


