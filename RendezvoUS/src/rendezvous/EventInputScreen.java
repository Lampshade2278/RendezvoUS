package rendezvous;

import javax.swing.*;
import java.awt.*;

public class EventInputScreen extends JPanel {

    public EventInputScreen() {
        // Set layout manager
        this.setLayout(new BorderLayout());

        // Create UI components
        JPanel topPanel = new JPanel();
        JLabel logo = new JLabel("LOGO");
        JLabel title = new JLabel("Title of Screen");
        JButton dashboardButton = new JButton("Dashboard");
        JButton groupViewButton = new JButton("Group View");
        JButton settingsButton = new JButton("Settings");

        topPanel.add(logo);
        topPanel.add(title);
        topPanel.add(dashboardButton);
        topPanel.add(groupViewButton);
        topPanel.add(settingsButton);

        // Event details input panel
        JPanel eventInputPanel = new JPanel(new GridLayout(5, 2));
        // Add components for event input (labels, text fields, etc.)
        // Example: eventInputPanel.add(new JLabel("Event Name"));
        // Example: eventInputPanel.add(new JTextField(20));

        // Adding panels to this JPanel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(eventInputPanel, BorderLayout.CENTER);

        // Add more components or panels as needed for the event input screen
    }
}
