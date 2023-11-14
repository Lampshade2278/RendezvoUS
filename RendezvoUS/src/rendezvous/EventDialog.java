package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class EventDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionField;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private CalendarEvent event;
    private final boolean isEdit;
    private final CalendarModel calendarModel;
    private MainScreen mainScreen;

    public EventDialog(Frame owner, boolean modal, CalendarEvent event, CalendarModel calendarModel, Date eventDate) {
        super(owner, modal);
        this.event = event;
        this.isEdit = (event != null);
        this.calendarModel = calendarModel;

        initializeUI(eventDate);
        ThemeManager.applyTheme(this.getContentPane(), ThemeManager.getCurrentTheme()); // Apply theme to dialog
    }



    private void initializeUI(Date eventDate) {
        setTitle(isEdit ? "Edit Event" : "Add Event");
        setLayout(new BorderLayout());
        setSize(400, 300);

        // Initialize UI components
        titleField = createTextField("Event Title", isEdit ? event.getTitle() : "");
        descriptionField = createTextArea("Event Description", isEdit ? event.getDescription() : "");
        dateSpinner = createSpinner("Event Date", eventDate);


        JComboBox<RecurrenceManager.Recurrence> RecurrenceComboBox = new JComboBox<>(RecurrenceManager.Recurrence.values());
        RecurrenceComboBox.addActionListener(e -> {
            RecurrenceManager.Recurrence selectedRecurrence = (RecurrenceManager.Recurrence) RecurrenceComboBox.getSelectedItem();
            mainScreen.changeRecurrence(selectedRecurrence); // Call changeTheme on MainScreen
        });


        // Initialize buttons
        saveButton = createButton("Save", this::saveEvent);
        cancelButton = createButton("Cancel", e -> dispose());
        deleteButton = createButton("Delete", this::deleteEvent);
        deleteButton.setVisible(isEdit);

        // Layout components
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
        fieldsPanel.add(titleField);
        fieldsPanel.add(descriptionField);
        fieldsPanel.add(dateSpinner);
        fieldsPanel.add(new JLabel("Is this Recurring?"));
        fieldsPanel.add(RecurrenceComboBox);


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        applyCurrentTheme();
    }

    private JTextField createTextField(String title, String text) {
        JTextField textField = new JTextField(20);
        textField.setBorder(BorderFactory.createTitledBorder(title));
        textField.setText(text);
        return textField;
    }

    private JTextArea createTextArea(String title, String text) {
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setBorder(BorderFactory.createTitledBorder(title));
        textArea.setText(text);
        return textArea;
    }

    private JSpinner createSpinner(String title, Date value) {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        spinner.setBorder(BorderFactory.createTitledBorder(title));
        spinner.setValue(value);
        return spinner;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void saveEvent(ActionEvent e) {
        // Extract information from fields
        String title = titleField.getText();
        String description = descriptionField.getText();
        Date date = (Date) dateSpinner.getValue();

        // Combine date and time
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar timeCalendar = Calendar.getInstance();
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

    private void applyCurrentTheme() {
        ThemeManager.Theme currentTheme = ThemeManager.getCurrentTheme();
        ThemeManager.applyTheme(titleField, currentTheme);
        ThemeManager.applyTheme(descriptionField, currentTheme);
        ThemeManager.applyTheme(dateSpinner, currentTheme);
        ThemeManager.applyTheme(saveButton, currentTheme);
        ThemeManager.applyTheme(cancelButton, currentTheme);
        ThemeManager.applyTheme(deleteButton, currentTheme);
    }
}
