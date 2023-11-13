package rendezvous;

import java.util.*;
import java.io.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class EventStorage implements Serializable {
    private transient Map<Date, List<CalendarEvent>> events;
    private static final Logger LOGGER = Logger.getLogger(EventStorage.class.getName());
    private static final String STORAGE_FILE = "event_storage.dat";

    public EventStorage() {
        events = new HashMap<>();
        loadEvents();
    }

    public void addEvent(CalendarEvent event) {
        List<CalendarEvent> eventsForDate = events.computeIfAbsent(truncateTime(event.getDate()), k -> new ArrayList<>());
        eventsForDate.add(event);
        saveEvents();
    }

    public void removeEvent(CalendarEvent event) {
        List<CalendarEvent> eventsForDate = events.get(truncateTime(event.getDate()));
        if (eventsForDate != null) {
            eventsForDate.remove(event);
            if (eventsForDate.isEmpty()) {
                events.remove(truncateTime(event.getDate()));
            }
        }
        saveEvents();
    }

    public void updateEvent(CalendarEvent updatedEvent) {
        removeEvent(updatedEvent);
        addEvent(updatedEvent);
    }

    public List<CalendarEvent> getEventsByDate(Date date) {
        return events.getOrDefault(truncateTime(date), Collections.emptyList());
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