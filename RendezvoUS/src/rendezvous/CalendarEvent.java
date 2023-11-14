package rendezvous;

import java.io.Serializable;
import java.util.Date;

// CalendarEvent class represents an event in the calendar.
// It is marked Serializable to allow saving and loading from a file.
public class CalendarEvent implements Serializable {
    // serialVersionUID is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible.
    private static final long serialVersionUID = 1L;

    // Title of the event
    private String title;
    // Date and time of the event
    private Date date;
    // Description of the event
    private String description;


    /**
     * Constructor for CalendarEvent.
     *
     * @param title       The title of the event.
     * @param date        The date and time when the event occurs.
     * @param description A brief description of the event.
     */
    public CalendarEvent(String title, Date date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    /**
     * Gets the title of the event.
     *
     * @return The title of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the date and time of the event.
     *
     * @return The date and time of the event.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the description of the event.
     *
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the title of the event.
     *
     * @param title The title of the event.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the event.
     *
     * @param description The description of the event.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the date and time of the event.
     *
     * @param newDate The new date and time of the event.
     */
    public void setDate(Date newDate) {
        this.date = newDate;
    }
}
