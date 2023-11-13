package rendezvous;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CalendarPanel calendarPanel; // Calendar panel
    private JPanel dashboardPanel; // Dashboard panel

    public MainScreen() {
        setTitle("Main Application Window");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

//        setLocationRelativeTo(null); // Center the window
//        setVisible(true);

        // Initialize panels
        calendarPanel = new CalendarPanel();
        dashboardPanel = new JPanel(); // Placeholder for dashboard panel
        // Initialize other panels here

        // Main panel with CardLayout for switching screens
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Add panels to the main panel
        mainPanel.add(calendarPanel, "Calendar");
        mainPanel.add(dashboardPanel, "Dashboard");
        // Add other panels here

        // Top Navigation Panel with buttons
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Horizontal layout

        // Calendar button
        JButton calendarButton = new JButton("Open Calendar");
        calendarButton.addActionListener(e -> cardLayout.show(mainPanel, "Calendar"));
        navPanel.add(calendarButton);

        // Dashboard button
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));
        navPanel.add(dashboardButton);

        // Settings button (placeholder)
        JButton settingsButton = new JButton("Settings");
        // Add action listener if needed
        navPanel.add(settingsButton);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        navPanel.add(logoutButton);

        // Add panels to the frame
        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void performLogout() {
        // Logic for logout action
        // For example, hide this window and show the login screen
        this.setVisible(false);
        new LoginScreen(); // Assuming LoginScreen sets itself visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        });
    }
}
