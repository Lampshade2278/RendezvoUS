package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.util.Date; // Import for Date class

public class MainScreen extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CalendarPanel calendarPanel; // Calendar panel
    private JPanel dashboardPanel; // Dashboard panel

    public MainScreen() {
        setTitle("RendezvoUS");
        setSize(800, 800); // Set the window size to 800x800 pixels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void initializeUI() {
        // Initialize panels
        calendarPanel = new CalendarPanel();
        dashboardPanel = new JPanel(); // Placeholder for dashboard panel

        // Main panel with CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(calendarPanel, "Calendar");
        mainPanel.add(dashboardPanel, "Dashboard");

        // Top Navigation Panel
        JPanel navPanel = setupNavigationPanel();

        // Add panels to the frame
        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel setupNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton calendarButton = new JButton("Open Calendar");
        calendarButton.addActionListener(e -> cardLayout.show(mainPanel, "Calendar"));
        navPanel.add(calendarButton);

        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));
        navPanel.add(dashboardButton);

        JButton settingsButton = new JButton("Settings");
        // Add action listener if needed
        navPanel.add(settingsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        navPanel.add(logoutButton);

        // Button to add events
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> openAddEventDialog());
        navPanel.add(addEventButton);

        return navPanel;
    }

    private void openAddEventDialog() {
        Date currentDate = new Date(); // Get the current date
        EventDialog eventDialog = new EventDialog(this, true, null, calendarPanel.getCalendarModel(), currentDate);
        eventDialog.setLocationRelativeTo(this);
        eventDialog.setVisible(true);

        calendarPanel.updateCalendar(); // Refresh the calendar
    }


    private void performLogout() {
        // Logic for logout action
        setVisible(false);
        new LoginScreen(); // Assuming LoginScreen sets itself visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        });
    }
}
