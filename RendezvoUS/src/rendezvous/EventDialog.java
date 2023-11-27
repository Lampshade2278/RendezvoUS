package rendezvous;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class EventDialog extends JDialog {
    protected JTextField titleField;
    protected JTextArea descriptionField;
    protected JSpinner dateSpinner;
    protected JComboBox<Integer> durationHoursComboBox;
    protected JComboBox<Integer> durationMinutesComboBox;
    protected JButton saveButton;
    protected JButton cancelButton;
    protected JButton deleteButton;
    protected CalendarEvent event;
    protected final boolean isEdit;
    protected final CalendarModel calendarModel;
    protected Date eventDate;

    public EventDialog(Frame owner, boolean modal, CalendarEvent event, CalendarModel calendarModel, Date eventDate) {
        super(owner, modal);
        this.event = event;
        this.isEdit = (event != null);
        this.calendarModel = calendarModel;
        this.eventDate = eventDate;
        initializeUI();
    }

    protected void initializeUI() {
        setTitle(isEdit ? "Edit Event" : "Add Event");
        setLayout(new BorderLayout());
        setSize(300, 550);
        int marginSize = 10;
        Border margin = new EmptyBorder(marginSize, marginSize, marginSize, marginSize);

        // Initialize UI components
        titleField = createTextField("Event Title", isEdit ? event.getTitle() : "");
        descriptionField = createTextArea("Event Description", isEdit ? event.getDescription() : "");
        dateSpinner = createSpinner("Event Date and Time", eventDate);

        durationHoursComboBox = createComboBox("Hours", 0, 23);
        durationMinutesComboBox = createComboBox("Minutes", 0, 30, 30);

        if(isEdit){
            durationHoursComboBox.setSelectedItem(event.getDurationMinutes()/60);
            durationMinutesComboBox.setSelectedItem(event.getDurationMinutes()%60);
        }
        JComboBox<RecurrenceManager.Recurrence> recurrenceComboBox = new JComboBox<>(RecurrenceManager.Recurrence.values());

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

        JPanel durationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        durationPanel.setBorder(BorderFactory.createTitledBorder("Duration:"));
        durationPanel.add(durationHoursComboBox);
        durationPanel.add(durationMinutesComboBox);
        fieldsPanel.add(durationPanel);

        JPanel recurrencePanel = new JPanel(new GridLayout(0,1));
        recurrencePanel.setBorder(BorderFactory.createTitledBorder("Is Event Recurring?"));
        recurrencePanel.add(recurrenceComboBox);
        fieldsPanel.add(recurrencePanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        applyCurrentTheme();
    }

    protected JTextField createTextField(String title, String text) {
        JTextField textField = new JTextField(20);
        textField.setBorder(BorderFactory.createTitledBorder(title));
        textField.setText(text);
        return textField;
    }

    protected JTextArea createTextArea(String title, String text) {
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setBorder(BorderFactory.createTitledBorder(title));
        textArea.setText(text);
        return textArea;
    }

    protected JSpinner createSpinner(String title, Date value) {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        spinner.setValue(value);

        // Set the titled border with the label "Event Date"
        spinner.setBorder(BorderFactory.createTitledBorder(title));

        // Align the text to the left
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor) spinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        return spinner;
    }

    protected JComboBox<Integer> createComboBox(String label, int minValue, int maxValue) {
        Integer[] values = new Integer[maxValue - minValue + 1];
        for (int i = 0; i <= maxValue - minValue; i++) {
            values[i] = minValue + i;
        }
        JComboBox<Integer> comboBox = new JComboBox<>(values);
        comboBox.setBorder(BorderFactory.createTitledBorder(label));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(40, 25);
            }
        });

        return comboBox;
    }
    protected JComboBox<Integer> createComboBox(String label, int minValue, int maxValue, int step) {
        Integer[] values = new Integer[(maxValue - minValue) / step + 1];
        for (int i = 0; i <= (maxValue - minValue) / step; i++) {
            values[i] = minValue + i * step;
        }
        JComboBox<Integer> comboBox = new JComboBox<>(values);
        comboBox.setBorder(BorderFactory.createTitledBorder(label));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(40, 25);
            }
        });

        return comboBox;
    }

    protected JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    protected void saveEvent(ActionEvent e) {
        String title = titleField.getText();
        String description = descriptionField.getText();
        Date date = (Date) dateSpinner.getValue();

        int durationHours = 0;
        int durationMinutes = 0;
        int duration = 0;

        durationHours = (int) durationHoursComboBox.getSelectedItem();
        durationMinutes = (int) durationMinutesComboBox.getSelectedItem();
        duration = durationMinutes + durationHours * 60;

        if (isEdit) {
            // Create a new event instance with updated details
            CalendarEvent updatedEvent = new CalendarEvent(title, date, description, duration);

            // Pass both the original and the updated event to the updateEvent method
            calendarModel.getEventStorage().updateEvent(this.event, updatedEvent);
        } else {
            // Create a new event
            CalendarEvent newEvent = new CalendarEvent(title, date, description, duration);
            calendarModel.getEventStorage().addEvent(newEvent);
        }

        dispose(); // Close the dialog
    }


    protected void deleteEvent(ActionEvent e) {
        if (isEdit) {
            calendarModel.getEventStorage().removeEvent(event);
        }
        dispose(); // Close the dialog
    }

    protected void applyCurrentTheme() {
        ThemeManager.Theme currentTheme = ThemeManager.getCurrentTheme();
        ThemeManager.applyTheme(titleField, currentTheme);
        ThemeManager.applyTheme(descriptionField, currentTheme);
        ThemeManager.applyTheme(dateSpinner, currentTheme);
        ThemeManager.applyTheme(saveButton, currentTheme);
        ThemeManager.applyTheme(cancelButton, currentTheme);
        ThemeManager.applyTheme(deleteButton, currentTheme);
    }
}