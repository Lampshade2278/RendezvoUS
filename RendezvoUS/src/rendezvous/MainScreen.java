package rendezvous;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private CalendarPanel calendarPanel;
    private final SettingsScreen settingsPanel;
    private final GroupViewScreen groupPanel;
    private final GroupSettings groupSettingsPanel;
    private final UserAccount userAccount;

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
        groupSettingsPanel = new GroupSettings(userAccount, groupPanel);

        mainPanel.add(calendarPanel, "Calendar");
        mainPanel.add(settingsPanel, "Settings");
        mainPanel.add(groupPanel, "Group");
        mainPanel.add(groupSettingsPanel, "Group Settings");

        add(setupNavigationPanel(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        ThemeManager.changeTheme(this, ThemeManager.Theme.LIGHT);
        setVisible(true);

    }

    private JPanel setupNavigationPanel() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton calendarButton = new JButton("Calendar");
        JButton groupButton = new JButton("Group");
        JButton settingsButton = new JButton("Settings");
        JButton groupSettingsButton = new JButton("Group Settings");
        JButton logoutButton = new JButton("Logout");

        calendarButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Calendar");
            calendarPanel.updateCalendar();
        });
        groupButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Group");
            groupPanel.updateCalendar();
        });
        settingsButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Settings");
        });
        groupSettingsButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Group Settings");
        });
        logoutButton.addActionListener(e -> performLogout());


        navPanel.add(calendarButton);
        navPanel.add(groupButton);
        navPanel.add(settingsButton);
        navPanel.add(groupSettingsButton);
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
    /*
    //for testing
    public static void main(String[] args) {
        // For testing purposes, create a dummy user account
        UserAccount testAccount = new UserAccount("testUser", "testPass");
        SwingUtilities.invokeLater(() -> new MainScreen(testAccount));
    }*/

    //todo implement event recurrence
    public void changeRecurrence(RecurrenceManager.Recurrence theme) {

    }
}