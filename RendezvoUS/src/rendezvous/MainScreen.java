package rendezvous;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CalendarPanel calendarPanel;
    private SettingsScreen settingsPanel;
    private GroupSettings groupPanel;
    private UserAccount userAccount;

    public MainScreen(UserAccount userAccount) {
        this.userAccount = userAccount;

        setTitle("RendezvoUS");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        calendarPanel = new CalendarPanel(userAccount);
        settingsPanel = new SettingsScreen(this);
        groupPanel = new GroupSettings(this);

        mainPanel.add(calendarPanel, "Calendar");
        mainPanel.add(settingsPanel, "Settings");
        mainPanel.add(groupPanel, "Group");

        add(setupNavigationPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        ThemeManager.changeTheme(this, ThemeManager.Theme.LIGHT);
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

    private void performLogout() {
        setVisible(false);
        new LoginScreen().setVisible(true);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreen(new UserAccount("test", "password"))); // For testing
    }
}