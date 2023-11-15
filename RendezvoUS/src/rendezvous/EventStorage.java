package rendezvous;

import java.util.*;
import java.io.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class EventStorage implements Serializable {
    private transient Map<Date, List<CalendarEvent>> events;
    private static final Logger LOGGER = Logger.getLogger(EventStorage.class.getName());
    private String storageFilePath; // File path for storing events, unique to each user

    // Constructor - initializes the storage and loads events for a specific user.
    public EventStorage(String username) {
        this.storageFilePath = username + "_events.dat"; // Set the file path based on the username
        events = new HashMap<>();
        loadEvents();
    }

    // Add an event to the storage
    public void addEvent(CalendarEvent event) {
        List<CalendarEvent> eventsForDate = events.computeIfAbsent(truncateTime(event.getDate()), k -> new ArrayList<>());
        eventsForDate.add(event);
        saveEvents();
    }

    // Remove an event from the storage
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

    // Update an event
    public void updateEvent(CalendarEvent oldEvent, Date newDate) {
        removeEvent(oldEvent);
        CalendarEvent newEvent = new CalendarEvent(oldEvent.getTitle(), newDate, oldEvent.getDescription());
        addEvent(newEvent);
    }

    // Get the list of events for a specific date
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

    // Save the current state of events to the user's file
    private void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFilePath))) {
            oos.writeObject(events);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving events to file", e);
        }
    }

    // Load the events from the user's file
    private void loadEvents() {
        File file = new File(storageFilePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                events = (Map<Date, List<CalendarEvent>>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading events from file", e);
            }
        }
    }
}
