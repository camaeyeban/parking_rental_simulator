public class ParkingStat implements ParkingStatInterface{

	private static double currentCash, discount, sedanDiscount, sportsDiscount, suvDiscount, MaintenanceCheckInCost, floorCost, TMC=0, sedanSales = 0, suvSales = 0, sportsSales = 0, sedanPrice = 42.68, suvPrice = 82.80, sportsPrice = 77.07, MemSales = 0, NonMemSales = 0;
	private static int floorCount, queuedCarRate, checkInCount=0, checkOutCount=0, queuedCarCount;
	
	public ParkingStat(){}
	
	// GETTERS
	public double getCurrentCash(){
		return currentCash - TMC;
	}

	public int getFloorCount(){
		return floorCount;
	}

	public int getCheckInCount(){
		return checkInCount;
	}

	public int getCheckOutCount(){
		return checkOutCount;
	}

	public double getTotalMaintainanceCost(){
		return TMC;
	}

	public double computeAverageEarningsPerMinute(){
		return (this.computeSalesPerSecond()*60);
	}

	public double computeSalesPerSecond(){
		return (currentCash-TMC)/PRSGameGUI.timer;
	}
	
	public double getTotalSalesByCarModel(String carModel){
		if(carModel.equals("sedan")) return sedanSales;
		else if(carModel.equals("suv")) return suvSales;
		else return sportsSales;
	}

	public double getTotalSalesByMembership(boolean isMember){
		if(isMember==true) return MemSales;
		else return NonMemSales;
	}
	
	public double getMembershipDiscount(){
		return discount;
	}
	
	public double getSedanDiscountRate(){
		return sedanDiscount;
	}
	
	public double getSportsDiscountRate(){
		return sportsDiscount;
	}

	public double getSUVdiscountRate(){
		return suvDiscount;
	}

	public double getMaintenanceCheckInCost(){
		return MaintenanceCheckInCost;
	}
	
	public double getFloorCost(){
		return floorCost;
	}
	
	public int getQueuedCarCount(){
		return queuedCarCount;
	}

	public int getQueuedCarRate(){
		return queuedCarRate ;
	}

	
	// SETTERS

	public static void setCurrentCash(double c){
		currentCash = c;
	}
	
	public void setFloorCount(int f){
		floorCount = f;
	}

	public void setMembershipDiscount(double d){
		discount = d;
	}

	public void setSedanDiscountRate(double s){
		sedanDiscount = s;
	}

	public void setSportsDiscountRate(double s){
		sportsDiscount = s;
	}

	public void setSUVdiscountRate(double s){
		suvDiscount = s;
	}

	public void setMaintenanceCheckInCost(double in){
		MaintenanceCheckInCost = in;
	}

	public void setFloorCost(double fc){
		floorCost = fc;
	}

	public void setQueuedCarCount(int c){
		queuedCarCount = c;
	}

	public void setQueuedCarRate(int cr){
		queuedCarRate = cr;
	}

	public void setCheckOutCount(int out){
		checkOutCount = out;
	}
	
	public void setCheckInCount(int in){
		checkInCount = in;
	}
	
	public void setTotalMaintenanceCost(){
		TMC = this.getMaintenanceCheckInCost()*this.getCheckInCount();
	}
	
	public static void setSedanSales(boolean IsMem, int in, int out){
		if(IsMem==false) sedanSales += ((out-in)*sedanPrice);
		else sedanSales += ((out-in)*(sedanPrice*discount));
	}
	
	public static void setSUVsales(boolean IsMem, int in, int out){
		if(IsMem==false) suvSales += ((out-in)*suvPrice);
		else suvSales += ((out-in)*(suvPrice*discount));
	}
	
	public static void setSportsSales(boolean IsMem, int in, int out){
		if(IsMem==false) sportsSales += ((out-in)*sportsPrice);
		else sportsSales += ((out-in)*(sportsPrice*discount));
	}
	
	public void setTotalSalesByMembership(boolean isMember, String model, int in, int out){
		if(isMember==true){
			if(model.equals("sedan")) MemSales += ((out-in)*(sedanPrice*discount));
			else if(model.equals("suv")) MemSales += ((out-in)*(suvPrice*discount));
			else MemSales += ((out-in)*(sedanPrice*discount));
		}else{
			if(model.equals("sedan")) NonMemSales += ((out-in)*(sedanPrice));
			else if(model.equals("suv")) NonMemSales += ((out-in)*(suvPrice));
			else NonMemSales += ((out-in)*(sedanPrice));
		}
	}
}