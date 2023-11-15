package rendezvous;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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

        // Recursively apply the theme to child components if the component is a container
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyTheme(child, theme);
            }
        }
    }

    private static void setLightTheme(Component component) {
        Color backgroundColor = new Color(220, 220, 220); // light gray
        Color foregroundColor = Color.BLACK; // black
        component.setBackground(backgroundColor);
        component.setForeground(foregroundColor);

        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            Border border = jComponent.getBorder();
            if (border instanceof TitledBorder) {
                ((TitledBorder) border).setTitleColor(foregroundColor);
            }
        }

        if (component instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) component;
            comboBox.setRenderer(new DefaultListCellRenderer());
        }

        // Apply the theme to child components recursively
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyTheme(child, Theme.LIGHT);
            }
        }
    }

    private static void setDarkTheme(Component component) {
        Color backgroundColor = new Color(64, 64, 64); // dark gray
        Color foregroundColor = Color.WHITE; // white
        component.setBackground(backgroundColor);
        component.setForeground(foregroundColor);

        if (component instanceof JComponent) {
            JComponent jComponent = (JComponent) component;
            Border border = jComponent.getBorder();
            if (border instanceof TitledBorder) {
                ((TitledBorder) border).setTitleColor(foregroundColor);
            }
        }

        if (component instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) component;
            comboBox.setRenderer(new ComboBoxRenderer());
        }
        if (component instanceof JTableHeader) {
            JTableHeader header = (JTableHeader) component;
            header.setBackground(backgroundColor); // Set header background for light theme
        }
    }

    // Custom renderer for JComboBox items to change text color
    static class ComboBoxRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(Color.DARK_GRAY);
                setForeground(Color.WHITE);
            }
            return this;
        }
    }

    public static void changeTheme(JFrame frame, Theme theme) {
        applyTheme(frame.getContentPane(), theme);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.repaint();
    }
}