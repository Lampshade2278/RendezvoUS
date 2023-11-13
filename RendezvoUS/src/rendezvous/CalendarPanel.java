package rendezvous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

// CalendarPanel class is responsible for displaying the calendar and handling its functionalities
public class CalendarPanel extends JPanel {
    // CalendarModel instance for managing calendar data
    private final CalendarModel calendarModel;
    // Label to display the current month and year
    private JLabel monthLabel;
    // Table to display the calendar
    private JTable calendarTable;

    // Constructor
    public CalendarPanel() {
        EventStorage eventStorage = new EventStorage(); // Event storage for handling events
        this.calendarModel = new CalendarModel(eventStorage, this); // Initialize CalendarModel with EventStorage
        initializeUI(); // Initialize user interface components
        updateCalendar(); // Update calendar display
    }

    // Initialize UI components
    private void initializeUI() {
        setLayout(new BorderLayout());

        // Month label initialization
        monthLabel = new JLabel("", JLabel.CENTER);
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(monthLabel, BorderLayout.NORTH);

        // Table model for the calendar
        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        calendarTable = new JTable(tableModel);
        calendarTable.setRowHeight(120); // Set row height
        calendarTable.addMouseListener(new CalendarTableMouseListener()); // Add mouse listener for table clicks
        add(new JScrollPane(calendarTable), BorderLayout.CENTER); // Add table with scroll pane

        setupNavigationButtons(); // Setup navigation buttons
    }

    // Setup navigation buttons
    private void setupNavigationButtons() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Button to go to the previous month
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> changeMonth(-1));
        controlPanel.add(prevButton);

        // Button to return to today's date
        JButton todayButton = new JButton("Today");
        todayButton.addActionListener(e -> goToday());
        controlPanel.add(todayButton);

        // Button to go to the next month
        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> changeMonth(1));
        controlPanel.add(nextButton);

        add(controlPanel, BorderLayout.SOUTH); // Add control panel to the south of BorderLayout
    }

    // Set calendar to today's date and update display
    private void goToday() {
        calendarModel.getCalendar().setTime(new Date());
        updateCalendar();
    }

    // Change the month by a given amount and update the display
    private void changeMonth(int amount) {
        calendarModel.getCalendar().add(Calendar.MONTH, amount);
        updateCalendar();
    }

    // Update the calendar display
    public void updateCalendar() {
        Calendar cal = (Calendar) calendarModel.getCalendar().clone();
        cal.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // Determine the first day of the week
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // Get the number of days in the month

        DefaultTableModel tableModel = (DefaultTableModel) calendarTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows
        tableModel.setColumnCount(7); // Set to 7 columns (days of the week)

        String[] headers = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        tableModel.setColumnIdentifiers(headers); // Set column headers

        int offset = firstDayOfWeek - Calendar.SUNDAY; // Calculate offset for first day of the month
        Vector<String> row = new Vector<>(); // Vector to hold a row of data

        // Fill initial empty cells
        for (int i = 0; i < offset; i++) {
            row.add("");
        }

        // Fill cells with days of the month and any event summaries
        for (int i = 1; i <= daysInMonth; i++) {
            row.add(Integer.toString(i) + getEventSummary(cal.getTime())); // Add day and event summary
            if (row.size() == 7) { // End of the week, add row to table
                tableModel.addRow(row);
                row = new Vector<>();
            }
            cal.add(Calendar.DAY_OF_MONTH, 1); // Move to the next day
        }

        // Fill remaining cells if the last week is not complete
        while (row.size() < 7) {
            row.add("");
        }
        if (!row.isEmpty()) {
            tableModel.addRow(row);
        }

        // Update the month and year label
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthYearFormat.format(calendarModel.getCalendar().getTime()));
    }

    // Get a summary of events for a given date
    private String getEventSummary(Date date) {
        List<CalendarEvent> events = calendarModel.getEventStorage().getEventsByDate(date);
        if (events.isEmpty()) {
            return ""; // Return empty string if no events
        }

        // Build a summary of events
        StringBuilder eventSummary = new StringBuilder();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        for (CalendarEvent event : events) {
            String eventName = event.getTitle();
            String eventTime = timeFormat.format(event.getDate());
            eventSummary.append(eventName).append(" at ").append(eventTime).append(", ");
        }

        // Remove the last comma and space
        if (eventSummary.length() > 0) {
            eventSummary.setLength(eventSummary.length() - 2);
        }

        return " (" + eventSummary.toString() + ")"; // Return event summary
    }

    // MouseAdapter class to handle mouse clicks on the calendar
    private class CalendarTableMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            int row = calendarTable.rowAtPoint(e.getPoint());
            int column = calendarTable.columnAtPoint(e.getPoint());
            if (row >= 0 && column >= 0) {
                handleDayClick(row, column); // Handle day click
            }
        }
    }

    // Handle clicks on specific days in the calendar
    private void handleDayClick(int row, int column) {
        Calendar cal = (Calendar) calendarModel.getCalendar().clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int day = (row * 7 + column) - offset + 1; // Calculate day number

        if (day > 0 && day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            cal.set(Calendar.DAY_OF_MONTH, day);
            Date selectedDate = cal.getTime();

            // Check if there are events on this day
            List<CalendarEvent> events = calendarModel.getEventStorage().getEventsByDate(selectedDate);
            if (!events.isEmpty()) {
                // Edit the first event of the day
                CalendarEvent firstEvent = events.get(0);
                EventDialog eventDialog = new EventDialog(JFrame.getFrames()[0], true, firstEvent, calendarModel, selectedDate);
                eventDialog.setLocationRelativeTo(this);
                eventDialog.setVisible(true);
            } else {
                // No events, open dialog to add a new event
                EventDialog eventDialog = new EventDialog(JFrame.getFrames()[0], true, null, calendarModel, selectedDate);
                eventDialog.setLocationRelativeTo(this);
                eventDialog.setVisible(true);
            }

            updateCalendar(); // Refresh the calendar view after closing the dialog
        }
    }

    // Open a dialog for a specific date to add an event
    private void openEventDialogForDate(Date date) {
        EventDialog eventDialog = new EventDialog(JFrame.getFrames()[0], true, null, calendarModel, date);
        eventDialog.setLocationRelativeTo(this);
        eventDialog.setVisible(true);
        updateCalendar(); // Refresh the calendar view
    }

    // Getter for CalendarModel
    public CalendarModel getCalendarModel() {
        return calendarModel;
    }

    // Refresh the calendar display
    public void refreshCalendar() {
        updateCalendar();
    }
}
