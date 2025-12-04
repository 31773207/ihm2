package service;

import java.io.FileWriter;
import java.io.IOException;
import model.Order;

public class OrderService {

    private static OrderService instance;

    public static OrderService getInstance() {
        if (instance == null) instance = new OrderService();
        return instance;
    }

    private final String FILE_NAME = "orders.txt";

    public void saveOrder(Order order) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(order.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

