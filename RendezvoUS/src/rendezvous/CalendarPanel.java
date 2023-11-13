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

public class CalendarPanel extends JPanel {
    private final CalendarModel calendarModel;
    private JLabel monthLabel;
    private JTable calendarTable;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public CalendarPanel() {
        EventStorage eventStorage = new EventStorage();
        this.calendarModel = new CalendarModel(eventStorage);
        // Set calendar to current date
        this.calendarModel.getCalendar().setTime(new Date());
        initializeUI();
        updateCalendar();
    }


    private void initializeUI() {
        setLayout(new BorderLayout());

        monthLabel = new JLabel("", JLabel.CENTER);
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(monthLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable = new JTable(tableModel);
        calendarTable.setRowHeight(120);
        calendarTable.addMouseListener(new CalendarTableMouseListener());
        add(new JScrollPane(calendarTable), BorderLayout.CENTER);

        setupNavigationButtons();
    }

    private void setupNavigationButtons() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> changeMonth(-1));
        controlPanel.add(prevButton);

        JButton todayButton = new JButton("Today");
        todayButton.addActionListener(e -> goToday());
        controlPanel.add(todayButton);

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> changeMonth(1));
        controlPanel.add(nextButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void goToday() {
        calendarModel.getCalendar().setTime(new Date());
        updateCalendar();
    }

    private void changeMonth(int amount) {
        calendarModel.getCalendar().add(Calendar.MONTH, amount);
        updateCalendar();
    }

    public void updateCalendar() {
        Calendar cal = (Calendar) calendarModel.getCalendar().clone();
        cal.set(Calendar.DAY_OF_MONTH, 1); // Set to the first day of the month
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // Day of the week for the 1st of the month
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // Number of days in the month

        DefaultTableModel tableModel = (DefaultTableModel) calendarTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows
        tableModel.setColumnCount(7); // 7 days in a week

        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        tableModel.setColumnIdentifiers(headers); // Set column headers

        int offset = firstDayOfWeek - Calendar.SUNDAY;
        Vector<String> row = new Vector<>();
        // Fill initial empty cells
        for (int i = 0; i < offset; i++) {
            row.add("");
        }

        // Fill cells with days of the month
        for (int i = 1; i <= daysInMonth; i++) {
            row.add(Integer.toString(i) + getEventSummary(cal.getTime()));
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

        // Update month and year label
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthYearFormat.format(calendarModel.getCalendar().getTime()));
    }



    private String getEventSummary(Date date) {
        List<CalendarEvent> events = calendarModel.getEventStorage().getEventsByDate(date);
        if (events.isEmpty()) {
            return "";
        }

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

        return " (" + eventSummary.toString() + ")";
    }

    private class CalendarTableMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            int row = calendarTable.rowAtPoint(e.getPoint());
            int column = calendarTable.columnAtPoint(e.getPoint());
            if (row >= 0 && column >= 0) {
                handleDayClick(row, column);
            }
        }
    }

    private void handleDayClick(int row, int column) {
        Calendar cal = (Calendar) calendarModel.getCalendar().clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int day = (row * 7 + column) - offset + 1;

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


    private void openEventDialogForDate(Date date) {
        EventDialog eventDialog = new EventDialog(JFrame.getFrames()[0], true, null, calendarModel, date);
        eventDialog.setLocationRelativeTo(this);
        eventDialog.setVisible(true);
        updateCalendar(); // Refresh the calendar view
    }

    public CalendarModel getCalendarModel() {
        return calendarModel;
    }
}
