package rendezvous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class CalendarPanel extends JPanel {
    private CalendarModel calendarModel;
    private JTable calendarTable;
    private DefaultTableModel tableModel;

    public CalendarPanel() {
        calendarModel = new CalendarModel();
        initializeUI();
        updateCalendar();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Table model for the calendar
        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        calendarTable = new JTable(tableModel);
        add(new JScrollPane(calendarTable), BorderLayout.CENTER);

        // Navigation controls
        JPanel controlPanel = new JPanel();
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");

        prevButton.addActionListener(e -> {
            calendarModel.changeMonth(-1);
            updateCalendar();
        });
        nextButton.addActionListener(e -> {
            calendarModel.changeMonth(1);
            updateCalendar();
        });

        controlPanel.add(prevButton);
        controlPanel.add(nextButton);
        add(controlPanel, BorderLayout.NORTH);
    }

    public void updateCalendar() {
        int year = calendarModel.getYear();
        int month = calendarModel.getMonth();
        int numberOfDays = calendarModel.getNumberOfDaysInMonth();
        int startDayOfMonth = calendarModel.getFirstDayOfWeekInMonth();

        // Clear the existing data
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        // Set column headers for days of the week
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String header : headers) {
            tableModel.addColumn(header);
        }

        Vector<String> row = new Vector<>();
        int currentDay = 1;

        // Fill the first row with initial blanks
        for (int i = 1; i < startDayOfMonth; i++) {
            row.add("");
        }

        // Fill the table with day numbers
        while (currentDay <= numberOfDays) {
            if (row.size() == 7) {
                tableModel.addRow(row);
                row = new Vector<>();
            }
            row.add(Integer.toString(currentDay));
            currentDay++;
        }

        // Fill the remaining cells if the last row is not complete
        while (row.size() < 7) {
            row.add("");
        }
        tableModel.addRow(row);
    }
}
