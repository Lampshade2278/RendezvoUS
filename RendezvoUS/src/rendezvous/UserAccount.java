package rendezvous;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class UserAccount implements Serializable {
    private String username;
    private String password;
    private List<CalendarEvent> recurringEvents; // List of user's calendar events
    private UserSettings settings; // User-specific settings

    // Constructor
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        recurringEvents = new ArrayList<>();
        this.settings = new UserSettings(); // Initialize with default settings
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public CalendarEvent getRecurringEvents(int index){
        return recurringEvents.get(index);
    }
    public UserSettings getSettings() { return settings; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setSettings(UserSettings settings) { this.settings = settings; }

    // Methods to manage recurring events
    public void addRecurringEvent(CalendarEvent event) { recurringEvents.add(event); }
    public void removeRecurringEvent(CalendarEvent event) { recurringEvents.remove(event); }
    // Additional methods as needed...
}
