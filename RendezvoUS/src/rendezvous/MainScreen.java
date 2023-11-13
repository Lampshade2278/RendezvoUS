package rendezvous;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CalendarPanel calendarPanel;
    private JPanel dashboardPanel;

    public MainScreen() {
        setTitle("RendezvoUS");
        setSize(1024, 768); // Adjust the size as needed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        calendarPanel = new CalendarPanel();
        dashboardPanel = new JPanel();

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(calendarPanel, "Calendar");
        mainPanel.add(dashboardPanel, "Dashboard");

        JPanel navPanel = setupNavigationPanel();

        add(navPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel setupNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));
        navPanel.add(dashboardButton);

        JButton calendarButton = new JButton("Open Calendar");
        calendarButton.addActionListener(e -> cardLayout.show(mainPanel, "Calendar"));
        navPanel.add(calendarButton);

        JButton settingsButton = new JButton("Settings");
        // Add action listener if needed
        navPanel.add(settingsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        navPanel.add(logoutButton);

        return navPanel;
    }

    private void performLogout() {
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
