import java.io.PrintWriter;


public class Tester{

	static ParkingStat r = new ParkingStat();
	static Thread t1;
	public static volatile boolean flag=false; // indicator of threads to stop (true) or continue running(false)
	public static Car[] QueuedCars = new Car[1000];
	public static Floor[] floors = new Floor[5];
	public static int i, current=0;
	
	public static void main(String[] args){
		
		Deserializer.deserialize();

		for(i=0; i<r.getQueuedCarCount();i++) // creates cars initially in queue
			QueuedCars[i] = new Car();
		
		for(i=0; i<r.getFloorCount(); i++)
			floors[i] = new Floor();
		
		PRSGameGUI gui = new PRSGameGUI();

		t1 = new Thread(gui);
		gui.createMenuGUI();
		t1.start();
		
		while(flag==false);
		Tester.printReport();
	}
	
	public static void printReport() {
	
		Parameter p;
		ParkingStat r = new ParkingStat();
		
		try {
			PrintWriter pw = new PrintWriter("projectreport.txt");
			pw.println("=====================================================================");
			pw.println("|                   Parking Rental Simulation Report                |");
			pw.println("=====================================================================");
			pw.println("Current Cash:\t\t\t\t" + r.getCurrentCash());
			pw.println("Number of Floors:\t\t\t" + r.getFloorCount());
			pw.println("Number of Check-Ins:\t\t\t" + r.getCheckInCount());
			pw.println("Number of Check-Outs:\t\t\t" + r.getCheckOutCount());
			pw.println("Total cost pain for maintenance:\t" + r.getTotalMaintainanceCost());
			pw.println("Average earning per minute:\t\t" + r.computeAverageEarningsPerMinute());
			pw.println("Sales per second:\t\t\t" + r.computeSalesPerSecond());
			pw.println("   Sales from Sedan:\t\t\t"+r.getTotalSalesByCarModel("sedan"));
			pw.println("   Sales from SUV:\t\t\t"+r.getTotalSalesByCarModel("suv"));
			pw.println("   Sales from Sports:\t\t\t"+r.getTotalSalesByCarModel("sports"));
			pw.println("   Sales from Members:\t\t\t"+r.getTotalSalesByMembership(true));
			pw.println("   Sales from Non-Members:\t\t"+r.getTotalSalesByMembership(false));
			
			pw.close();
		}
		catch(Exception e) {
			System.out.println("Error in file writing.");
		}
	}
	
}