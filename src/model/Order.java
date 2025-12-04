package model;

import java.util.List;

public class Order {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String postal;
    private double totalAmount;
    private List<Book> cartItems;

    public Order(String firstName, String lastName, String email, String phone,
                 String address, String city, String postal, double totalAmount,
                 List<Book> cartItems) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.postal = postal;
        this.totalAmount = totalAmount;
        this.cartItems = cartItems;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getPostal() { return postal; }
    public double getTotalAmount() { return totalAmount; }
    public List<Book> getCartItems() { return cartItems; }

   @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Name: ").append(firstName).append(" ").append(lastName).append("\n");
    sb.append("Email: ").append(email).append("\n");
    sb.append("Phone: ").append(phone).append("\n");
    sb.append("Address: ").append(address).append(", ").append(city).append(", ").append(postal).append("\n");
    sb.append("Total Amount: ").append(String.format("%.2f DZD", totalAmount)).append("\n");
    sb.append("Cart Items:\n");
    for (Book b : cartItems) {
        sb.append(" - ").append(b.getTitle()).append(" (").append(b.getPrice()).append(" DZD)\n");
    }
    sb.append("----------------------------------------------------------\n");
    return sb.toString();
}


  

}




