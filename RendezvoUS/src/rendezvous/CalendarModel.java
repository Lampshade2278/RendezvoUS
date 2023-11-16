package rendezvous;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel {
    private Calendar calendar;
    private UserAccount userAccount; // Reference to the user's account
    private CalendarPanel calendarPanel;

    public CalendarModel(UserAccount userAccount, CalendarPanel calendarPanel) {
        this.calendar = new GregorianCalendar();
        this.userAccount = userAccount;
        this.calendarPanel = calendarPanel;
    }

    // Sets the calendar to a specific month
    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month);
    }

    // Sets the calendar to a specific year
    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
    }

    // Returns the number of days in the current month
    public int getNumberOfDaysInMonth() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    // Returns the first day of the week in the current month (e.g., Sunday, Monday)
    public int getFirstDayOfWeekInMonth() {
        Calendar tempCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        return tempCalendar.get(Calendar.DAY_OF_WEEK);
    }

    // Returns the current month
    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    // Returns the current year
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    // Changes the current month by a specified amount (e.g., next month, previous month)
    public void changeMonth(int amount) {
        calendar.add(Calendar.MONTH, amount);
    }

    // Provides access to the underlying Calendar object
    public Calendar getCalendar() {
        return calendar;
    }

    // Provides access to the EventStorage instance
    public EventStorage getEventStorage() {
        return new EventStorage(userAccount.getUsername()); // Create a new EventStorage for the user
    }

    // Method to notify the CalendarPanel to refresh the calendar view
    public void notifyCalendarUpdate() {
        if (calendarPanel != null) {
            calendarPanel.refreshCalendar();
        }
    }
}
