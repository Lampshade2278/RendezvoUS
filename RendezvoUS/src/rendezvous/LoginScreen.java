package rendezvous;

import javax.swing.*;
import java.awt.*;

// The LoginScreen class provides the user interface for the login process.
public class LoginScreen {

    private JFrame frame; // Main frame for the login screen
    private JTextField usernameField; // Text field for username
    private JPasswordField passwordField; // Password field for password
    private JLabel loginErrorLabel; // Label to display login errors

    public LoginScreen() {
        // Setup the main frame
        frame = new JFrame("RendezvoUS");
        frame.setSize(1024, 768); // Window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Default close operation
        frame.setLayout(new BorderLayout()); // Layout manager

        frame.setLocationRelativeTo(null); // Center the window on the screen

        // Logo panel at the top
        JPanel logoPanel = new JPanel();
        logoPanel.add(new JLabel("LOGO")); // Placeholder for the logo
        frame.add(logoPanel, BorderLayout.NORTH); // Add logo panel to the top

        // Center panel for user input fields and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Panel for username and password fields
        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernameField = new JTextField(20); // Username field
        passwordField = new JPasswordField(20); // Password field
        fieldsPanel.add(new JLabel("Username:")); // Label for username
        fieldsPanel.add(usernameField); // Add username field
        fieldsPanel.add(new JLabel("Password:")); // Label for password
        fieldsPanel.add(passwordField); // Add password field
        centerPanel.add(fieldsPanel); // Add fields panel to the center panel

        // Buttons panel for login and sign up
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> checkLogin()); // Action listener for login
        buttonPanel.add(loginButton);

        JButton signUpButton = new JButton("Sign Up Here");
        signUpButton.addActionListener(e -> openSignUpScreen()); // Open sign up dialog
        buttonPanel.add(signUpButton);

        centerPanel.add(buttonPanel); // Add buttons panel to center panel
        frame.add(centerPanel, BorderLayout.CENTER); // Add center panel to frame

        // Label for displaying login error messages
        loginErrorLabel = new JLabel("Incorrect username or password. Try Again.");
        loginErrorLabel.setForeground(Color.RED); // Set text color to red
        loginErrorLabel.setVisible(false); // Initially hidden
        frame.add(loginErrorLabel, BorderLayout.SOUTH); // Add to the bottom of the frame

        frame.setVisible(true); // Make the frame visible
    }

    // Method to check login credentials
    private void checkLogin() {
        // Replace with actual authentication logic
        if ("admin".equals(usernameField.getText()) && "password".equals(new String(passwordField.getPassword()))) {
            loginErrorLabel.setVisible(false); // Hide error label
            openMainScreen(); // Open main screen on successful login
        } else {
            loginErrorLabel.setVisible(true); // Show error on failed login
        }
    }

    // Method to open the main application screen
    private void openMainScreen() {
        frame.setVisible(false); // Hide the login screen
        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true); // Show the main application screen
    }

    // Method to open the sign-up dialog
    private void openSignUpScreen() {
        JDialog signUpDialog = new JDialog(frame, "Sign Up", true); // Modal dialog for sign-up
        signUpDialog.setLayout(new BorderLayout());
        signUpDialog.setSize(400, 300); // Size of the sign-up dialog

        // Form panel for sign-up fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        JTextField newUsernameField = new JTextField(20); // Field for new username
        JPasswordField newPasswordField = new JPasswordField(20); // Field for new password
        JPasswordField confirmPasswordField = new JPasswordField(20); // Field to confirm password

        // Add fields to form panel
        formPanel.add(new JLabel("Username:"));
        formPanel.add(newUsernameField);
        formPanel.add(new JLabel("Password (at least 8 characters):"));
        formPanel.add(newPasswordField);
        formPanel.add(new JLabel("Confirm Password:"));
        formPanel.add(confirmPasswordField);

        // Buttons panel for register and cancel
        JPanel buttonsPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            // Registration logic (password match and length check)
            if (new String(newPasswordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))
                    && new String(newPasswordField.getPassword()).length() >= 8) {
                JOptionPane.showMessageDialog(signUpDialog, "Registration Successful!");
                signUpDialog.dispose(); // Close the dialog on successful registration
            } else {
                JOptionPane.showMessageDialog(signUpDialog, "Password does not match or is less than 8 characters.",
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonsPanel.add(registerButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> signUpDialog.dispose()); // Close dialog without registration
        buttonsPanel.add(cancelButton);

        // Add panels to the dialog
        signUpDialog.add(formPanel, BorderLayout.CENTER);
        signUpDialog.add(buttonsPanel, BorderLayout.SOUTH);

        signUpDialog.setLocationRelativeTo(null); // Center the dialog on the screen
        signUpDialog.setVisible(true); // Make the dialog visible
    }

    // Main method to launch the login screen
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
