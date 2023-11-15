package rendezvous;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CalendarPanel calendarPanel;
    private SettingsScreen settingsPanel;
    private GroupSettings groupPanel;

    public MainScreen() {
        setTitle("RendezvoUS");
        setSize(1024, 768); // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on screen

        // Main panel with CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Initialize panels
        calendarPanel = new CalendarPanel();
        settingsPanel = new SettingsScreen(this); // Pass this MainScreen instance to SettingsScreen
        groupPanel = new GroupSettings(this);

        // Add panels to CardLayout
        mainPanel.add(calendarPanel, "Calendar");
        mainPanel.add(settingsPanel, "Settings");
        mainPanel.add(groupPanel, "Group");


        // Add navigation panel
        add(setupNavigationPanel(), BorderLayout.NORTH);

        // Add main panel
        add(mainPanel, BorderLayout.CENTER);

        // Apply default theme (this can be set based on user preferences)
        ThemeManager.changeTheme(this, ThemeManager.Theme.LIGHT); // Or DARK

        setVisible(true);
    }

    private JPanel setupNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton calendarButton = new JButton("Calendar");
        calendarButton.addActionListener(e -> cardLayout.show(mainPanel, "Calendar"));
        navPanel.add(calendarButton);

        JButton groupButton = new JButton("Group");
        groupButton.addActionListener(e -> cardLayout.show(mainPanel, "Group"));
        navPanel.add(groupButton);

        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> cardLayout.show(mainPanel, "Settings"));
        navPanel.add(settingsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> performLogout());
        navPanel.add(logoutButton);

        return navPanel;
    }

    // Method to change the theme
    public void changeTheme(ThemeManager.Theme theme) {
        ThemeManager.changeTheme(this, theme);
        updateComponentsPostThemeChange();
    }

    public void changeRecurrence(RecurrenceManager.Recurrence theme) {
    }

    // Refresh UI components after theme change
    public void updateComponentsPostThemeChange() {
        mainPanel.revalidate();
        mainPanel.repaint();
        mainPanel.setOpaque(false);
    }

    private void performLogout() {
        // Logout logic
        setVisible(false);
        // Assuming LoginScreen is your login window
        new LoginScreen().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreen());
    }
}
