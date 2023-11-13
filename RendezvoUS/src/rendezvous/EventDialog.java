package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    public EventDialog(Frame owner, boolean modal, CalendarEvent event, CalendarModel calendarModel, Date eventDate) {
        super(owner, modal);
        this.event = event;
        this.isEdit = (event != null);
        this.calendarModel = calendarModel;

        initializeUI(eventDate);
    }

    private void initializeUI(Date eventDate) {
        setTitle(isEdit ? "Edit Event" : "Add Event");
        setLayout(new BorderLayout());
        setSize(400, 300);

        titleField = new JTextField(20);
        titleField.setBorder(BorderFactory.createTitledBorder("Event Title"));
        if (isEdit) titleField.setText(event.getTitle());

        descriptionField = new JTextArea(5, 20);
        descriptionField.setBorder(BorderFactory.createTitledBorder("Event Description"));
        if (isEdit) descriptionField.setText(event.getDescription());

        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setBorder(BorderFactory.createTitledBorder("Event Date"));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy/MM/dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(eventDate);

        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setBorder(BorderFactory.createTitledBorder("Event Time"));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(eventDate);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this::saveEvent);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteEvent);
        deleteButton.setVisible(isEdit); // Show delete button only when editing

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
        String title = titleField.getText();
        String description = descriptionField.getText();
        Date date = (Date) dateSpinner.getValue();
        Date time = (Date) timeSpinner.getValue();

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
            calendarModel.getEventStorage().updateEvent(event);
        } else {
            event = new CalendarEvent(title, combinedDateTime, description);
            calendarModel.getEventStorage().addEvent(event);
        }

        dispose();
    }

    private void deleteEvent(ActionEvent e) {
        if (isEdit) {
            calendarModel.getEventStorage().removeEvent(event);
        }
        dispose();
    }
}
