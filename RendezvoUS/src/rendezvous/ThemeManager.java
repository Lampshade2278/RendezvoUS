package rendezvous;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ThemeManager {

    public enum Theme {
        LIGHT, DARK
    }

    private static Theme currentTheme = Theme.LIGHT;

    public static Theme getCurrentTheme() {
        return currentTheme;
    }

    public static void applyTheme(Component component, Theme theme) {
        currentTheme = theme;
        switch (theme) {
            case LIGHT:
                setLightTheme(component);
                break;
            case DARK:
                setDarkTheme(component);
                break;
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyTheme(child, theme);
            }
        }
    }

    private static void setLightTheme(Component component) {
        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLACK);
    }

    private static void setDarkTheme(Component component) {
        component.setBackground(Color.DARK_GRAY);

        if (component instanceof JButton || component instanceof JComboBox || component instanceof JTable) {
            component.setBackground(new Color(64, 64, 64));
            component.setForeground(new Color(64, 64, 64));
        }
        if (component instanceof JSpinner || component instanceof JLabel || component instanceof JTextField || component instanceof JTextArea  || component instanceof JTableHeader) {
            component.setBackground(new Color(64, 64, 64));
            component.setForeground(Color.WHITE); // Set text color to white for inputs
        }
    }

    public static void changeTheme(JFrame frame, Theme theme) {
        applyTheme(frame.getContentPane(), theme);
        frame.repaint();
    }
}
