package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsScreen extends JPanel {
    private MainScreen mainScreen;

    public SettingsScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        setLayout(new BorderLayout());

        add(createAppearanceSection(), BorderLayout.NORTH);
        add(createAccountManagementSection(), BorderLayout.CENTER);
    }

    private JPanel createAppearanceSection() {
        JPanel appearancePanel = new JPanel(new FlowLayout());
        appearancePanel.add(new JLabel("Appearance:"));

        JComboBox<ThemeManager.Theme> themeComboBox = new JComboBox<>(ThemeManager.Theme.values());
        themeComboBox.addActionListener(e -> {
            ThemeManager.Theme selectedTheme = (ThemeManager.Theme) themeComboBox.getSelectedItem();
            mainScreen.changeTheme(selectedTheme);
        });
        appearancePanel.add(themeComboBox);

        return appearancePanel;
    }

    private JPanel createAccountManagementSection() {
        JPanel accountPanel = new JPanel(new FlowLayout());

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(e -> deleteAccount());
        accountPanel.add(deleteAccountButton);

        JButton sendFeedbackButton = new JButton("Send Feedback");
        sendFeedbackButton.setEnabled(false);
        accountPanel.add(sendFeedbackButton);

        return accountPanel;
    }

    private void deleteAccount() {
        String username = getCurrentUser(); // Ensure this returns the logged-in user's username
        File accountFile = new File(username + ".dat");
        File eventsFile = new File(username + "_events.dat");
        boolean accountDeleted = accountFile.delete();
        boolean eventsDeleted = eventsFile.delete();

        if (accountDeleted && eventsDeleted) {
            JOptionPane.showMessageDialog(this, "Account and associated events deleted successfully.");
            mainScreen.performLogout();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting account or events.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private String getCurrentUser() {
        return mainScreen.getUserAccount().getUsername();
    }
}
