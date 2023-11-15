package rendezvous;

import javax.swing.*;
import java.awt.*;

public class LoginScreen {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel loginErrorLabel;

    public LoginScreen() {
        frame = new JFrame("RendezvoUS");
        frame.setSize(375, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        initializeComponents();
        frame.setVisible(true);
    }

    private void initializeComponents() {
        // Create a panel to hold the logo
        JPanel logoPanel = new JPanel();

        // Load the logo image icon from a file
        ImageIcon logoIcon = new ImageIcon("RendezvoUS\\bin\\resources\\LOGO.png"); // Ensure the 'resources' directory is correctly located

        // Resize the logo to the desired dimensions
        Image logoImage = logoIcon.getImage();
        Image newImg = logoImage.getScaledInstance(200, 130, Image.SCALE_SMOOTH); // Replace 100, 100 with the desired width and height
        logoIcon = new ImageIcon(newImg); // Transform it back into an ImageIcon

        // Create a label to hold the resized logo
        JLabel logoLabel = new JLabel(logoIcon);

        // Add the label to the logo panel
        logoPanel.add(logoLabel);

        // Add the logo panel to the frame's north position
        frame.add(logoPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        fieldsPanel.add(new JLabel("Username:"));
        fieldsPanel.add(usernameField);
        fieldsPanel.add(new JLabel("Password:"));
        fieldsPanel.add(passwordField);
        centerPanel.add(fieldsPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> checkLogin());
        buttonPanel.add(loginButton);

        JButton signUpButton = new JButton("Sign Up Here");
        signUpButton.addActionListener(e -> openSignUpScreen());
        buttonPanel.add(signUpButton);

        centerPanel.add(buttonPanel);
        frame.add(centerPanel, BorderLayout.CENTER);

        loginErrorLabel = new JLabel("Incorrect username or password. Try Again.");
        loginErrorLabel.setForeground(Color.RED);
        loginErrorLabel.setVisible(false);
        frame.add(loginErrorLabel, BorderLayout.SOUTH);

        ThemeManager.applyTheme(frame, ThemeManager.getCurrentTheme());
    }

    private void checkLogin() {
        if ("admin".equals(usernameField.getText()) && "password".equals(new String(passwordField.getPassword()))) {
            loginErrorLabel.setVisible(false);
            openMainScreen();
        } else {
            loginErrorLabel.setVisible(true);
        }
    }

    private void openMainScreen() {
        frame.setVisible(false);
        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true);
    }

    private void openSignUpScreen() {
        JDialog signUpDialog = new JDialog(frame, "Sign Up", true);
        signUpDialog.setLayout(new BorderLayout());
        signUpDialog.setSize(400, 300);

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
        signUpDialog.setLocationRelativeTo(null);
        signUpDialog.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
// test