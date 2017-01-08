import java.io.FileInputStream;
import java.io.ObjectInputStream;



public class Deserializer{
	static Parameter p;
	static ParkingStat r = new ParkingStat();
	
	static void deserialize(){
		try{

			FileInputStream fileIn = new FileInputStream("parameter.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

			p = (Parameter) in.readObject(); // starting capital
			r.setCurrentCash(p.getDoubleValue());
			System.out.println("starting capital: "+r.getCurrentCash());

			p = (Parameter) in.readObject(); // starting number of floors
			r.setFloorCount(p.getIntValue());
			System.out.println("starting number of floors: "+r.getFloorCount());

			p = (Parameter) in.readObject(); // membership discount
			r.setMembershipDiscount(p.getDoubleValue());
			System.out.println("membership discount: "+r.getMembershipDiscount());

			p = (Parameter) in.readObject(); // parking rate per second of a sedan with membership discount
			r.setSedanDiscountRate(p.getDoubleValue());
			System.out.println("parking rate per second of a sedan with membership discount: "+r.getSedanDiscountRate());

			p = (Parameter) in.readObject(); // parking rate per second of sports car with membership discount
			r.setSportsDiscountRate(p.getDoubleValue());
			System.out.println("parking rate per second of sports car with membership discount: "+r.getSportsDiscountRate());

			p = (Parameter) in.readObject(); // parking rate per second of suv with membership discount
			r.setSUVdiscountRate(p.getDoubleValue());
			System.out.println("parking rate per second of suv with membership discount: "+r.getSUVdiscountRate());

			p = (Parameter) in.readObject(); // maintenance cost per check-in
			r.setMaintenanceCheckInCost(p.getDoubleValue());
			System.out.println("maintenance cost per check-in: "+r.getMaintenanceCheckInCost());

			p = (Parameter) in.readObject(); // cost of floors
			r.setFloorCost(p.getDoubleValue());
			System.out.println("cost of floors: "+r.getFloorCost());

			p = (Parameter) in.readObject(); // initial size of queued cars
			r.setQueuedCarCount(p.getIntValue());
			System.out.println("initial size of queued cars: "+r.getQueuedCarCount());

			p = (Parameter) in.readObject(); // rate of queued cars per minute
			r.setQueuedCarRate(p.getIntValue());
			System.out.println("rate of queued cars per minute: "+r.getQueuedCarRate());

			in.close();
			fileIn.close();

		}catch(Exception e){
			System.out.println("Error while de-serializing parameter.ser.");
		}
		
	}
	
}