package service;

import javax.swing.*;

public class SearchController {

    private JTextField field;
    private JButton button;
    private JPanel resultsPanel;

    public SearchController(JTextField field, JButton button, JPanel resultsPanel) {
        this.field = field;
        this.button = button;
        this.resultsPanel = resultsPanel;

        button.addActionListener(e -> search());
        field.addActionListener(e -> search());
    }

    private void search() {
        String text = field.getText().trim();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search query.");
            return;
        }

        // TEST RESULT
        resultsPanel.removeAll();
        resultsPanel.add(new JLabel("Résultats pour : " + text));
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}