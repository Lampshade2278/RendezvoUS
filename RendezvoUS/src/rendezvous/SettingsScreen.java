package rendezvous;

import javax.swing.*;
import java.awt.*;

public class SettingsScreen {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Set layout manager
        frame.setLayout(new BorderLayout());

        // Create UI components for the top panel (logo and navigation buttons)
        JPanel topPanel = new JPanel();
        JLabel logo = new JLabel("LOGO");
        JButton dashboardButton = new JButton("Dashboard");
        JButton groupViewButton = new JButton("Group View");
        JButton settingsButton = new JButton("Settings");
        
        topPanel.add(logo);
        topPanel.add(dashboardButton);
        topPanel.add(groupViewButton);
        topPanel.add(settingsButton);

        // Settings components
        JPanel settingsPanel = new JPanel(new GridLayout(4, 2));
        
        JLabel changeUsernameLabel = new JLabel("Change Username");
        JTextField newUsernameField = new JTextField("New username");
        JButton changeUsernameButton = new JButton("Go");

        JLabel changePasswordLabel = new JLabel("Change Password");
        JPasswordField newPasswordField = new JPasswordField("New Pass");
        JPasswordField confirmNewPasswordField = new JPasswordField("Confirm New Pass");
        JButton changePasswordButton = new JButton("Go");

        JLabel changeEmailLabel = new JLabel("Change Email");
        JTextField newEmailField = new JTextField("New email");
        JTextField confirmNewEmailField = new JTextField("Confirm New email");
        JButton changeEmailButton = new JButton("Go");

        JLabel colorBlindModeLabel = new JLabel("Turn on color blind mode");
        JRadioButton yesRadioButton = new JRadioButton("Yes");
        JRadioButton noRadioButton = new JRadioButton("No");
        JButton colorBlindModeButton = new JButton("Go");

        // Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(yesRadioButton);
        group.add(noRadioButton);

        settingsPanel.add(changeUsernameLabel);
        settingsPanel.add(newUsernameField);
        settingsPanel.add(changePasswordLabel);
        settingsPanel.add(newPasswordField);
        settingsPanel.add(confirmNewPasswordField);
        settingsPanel.add(changeEmailLabel);
        settingsPanel.add(newEmailField);
        settingsPanel.add(confirmNewEmailField);
        settingsPanel.add(colorBlindModeLabel);
        settingsPanel.add(yesRadioButton);
        settingsPanel.add(noRadioButton);

        // Add the buttons last to ensure they are at the bottom
        settingsPanel.add(changeUsernameButton);
        settingsPanel.add(changePasswordButton);
        settingsPanel.add(changeEmailButton);
        settingsPanel.add(colorBlindModeButton);

        // Add components to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(settingsPanel, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }
}
