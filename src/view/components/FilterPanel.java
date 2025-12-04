package view.components;

import javax.swing.*;
import java.awt.*;

public class FilterPanel extends JPanel {

    public JTextField titleField;
    public JComboBox<String> genreBox;
    public JTextField priceField;
    public JButton filterBtn;

    public FilterPanel() {

        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        setBackground(new Color(218, 168, 131));

        titleField = new JTextField(15);
        priceField = new JTextField(10);

        String[] genres = {"All", "Romance", "Fantasy", "Sci-Fi", "Drama", "Horror"};
        genreBox = new JComboBox<>(genres);

        filterBtn = new JButton("Filter");

        add(new JLabel("Title:"));
        add(titleField);

        add(new JLabel("Genre:"));
        add(genreBox);

        add(new JLabel("Max Price:"));
        add(priceField);

        add(filterBtn);
    }
}
