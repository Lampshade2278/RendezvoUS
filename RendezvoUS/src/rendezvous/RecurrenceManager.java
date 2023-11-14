package rendezvous;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class RecurrenceManager {

    public enum Recurrence {
        NO, YES
    }

    private static Recurrence currentRecurrence = Recurrence.NO;

    public static Recurrence getcurrentRecurrence() {
        return currentRecurrence;
    }

    public static void applyRecurrence(Component component, Recurrence Recurrence) {
        currentRecurrence = Recurrence;
        switch (Recurrence) {
            case NO:
                setRecurrenceNO(component);
                break;
            case YES:
                setRecurrenceYES(component);
                break;
        }

        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                applyRecurrence(child, Recurrence);
            }
        }
    }

    private static void setRecurrenceNO(Component component) {

    }

    private static void setRecurrenceYES(Component component) {

    }
}
