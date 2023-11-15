package rendezvous;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class EventDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionField;
    private JSpinner dateSpinner;
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
        setSize(300, 400);
        int marginSize = 10;
        Border margin = new EmptyBorder(marginSize, marginSize, marginSize, marginSize);

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
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 1, marginSize, marginSize));
        fieldsPanel.setBorder(margin);
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
        spinner.setValue(value);

        // Set the titled border with the label "Event Date"
        spinner.setBorder(BorderFactory.createTitledBorder(title));

        // Align the text to the left
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) spinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

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

        if (isEdit) {
            // Update the event
            calendarModel.getEventStorage().updateEvent(event, date);
        } else {
            // Create a new event
            event = new CalendarEvent(title, date, description);
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