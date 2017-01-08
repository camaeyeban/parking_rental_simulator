public class Slot{
	boolean IsAvailable;
	
	private static int i, j;
	
	Slot(){
		IsAvailable = true;
	}

	public void setIsAvailable(boolean b){
		this.IsAvailable = b;
	}
	
	public static boolean checkAvailability(int floorNum, int slotNum){
		return Tester.floors[floorNum].getSlots()[slotNum].IsAvailable;
	}

}