//Purpose: Saves events to a file called "username_events.dat"
package rendezvous;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventStorage implements Serializable {
    private transient Map<Date, List<CalendarEvent>> eventsByDay;
    private static final Logger LOGGER = Logger.getLogger(EventStorage.class.getName());
    private String userFileName;

    public EventStorage(String username) {
        this.userFileName = username + "_events.dat";
        eventsByDay = new HashMap<>();
        loadEvents();
    }

    public void addEvent(CalendarEvent event) {
        Date truncatedDate = truncateTime(event.getDate());
        List<CalendarEvent> eventsForDate = eventsByDay.computeIfAbsent(truncatedDate, k -> new ArrayList<>());
        eventsForDate.add(event);
        saveEvents();
    }


    public void removeEvent(CalendarEvent event) {
        Date truncatedDate = truncateTime(event.getDate());
        List<CalendarEvent> eventsForDate = eventsByDay.get(truncatedDate);
        if (eventsForDate != null) {
            eventsForDate.remove(event);
            if (eventsForDate.isEmpty()) {
                eventsByDay.remove(truncatedDate);
            }
        }
        saveEvents();
    }

    public void updateEvent(CalendarEvent oldEvent, CalendarEvent updatedEvent) {
        Date oldTruncatedDate = truncateTime(oldEvent.getDate());
        Date newTruncatedDate = truncateTime(updatedEvent.getDate());

        // Remove the old event
        List<CalendarEvent> eventsForOldDate = eventsByDay.get(oldTruncatedDate);
        if (eventsForOldDate != null) {
            eventsForOldDate.remove(oldEvent);
            if (eventsForOldDate.isEmpty()) {
                eventsByDay.remove(oldTruncatedDate);
            }
        }

        // Add the updated event to the new date
        List<CalendarEvent> eventsForNewDate = eventsByDay.computeIfAbsent(newTruncatedDate, k -> new ArrayList<>());
        eventsForNewDate.add(updatedEvent);

        saveEvents();
    }

    public List<CalendarEvent> getEventsByDate(Date date) {
        Date truncatedDate = truncateTime(date);
        return eventsByDay.getOrDefault(truncatedDate, Collections.emptyList());
    }

    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFileName))) {
            oos.writeObject(eventsByDay);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving events to file", e);
        }
    }

    private void loadEvents() {
        File file = new File(userFileName);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                eventsByDay = (Map<Date, List<CalendarEvent>>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading events from file", e);
            }
        }
    }
}
