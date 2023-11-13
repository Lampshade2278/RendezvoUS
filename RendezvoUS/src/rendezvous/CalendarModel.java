package rendezvous;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarModel {
    private Calendar calendar;

    public CalendarModel() {
        calendar = new GregorianCalendar();
    }

    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month);
    }

    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
    }

    // Get the number of days in the current month
    public int getNumberOfDaysInMonth() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    // Get the first day of the week in the current month (e.g., Sunday, Monday, etc.)
    public int getFirstDayOfWeekInMonth() {
        Calendar tempCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        return tempCalendar.get(Calendar.DAY_OF_WEEK);
    }

    // Get the current month
    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    // Get the current year
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    // Increment or decrement the month
    public void changeMonth(int amount) {
        calendar.add(Calendar.MONTH, amount);
    }

    // ... Add more methods as needed
}
