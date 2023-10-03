package rendezvoUS;
// import rendezvoUS.Day;   Possible class for each day to store events???
public class WeeklySchedule {
	
	// Stores availability for each hour in the week. 
	// False = available
	// True = busy
	private boolean[][] availability = new boolean[7][24];
	
	public boolean getAvailability(int day, int hour){
		return availability[day][hour];
	}
	
	public void setAvailable(int day, int hour){
		availability[day][hour] = false;
	}
	
	public void setBusy(int day, int hour){
		availability[day][hour] = true;
	}
	
	public void setAvailability(int day, int hour, boolean status){
		availability[day][hour] = status;
	}
	
	// Overlap method
	// returns: void
	// Gets two schedules as args, 1st is the compared schedule, 2nd is the output schedule
	public void getOverlap(WeeklySchedule otherSchedule, WeeklySchedule overlap ){
		for(int day = 0; day < 7; day++) {
			for(int hour = 0; hour < 24; hour++) {
				// if at least one schedule is busy, the overlapping schedule is busy
				overlap.availability[day][hour] = this.availability[day][hour] || otherSchedule.availability[day][hour];
			}
		}
	}
	
	public void getBusyHoursOfDay(int day, int busyHours[]) {
		int newIndex = 0;
		
		for(int hourIndex = 0; hourIndex < availability[day].length; hourIndex++) {
			if(availability[day][hourIndex] == true){
				busyHours[newIndex] = hourIndex;
				newIndex++;
			}
		}
		
		for(; newIndex < busyHours.length; newIndex++) {
			busyHours[newIndex] = -1;
		}
		
		
	}
	
	public String getBusyHoursOfDay(int day) {
		String busyString = new String();
		
		for(int hourIndex = 0; hourIndex < availability[day].length; hourIndex++) {
			if(availability[day][hourIndex] == true){
				busyString += String.valueOf(hourIndex) + ":00 \n";
			}
		}
		
		return busyString;
	}
	
	public void showAvailability() {
		
		// Prints the hours as rows, and the days as columns
		for(int day = 0; day < 7; day++) {
			for(int hour = 0; hour < 24; hour++) {
				System.out.print(this.availability[day][hour] + " ");
			}
			System.out.println();
		}
	}
}
