package rendezvous;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class UserAccount implements Serializable {
    private String username;
    private String password; // todo Consider storing hashed passwords for security
    private List<CalendarEvent> events; // List of user's calendar events
    private UserSettings settings; // User-specific settings

    // Constructor
    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.events = new ArrayList<>();//todo not used
        this.settings = new UserSettings(); // Initialize with default settings
    }

    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<CalendarEvent> getEvents() { return events; }
    public UserSettings getSettings() { return settings; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEvents(List<CalendarEvent> events) { this.events = events; }
    public void setSettings(UserSettings settings) { this.settings = settings; }



    // Methods to manage events
    public void addEvent(CalendarEvent event) { events.add(event); }//todo if these aren't used what is 'events' for?
    public void removeEvent(CalendarEvent event) { events.remove(event); }
    // Additional methods as needed...
}
