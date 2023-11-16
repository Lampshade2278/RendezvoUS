package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsScreen extends JPanel {
    private MainScreen mainScreen;
    private UserAccount userAccount;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private JButton changePasswordButton;

    public SettingsScreen(MainScreen mainScreen, UserAccount userAccount) {
        this.mainScreen = mainScreen;
        this.userAccount = userAccount;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createAppearanceSection());
        add(createPasswordSection());
        add(createNotificationSection());
        add(createFeedbackSection());
        add(createMiscellaneousSection());
        add(createAccountManagementSection());
    }

    private JPanel createAppearanceSection() {
        JPanel appearancePanel = new JPanel();
        appearancePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        appearancePanel.setBorder(BorderFactory.createTitledBorder("Appearance Settings"));

        JComboBox<ThemeManager.Theme> themeComboBox = new JComboBox<>(ThemeManager.Theme.values());
        themeComboBox.addActionListener(e -> {
            ThemeManager.Theme selectedTheme = (ThemeManager.Theme) themeComboBox.getSelectedItem();
            mainScreen.changeTheme(selectedTheme);
            userAccount.getSettings().setTheme(selectedTheme.name());
            UserDataManager.saveUserAccount(userAccount);
        });
        appearancePanel.add(new JLabel("Select Theme:"));
        appearancePanel.add(themeComboBox);

        return appearancePanel;
    }

    private JPanel createPasswordSection() {
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(4, 2, 5, 5));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Password Management"));

        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmNewPasswordField = new JPasswordField(20);
        changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> changePassword());

        passwordPanel.add(new JLabel("Current Password:"));
        passwordPanel.add(currentPasswordField);
        passwordPanel.add(new JLabel("New Password:"));
        passwordPanel.add(newPasswordField);
        passwordPanel.add(new JLabel("Confirm New Password:"));
        passwordPanel.add(confirmNewPasswordField);
        passwordPanel.add(new JLabel("")); // Placeholder for layout
        passwordPanel.add(changePasswordButton);

        return passwordPanel;
    }

    private JPanel createNotificationSection() {
        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        notificationPanel.setBorder(BorderFactory.createTitledBorder("Notification Settings"));

        JCheckBox emailNotifications = new JCheckBox("Email Notifications");
        emailNotifications.setEnabled(false);
        notificationPanel.add(emailNotifications);

        JCheckBox pushNotifications = new JCheckBox("Push Notifications");
        pushNotifications.setEnabled(false);
        notificationPanel.add(pushNotifications);

        JCheckBox eventReminders = new JCheckBox("Event Reminders");
        eventReminders.setEnabled(false);
        notificationPanel.add(eventReminders);

        // Additional greyed out notification options
        JCheckBox dailySummary = new JCheckBox("Daily Summary");
        dailySummary.setEnabled(false);
        notificationPanel.add(dailySummary);

        JCheckBox weeklyDigest = new JCheckBox("Weekly Digest");
        weeklyDigest.setEnabled(false);
        notificationPanel.add(weeklyDigest);

        return notificationPanel;
    }

    private JPanel createFeedbackSection() {
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        feedbackPanel.setBorder(BorderFactory.createTitledBorder("Feedback"));

        JButton sendFeedbackButton = new JButton("Send Feedback");
        sendFeedbackButton.setEnabled(false); // Currently not implemented
        feedbackPanel.add(sendFeedbackButton);

        return feedbackPanel;
    }

    private JPanel createMiscellaneousSection() {
        JPanel miscPanel = new JPanel();
        miscPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        miscPanel.setBorder(BorderFactory.createTitledBorder("Miscellaneous"));

        JButton exportDataButton = new JButton("Export Data");
        exportDataButton.setEnabled(false); // Placeholder for future implementation
        miscPanel.add(exportDataButton);

        JButton importDataButton = new JButton("Import Data");
        importDataButton.setEnabled(false); // Placeholder for future implementation
        miscPanel.add(importDataButton);

        JButton syncCalendarButton = new JButton("Sync Calendar");
        syncCalendarButton.setEnabled(false); // Placeholder for future implementation
        miscPanel.add(syncCalendarButton);

        return miscPanel;
    }

    private JPanel createAccountManagementSection() {
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        accountPanel.setBorder(BorderFactory.createTitledBorder("Account Management"));

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(e -> deleteAccount());
        accountPanel.add(deleteAccountButton);

        return accountPanel;
    }

    private void changePassword() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        if (!currentPassword.equals(userAccount.getPassword())) {
            JOptionPane.showMessageDialog(this, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add your password strength or other checks here if needed

        userAccount.setPassword(newPassword);
        UserDataManager.saveUserAccount(userAccount);
        JOptionPane.showMessageDialog(this, "Password changed successfully.");
    }

    private void deleteAccount() {
        String username = getCurrentUser();
        File accountFile = new File(username + ".dat");
        File eventsFile = new File(username + "_events.dat");

        if (accountFile.delete() && eventsFile.delete()) {
            JOptionPane.showMessageDialog(this, "Account deleted successfully.");
            mainScreen.performLogout();
        } else {
            JOptionPane.showMessageDialog(this, "Error deleting account.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getCurrentUser() {
        return userAccount.getUsername();
    }
}
