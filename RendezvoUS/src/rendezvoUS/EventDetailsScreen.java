import javax.swing.*;
import java.awt.*;

public class EventDetailsScreen {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Event Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Set layout manager
        frame.setLayout(new BorderLayout());

        // Create UI components for the top panel (logo and navigation buttons)
        JPanel topPanel = new JPanel();
        JLabel logo = new JLabel("LOGO");
        JButton dashboardButton = new JButton("Dashboard");
        JButton groupViewButton = new JButton("Group View");
        JButton settingsButton = new JButton("Settings");
        
        topPanel.add(logo);
        topPanel.add(dashboardButton);
        topPanel.add(groupViewButton);
        topPanel.add(settingsButton);

        // Display Event Details panel
        JPanel eventDetailsPanel = new JPanel(new GridLayout(5, 1));
        JLabel eventName = new JLabel("Name of Event");
        JLabel organizer = new JLabel("Organizer");
        JLabel dateTime = new JLabel("Date / Time");
        JTextArea notes = new JTextArea("Notes");
        JButton joinLeaveButton = new JButton("Join / Leave Event");

        eventDetailsPanel.add(eventName);
        eventDetailsPanel.add(organizer);
        eventDetailsPanel.add(dateTime);
        eventDetailsPanel.add(notes);
        eventDetailsPanel.add(joinLeaveButton);

        // Add components to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(eventDetailsPanel, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }
}
