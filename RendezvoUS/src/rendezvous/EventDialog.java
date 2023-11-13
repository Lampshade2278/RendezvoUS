package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class EventDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionField;
    private JSpinner timeSpinner;
    private JSpinner dateSpinner;
    private JButton saveButton;
    private JButton cancelButton;
    private CalendarEvent event;
    private boolean isEdit;
    private CalendarModel calendarModel;

    public EventDialog(Frame owner, boolean modal, CalendarEvent event, CalendarModel calendarModel, Date eventDate) {
        super(owner, modal);
        this.event = event;
        this.isEdit = (event != null);
        this.calendarModel = calendarModel;

        // If editing, use the event's date, otherwise use the provided eventDate
        Date initialDate = isEdit ? event.getDate() : eventDate;

        initializeUI(initialDate); // Pass the initial date to the UI initialization
    }

    private void initializeUI(Date initialDate) {
        setTitle(isEdit ? "Edit Event" : "Add Event");
        setLayout(new BorderLayout());
        setSize(400, 300);

        // Set the initial values for dateSpinner and timeSpinner
        dateSpinner.setValue(initialDate);
        timeSpinner.setValue(initialDate);

        // Event title
        titleField = new JTextField(20);
        titleField.setBorder(BorderFactory.createTitledBorder("Event Title"));
        if (isEdit) titleField.setText(event.getTitle());

        // Event description
        descriptionField = new JTextArea(5, 20);
        descriptionField.setBorder(BorderFactory.createTitledBorder("Event Description"));
        if (isEdit) descriptionField.setText(event.getDescription());

        // Date spinner
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setBorder(BorderFactory.createTitledBorder("Event Date"));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy/MM/dd");
        dateSpinner.setEditor(dateEditor);
        if (isEdit) dateSpinner.setValue(event.getDate());

        // Time spinner
        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setBorder(BorderFactory.createTitledBorder("Event Time"));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        if (isEdit) timeSpinner.setValue(event.getDate());

        // Save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveEvent);

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
        fieldsPanel.add(titleField);
        fieldsPanel.add(descriptionField);
        fieldsPanel.add(dateSpinner);
        fieldsPanel.add(timeSpinner);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveEvent(ActionEvent e) {
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

        Date combinedDateTime = calendar.getTime();

        if (isEdit) {
            event.setTitle(title);
            event.setDescription(description);
            event.setDate(combinedDateTime);
        } else {
            event = new CalendarEvent(title, combinedDateTime, description);
            calendarModel.getEventStorage().addEvent(event);
        }

        dispose();
    }
}
