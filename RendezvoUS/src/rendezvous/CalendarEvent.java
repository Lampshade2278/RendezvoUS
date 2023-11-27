package rendezvous;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CalendarEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private Date date;
    private int durationMinutes;
    private String description;

    public CalendarEvent(String title, Date date, String description, int durationMinutes) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.durationMinutes = durationMinutes;
    }

    public String getTitle() {
        return title;
    }
    public Date getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarEvent)) return false;
        CalendarEvent that = (CalendarEvent) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date);
    }
}
