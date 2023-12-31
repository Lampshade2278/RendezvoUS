package rendezvous;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class CalendarPanel extends JPanel {
    protected final CalendarModel calendarModel;
    protected JLabel monthLabel;
    protected JTable calendarTable;
    public Image backgroundImage;

    // Assume that this username is the currently logged-in user's username
    protected String username = "current_user"; // Replace with the actual username

    public CalendarPanel(UserAccount userAccount) {
        this.calendarModel = new CalendarModel(userAccount, this);
        backgroundImage = new ImageIcon("RendezvoUS/bin/resources/LOGO21.png").getImage();
        initializeUI();
        updateCalendar();
    }

    protected void initializeUI() {
        setLayout(new BorderLayout());

        monthLabel = new JLabel("", JLabel.CENTER);
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(monthLabel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        calendarTable = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(getBackground());
                    int modelRow = convertRowIndexToModel(row);
                    String type = (String) getModel().getValueAt(modelRow, column);
                    if (ThemeManager.getCurrentTheme() == ThemeManager.Theme.DARK) {
                        c.setForeground(Color.WHITE); // White text for dark theme
                    } else {
                        c.setForeground(Color.BLACK); // Black text for light theme
                    }
                }
                return c;
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new CalendarCellRenderer();
            }

        };
        //Disables column reordering
        calendarTable.getTableHeader().setReorderingAllowed(false);

        calendarTable.setRowHeight(120);
        calendarTable.setOpaque(false);
        calendarTable.addMouseListener(new CalendarTableMouseListener());

        JScrollPane scrollPane = new JScrollPane(calendarTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        setupNavigationButtons();
    }

    protected void setupNavigationButtons() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> changeMonth(-1));
        controlPanel.add(prevButton);

        JButton todayButton = new JButton("Current Month");
        todayButton.addActionListener(e -> goToday());
        controlPanel.add(todayButton);

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> changeMonth(1));
        controlPanel.add(nextButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw the background image
        int x = (this.getWidth() - backgroundImage.getWidth(null)) / 2;
        int y = (this.getHeight() - backgroundImage.getHeight(null)) / 2;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, x, y, this);
        }

        // Draw a semi-transparent overlay
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 0.5f for 50% transparency
        g2d.setColor(getBackground()); // Use the panel's background color
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    protected void goToday() {
        calendarModel.getCalendar().setTime(new Date());
        updateCalendar();
    }

    protected void changeMonth(int amount) {
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
        tableModel.setColumnCount(7);

        String[] headers = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        tableModel.setColumnIdentifiers(headers);

        int offset = firstDayOfWeek - Calendar.SUNDAY;
        Vector<String> row = new Vector<>();

        for (int i = 0; i < offset; i++) {
            row.add("");
        }

        for (int i = 1; i <= daysInMonth; i++) {
            row.add(Integer.toString(i) + getEventSummary(cal.getTime()));
            if (row.size() == 7) {
                tableModel.addRow(row);
                row = new Vector<>();
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        while (row.size() < 7) {
            row.add("");
        }
        if (!row.isEmpty()) {
            tableModel.addRow(row);
        }

        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
        monthLabel.setText(monthYearFormat.format(calendarModel.getCalendar().getTime()));
    }

    protected String getEventSummary(Date date) {
        List<CalendarEvent> events = calendarModel.getEventStorage().getEventsByDate(date);
        if (events.isEmpty()) {
            return "";
        }

        StringBuilder eventSummary = new StringBuilder();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        for (CalendarEvent event : events) {
            eventSummary.append(event.getTitle()).append(" at ").append(timeFormat.format(event.getDate())).append(", ");
        }

        if (!eventSummary.isEmpty()) {
            eventSummary.setLength(eventSummary.length() - 2);
        }

        return " " + eventSummary + " ";
    }

    protected class CalendarTableMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            int row = calendarTable.rowAtPoint(e.getPoint());
            int column = calendarTable.columnAtPoint(e.getPoint());
            if (row >= 0 && column >= 0) {
                handleDayClick(row, column);
            }
        }
    }

    protected void handleDayClick(int row, int column) {
        Calendar cal = (Calendar) calendarModel.getCalendar().clone();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int day = (row * 7 + column) - offset + 1;

        if (day > 0 && day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            cal.set(Calendar.DAY_OF_MONTH, day);
            Date selectedDate = cal.getTime();

            displayEventsOnDay(selectedDate);

        }
    }

    protected void displayEventsOnDay(Date selectedDate) {

        SimpleDateFormat headerDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
        String formattedHeaderDate = headerDateFormat.format(selectedDate);

        JFrame dayEventsFrame = new JFrame("Events on " + formattedHeaderDate);
        List<CalendarEvent> events = calendarModel.getEventStorage().getEventsByDate(selectedDate);

        dayEventsFrame.setSize(300, 400);
        dayEventsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Create a panel to display events and the "Add Event" button
        JPanel eventsOnDayPanel = new JPanel(new FlowLayout());

        // Display existing events with buttons for each event
        for (CalendarEvent event : events) {
            String eventTime = new SimpleDateFormat("hh:mm aa").format(event.getDate());
            JButton eventButton = new JButton(event.getTitle() + " at " + eventTime + " for " +
                                                ((float)event.getDurationMinutes()/60) + " hrs");
            //Action Listener
            eventButton.addActionListener(e -> {
            	//Event dialog now uses the stored date and time instead of the current time
                EventDialog eventDialog = new EventDialog(JFrame.getFrames()[0], true, event, calendarModel, event.getDate());
                eventDialog.setLocationRelativeTo(this);
                eventDialog.setVisible(true);

                // Refresh the calendar after editing an event
                updateCalendar();
                //close Events frame after editing event
                dayEventsFrame.dispose();
            });
            eventsOnDayPanel.add(eventButton);

        }
        dayEventsFrame.getContentPane().add(eventsOnDayPanel);
        dayEventsFrame.setLocationRelativeTo(this);
        dayEventsFrame.setVisible(true);
        // Create "Add Event" button
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> {
            EventDialog eventDialog = new EventDialog(JFrame.getFrames()[0], true, null, calendarModel, selectedDate);
            eventDialog.setLocationRelativeTo(this);
            eventDialog.setVisible(true);

            // Refresh the calendar after adding an event
            updateCalendar();
            //close Events frame after adding event
            dayEventsFrame.dispose();

        });
        JPanel addButtonPanel = new JPanel(new BorderLayout());
        addButtonPanel.add(addEventButton, BorderLayout.SOUTH);
        dayEventsFrame.add(addButtonPanel, BorderLayout.SOUTH);
    }

    public void refreshCalendar() {
        updateCalendar();
    }
    protected class CalendarCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(JLabel.LEFT);
            setVerticalAlignment(JLabel.TOP);
            setOpaque(false);
            return this;
        }
    }
}