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

public class GroupViewScreen extends CalendarPanel{

	//Store usernames of all group members
    protected GroupCalendar groupCalendar;
	public final ArrayList<String> groupMembers = new ArrayList<>();

    public GroupViewScreen(UserAccount groupOwner) {
        super(groupOwner);
        this.groupCalendar = new GroupCalendar(groupOwner, this);
        groupMembers.add(groupOwner.getUsername());
        groupMembers.add("Member1");
        groupMembers.add("Member2");

    }
    @Override
    protected void displayEventsOnDay(Date selectedDate) {
        // Create a panel to display events and the "Add Event" button
        JPanel eventsOnDayPanel = new JPanel(new FlowLayout());
        SimpleDateFormat headerDateFormat = new SimpleDateFormat("MMM-dd-yyyy");
        String formattedHeaderDate = headerDateFormat.format(selectedDate);

        JFrame dayEventsFrame = new JFrame("Events on " + formattedHeaderDate);
        dayEventsFrame.setSize(300, 400);
        dayEventsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //display events for all group members
        for(int i = 0; i < groupCalendar.getAccountsInGroupSize(); i++) {
            List<CalendarEvent> events = groupCalendar.getEventStorage(groupCalendar.getAccountInGroup(i).
                    getUsername()).getEventsByDate(selectedDate);

            // Display existing events with buttons for each event
            for (CalendarEvent event : events) {
                String eventTime = new SimpleDateFormat("hh:mm aa").format(event.getDate());
                JButton eventButton = new JButton(event.getTitle() + " at " + eventTime);
                //Action Listener
                eventButton.addActionListener(e -> {
                    GroupEventDialog eventDialog = new GroupEventDialog(JFrame.getFrames()[0], true, event,
                            groupCalendar, event.getDate(), groupMembers);
                    eventDialog.setLocationRelativeTo(this);
                    eventDialog.setVisible(true);

                    // Refresh the calendar after editing an event
                    updateCalendar();
                    //close Events frame after editing event
                    dayEventsFrame.dispose();
                });
                eventsOnDayPanel.add(eventButton);

            }
        }
        dayEventsFrame.getContentPane().add(eventsOnDayPanel);
        dayEventsFrame.setLocationRelativeTo(this);
        dayEventsFrame.setVisible(true);
        // Create "Add Event" button
        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> {
            GroupEventDialog eventDialog = new GroupEventDialog(JFrame.getFrames()[0], true, null,
                    groupCalendar, selectedDate, groupMembers);
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


