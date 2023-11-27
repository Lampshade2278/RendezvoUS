package rendezvous;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GroupEventDialog extends EventDialog{
	
	//How precise the conflict search will be
	
	private int TIME_CHUNK = 30; //How many minutes per chunk of time
	private int HOURLY_CHUNKS = 60 / TIME_CHUNK; //Number of chunks per hour
	private int TOTAL_CHUNKS = 24 * HOURLY_CHUNKS; //Total number of chunks per day (Array size)
			
	private boolean[] busyTimes; // Stores the overlapping busy times
	private JTextArea conflictWarning; // UI component to give conflict feedback
	
	//Store usernames of all group members
	public ArrayList<String> groupMembers;
	
	// Constructor
	public GroupEventDialog(Frame owner, boolean modal, CalendarEvent event, CalendarModel calendarModel, Date eventDate, ArrayList<String> members) {
		
		//Call parent constructor
		super(owner, modal, event, calendarModel, eventDate);
		
		// Initialize time table array
    	busyTimes = new boolean[TOTAL_CHUNKS];
    	
    	//Copy group member list
    	this.groupMembers = new ArrayList<String>();
        this.groupMembers = new ArrayList<String>(members);
        
        // Calculate overlap
        calculateBusyTimes();
        
        //Check for conflicts
        if(checkBusy()) {
			conflictWarning.setVisible(true);
			saveButton.setEnabled(false);

        }
        
    }
	
	@Override
	protected void initializeUI() {
        setTitle(isEdit ? "Edit Event" : "Add Event");
        setLayout(new BorderLayout());
        setSize(300, 400);
        int marginSize = 10;
        Border margin = new EmptyBorder(marginSize, marginSize, marginSize, marginSize);

        // Initialize UI components
        titleField = createTextField("Event Title", isEdit ? event.getTitle() : "");
        descriptionField = createTextArea("Event Description", isEdit ? event.getDescription() : "");
        dateSpinner = createSpinner("Event Date and Time", eventDate);
        
        //Initialize Conflict feedback text
        conflictWarning = new JTextArea("Selected time conflicts with one or more group members", 2, 12);
        conflictWarning.setLineWrap(true);
        conflictWarning.setWrapStyleWord(true);
        conflictWarning.setVisible(false);
        conflictWarning.setEditable(false);

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
        fieldsPanel.add(conflictWarning);
        fieldsPanel.add(new JLabel("Is this Recurring?"));
        fieldsPanel.add(RecurrenceComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        applyCurrentTheme();
        
        //Check for conflicts and update Conflict Warning text
        dateSpinner.addChangeListener(new ChangeListener() {      
            @Override
            	  public void stateChanged(ChangeEvent e){
            		eventDate = (Date)dateSpinner.getValue();
            		calculateBusyTimes();
            			if(checkBusy()) {
            					conflictWarning.setVisible(true);
            					saveButton.setEnabled(false);
                	
            			}
            			else {
            				conflictWarning.setVisible(false);
        					saveButton.setEnabled(true);
            			}
            				
            				
            	  }
            	});
        
    }
	
	@Override
	protected void applyCurrentTheme() {
        ThemeManager.Theme currentTheme = ThemeManager.getCurrentTheme();
        ThemeManager.applyTheme(titleField, currentTheme);
        ThemeManager.applyTheme(descriptionField, currentTheme);
        ThemeManager.applyTheme(dateSpinner, currentTheme);
        ThemeManager.applyTheme(saveButton, currentTheme);
        ThemeManager.applyTheme(cancelButton, currentTheme);
        ThemeManager.applyTheme(deleteButton, currentTheme);
        ThemeManager.applyTheme(conflictWarning, currentTheme);
        conflictWarning.setForeground(Color.red);
    }
	
	public void calculateBusyTimes(){
		
		//Initialize all elements to false (free)
		for(int i = 0; i < TOTAL_CHUNKS; i++) {
			busyTimes[i] = false;
		}
		
		String user = new String(); // Username
		EventStorage eventStore; //Event Storage object
		ArrayList<CalendarEvent> eventList; //List of events in a day
		
		Calendar eventTime = Calendar.getInstance(); //Store the time of an event
		int hour;
		int minute;
		double index; //The element in the timetable to be edited
		
		
		for(int i = 0; i < groupMembers.size(); i++) {
			//Get the info of user i
			user = groupMembers.get(i);
			eventStore = new EventStorage(user);
			eventList = new ArrayList<CalendarEvent>(eventStore.getEventsByDate(eventDate));
			eventList.trimToSize();
			
			for(int j = 0; j < eventList.size(); j++) {
					
				//Exclude an event being edited
				if(!eventList.get(j).equals(this.event)) {
					//Get event time
					eventTime.setTime(eventList.get(j).getDate());
					hour = eventTime.get(Calendar.HOUR_OF_DAY);
					minute = eventTime.get(Calendar.MINUTE);
					
					//Get index of the block that contains the event
					//Hourly Chunks * (hour + minute(in decimal form)) 
					index = HOURLY_CHUNKS * (hour + (double)minute / 60);
					
					//Mark a whole hour as busy, starting with the chunk that contains the event
					for(int k = 0; k < HOURLY_CHUNKS; k++) {
						if((int)index + k < TOTAL_CHUNKS) {
							busyTimes[(int)index + k] = true;
						}
					}
				}
			}
		}
	}
	
	protected boolean checkBusy() {
		
		//Get the times for the current event
		Calendar eventTime = Calendar.getInstance();
		eventTime.setTime(eventDate);
		int hour = eventTime.get(Calendar.HOUR_OF_DAY);
		int minute = eventTime.get(Calendar.MINUTE);
		
		//Find its index on the time table
		//Hourly Chunks * (hour + minute(in decimal form)) 
		double index = HOURLY_CHUNKS * (hour + (double)minute / 60);
		
		//Return if busy or not
		return busyTimes[(int)index];
		
	}
}
