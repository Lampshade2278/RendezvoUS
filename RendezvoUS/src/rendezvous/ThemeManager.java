package rendezvous;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {

    public enum Theme {
        LIGHT, DARK;
    }

    public static void applyTheme(Component component, Theme theme) {
        switch (theme) {
            case LIGHT:
                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLACK);
                break;
            case DARK:
                component.setBackground(Color.DARK_GRAY);
                component.setForeground(Color.WHITE);
                if (component instanceof JButton || component instanceof JComboBox || component instanceof JTable) {
                    component.setBackground(new Color(64, 64, 64)); // Medium-dark grey
                    component.setForeground(new Color(64, 64, 64)); // Medium-dark grey

                }
                break;
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyTheme(child, theme); // Apply theme recursively
            }
        }
    }

    public static void changeTheme(JFrame frame, Theme theme) {
        applyTheme(frame.getContentPane(), theme);
        if (frame instanceof MainScreen) {
            ((MainScreen) frame).updateComponentsPostThemeChange();
        }
        frame.repaint();
    }
}
