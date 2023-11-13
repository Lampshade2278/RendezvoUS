package rendezvous;

import java.io.Serializable;
import java.util.Date;

public class CalendarEvent implements Serializable {
    private static final long serialVersionUID = 1L; // Match this with the expected value

    private String title;
    private Date date;
    private String description;

    // Constructor, getters, setters, etc.
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

    public void setDate(Date newDate) {
        this.date = newDate;
    }
}
