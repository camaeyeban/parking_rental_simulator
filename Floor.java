public class Floor{
	private int floorNumber;
	private boolean isFull;
	private Slot[] slots = new Slot[10];
	
	public Floor(){
		this.floorNumber = floorNumber;
		this.isFull = false;
		for(int i=0; i<10; i++)
			this.slots[i] = new Slot();
	}
	
	public int getFloorNumber(){
		return this.floorNumber;
	}
	
	public boolean isFull(){
		return this.isFull;
	}
	
	public void setisFull(boolean isFull){
		this.isFull = isFull;
	}
	
	public Slot[] getSlots() {
		return this.slots;
	}
	
}