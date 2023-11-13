package rendezvous;

import javax.swing.*;
import java.awt.*;

public class LoginScreen {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel loginErrorLabel;


    public LoginScreen() {
        // Frame setup
        frame = new JFrame("RendezvoUS");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);

        // Logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.add(new JLabel("LOGO"));
        frame.add(logoPanel, BorderLayout.NORTH);

        // Center panel for fields and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Fields panel
        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        fieldsPanel.add(new JLabel("Username:"));
        fieldsPanel.add(usernameField);
        fieldsPanel.add(new JLabel("Password:"));
        fieldsPanel.add(passwordField);
        centerPanel.add(fieldsPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> checkLogin());
        buttonPanel.add(loginButton);

        JButton signUpButton = new JButton("Sign Up Here");
        signUpButton.addActionListener(e -> openSignUpScreen());
        buttonPanel.add(signUpButton);

        centerPanel.add(buttonPanel);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Error label
        loginErrorLabel = new JLabel("Incorrect username or password. Try Again.");
        loginErrorLabel.setForeground(Color.RED);
        loginErrorLabel.setVisible(false);
        frame.add(loginErrorLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void checkLogin() {
        // Login logic
        if ("admin".equals(usernameField.getText()) && "password".equals(new String(passwordField.getPassword()))) {
            loginErrorLabel.setVisible(false);
            openMainScreen();
        } else {
            loginErrorLabel.setVisible(true);
        }
    }

    private void openMainScreen() {
        // Open main screen logic
        this.frame.setVisible(false); // Hide the login screen
        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true); // Show the main application screen
    }

    private void openSignUpScreen() {
        // Create a dialog for the sign-up form
        JDialog signUpDialog = new JDialog(frame, "Sign Up", true);
        signUpDialog.setLayout(new BorderLayout());
        signUpDialog.setSize(400, 300);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        JTextField newUsernameField = new JTextField(20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        formPanel.add(new JLabel("Username:"));
        formPanel.add(newUsernameField);
        formPanel.add(new JLabel("Password (at least 8 characters):"));
        formPanel.add(newPasswordField);
        formPanel.add(new JLabel("Confirm Password:"));
        formPanel.add(confirmPasswordField);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            if (new String(newPasswordField.getPassword()).equals(new String(confirmPasswordField.getPassword()))
                    && new String(newPasswordField.getPassword()).length() >= 8) {
                JOptionPane.showMessageDialog(signUpDialog, "Registration Successful!");
                signUpDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(signUpDialog, "Password does not match or is less than 8 characters.",
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonsPanel.add(registerButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> signUpDialog.dispose());
        buttonsPanel.add(cancelButton);

        signUpDialog.add(formPanel, BorderLayout.CENTER);
        signUpDialog.add(buttonsPanel, BorderLayout.SOUTH);

        signUpDialog.setLocationRelativeTo(null); // Center the dialog on the screen
        signUpDialog.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
