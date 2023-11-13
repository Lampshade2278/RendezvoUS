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
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public CalendarPanel() {
        EventStorage eventStorage = new EventStorage();
        this.calendarModel = new CalendarModel(eventStorage);
        initializeUI();
        updateCalendar();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Month label
        monthLabel = new JLabel("", JLabel.CENTER);
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(monthLabel, BorderLayout.NORTH);

        // Calendar table
        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable = new JTable(tableModel);
        calendarTable.setRowHeight(100);
        calendarTable.addMouseListener(new CalendarTableMouseListener());
        add(new JScrollPane(calendarTable), BorderLayout.CENTER);

        // Navigation buttons
        setupNavigationButtons();
    }

    private void setupNavigationButtons() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Navigation Buttons
        JButton todayButton = new JButton("Today");
        todayButton.addActionListener(e -> goToday());
        controlPanel.add(todayButton);

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> changeMonth(-1));
        controlPanel.add(prevButton);

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> changeMonth(1));
        controlPanel.add(nextButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void goToday() {
        calendarModel.getCalendar().setTime(new Date()); // Set to current date
        updateCalendar();
    }

    private void changeMonth(int amount) {
        calendarModel.getCalendar().add(Calendar.MONTH, amount);
        updateCalendar();
    }

    public void updateCalendar() {
        Calendar cal = (Calendar) calendarModel.getCalendar().clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        DefaultTableModel tableModel = (DefaultTableModel) calendarTable.getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String header : headers) {
            tableModel.addColumn(header);
        }

        int offset = firstDayOfWeek - 1;
        Vector<String> row = new Vector<>();
        for (int i = 0; i < offset; i++) {
            row.add("");
        }

        for (int i = 1; i <= daysInMonth; i++) {
            row.add(i + getEventSummary(cal.getTime()));
            if (row.size() == 7) {
                tableModel.addRow(row);
                row = new Vector<>();
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        while (row.size() < 7) {
            row.add("");
        }
        tableModel.addRow(row);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(sdf.format(calendarModel.getCalendar().getTime()));
    }

    private String getEventSummary(Date date) {
        List<CalendarEvent> events = calendarModel.getEventStorage().getEventsByDate(date);
        if (!events.isEmpty()) {
            return " (" + events.size() + " events)";
        }
        return "";
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
            openEventDialogForDate(selectedDate);
        }
    }

    private void openEventDialogForDate(Date date) {
        EventDialog eventDialog = new EventDialog(JFrame.getFrames()[0], true, null, calendarModel, date);
        eventDialog.setLocationRelativeTo(this);
        eventDialog.setVisible(true);

        updateCalendar(); // Refresh the calendar
    }

    public CalendarModel getCalendarModel() {
        return calendarModel;
    }
}