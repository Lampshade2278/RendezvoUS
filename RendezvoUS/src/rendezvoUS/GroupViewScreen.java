package rendezvous;
import javax.swing.*;
import java.awt.*;

public class GroupViewScreen {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Group View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
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

        // Main Panel with two Calendars
        JPanel calendarsPanel = new JPanel(new GridLayout(1, 2));
        
        JPanel yourCalendarPanel = new JPanel(new BorderLayout());
        yourCalendarPanel.add(new JLabel("Your Calendar", SwingConstants.CENTER), BorderLayout.NORTH);
        JPanel yourDaysPanel = new JPanel(new GridLayout(5, 7));
        for (int i = 0; i < 35; i++) {
            yourDaysPanel.add(new JButton(Integer.toString(i + 1)));
        }
        yourCalendarPanel.add(yourDaysPanel, BorderLayout.CENTER);
        calendarsPanel.add(yourCalendarPanel);

        JPanel theirCalendarPanel = new JPanel(new BorderLayout());
        theirCalendarPanel.add(new JLabel("Their Calendar", SwingConstants.CENTER), BorderLayout.NORTH);
        JPanel theirDaysPanel = new JPanel(new GridLayout(5, 7));
        for (int i = 0; i < 35; i++) {
            theirDaysPanel.add(new JButton(Integer.toString(i + 1)));
        }
        theirCalendarPanel.add(theirDaysPanel, BorderLayout.CENTER);
        calendarsPanel.add(theirCalendarPanel);

        frame.add(calendarsPanel, BorderLayout.CENTER);

        // Month Panel
        JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        monthPanel.add(new JButton("<"));
        monthPanel.add(new JLabel("MONTH", SwingConstants.CENTER));
        monthPanel.add(new JButton(">"));
        frame.add(monthPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
