package view.dialogs;

import java.awt.*;
import javax.swing.*;
import view.panels.CheckoutPanel;

public class CheckoutFormDialog extends JDialog {

    public CheckoutFormDialog(Window parent) {
        super(parent, "Checkout", ModalityType.APPLICATION_MODAL);

        // WIDTH = 450 pixels, HEIGHT = 520 pixels
        setSize(650, 820); // This sets width=550, height=720
        
        setLayout(new BorderLayout());

        // allow close by X button
        //setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Rounded card background
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // TITLE
        JLabel title = new JLabel("Checkout", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(110, 60, 16));
        card.add(title, BorderLayout.NORTH);

        // FORM
        CheckoutPanel form = new CheckoutPanel();
        card.add(form, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);

        // This centers the dialog on the screen
        setLocationRelativeTo(null);
    }
}


