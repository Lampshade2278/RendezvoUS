package rendezvous;

import javax.swing.*;
import java.awt.*;

// MainScreen class extends JFrame to create a window for the application
public class MainScreen extends JFrame {
    // CardLayout allows switching between different panels
    private CardLayout cardLayout;
    // Main panel to hold different screens like Calendar and Dashboard
    private JPanel mainPanel;
    // CalendarPanel instance for the calendar functionality
    private CalendarPanel calendarPanel;
    // Placeholder for dashboard, can be replaced with an actual dashboard panel
    private JPanel dashboardPanel;

    // Constructor of MainScreen
    public MainScreen() {
        setTitle("RendezvoUS"); // Set the title of the window
        setSize(1024, 768); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set default close operation
        initializeUI(); // Initialize the user interface
        setLocationRelativeTo(null); // Center the window on the screen
    }

    // Initialize the user interface
    private void initializeUI() {
        // Initialize the calendar panel
        calendarPanel = new CalendarPanel();
        // Initialize the dashboard panel (currently a placeholder)
        dashboardPanel = new JPanel();

        // Set up the main panel with CardLayout for screen switching
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        // Add the calendar and dashboard panels to the main panel
        mainPanel.add(calendarPanel, "Calendar");
        mainPanel.add(dashboardPanel, "Dashboard");

        // Set up the navigation panel with buttons
        JPanel navPanel = setupNavigationPanel();

        // Add the navigation and main panels to the JFrame
        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    // Set up the navigation panel with buttons and their action listeners
    private JPanel setupNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Dashboard button to switch to the dashboard screen
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));
        navPanel.add(dashboardButton);

        // Calendar button to switch to the calendar screen
        JButton calendarButton = new JButton("Open Calendar");
        calendarButton.addActionListener(e -> cardLayout.show(mainPanel, "Calendar"));
        navPanel.add(calendarButton);

        // Settings button (functionality can be added later)
        JButton settingsButton = new JButton("Settings");
        // TODO: Add action listener for settings button
        navPanel.add(settingsButton);

        // Logout button to exit the application and return to the login screen
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        navPanel.add(logoutButton);

        return navPanel;
    }

    // Perform logout operation
    private void performLogout() {
        setVisible(false); // Hide the MainScreen
        new LoginScreen(); // Open the LoginScreen
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Ensure the UI is created on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true); // Make the MainScreen visible
        });
    }
}
