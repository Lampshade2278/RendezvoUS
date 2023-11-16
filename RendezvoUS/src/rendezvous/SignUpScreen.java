package rendezvous;

import javax.swing.*;
import java.awt.*;

public class SignUpScreen {

    private final JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton signUpButton;

    public SignUpScreen() {
        frame = new JFrame("RendezvoUS - Registration");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        frame.add(panel, BorderLayout.CENTER);

        // Initialize components
        initializeComponents(panel);

        // Set up Enter key binding for the Sign Up button
        frame.getRootPane().setDefaultButton(signUpButton);

        frame.setVisible(true);
    }

    private void initializeComponents(JPanel panel) {
        // Username
        usernameField = new JTextField(20);
        panel.add(new JLabel("Username: "));
        panel.add(usernameField);

        // Password
        passwordField = new JPasswordField(20);
        panel.add(new JLabel("Password: "));
        panel.add(passwordField);

        // Confirm Password
        confirmPasswordField = new JPasswordField(20);
        panel.add(new JLabel("Confirm Password: "));
        panel.add(confirmPasswordField);

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> registerUser());
        frame.add(signUpButton, BorderLayout.SOUTH);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (password.equals(confirmPassword)) {
            // Save user account
            UserAccount newUserAccount = new UserAccount(username, password);
            UserDataManager.saveUserAccount(newUserAccount); // Save to .dat file
            JOptionPane.showMessageDialog(frame, "Registration Successful!");

            // Close the registration screen
            frame.dispose();

            // Open the main screen with the new user account
            MainScreen mainScreen = new MainScreen(newUserAccount);
            mainScreen.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Password does not match.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new SignUpScreen();
    }
}
