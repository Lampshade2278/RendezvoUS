package rendezvous;
import javax.swing.*;
import java.awt.*;

public class SignUpScreen {

    private JFrame frame;
    private JPanel panel;
    private JTextField firstNameField, lastNameField, emailField, passwordField, confirmPasswordField;
    private JLabel logoLabel, titleLabel, firstNameLabel, lastNameLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    private JButton signUpButton;

    public SignUpScreen() {
        frame = new JFrame("RendezvoUS - Sign Up");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(6, 2));
        frame.add(panel);

        // Logo and Title
        logoLabel = new JLabel("LOGO", SwingConstants.CENTER);
        titleLabel = new JLabel("Title of Screen", SwingConstants.CENTER);
        frame.add(logoLabel, BorderLayout.NORTH);
        frame.add(titleLabel, BorderLayout.SOUTH);

        // First Name
        firstNameLabel = new JLabel("First Name: ");
        firstNameField = new JTextField(20);
        panel.add(firstNameLabel);
        panel.add(firstNameField);

        // Last Name
        lastNameLabel = new JLabel("Last Name: ");
        lastNameField = new JTextField(20);
        panel.add(lastNameLabel);
        panel.add(lastNameField);

        // Email
        emailLabel = new JLabel("Email: ");
        emailField = new JTextField(20);
        panel.add(emailLabel);
        panel.add(emailField);

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
        frame.add(signUpButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SignUpScreen();
    }
}
