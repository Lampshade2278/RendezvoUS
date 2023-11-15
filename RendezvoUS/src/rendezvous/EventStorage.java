package rendezvous;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventStorage implements Serializable {
    private Map<Date, List<CalendarEvent>> events;
    private static final Logger LOGGER = Logger.getLogger(EventStorage.class.getName());
    private static final String STORAGE_FILE = "event_storage.dat";

    public EventStorage() {
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

    public void updateEvent(CalendarEvent oldEvent, Date newDate) {
        Date oldTruncatedDate = truncateTime(oldEvent.getDate());
        Date newTruncatedDate = truncateTime(newDate);

        List<CalendarEvent> eventsForOldDate = events.get(oldTruncatedDate);
        if (eventsForOldDate != null) {
            eventsForOldDate.remove(oldEvent);
            if (eventsForOldDate.isEmpty()) {
                events.remove(oldTruncatedDate);
            }
        }

        List<CalendarEvent> eventsForNewDate = events.computeIfAbsent(newTruncatedDate, k -> new ArrayList<>());
        eventsForNewDate.add(new CalendarEvent(oldEvent.getTitle(), newDate, oldEvent.getDescription()));

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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(events);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving events to file", e);
        }
    }

    private void loadEvents() {
        File file = new File(STORAGE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                events = (Map<Date, List<CalendarEvent>>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading events from file", e);
            }
        }
    }
}