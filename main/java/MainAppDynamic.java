import controllers.BubbleController;
import controllers.DynamicController;

import javax.swing.*;
import java.awt.*;

public class MainAppDynamic {
    private JFrame frame;
    public MainAppDynamic() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.frame.setLayout(new BorderLayout());
        DynamicController con = new DynamicController();
        this.frame.add(con.getView(), BorderLayout.CENTER);

        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }
}
