package rendezvous;
import javax.swing.*;
import java.awt.*;

public class DashboardScreen {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(new JLabel("LOGO"));
        headerPanel.add(new JLabel("Title of Screen"));
        JButton dashboardButton = new JButton("Dashboard");
        JButton groupViewButton = new JButton("Group View");
        JButton settingsButton = new JButton("Settings");
        headerPanel.add(dashboardButton);
        headerPanel.add(groupViewButton);
        headerPanel.add(settingsButton);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Calendar Panel
        JPanel calendarPanel = new JPanel(new GridLayout(5, 7)); // Assuming a 5x7 grid for the month
        // Add days (this can be improved with actual calendar logic)
        for (int i = 0; i < 35; i++) {
            calendarPanel.add(new JButton(Integer.toString(i + 1))); // Temporary day number buttons
        }

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(calendarPanel, BorderLayout.CENTER);
        JPanel monthControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        monthControlPanel.add(new JButton("<")); // Previous month button
        monthControlPanel.add(new JLabel("MONTH"));
        monthControlPanel.add(new JButton(">")); // Next month button
        rightPanel.add(monthControlPanel, BorderLayout.NORTH);

        frame.add(rightPanel, BorderLayout.CENTER);

        // Events Panel
        JPanel eventsPanel = new JPanel(new BorderLayout());
        eventsPanel.add(new JLabel("Click day to create new event or click event to edit event."), BorderLayout.NORTH);
        JList<String> upcomingEvents = new JList<>(new String[] {"Event 1", "Event 2", "Event 3"}); // Sample events
        eventsPanel.add(new JScrollPane(upcomingEvents), BorderLayout.CENTER);

        frame.add(eventsPanel, BorderLayout.WEST);

        frame.setVisible(true);
    }
}
