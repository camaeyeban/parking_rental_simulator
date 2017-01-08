import java.awt.Image;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Car{
	static Random randomizer = new Random();
	static ParkingStat r = new ParkingStat();
	
	private String model;
	private boolean IsMember;
	private int floor, slot;
	
	Car(){
		this.setIsMember();
		this.setModel();
		/*System.out.println("model: "+this.model);
		System.out.println("Membership: "+this.IsMember);*/
	}
	
	public boolean getIsMember(){
		return this.IsMember;	
	}
	
	public void setModel(){
		int m = randomizer.nextInt(3);
		
		if(m==0) this.model = "sedan";
		else if(m==1) this.model = "suv";
		else this.model = "sports";
	}
		
	public void setIsMember(){
		this.IsMember = randomizer.nextBoolean();
	}

	public String getModel(){
		return this.model;
	}
	
	public void setFloor(){
		this.floor = (randomizer.nextInt(r.getFloorCount()));
	}

	public void setSlot(){
		this.slot = (randomizer.nextInt(10));
	}
	
	public int getFloor(){
		return this.floor;
	}

	public int getSlot(){
		return this.slot;
	}
	
	public static void AddCar(){
		Tester.QueuedCars[r.getQueuedCarCount()] = new Car();
		r.setQueuedCarCount(r.getQueuedCarCount()+1);
	}
	
	public void ParkCar(){
		this.setFloor();
		System.out.println("Floor: "+this.getFloor());
		do{
			this.setSlot();
			System.out.println("Slot: "+this.getSlot()+"\n");
		}while(Slot.checkAvailability(this.getFloor(), this.getSlot())==false);
		Tester.floors[this.getFloor()].getSlots()[this.getSlot()].setIsAvailable(false);
		System.out.println("Slot: "+this.getSlot()+"\n");
		r.setCheckInCount(r.getCheckInCount()+1);
		r.setTotalMaintenanceCost();
	}
	


}