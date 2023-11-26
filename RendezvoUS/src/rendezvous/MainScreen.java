package rendezvous;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private CalendarPanel calendarPanel;
    private SettingsScreen settingsPanel;
    private GroupViewScreen groupPanel;
    private UserAccount userAccount;

    // MainScreen constructor
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
        settingsPanel = new SettingsScreen(this, userAccount);
        groupPanel = new GroupViewScreen(userAccount);

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

    public void performLogout() {
        setVisible(false);
        new LoginScreen().setVisible(true);
    }

    public void changeTheme(ThemeManager.Theme theme) {
        ThemeManager.changeTheme(this, theme);
        updateComponentsPostThemeChange();
    }

    private void updateComponentsPostThemeChange() {
        mainPanel.revalidate();
        mainPanel.repaint();
        mainPanel.setOpaque(false);
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    //todo remove before finishing
    public static void main(String[] args) {
        // For testing purposes, create a dummy user account
        UserAccount testAccount = new UserAccount("testUser", "testPass");
        SwingUtilities.invokeLater(() -> new MainScreen(testAccount));
    }

    //todo implement event recurrence
    public void changeRecurrence(RecurrenceManager.Recurrence theme) {
    }
}