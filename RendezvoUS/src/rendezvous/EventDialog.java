package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

// EventDialog class provides a dialog box for creating or editing calendar events.
public class EventDialog extends JDialog {
    private JTextField titleField; // Field for event title
    private JTextArea descriptionField; // Area for event description
    private JSpinner dateSpinner; // Spinner for selecting the event date
    private JSpinner timeSpinner; // Spinner for selecting the event time
    private JButton saveButton; // Button to save the event
    private JButton cancelButton; // Button to cancel the dialog
    private JButton deleteButton; // Button to delete the event
    private CalendarEvent event; // The current event being edited or null for a new event
    private final boolean isEdit; // Indicates if the dialog is for editing an existing event
    private final CalendarModel calendarModel; // Model of the calendar

    public EventDialog(Frame owner, boolean modal, CalendarEvent event, CalendarModel calendarModel, Date eventDate) {
        super(owner, modal);
        this.event = event;
        this.isEdit = (event != null); // Check if an existing event is being edited
        this.calendarModel = calendarModel; // The calendar model

        initializeUI(eventDate); // Initialize the user interface
    }

    private void initializeUI(Date eventDate) {
        setTitle(isEdit ? "Edit Event" : "Add Event"); // Set title based on the mode
        setLayout(new BorderLayout());
        setSize(400, 300); // Set the size of the dialog

        // Initialize the title field
        titleField = new JTextField(20);
        titleField.setBorder(BorderFactory.createTitledBorder("Event Title"));
        if (isEdit) titleField.setText(event.getTitle()); // Set text if editing

        // Initialize the description field
        descriptionField = new JTextArea(5, 20);
        descriptionField.setBorder(BorderFactory.createTitledBorder("Event Description"));
        if (isEdit) descriptionField.setText(event.getDescription()); // Set text if editing

        // Initialize the date spinner
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setBorder(BorderFactory.createTitledBorder("Event Date"));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy/MM/dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(eventDate); // Set initial date value

        // Initialize the time spinner
        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setBorder(BorderFactory.createTitledBorder("Event Time"));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(eventDate); // Set initial time value

        // Initialize buttons
        saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveEvent);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteEvent);
        deleteButton.setVisible(isEdit); // Show delete button only when editing

        // Arrange fields and buttons in panels
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
        fieldsPanel.add(titleField);
        fieldsPanel.add(descriptionField);
        fieldsPanel.add(dateSpinner);
        fieldsPanel.add(timeSpinner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveEvent(ActionEvent e) {
        // Extract information from fields
        String title = titleField.getText();
        String description = descriptionField.getText();
        Date date = (Date) dateSpinner.getValue();
        Date time = (Date) timeSpinner.getValue();

        // Combine date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));

        Date newDate = calendar.getTime(); // Combine date and time

        if (isEdit) {
            // Update the event
            calendarModel.getEventStorage().updateEvent(event, newDate);
        } else {
            // Create a new event
            event = new CalendarEvent(title, newDate, description);
            calendarModel.getEventStorage().addEvent(event);
        }

        dispose(); // Close the dialog
    }

    private void deleteEvent(ActionEvent e) {
        if (isEdit) {
            // Remove the event from the storage
            calendarModel.getEventStorage().removeEvent(event);
        }
        dispose(); // Close the dialog
    }
}
