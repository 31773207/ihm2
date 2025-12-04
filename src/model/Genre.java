//Book categories (Romance, Mystery, Sci-Fi)
package model;

public class Genre {
    private String name;
    private String imagePath;

    public Genre(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}


