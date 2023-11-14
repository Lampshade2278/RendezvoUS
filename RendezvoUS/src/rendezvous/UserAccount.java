package rendezvous;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class UserAccount implements Serializable {
    private String username;
    private String password; // Store hashed passwords for security
    private List<CalendarEvent> events; // List of user's calendar events

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        this.events = new ArrayList<>();
    }

    // Getters and setters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<CalendarEvent> getEvents() { return events; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEvents(List<CalendarEvent> events) { this.events = events; }

    // Add methods to manage events
    public void addEvent(CalendarEvent event) { events.add(event); }
    public void removeEvent(CalendarEvent event) { events.remove(event); }
    // Additional methods as needed...
}
