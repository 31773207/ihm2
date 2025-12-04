package model;

public class Author {
    private String name;
    private String genre;        // Add this field
    private String description;  // Add this field
    private String tags;         // Add this field
    
    // Constructor
    public Author(String name, String genre, String description, String tags) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.tags = tags;
    }
    
    // Existing getters
    public String getName() {
        return name;
    }
    
    // New getters you need to add:
    public String getGenre() {
        return genre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getTags() {
        return tags;
    }
    
    // Setters (optional but recommended)
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
}
