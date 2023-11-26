package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginScreen {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel loginErrorLabel;

    public LoginScreen() {
        frame = new JFrame("RendezvoUS");
        frame.setSize(330, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        initializeComponents();
        frame.setVisible(true);
    }

    private void initializeComponents() {
        // Logo Panel
        JPanel logoPanel = new JPanel();
        ImageIcon logoIcon = new ImageIcon("RendezvoUS/bin/resources/LOGO.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(190, 130, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoIcon);
        logoPanel.add(logoLabel);
        frame.add(logoPanel, BorderLayout.NORTH);

        // Center Panel
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

        // Buttons Panel
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

        // Add key listener to password field for Enter key press
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    checkLogin();
                }
            }
        });
    }

    private void checkLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        UserAccount userAccount = UserDataManager.loadUserAccount(username);
        if (userAccount != null && userAccount.getPassword().equals(password)) {
            loginErrorLabel.setVisible(false);
            openMainScreen(userAccount);
        } else {
            loginErrorLabel.setVisible(true);
        }
    }

    private void openMainScreen(UserAccount userAccount) {
        frame.setVisible(false);
        MainScreen mainScreen = new MainScreen(userAccount);
        mainScreen.setVisible(true);
    }

    private void openSignUpScreen() {

        JDialog signUpDialog = new JDialog(frame, "Sign Up", true);
        signUpDialog.setLayout(new BorderLayout());
        signUpDialog.setSize(400, 340);


        JPanel logoPanel2 = new JPanel();

        // Load the logo image icon from a file
        ImageIcon logoIcon = new ImageIcon("RendezvoUS/bin/resources/LOGO.png");
        // Ensure the 'resources' directory is correctly located
        if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image logoImage = logoIcon.getImage();
            // Resize and use the image
        } else {
            // Handle the case where the image is not loaded
            System.out.println("Image not loaded");
        }

        // Resize the logo to the desired dimensions
        Image logoImage = logoIcon.getImage();
        Image newImg = logoImage.getScaledInstance(150, 100, Image.SCALE_SMOOTH); // Replace 100, 100 with the desired width and height
        logoIcon = new ImageIcon(newImg); // Transform it back into an ImageIcon

        // Create a label to hold the resized logo
        JLabel logoLabel = new JLabel(logoIcon);

        // Add the label to the logo panel
        logoPanel2.add(logoLabel);

        // Add the logo panel to the frame's north position
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 1, 1));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        JTextField newUsernameField = new JTextField(20);
        JPasswordField newPasswordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        formPanel.add(new JLabel("Username:"));
        formPanel.add(newUsernameField);
        formPanel.add(new JLabel("Password: (At Least 8 Characters)"));
        formPanel.add(newPasswordField);
        formPanel.add(new JLabel("Confirm Password:"));
        formPanel.add(confirmPasswordField);



        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));


        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String newUsername = newUsernameField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (newPassword.equals(confirmPassword) && newPassword.length() >= 8) {
                UserAccount newUserAccount = new UserAccount(newUsername, newPassword);
                UserDataManager.saveUserAccount(newUserAccount);
                JOptionPane.showMessageDialog(signUpDialog, "Registration Successful!");
                signUpDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(signUpDialog, "Password does not match or is less than 8 characters.",
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonsPanel.add(registerButton);

        signUpDialog.add(centerPanel, BorderLayout.CENTER);


        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> signUpDialog.dispose());
        buttonsPanel.add(cancelButton);

        JPanel bottomBuffer = new JPanel();
        bottomBuffer.setPreferredSize(new Dimension(frame.getWidth(), 20)); // Height of 20 pixels for the buffer
        bottomBuffer.setOpaque(false); // Make it transparent

        signUpDialog.add(logoPanel2, BorderLayout.NORTH);
        signUpDialog.add(formPanel, BorderLayout.CENTER);
        signUpDialog.add(buttonsPanel, BorderLayout.SOUTH);
        signUpDialog.setLocationRelativeTo(null);
        signUpDialog.pack();
        ThemeManager.applyTheme(signUpDialog, ThemeManager.getCurrentTheme());
        signUpDialog.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}