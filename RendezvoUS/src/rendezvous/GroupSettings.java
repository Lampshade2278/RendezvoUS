package rendezvous;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GroupSettings extends JPanel {
    private MainScreen mainScreen;

    public GroupSettings(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        setLayout(new BorderLayout());

        add(createAppearanceSection(), BorderLayout.NORTH);
        add(createAccountManagementSection(), BorderLayout.CENTER);
    }

    private JPanel createAppearanceSection() {
        JPanel appearancePanel = new JPanel();
        appearancePanel.setLayout(new FlowLayout());
        appearancePanel.add(new JLabel("Appearance:"));

        JComboBox<ThemeManager.Theme> themeComboBox = new JComboBox<>(ThemeManager.Theme.values());
        themeComboBox.addActionListener(e -> {
            ThemeManager.Theme selectedTheme = (ThemeManager.Theme) themeComboBox.getSelectedItem();
            mainScreen.changeTheme(selectedTheme); // Call changeTheme on MainScreen
        });
        appearancePanel.add(themeComboBox);

        return appearancePanel;
    }

    private JPanel createAccountManagementSection() {
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new FlowLayout());

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(e -> deleteAccount());
        accountPanel.add(deleteAccountButton);

        JButton sendFeedbackButton = new JButton("Send Feedback");
        sendFeedbackButton.setEnabled(false); // Disabled as functionality not implemented yet
        accountPanel.add(sendFeedbackButton);

        return accountPanel;
    }

    private void deleteAccount() {
        // Placeholder for delete account functionality
        String username = getCurrentUser();
        File file = new File(username + ".dat");
        if (file.delete()) {
            JOptionPane.showMessageDialog(this, "Account deleted successfully.");
            System.exit(0); // Exit the application
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting account.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getCurrentUser() {
        // Placeholder method for getting current user's username
        return "current_user";
    }
}