package rendezvoUS;
//import rendezvoUS.WeeklySchedule;

import java.awt.EventQueue;

//import rendezvoUS.GuiFrame;
public class DriverRendezvoUS {

	public static void main(String[] args) {
		WeeklySchedule schedule1 = new WeeklySchedule();
		WeeklySchedule schedule2 = new WeeklySchedule();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiFrame frame = new GuiFrame(schedule1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		schedule1.setBusy(0, 0);
		schedule2.setBusy(1, 0);
		
		schedule1.getOverlap(schedule2, schedule2);
		schedule2.showAvailability();

	}

}
