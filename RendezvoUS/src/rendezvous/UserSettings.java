package rendezvous;

import java.io.Serializable;

class UserSettings implements Serializable {
    // Example setting: theme
    private String theme;

    public UserSettings() {
        // Default values
        this.theme = "Light"; // Default theme
    }

    // Getters and setters for each setting
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    // Additional settings as needed...
}
