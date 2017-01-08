public interface ParkingStatInterface {

	public double getCurrentCash();
	public int getFloorCount();
	public int getCheckInCount();
	public int getCheckOutCount();
	public double getTotalMaintainanceCost();
	public double computeAverageEarningsPerMinute();
	public double computeSalesPerSecond();
	public double getTotalSalesByCarModel(String carModel);
	public double getTotalSalesByMembership(boolean isMember);

}