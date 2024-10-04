import controllers.BubbleController;

import javax.swing.*;
import java.awt.*;

public class MainApp {
    private JFrame frame;
    public MainApp() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.setLayout(new BorderLayout());
        BubbleController con = new BubbleController();
        this.frame.add(con.getView(), BorderLayout.CENTER);

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }
}
