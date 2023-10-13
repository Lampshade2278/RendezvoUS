import javax.swing.*;
import java.awt.*;

public class EventInputScreen {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Add/Edit Event");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Set layout manager
        frame.setLayout(new BorderLayout());

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
        JLabel eventNameLabel = new JLabel("Event Name:");
        JTextField eventNameField = new JTextField();
        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField();
        JLabel timeLabel = new JLabel("Time:");
        JTextField timeField = new JTextField();
        JLabel invitationsLabel = new JLabel("Invitations to join/sign up:");
        JTextField invitationsField = new JTextField();
        JLabel notesLabel = new JLabel("Notes:");
        JTextArea notesArea = new JTextArea();

        eventInputPanel.add(eventNameLabel);
        eventInputPanel.add(eventNameField);
        eventInputPanel.add(dateLabel);
        eventInputPanel.add(dateField);
        eventInputPanel.add(timeLabel);
        eventInputPanel.add(timeField);
        eventInputPanel.add(invitationsLabel);
        eventInputPanel.add(invitationsField);
        eventInputPanel.add(notesLabel);
        eventInputPanel.add(notesArea);

        // Add components to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(eventInputPanel, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }
}
