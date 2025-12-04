package view.panels;
import java.awt.*;
import javax.swing.*;

public class LoginPanel extends JPanel {
    public LoginPanel() {
        setPreferredSize(new Dimension(800, 750));
        setBackground(Color.YELLOW);
        add(new JLabel("Login Panel"));
    }
}

