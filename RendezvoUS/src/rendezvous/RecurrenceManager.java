package rendezvous;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class RecurrenceManager {
    public enum Recurrence {
        NO, WEEKLY, BIWEEKLY, MONTHLY
    }

    private static Recurrence currentRecurrence = Recurrence.NO;

    public static Recurrence getcurrentRecurrence() {
        return currentRecurrence;
    }

    public static void updateRecurrence(CalendarEvent event, Recurrence recurrence) {

        currentRecurrence = recurrence;

        if(recurrence == Recurrence.NO) {
            removeEventFromRecurringEvents(event);
        }
        else {
            addEventToRecurringEvents(event, recurrence);
        }

    }

    private static void addEventToRecurringEvents(CalendarEvent event, Recurrence recurrence) {

    }

    private static void removeEventFromRecurringEvents(CalendarEvent event) {

    }


}
