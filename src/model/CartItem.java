//User's shopping cart with items

package model;

public class CartItem {
    private String title;
    private double price;
    private int quantity;
    
    public CartItem(String title, double price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getTitle() {
        return title;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}