package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main application class
public class MainApplication extends JFrame {

    // Constructor
    public MainApplication() {
        // Set frame properties
        setTitle("Main Application Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        // Add navigation buttons for each window
        JButton btnScreen1 = new JButton("Open Screen 1");
        JButton btnScreen2 = new JButton("Open Screen 2");
        JButton btnScreen3 = new JButton("Open Screen 3");
        // ... Add buttons for other screens as necessary

        btnScreen1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to open Screen 1
                new Screen1().setVisible(true);
            }
        });

        btnScreen2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to open Screen 2
                new Screen2().setVisible(true);
            }
        });

        btnScreen3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logic to open Screen 3
                new Screen3().setVisible(true);
            }
        });

        // Add buttons to the panel
        panel.add(btnScreen1);
        panel.add(btnScreen2);
        panel.add(btnScreen3);

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApplication().setVisible(true);
            }
        });
    }
}

// You'll need to define classes like Screen1, Screen2, etc., for each window/screen in your program.
