package rendezvoUS;
import rendezvoUS.WeeklySchedule;
public class DriverRendezvoUS {

	public static void main(String[] args) {
		WeeklySchedule schedule1 = new WeeklySchedule();
		WeeklySchedule schedule2 = new WeeklySchedule();
		
		
		schedule1.setBusy(0, 0);
		schedule2.setBusy(1, 0);
		
		schedule1.getOverlap(schedule2, schedule2);
		schedule2.showAvailability();

	}

}
