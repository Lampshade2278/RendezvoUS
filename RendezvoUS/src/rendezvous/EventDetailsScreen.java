package rendezvous;

import javax.swing.*;
import java.awt.*;
//todo Class not used
public class EventDetailsScreen extends JPanel {

    public EventDetailsScreen() {
        // Setup the panel here
        this.setLayout(new BorderLayout());

        // Create UI components for the top panel (logo and navigation buttons)
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(new JLabel("LOGO"));
        topPanel.add(new JLabel("Event Details"));

        // Main content of the Event Details Screen
        JPanel contentPanel = new JPanel();
        // Add components to the content panel
        contentPanel.add(new JLabel("Event Information"));

        // Add topPanel and contentPanel to this JPanel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }
}
