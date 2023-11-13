package rendezvous;

import java.util.*;
import java.io.*;
import java.util.logging.Logger;
import java.util.logging.Level;

// The EventStorage class handles the storage and management of calendar events.
public class EventStorage implements Serializable {
    private transient Map<Date, List<CalendarEvent>> events; // A map to store events by date
    private static final Logger LOGGER = Logger.getLogger(EventStorage.class.getName()); // Logger for logging errors
    private static final String STORAGE_FILE = "event_storage.dat"; // File name for storing events

    // Constructor - initializes the storage and loads events from the file.
    public EventStorage() {
        events = new HashMap<>();
        loadEvents(); // Load events from the file when the storage is created
    }

    // Add an event to the storage
    public void addEvent(CalendarEvent event) {
        // Retrieve the list of events for a specific date, or create a new list if it doesn't exist
        List<CalendarEvent> eventsForDate = events.computeIfAbsent(truncateTime(event.getDate()), k -> new ArrayList<>());
        eventsForDate.add(event); // Add the event to the list
        saveEvents(); // Save the updated list of events to the file
    }

    // Remove an event from the storage
    public void removeEvent(CalendarEvent event) {
        List<CalendarEvent> eventsForDate = events.get(truncateTime(event.getDate()));
        if (eventsForDate != null) {
            eventsForDate.remove(event); // Remove the event from the list
            if (eventsForDate.isEmpty()) {
                events.remove(truncateTime(event.getDate())); // Remove the date from the map if no more events
            }
        }
        saveEvents(); // Save the updated list of events to the file
    }

    // Update an event by removing the old event and adding a new one with updated details
    public void updateEvent(CalendarEvent oldEvent, Date newDate) {
        removeEvent(oldEvent); // Remove the old event
        // Create a new event with the updated date and the same title and description
        CalendarEvent newEvent = new CalendarEvent(oldEvent.getTitle(), newDate, oldEvent.getDescription());
        addEvent(newEvent); // Add the updated event
    }

    // Get the list of events for a specific date
    public List<CalendarEvent> getEventsByDate(Date date) {
        // Return the list of events for the date, or an empty list if no events are found
        return events.getOrDefault(truncateTime(date), Collections.emptyList());
    }

    // Utility method to truncate time from a Date object (keeps only the date part)
    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // Save the current state of events to a file
    private void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(events); // Write the events map to the file
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving events to file", e);
        }
    }

    // Load the events from a file
    private void loadEvents() {
        File file = new File(STORAGE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                events = (Map<Date, List<CalendarEvent>>) ois.readObject(); // Read the events map from the file
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Error loading events from file", e);
            }
        }
    }
}
