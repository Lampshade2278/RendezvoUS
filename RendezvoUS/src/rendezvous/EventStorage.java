package rendezvous;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventStorage implements Serializable {
    private transient Map<Date, List<CalendarEvent>> events;
    private static final Logger LOGGER = Logger.getLogger(EventStorage.class.getName());
    private String userFileName;

    public EventStorage(String username) {
        this.userFileName = username + "_events.dat";
        events = new HashMap<>();
        loadEvents();
    }

    public void addEvent(CalendarEvent event) {
        Date truncatedDate = truncateTime(event.getDate());
        List<CalendarEvent> eventsForDate = events.computeIfAbsent(truncatedDate, k -> new ArrayList<>());
        eventsForDate.add(event);
        saveEvents();
    }

    public void removeEvent(CalendarEvent event) {
        Date truncatedDate = truncateTime(event.getDate());
        List<CalendarEvent> eventsForDate = events.get(truncatedDate);
        if (eventsForDate != null) {
            eventsForDate.remove(event);
            if (eventsForDate.isEmpty()) {
                events.remove(truncatedDate);
            }
        }
        saveEvents();
    }

    public void updateEvent(CalendarEvent oldEvent, CalendarEvent updatedEvent) {
        Date oldTruncatedDate = truncateTime(oldEvent.getDate());
        List<CalendarEvent> eventsForOldDate = events.get(oldTruncatedDate);

        // Remove the old event
        if (eventsForOldDate != null) {
            eventsForOldDate.remove(oldEvent);
            if (eventsForOldDate.isEmpty()) {
                events.remove(oldTruncatedDate);
            }
        }

        // Add the updated event
        Date newTruncatedDate = truncateTime(updatedEvent.getDate());
        List<CalendarEvent> eventsForNewDate = events.computeIfAbsent(newTruncatedDate, k -> new ArrayList<>());
        eventsForNewDate.add(updatedEvent);

        saveEvents();
    }

    public List<CalendarEvent> getEventsByDate(Date date) {
        Date truncatedDate = truncateTime(date);
        return events.getOrDefault(truncatedDate, Collections.emptyList());
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
            oos.writeObject(events);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving events to file", e);
        }
    }

    private void loadEvents() {
        File file = new File(userFileName);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                events = (Map<Date, List<CalendarEvent>>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading events from file", e);
            }
        }
    }
}
