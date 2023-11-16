package rendezvous;
import javax.swing.*;
import java.awt.*;

public class GroupViewScreen extends JPanel {

    public GroupViewScreen(MainScreen mainScreen) {
        // Set the layout for this panel
        this.setLayout(new BorderLayout());

        //this.add(headerPanel, BorderLayout.NORTH);

        // Main Panel with two Calendars
        JPanel calendarsPanel = new JPanel(new GridLayout(1, 2));

        // First Calendar Panel
        JPanel yourCalendarPanel = new JPanel(new BorderLayout());
        yourCalendarPanel.add(new JLabel("Your Calendar"), BorderLayout.NORTH);
        // Add calendar component here
        calendarsPanel.add(yourCalendarPanel);

        // Second Calendar Panel
        JPanel groupCalendarPanel = new JPanel(new BorderLayout());
        groupCalendarPanel.add(new JLabel("Group Calendar"), BorderLayout.NORTH);
        // Add calendar component here
        calendarsPanel.add(groupCalendarPanel);

        // Add calendars panel to the main panel
        this.add(calendarsPanel, BorderLayout.CENTER);
    }
}
