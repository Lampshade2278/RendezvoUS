package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class EventDialog extends JDialog {
    private JTextField titleField;
    private JTextField descriptionField;
    private JButton saveButton;
    private JButton cancelButton;
    private CalendarEvent event;
    private boolean isEdit;
    private Date eventDate; // Date for the event

    private CalendarModel calendarModel; // Add a CalendarModel field

    // Constructor now takes CalendarModel as an argument
    public EventDialog(Frame owner, boolean modal, CalendarEvent event, CalendarModel calendarModel, Date eventDate) {
        super(owner, modal);
        this.event = event;
        this.isEdit = (event != null);
        this.calendarModel = calendarModel; // Assign the passed CalendarModel

        initializeUI();
    }

    private void initializeUI() {
        setTitle(isEdit ? "Edit Event" : "Add Event");
        setLayout(new BorderLayout());
        setSize(300, 200);

        // Event title
        titleField = new JTextField(20);
        titleField.setBorder(BorderFactory.createTitledBorder("Event Title"));
        if (isEdit) titleField.setText(event.getTitle());

        // Event description
        descriptionField = new JTextField(20);
        descriptionField.setBorder(BorderFactory.createTitledBorder("Event Description"));
        if (isEdit) descriptionField.setText(event.getDescription());

        // Save button
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveEvent();
            }
        });

        // Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(titleField, BorderLayout.NORTH);
        add(descriptionField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveEvent() {
        String title = titleField.getText();
        String description = descriptionField.getText();

        if (isEdit) {
            event.setTitle(title);
            event.setDescription(description);
        } else {
            event = new CalendarEvent(title, new Date(), description);
            calendarModel.getEventStorage().addEvent(event); // Use the calendarModel directly
        }

        if (!isEdit) {
            // If not editing, create a new event with the selected date
            event = new CalendarEvent(title, eventDate, description);
            calendarModel.getEventStorage().addEvent(event);
        }

        dispose();
    }
}
