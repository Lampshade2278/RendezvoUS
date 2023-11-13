package rendezvous;
import javax.swing.*;
import java.awt.*;

public class DashboardScreen extends JPanel {

    public DashboardScreen() {
        this.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(new JLabel("LOGO"));
        headerPanel.add(new JLabel("Dashboard"));

        // Main content of the Dashboard
        JPanel mainPanel = new JPanel();
        mainPanel.add(new JLabel("Dashboard Content Here"));

        // Adding components to this JPanel
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }
}
