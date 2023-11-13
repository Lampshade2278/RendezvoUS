package rendezvous;

import java.util.Date;

public class CalendarEvent {
    private String title;
    private Date date;
    private String description;

    // Constructor
    public CalendarEvent(String title, Date date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
