package rendezvous;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GroupViewScreen extends CalendarPanel {

	//Store usernames of all group members
	public final ArrayList<String> groupMembers = new ArrayList<String>();

    public GroupViewScreen(UserAccount groupOwner) {
        super(groupOwner);
        groupMembers.add(groupOwner.getUsername());
        groupMembers.add("Member1");
        groupMembers.add("Member2");

        /*
        // Set the layout for this panel
        this.setLayout(new BorderLayout());

        // Main Panel with two Calendars
        JPanel calendarsPanel = new JPanel(new GridLayout(1, 2));

        // First Calendar Panel
        JPanel yourCalendarPanel = new JPanel(new BorderLayout());
        yourCalendarPanel.add(new JLabel("Your Calendar"), BorderLayout.NORTH);
        // Add calendar component here
        calendarsPanel.add(yourCalendarPanel);

        // Second Calendar Panel
        JPanel groupCalendarPanel = new JPanel(new BorderLayout());
        groupCalendarPanel.add(new JLabel("Group Calendar"), BorderLayout.NORTH);
        // Add calendar component here
        calendarsPanel.add(groupCalendarPanel);

        // Add calendars panel to the main panel
        this.add(calendarsPanel, BorderLayout.CENTER);

         */
    }

    @Override
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
            JButton eventButton = new JButton(event.getTitle() + " at " + eventTime);
            //Action Listener
            eventButton.addActionListener(e -> {
                GroupEventDialog eventDialog = new GroupEventDialog(JFrame.getFrames()[0], true, event,
                        calendarModel, event.getDate(), groupMembers);
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
            GroupEventDialog eventDialog = new GroupEventDialog(JFrame.getFrames()[0], true, null,
                    calendarModel, selectedDate, groupMembers);
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
}


