package rendezvous;

import java.util.*;

public class EventStorage {
    private Map<Date, List<CalendarEvent>> events;

    public EventStorage() {
        events = new HashMap<>();
    }

    // Add an event
    public void addEvent(CalendarEvent event) {
        // Retrieve the list of events for the given date, or create it if it doesn't exist
        List<CalendarEvent> eventsForDate = events.computeIfAbsent(truncateTime(event.getDate()), k -> new ArrayList<>());
        eventsForDate.add(event);
    }

    // Remove an event
    public void removeEvent(CalendarEvent event) {
        List<CalendarEvent> eventsForDate = events.get(truncateTime(event.getDate()));
        if (eventsForDate != null) {
            eventsForDate.remove(event);
            if (eventsForDate.isEmpty()) {
                events.remove(truncateTime(event.getDate()));
            }
        }
    }

    // Get events for a specific date
    public List<CalendarEvent> getEventsByDate(Date date) {
        return events.getOrDefault(truncateTime(date), Collections.emptyList());
    }

    // Utility method to truncate time from a Date object
    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
