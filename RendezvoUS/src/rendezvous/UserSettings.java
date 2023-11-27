package rendezvous;

import java.io.Serializable;

class UserSettings implements Serializable {
    // Example setting: theme
    private ThemeManager.Theme theme;

    public UserSettings() {
        // Default values
        this.theme = ThemeManager.Theme.LIGHT; // Default theme
    }

    // Getters and setters for each setting
    public ThemeManager.Theme getTheme() { return theme; }
    public void setTheme(ThemeManager.Theme theme) { this.theme = theme; }
    // Additional settings as needed...
}
