package rendezvous;
import javax.swing.*;
import java.awt.*;

public class SignUpScreen {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel logoLabel, titleLabel, usernameLabel, passwordLabel, confirmPasswordLabel;
    private JButton signUpButton;

    public SignUpScreen() {
        frame = new JFrame("RendezvoUS - Registration");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        frame.add(panel, BorderLayout.CENTER);

        // Logo and Title
        logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
        titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        frame.add(logoLabel, BorderLayout.NORTH);
        frame.add(titleLabel, BorderLayout.SOUTH);

        // Username
        usernameLabel = new JLabel("Username: ");
        usernameField = new JTextField(20);
        panel.add(usernameLabel);
        panel.add(usernameField);

        // Password
        passwordLabel = new JLabel("Password: ");
        passwordField = new JPasswordField(20);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Confirm Password
        confirmPasswordLabel = new JLabel("Confirm Password: ");
        confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);

        // Sign Up Button
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> registerUser());
        frame.add(signUpButton, BorderLayout.SOUTH);

        frame.setVisible(true);
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
