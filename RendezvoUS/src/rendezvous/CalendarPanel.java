package rendezvous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

public class CalendarPanel extends JPanel {
    private CalendarModel calendarModel;
    private JTable calendarTable;
    private DefaultTableModel tableModel;
    private JLabel monthLabel; // Label for displaying the current month and year

    public CalendarPanel() {
        calendarModel = new CalendarModel();
        initializeUI();
        updateCalendar(); // Update the calendar for the first time
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Month and year label
        monthLabel = new JLabel("", JLabel.CENTER);
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(monthLabel, BorderLayout.NORTH);

        // Table model for the calendar
        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        calendarTable = new JTable(tableModel);
        calendarTable.setRowHeight(100); // Set a fixed row height

        add(new JScrollPane(calendarTable), BorderLayout.CENTER);

        // Navigation panel with buttons
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> {
            calendarModel.changeMonth(-1);
            updateCalendar();
        });
        controlPanel.add(prevButton);

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> {
            calendarModel.changeMonth(1);
            updateCalendar();
        });
        controlPanel.add(nextButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void updateCalendar() {
        Calendar cal = (Calendar) calendarModel.getCalendar().clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Adjust for Sunday start (Java Calendar starts on Sunday, index 1)
        int offset = firstDayOfWeek - 1;

        // Clear the existing data
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Set column headers for days of the week
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String header : headers) {
            tableModel.addColumn(header);
        }

        Vector<String> row = new Vector<>();
        // Fill the first row with blanks up to the first day of the week
        for (int i = 0; i < offset; i++) {
            row.add("");
        }

        // Fill the table with day numbers
        for (int i = 1; i <= daysInMonth; i++) {
            if (row.size() == 7) {
                tableModel.addRow(row);
                row = new Vector<>();
            }
            row.add(Integer.toString(i));
        }

        // Fill the remaining cells if the last row is not complete
        while (row.size() < 7) {
            row.add("");
        }
        tableModel.addRow(row);

        // Update the month label
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(sdf.format(cal.getTime()));
    }
}
