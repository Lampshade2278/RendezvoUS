package rendezvous;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel loginErrorLabel;

    public LoginScreen() {
        // Create frame
        frame = new JFrame("RendezvoUS Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Logo panel
        JPanel logoPanel = new JPanel();
        JLabel logoLabel = new JLabel("LOGO");
        logoPanel.add(logoLabel);
        frame.add(logoPanel, BorderLayout.NORTH);

        // Login fields panel
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        
        usernameField = new JTextField("user name field", 20);
        passwordField = new JPasswordField("user password field", 20);
        
        fieldsPanel.add(usernameField);
        fieldsPanel.add(passwordField);
        frame.add(fieldsPanel, BorderLayout.CENTER);

        // Button and signup panel
        JPanel buttonPanel = new JPanel();
        
        JButton loginButton = new JButton("GO");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLogin();
            }
        });
        buttonPanel.add(loginButton);
        
        JLabel signUpLabel = new JLabel("Sign up here link");
        // Add an action listener to sign up label if needed
        
        buttonPanel.add(signUpLabel);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Error label
        loginErrorLabel = new JLabel("Incorrect Pass or user name. Try Again.");
        loginErrorLabel.setForeground(Color.RED);
        loginErrorLabel.setVisible(false);
        
        frame.add(loginErrorLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void checkLogin() {
        // Dummy login check. You should integrate real login logic here.
        if ("admin".equals(usernameField.getText()) && "password".equals(new String(passwordField.getPassword()))) {
            loginErrorLabel.setVisible(false);
            // Proceed to the calendar part of RendezvoUS application
        } else {
            loginErrorLabel.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
