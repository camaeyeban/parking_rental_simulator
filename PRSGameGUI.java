import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class PRSGameGUI implements ActionListener, Runnable {
	static boolean clicked;
	public static int z,y;
	static JButton[][] parkingLot = new JButton[5][10];
	
	JFrame frame;
	public static int timer2;
	
	String[][] model = new String[5][10];
	boolean[][] IsMem = new boolean[5][10];
	int CheckInTime[][] = new int[5][10];
	int CheckOutTime[][] = new int[5][10];

	JPanel menuContainer;
	JLabel background;
	JLabel gameBackground;
	
	static JLabel queue;
	static JLabel currentCash;
	static JLabel numOfFloors;
	static JLabel numOfCheckIns;
	static JLabel numOfCheckOuts;
	static JLabel totalMaintCost;
	static JLabel aveEarnPerMin;
	static JLabel salesPerSec;
	static JLabel sedanSale;
	static JLabel suvSale;
	static JLabel SportsSale;
	static JLabel MemberSale;
	static JLabel NonMemberSale;
	
	public static int timer;
	public static int k;
	
	JButton startGame;
	JButton quitGame;
	
	JButton parkingSpace;
	
	JButton buyFloor;
	JButton first;
	JButton second;
	JButton third;
	JButton fourth;
	JButton fifth;
	
	JPanel gameContainer;
	JPanel parkingContainer;
	JPanel statsAndOptions;
	JPanel status;
	JPanel options;
	
	JPanel tempPrev;
	
	ArrayList<ArrayList> lotsAndFloors;
	ArrayList<JPanel> floors;

	ParkingStat r = new ParkingStat();
	
	//METHOD for creating the main menu of the game
	public void createMenuGUI(){
	
		frame = new JFrame("Parking Rental Simulation");
		//JPanel container for menu
		menuContainer = new JPanel();

		//sets the background image of the menu
		menuContainer.setLayout(new BorderLayout());
		background = new JLabel(new ImageIcon("images/welcome.png"));
		menuContainer.add(background);
		background.setLayout(new FlowLayout(1, 20, 400));		
	
		//main menu buttons
		startGame = new JButton(new ImageIcon("images/startbutton.png"));
		quitGame = new JButton(new ImageIcon("images/exitbutton.png"));
		//action listener for the buttons
		startGame.addActionListener(this);
		quitGame.addActionListener(this);
		
		//add to background the startGame and quitGame buttons;
		background.add(startGame);
		background.add(quitGame);
		menuContainer.setPreferredSize(new Dimension(1100,600));
	
		//frame now contains the menu container and all it's contents
		
		frame.setPreferredSize(new Dimension(1100,600));
		frame.add(menuContainer);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	
	//=======================================================================================================================================================
	//METHOD for creating the GUI of the game-play
	public void createGameGUI(){
		
		for(z=0; z<5; z++){
			for(y=0; y<10; y++){
				model[z][y] = new String();
			}
		}
		
		//JPanel container for the parking lots(10 JButtons)
		parkingContainer = new JPanel(new BorderLayout());
		gameBackground = new JLabel(new ImageIcon("images/background.png"));
		parkingContainer.add(gameBackground);
		gameBackground.setLayout(new GridLayout(2,5,25,120));

		//JPanel container for status and option JPanel
		statsAndOptions = new JPanel(new BorderLayout());

		//JPanel container for buyFloor, quitGame, floorLevels JButton
		options = new JPanel(new GridLayout(4,2));
		ImageIcon buy = new ImageIcon("images/buy.jpg");
		buyFloor = new JButton(buy);
		ImageIcon quit = new ImageIcon("images/quit.jpg");
		quitGame = new JButton(quit);

		//JPanel container for the parkingContainer and the statsAndOption
		gameContainer = new JPanel(new BorderLayout());
		
		JLabel gameLabel = new JLabel("PARKING LOT");
		JLabel opts = new JLabel("             Options");
		JLabel stats = new JLabel("                            Game Status");
		ImageIcon lot = new ImageIcon("images/lot.png");
		
		//this panel should contain the info in .ser file, # o queued cars, updated randomized parking places
		status = new JPanel(new GridLayout(14,1));
		
		for(int i=0; i<5; i++){
			for(int j=0; j<10; j++){
				parkingSpace = new JButton(lot);
				parkingSpace.setEnabled(false);
				parkingSpace.addActionListener(this);
				parkingLot[i][j] = new JButton();
				parkingLot[i][j] = parkingSpace;
			}
		}
		
		for(int i=0; i<10; i++){
			gameBackground.add(parkingLot[0][i]);
		}
		
		options.add(opts);
		options.add(buyFloor);
		
		//JButtons for floor navigation
		ImageIcon f1 = new ImageIcon("images/floor1.jpg");
		ImageIcon f2 = new ImageIcon("images/floor2.jpg");
		ImageIcon f3 = new ImageIcon("images/floor3.jpg");
		ImageIcon f4 = new ImageIcon("images/floor4.jpg");
		ImageIcon f5 = new ImageIcon("images/floor5.jpg");
		
		first = new JButton(f1);
		second = new JButton(f2);
		third = new JButton(f3);
		fourth = new JButton(f4);
		fifth = new JButton(f5);
		
		options.add(first);
		options.add(second);
		options.add(third);
		options.add(fourth);
		options.add(fifth);
		
		third.setEnabled(false);
		fourth.setEnabled(false);
		fifth.setEnabled(false);
		
		options.add(quitGame);
		
		status.add(stats);
		status.setBackground(Color.CYAN);
		
		queue = new JLabel("   Remaining cars in queue: "+r.getQueuedCarCount());
		currentCash = new JLabel("   Current Cash: "+r.getCurrentCash());
		numOfFloors = new JLabel("   Number of Floors: "+r.getFloorCount());
		numOfCheckIns = new JLabel("   Check-ins: "+r.getCheckInCount());
		numOfCheckOuts = new JLabel("   Check-outs: "+r.getCheckOutCount());
		totalMaintCost = new JLabel("   Total Maintenance Cost: "+r.getTotalMaintainanceCost());
		aveEarnPerMin = new JLabel("   Earnings/min: "+r.computeAverageEarningsPerMinute());
		salesPerSec = new JLabel("   Sales/sec: "+r.computeSalesPerSecond());
		sedanSale = new JLabel("   Sales from Sedan: "+r.computeSalesPerSecond());
		suvSale = new JLabel("   Sales from SUV: "+r.computeSalesPerSecond());
		SportsSale = new JLabel("   Sales from Sports: "+r.computeSalesPerSecond());
		MemberSale = new JLabel("   Sales from Members: "+r.computeSalesPerSecond());
		NonMemberSale = new JLabel("   Sales from Non-Members: "+r.computeSalesPerSecond());
		
		status.add(queue);
		status.add(currentCash);
		status.add(numOfFloors);
		status.add(numOfCheckIns);
		status.add(numOfCheckOuts);
		status.add(totalMaintCost);
		status.add(aveEarnPerMin);
		status.add(salesPerSec);
		status.add(sedanSale);
		status.add(suvSale);
		status.add(SportsSale);
		status.add(MemberSale);
		status.add(NonMemberSale);
		
		//add the status and option JPanel
		statsAndOptions.add(options, BorderLayout.NORTH);
		statsAndOptions.add(status, BorderLayout.CENTER);
		
		//action listener for the buttons
		first.addActionListener(this);
		second.addActionListener(this);
		third.addActionListener(this);
		fourth.addActionListener(this);
		fifth.addActionListener(this);
		
		//parkingSpace.addActionListener(this);
		buyFloor.addActionListener(this);
		quitGame.addActionListener(this);
		
		//add all the contents on the main JPanel of this method which is gameContainer
		gameContainer.add(gameLabel, BorderLayout.NORTH);
		gameContainer.add(parkingContainer, BorderLayout.CENTER);
		gameContainer.add(statsAndOptions, BorderLayout.EAST);
		gameContainer.setPreferredSize(new Dimension(1100,600));

		frame.add(gameContainer);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}
	//=======================================================================================================================================================
	
	
	//ACTION LISTENER for JButtons
	public void actionPerformed(ActionEvent e){
		
		//ImageIcon lot = new ImageIcon("images/lot.png");
		//condition for clicking the startGame button
		if((JButton)e.getSource() == startGame){
			frame.remove(menuContainer);
			System.out.println("Game Starts!");
			this.createGameGUI();
		}
		
		//condition for clicking the quitGame button
		else if((JButton)e.getSource() == quitGame){
			frame.dispose();
			Tester.flag=true;
			while(!Tester.flag);
			System.out.println("Game Closed!");
		}
		
		//condition for clicking the buyFloor button
		else if((JButton)e.getSource() == buyFloor){
			
			if(r.getCurrentCash() > r.getFloorCost()){
				clicked = true;
				
				if(clicked){
					System.out.println("Floor bought!");
					r.setFloorCount(r.getFloorCount()+1);
					r.setCurrentCash(r.getCurrentCash() - r.getFloorCost());
				}
				if(r.getFloorCount() == 3){
					third.setEnabled(true);
				}
				else if(r.getFloorCount() == 4){
					fourth.setEnabled(true);
				}
				else if(r.getFloorCount() == 5){
					fifth.setEnabled(true);
					buyFloor.setEnabled(false);
				}
			}
			else{
				buyFloor.setEnabled(false);
			}
			
		}


		else if((JButton)e.getSource() == (parkingLot[0][0])){
			System.out.println("Car removed!");
			parkingLot[0][0].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][0].setEnabled(false);
			CheckOutTime[0][0] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[0].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][0], model[0][0], CheckInTime[0][0], CheckOutTime[0][0]);
			if(model[0][0] == "sedan") ParkingStat.setSedanSales(IsMem[0][0], CheckInTime[0][0], CheckOutTime[0][0]);
			else if(model[0][0] == "suv") ParkingStat.setSUVsales(IsMem[0][0], CheckInTime[0][0], CheckOutTime[0][0]);
			else if(model[0][0] == "sports") ParkingStat.setSportsSales(IsMem[0][0], CheckInTime[0][0], CheckOutTime[0][0]);
		}else if((JButton)e.getSource() == (parkingLot[0][1])){
			System.out.println("Car removed!");
			parkingLot[0][1].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][1].setEnabled(false);
			CheckOutTime[0][1] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[1].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][1], model[0][1], CheckInTime[0][1], CheckOutTime[0][1]);
			if(model[0][1] == "sedan") ParkingStat.setSedanSales(IsMem[0][1], CheckInTime[0][1], CheckOutTime[0][1]);
			else if(model[0][1] == "suv") ParkingStat.setSUVsales(IsMem[0][1], CheckInTime[0][1], CheckOutTime[0][1]);
			else if(model[0][1] == "sports") ParkingStat.setSportsSales(IsMem[0][1], CheckInTime[0][1], CheckOutTime[0][1]);
		}else if((JButton)e.getSource() == (parkingLot[0][2])){
			System.out.println("Car removed!");
			parkingLot[0][2].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][2].setEnabled(false);
			CheckOutTime[0][2] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[2].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][2], model[0][2], CheckInTime[0][2], CheckOutTime[0][2]);
			if(model[0][2] == "sedan") ParkingStat.setSedanSales(IsMem[0][2], CheckInTime[0][2], CheckOutTime[0][2]);
			else if(model[0][2] == "suv") ParkingStat.setSUVsales(IsMem[0][2], CheckInTime[0][2], CheckOutTime[0][2]);
			else if(model[0][2] == "sports") ParkingStat.setSportsSales(IsMem[0][2], CheckInTime[0][2], CheckOutTime[0][2]);
		}else if((JButton)e.getSource() == (parkingLot[0][3])){
			System.out.println("Car removed!");
			parkingLot[0][3].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][3].setEnabled(false);
			CheckOutTime[0][3] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[3].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][3], model[0][3], CheckInTime[0][3], CheckOutTime[0][3]);
			if(model[0][3] == "sedan") ParkingStat.setSedanSales(IsMem[0][3], CheckInTime[0][3], CheckOutTime[0][3]);
			else if(model[0][3] == "suv") ParkingStat.setSUVsales(IsMem[0][3], CheckInTime[0][3], CheckOutTime[0][3]);
			else if(model[0][3] == "sports") ParkingStat.setSportsSales(IsMem[0][3], CheckInTime[0][3], CheckOutTime[0][3]);
		}else if((JButton)e.getSource() == (parkingLot[0][4])){
			System.out.println("Car removed!");
			parkingLot[0][4].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][4].setEnabled(false);
			CheckOutTime[0][4] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[4].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][4], model[0][4], CheckInTime[0][4], CheckOutTime[0][4]);
			if(model[0][4] == "sedan") ParkingStat.setSedanSales(IsMem[0][4], CheckInTime[0][4], CheckOutTime[0][4]);
			else if(model[0][4] == "suv") ParkingStat.setSUVsales(IsMem[0][4], CheckInTime[0][4], CheckOutTime[0][4]);
			else if(model[0][4] == "sports") ParkingStat.setSportsSales(IsMem[0][4], CheckInTime[0][4], CheckOutTime[0][4]);
		}else if((JButton)e.getSource() == (parkingLot[0][5])){
			System.out.println("Car removed!");
			parkingLot[0][5].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][5].setEnabled(false);
			CheckOutTime[0][5] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[5].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][5], model[0][5], CheckInTime[0][5], CheckOutTime[0][5]);
			if(model[0][5] == "sedan") ParkingStat.setSedanSales(IsMem[0][5], CheckInTime[0][5], CheckOutTime[0][5]);
			else if(model[0][5] == "suv") ParkingStat.setSUVsales(IsMem[0][5], CheckInTime[0][5], CheckOutTime[0][5]);
			else if(model[0][5] == "sports") ParkingStat.setSportsSales(IsMem[0][5], CheckInTime[0][5], CheckOutTime[0][5]);
		}else if((JButton)e.getSource() == (parkingLot[0][6])){
			System.out.println("Car removed!");
			parkingLot[0][6].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][6].setEnabled(false);
			CheckOutTime[0][6] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[6].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][6], model[0][6], CheckInTime[0][6], CheckOutTime[0][6]);
			if(model[0][6] == "sedan") ParkingStat.setSedanSales(IsMem[0][6], CheckInTime[0][6], CheckOutTime[0][6]);
			else if(model[0][6] == "suv") ParkingStat.setSUVsales(IsMem[0][6], CheckInTime[0][6], CheckOutTime[0][6]);
			else if(model[0][6] == "sports") ParkingStat.setSportsSales(IsMem[0][6], CheckInTime[0][6], CheckOutTime[0][6]);
		}else if((JButton)e.getSource() == (parkingLot[0][7])){
			System.out.println("Car removed!");
			parkingLot[0][7].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][7].setEnabled(false);
			CheckOutTime[0][7] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[7].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][7], model[0][7], CheckInTime[0][7], CheckOutTime[0][7]);
			if(model[0][7] == "sedan") ParkingStat.setSedanSales(IsMem[0][7], CheckInTime[0][7], CheckOutTime[0][7]);
			else if(model[0][7] == "suv") ParkingStat.setSUVsales(IsMem[0][7], CheckInTime[0][7], CheckOutTime[0][7]);
			else if(model[0][7] == "sports") ParkingStat.setSportsSales(IsMem[0][7], CheckInTime[0][7], CheckOutTime[0][7]);
		}else if((JButton)e.getSource() == (parkingLot[0][8])){
			System.out.println("Car removed!");
			parkingLot[0][8].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][8].setEnabled(false);
			CheckOutTime[0][8] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[8].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][8], model[0][8], CheckInTime[0][8], CheckOutTime[0][8]);
			if(model[0][8] == "sedan") ParkingStat.setSedanSales(IsMem[0][8], CheckInTime[0][8], CheckOutTime[0][8]);
			else if(model[0][8] == "suv") ParkingStat.setSUVsales(IsMem[0][8], CheckInTime[0][8], CheckOutTime[0][8]);
			else if(model[0][8] == "sports") ParkingStat.setSportsSales(IsMem[0][8], CheckInTime[0][8], CheckOutTime[0][8]);
		}else if((JButton)e.getSource() == (parkingLot[0][9])){
			System.out.println("Car removed!");
			parkingLot[0][9].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[0][9].setEnabled(false);
			CheckOutTime[0][9] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[0].getSlots()[9].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[0][9], model[0][9], CheckInTime[0][9], CheckOutTime[0][9]);
			if(model[0][9] == "sedan") ParkingStat.setSedanSales(IsMem[0][9], CheckInTime[0][9], CheckOutTime[0][9]);
			else if(model[0][9] == "suv") ParkingStat.setSUVsales(IsMem[0][9], CheckInTime[0][9], CheckOutTime[0][9]);
			else if(model[0][9] == "sports") ParkingStat.setSportsSales(IsMem[0][9], CheckInTime[0][9], CheckOutTime[0][9]);
		}
		
		
		else if((JButton)e.getSource() == (parkingLot[1][0])){
			System.out.println("Car removed!");
			parkingLot[1][0].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][0].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[0].setIsAvailable(true);
			CheckOutTime[1][0] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][0], model[1][0], CheckInTime[1][0], CheckOutTime[1][0]);
			if(model[1][0] == "sedan") ParkingStat.setSedanSales(IsMem[1][0], CheckInTime[1][0], CheckOutTime[1][0]);
			else if(model[1][0] == "suv") ParkingStat.setSUVsales(IsMem[1][0], CheckInTime[1][0], CheckOutTime[1][0]);
			else if(model[1][0] == "sports") ParkingStat.setSportsSales(IsMem[1][0], CheckInTime[1][0], CheckOutTime[1][0]);
		}else if((JButton)e.getSource() == (parkingLot[1][1])){
			System.out.println("Car removed!");
			parkingLot[1][1].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][1].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[1].setIsAvailable(true);
			CheckOutTime[1][1] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][1], model[1][1], CheckInTime[1][1], CheckOutTime[1][1]);
			if(model[1][1] == "sedan") ParkingStat.setSedanSales(IsMem[1][1], CheckInTime[1][1], CheckOutTime[1][1]);
			else if(model[1][1] == "suv") ParkingStat.setSUVsales(IsMem[1][1], CheckInTime[1][1], CheckOutTime[1][1]);
			else if(model[1][1] == "sports") ParkingStat.setSportsSales(IsMem[1][1], CheckInTime[1][1], CheckOutTime[1][1]);
		}else if((JButton)e.getSource() == (parkingLot[1][2])){
			System.out.println("Car removed!");
			parkingLot[1][2].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][2].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[2].setIsAvailable(true);
			CheckOutTime[1][2] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][2], model[1][2], CheckInTime[1][2], CheckOutTime[1][2]);
			if(model[1][2] == "sedan") ParkingStat.setSedanSales(IsMem[1][2], CheckInTime[1][2], CheckOutTime[1][2]);
			else if(model[1][2] == "suv") ParkingStat.setSUVsales(IsMem[1][2], CheckInTime[1][2], CheckOutTime[1][2]);
			else if(model[1][2] == "sports") ParkingStat.setSportsSales(IsMem[1][2], CheckInTime[1][2], CheckOutTime[1][2]);
		}else if((JButton)e.getSource() == (parkingLot[1][3])){
			System.out.println("Car removed!");
			parkingLot[1][3].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][3].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[3].setIsAvailable(true);
			CheckOutTime[1][3] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][3], model[1][3], CheckInTime[1][3], CheckOutTime[1][3]);
			if(model[1][3] == "sedan") ParkingStat.setSedanSales(IsMem[1][3], CheckInTime[1][3], CheckOutTime[1][3]);
			else if(model[1][3] == "suv") ParkingStat.setSUVsales(IsMem[1][3], CheckInTime[1][3], CheckOutTime[1][3]);
			else if(model[1][3] == "sports") ParkingStat.setSportsSales(IsMem[1][3], CheckInTime[1][3], CheckOutTime[1][3]);
		}else if((JButton)e.getSource() == (parkingLot[1][4])){
			System.out.println("Car removed!");
			parkingLot[1][4].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][4].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[4].setIsAvailable(true);
			CheckOutTime[1][4] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][4], model[1][4], CheckInTime[1][4], CheckOutTime[1][4]);
			if(model[1][4] == "sedan") ParkingStat.setSedanSales(IsMem[1][4], CheckInTime[1][4], CheckOutTime[1][4]);
			else if(model[1][4] == "suv") ParkingStat.setSUVsales(IsMem[1][4], CheckInTime[1][4], CheckOutTime[1][4]);
			else if(model[1][4] == "sports") ParkingStat.setSportsSales(IsMem[1][4], CheckInTime[1][4], CheckOutTime[1][4]);
		}else if((JButton)e.getSource() == (parkingLot[1][5])){
			System.out.println("Car removed!");
			parkingLot[1][5].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][5].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[5].setIsAvailable(true);
			CheckOutTime[1][5] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][5], model[1][5], CheckInTime[1][5], CheckOutTime[1][5]);
			if(model[1][5] == "sedan") ParkingStat.setSedanSales(IsMem[1][5], CheckInTime[1][5], CheckOutTime[1][5]);
			else if(model[1][5] == "suv") ParkingStat.setSUVsales(IsMem[1][5], CheckInTime[1][5], CheckOutTime[1][5]);
			else if(model[1][5] == "sports") ParkingStat.setSportsSales(IsMem[1][5], CheckInTime[1][5], CheckOutTime[1][5]);
		}else if((JButton)e.getSource() == (parkingLot[1][6])){
			System.out.println("Car removed!");
			parkingLot[1][6].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][6].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[6].setIsAvailable(true);
			CheckOutTime[1][6] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][6], model[1][6], CheckInTime[1][6], CheckOutTime[1][6]);
			if(model[1][6] == "sedan") ParkingStat.setSedanSales(IsMem[1][6], CheckInTime[1][6], CheckOutTime[1][6]);
			else if(model[1][6] == "suv") ParkingStat.setSUVsales(IsMem[1][6], CheckInTime[1][6], CheckOutTime[1][6]);
			else if(model[1][6] == "sports") ParkingStat.setSportsSales(IsMem[1][6], CheckInTime[1][6], CheckOutTime[1][6]);
		}else if((JButton)e.getSource() == (parkingLot[1][7])){
			System.out.println("Car removed!");
			parkingLot[1][7].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][7].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[7].setIsAvailable(true);
			CheckOutTime[1][7] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][7], model[1][7], CheckInTime[1][7], CheckOutTime[1][7]);
			if(model[1][7] == "sedan") ParkingStat.setSedanSales(IsMem[1][7], CheckInTime[1][7], CheckOutTime[1][7]);
			else if(model[1][7] == "suv") ParkingStat.setSUVsales(IsMem[1][7], CheckInTime[1][7], CheckOutTime[1][7]);
			else if(model[1][7] == "sports") ParkingStat.setSportsSales(IsMem[1][7], CheckInTime[1][7], CheckOutTime[1][7]);
		}else if((JButton)e.getSource() == (parkingLot[1][8])){
			System.out.println("Car removed!");
			parkingLot[1][8].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][8].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[8].setIsAvailable(true);
			CheckOutTime[1][8] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][8], model[1][8], CheckInTime[1][8], CheckOutTime[1][8]);
			if(model[1][8] == "sedan") ParkingStat.setSedanSales(IsMem[1][8], CheckInTime[1][8], CheckOutTime[1][8]);
			else if(model[1][8] == "suv") ParkingStat.setSUVsales(IsMem[1][8], CheckInTime[1][8], CheckOutTime[1][8]);
			else if(model[1][8] == "sports") ParkingStat.setSportsSales(IsMem[1][8], CheckInTime[1][8], CheckOutTime[1][8]);
		}else if((JButton)e.getSource() == (parkingLot[1][9])){
			System.out.println("Car removed!");
			parkingLot[1][9].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[1][9].setEnabled(false);
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[1].getSlots()[9].setIsAvailable(true);
			CheckOutTime[1][9] = PRSGameGUI.timer;
			r.setTotalSalesByMembership(IsMem[1][9], model[1][9], CheckInTime[1][9], CheckOutTime[1][9]);
			if(model[1][9] == "sedan") ParkingStat.setSedanSales(IsMem[1][9], CheckInTime[1][9], CheckOutTime[1][9]);
			else if(model[1][9] == "suv") ParkingStat.setSUVsales(IsMem[1][9], CheckInTime[1][9], CheckOutTime[1][9]);
			else if(model[1][9] == "sports") ParkingStat.setSportsSales(IsMem[1][9], CheckInTime[1][9], CheckOutTime[1][9]);
		}
		
		
		else if((JButton)e.getSource() == (parkingLot[2][0])){
			System.out.println("Car removed!");
			parkingLot[2][0].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][0].setEnabled(false);
			CheckOutTime[2][0] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[0].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][0], model[2][0], CheckInTime[2][0], CheckOutTime[2][0]);
			if(model[2][0] == "sedan") ParkingStat.setSedanSales(IsMem[2][0], CheckInTime[2][0], CheckOutTime[2][0]);
			else if(model[2][0] == "suv") ParkingStat.setSUVsales(IsMem[2][0], CheckInTime[2][0], CheckOutTime[2][0]);
			else if(model[2][0] == "sports") ParkingStat.setSportsSales(IsMem[2][0], CheckInTime[2][0], CheckOutTime[2][0]);
		}else if((JButton)e.getSource() == (parkingLot[2][1])){
			System.out.println("Car removed!");
			parkingLot[2][1].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][1].setEnabled(false);
			CheckOutTime[2][1] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[1].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][1], model[2][1], CheckInTime[2][1], CheckOutTime[2][1]);
			if(model[2][1] == "sedan") ParkingStat.setSedanSales(IsMem[2][1], CheckInTime[2][1], CheckOutTime[2][1]);
			else if(model[2][1] == "suv") ParkingStat.setSUVsales(IsMem[2][1], CheckInTime[2][1], CheckOutTime[2][1]);
			else if(model[2][1] == "sports") ParkingStat.setSportsSales(IsMem[2][1], CheckInTime[2][1], CheckOutTime[2][1]);
		}else if((JButton)e.getSource() == (parkingLot[2][2])){
			System.out.println("Car removed!");
			parkingLot[2][2].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][2].setEnabled(false);
			CheckOutTime[2][2] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[2].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][2], model[2][2], CheckInTime[2][2], CheckOutTime[2][2]);
			if(model[2][2] == "sedan") ParkingStat.setSedanSales(IsMem[2][2], CheckInTime[2][2], CheckOutTime[2][2]);
			else if(model[2][2] == "suv") ParkingStat.setSUVsales(IsMem[2][2], CheckInTime[2][2], CheckOutTime[2][2]);
			else if(model[2][2] == "sports") ParkingStat.setSportsSales(IsMem[2][2], CheckInTime[2][2], CheckOutTime[2][2]);
		}else if((JButton)e.getSource() == (parkingLot[2][3])){
			System.out.println("Car removed!");
			parkingLot[2][3].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][3].setEnabled(false);
			CheckOutTime[2][3] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[3].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][3], model[2][3], CheckInTime[2][3], CheckOutTime[2][3]);
			if(model[2][3] == "sedan") ParkingStat.setSedanSales(IsMem[2][3], CheckInTime[2][3], CheckOutTime[2][3]);
			else if(model[2][3] == "suv") ParkingStat.setSUVsales(IsMem[2][3], CheckInTime[2][3], CheckOutTime[2][3]);
			else if(model[2][3] == "sports") ParkingStat.setSportsSales(IsMem[2][3], CheckInTime[2][3], CheckOutTime[2][3]);
		}else if((JButton)e.getSource() == (parkingLot[2][4])){
			System.out.println("Car removed!");
			parkingLot[2][4].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][4].setEnabled(false);
			CheckOutTime[2][4] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[4].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][4], model[2][4], CheckInTime[2][4], CheckOutTime[2][4]);
			if(model[2][4] == "sedan") ParkingStat.setSedanSales(IsMem[2][4], CheckInTime[2][4], CheckOutTime[2][4]);
			else if(model[2][4] == "suv") ParkingStat.setSUVsales(IsMem[2][4], CheckInTime[2][4], CheckOutTime[2][4]);
			else if(model[2][4] == "sports") ParkingStat.setSportsSales(IsMem[2][4], CheckInTime[2][4], CheckOutTime[2][4]);
		}else if((JButton)e.getSource() == (parkingLot[2][5])){
			System.out.println("Car removed!");
			parkingLot[2][5].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][5].setEnabled(false);
			CheckOutTime[2][5] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[5].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][5], model[2][5], CheckInTime[2][5], CheckOutTime[2][5]);
			if(model[2][5] == "sedan") ParkingStat.setSedanSales(IsMem[2][5], CheckInTime[2][5], CheckOutTime[2][5]);
			else if(model[2][5] == "suv") ParkingStat.setSUVsales(IsMem[2][5], CheckInTime[2][5], CheckOutTime[2][5]);
			else if(model[2][5] == "sports") ParkingStat.setSportsSales(IsMem[2][5], CheckInTime[2][5], CheckOutTime[2][5]);
		}else if((JButton)e.getSource() == (parkingLot[2][6])){
			System.out.println("Car removed!");
			parkingLot[2][6].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][6].setEnabled(false);
			CheckOutTime[2][6] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[6].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][6], model[2][6], CheckInTime[2][6], CheckOutTime[2][6]);
			if(model[2][6] == "sedan") ParkingStat.setSedanSales(IsMem[2][6], CheckInTime[2][6], CheckOutTime[2][6]);
			else if(model[2][6] == "suv") ParkingStat.setSUVsales(IsMem[2][6], CheckInTime[2][6], CheckOutTime[2][6]);
			else if(model[2][6] == "sports") ParkingStat.setSportsSales(IsMem[2][6], CheckInTime[2][6], CheckOutTime[2][6]);
		}else if((JButton)e.getSource() == (parkingLot[2][7])){
			System.out.println("Car removed!");
			parkingLot[2][7].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][7].setEnabled(false);
			CheckOutTime[2][7] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[7].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][7], model[2][7], CheckInTime[2][7], CheckOutTime[2][7]);
			if(model[2][7] == "sedan") ParkingStat.setSedanSales(IsMem[2][7], CheckInTime[2][7], CheckOutTime[2][7]);
			else if(model[2][7] == "suv") ParkingStat.setSUVsales(IsMem[2][7], CheckInTime[2][7], CheckOutTime[2][7]);
			else if(model[2][7] == "sports") ParkingStat.setSportsSales(IsMem[2][7], CheckInTime[2][7], CheckOutTime[2][7]);
		}else if((JButton)e.getSource() == (parkingLot[2][8])){
			System.out.println("Car removed!");
			parkingLot[2][8].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][8].setEnabled(false);
			CheckOutTime[2][8] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[8].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][8], model[2][8], CheckInTime[2][8], CheckOutTime[2][8]);
			if(model[2][8] == "sedan") ParkingStat.setSedanSales(IsMem[2][8], CheckInTime[2][8], CheckOutTime[2][8]);
			else if(model[2][8] == "suv") ParkingStat.setSUVsales(IsMem[2][8], CheckInTime[2][8], CheckOutTime[2][8]);
			else if(model[2][8] == "sports") ParkingStat.setSportsSales(IsMem[2][8], CheckInTime[2][8], CheckOutTime[2][8]);
		}else if((JButton)e.getSource() == (parkingLot[2][9])){
			System.out.println("Car removed!");
			parkingLot[2][9].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[2][9].setEnabled(false);
			CheckOutTime[2][9] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[2].getSlots()[9].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[2][9], model[2][9], CheckInTime[2][9], CheckOutTime[2][9]);
			if(model[2][9] == "sedan") ParkingStat.setSedanSales(IsMem[2][9], CheckInTime[2][9], CheckOutTime[2][9]);
			else if(model[2][9] == "suv") ParkingStat.setSUVsales(IsMem[2][9], CheckInTime[2][9], CheckOutTime[2][9]);
			else if(model[2][9] == "sports") ParkingStat.setSportsSales(IsMem[2][9], CheckInTime[2][9], CheckOutTime[2][9]);
		}
		
		
		else if((JButton)e.getSource() == (parkingLot[3][0])){
			System.out.println("Car removed!");
			parkingLot[3][0].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][0].setEnabled(false);
			CheckOutTime[3][0] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[0].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][0], model[3][0], CheckInTime[3][0], CheckOutTime[3][0]);
			if(model[3][0] == "sedan") ParkingStat.setSedanSales(IsMem[3][0], CheckInTime[3][0], CheckOutTime[3][0]);
			else if(model[3][0] == "suv") ParkingStat.setSUVsales(IsMem[3][0], CheckInTime[3][0], CheckOutTime[3][0]);
			else if(model[3][0] == "sports") ParkingStat.setSportsSales(IsMem[3][0], CheckInTime[3][0], CheckOutTime[3][0]);
		}else if((JButton)e.getSource() == (parkingLot[3][1])){
			System.out.println("Car removed!");
			parkingLot[3][1].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][1].setEnabled(false);
			CheckOutTime[3][1] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[1].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][1], model[3][1], CheckInTime[3][1], CheckOutTime[3][1]);
			if(model[3][1] == "sedan") ParkingStat.setSedanSales(IsMem[3][1], CheckInTime[3][1], CheckOutTime[3][1]);
			else if(model[3][1] == "suv") ParkingStat.setSUVsales(IsMem[3][1], CheckInTime[3][1], CheckOutTime[3][1]);
			else if(model[3][1] == "sports") ParkingStat.setSportsSales(IsMem[3][1], CheckInTime[3][1], CheckOutTime[3][1]);
		}else if((JButton)e.getSource() == (parkingLot[3][2])){
			System.out.println("Car removed!");
			parkingLot[3][2].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][2].setEnabled(false);
			CheckOutTime[3][2] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[2].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][2], model[3][2], CheckInTime[3][2], CheckOutTime[3][2]);
			if(model[3][2] == "sedan") ParkingStat.setSedanSales(IsMem[3][2], CheckInTime[3][2], CheckOutTime[3][2]);
			else if(model[3][2] == "suv") ParkingStat.setSUVsales(IsMem[3][2], CheckInTime[3][2], CheckOutTime[3][2]);
			else if(model[3][2] == "sports") ParkingStat.setSportsSales(IsMem[3][2], CheckInTime[3][2], CheckOutTime[3][2]);
		}else if((JButton)e.getSource() == (parkingLot[3][3])){
			System.out.println("Car removed!");
			parkingLot[3][3].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][3].setEnabled(false);
			CheckOutTime[3][3] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[3].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][3], model[3][3], CheckInTime[3][3], CheckOutTime[3][3]);
			if(model[3][3] == "sedan") ParkingStat.setSedanSales(IsMem[3][3], CheckInTime[3][3], CheckOutTime[3][3]);
			else if(model[3][3] == "suv") ParkingStat.setSUVsales(IsMem[3][3], CheckInTime[3][3], CheckOutTime[3][3]);
			else if(model[3][3] == "sports") ParkingStat.setSportsSales(IsMem[3][3], CheckInTime[3][3], CheckOutTime[3][3]);
		}else if((JButton)e.getSource() == (parkingLot[3][4])){
			System.out.println("Car removed!");
			parkingLot[3][4].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][4].setEnabled(false);
			CheckOutTime[3][4] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[4].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][4], model[3][4], CheckInTime[3][4], CheckOutTime[3][4]);
			if(model[3][4] == "sedan") ParkingStat.setSedanSales(IsMem[3][4], CheckInTime[3][4], CheckOutTime[3][4]);
			else if(model[3][4] == "suv") ParkingStat.setSUVsales(IsMem[3][4], CheckInTime[3][4], CheckOutTime[3][4]);
			else if(model[3][4] == "sports") ParkingStat.setSportsSales(IsMem[3][4], CheckInTime[3][4], CheckOutTime[3][4]);
		}else if((JButton)e.getSource() == (parkingLot[3][5])){
			System.out.println("Car removed!");
			parkingLot[3][5].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][5].setEnabled(false);
			CheckOutTime[3][5] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[5].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][5], model[3][5], CheckInTime[3][5], CheckOutTime[3][5]);
			if(model[3][5] == "sedan") ParkingStat.setSedanSales(IsMem[3][5], CheckInTime[3][5], CheckOutTime[3][5]);
			else if(model[3][5] == "suv") ParkingStat.setSUVsales(IsMem[3][5], CheckInTime[3][5], CheckOutTime[3][5]);
			else if(model[3][5] == "sports") ParkingStat.setSportsSales(IsMem[3][5], CheckInTime[3][5], CheckOutTime[3][5]);
		}else if((JButton)e.getSource() == (parkingLot[3][6])){
			System.out.println("Car removed!");
			parkingLot[3][6].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][6].setEnabled(false);
			CheckOutTime[3][6] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[6].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][6], model[3][6], CheckInTime[3][6], CheckOutTime[3][6]);
			if(model[3][6] == "sedan") ParkingStat.setSedanSales(IsMem[3][6], CheckInTime[3][6], CheckOutTime[3][6]);
			else if(model[3][6] == "suv") ParkingStat.setSUVsales(IsMem[3][6], CheckInTime[3][6], CheckOutTime[3][6]);
			else if(model[3][6] == "sports") ParkingStat.setSportsSales(IsMem[3][6], CheckInTime[3][6], CheckOutTime[3][6]);
		}else if((JButton)e.getSource() == (parkingLot[3][7])){
			System.out.println("Car removed!");
			parkingLot[3][7].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][7].setEnabled(false);
			CheckOutTime[3][7] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[7].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][7], model[3][7], CheckInTime[3][7], CheckOutTime[3][7]);
			if(model[3][7] == "sedan") ParkingStat.setSedanSales(IsMem[3][7], CheckInTime[3][7], CheckOutTime[3][7]);
			else if(model[3][7] == "suv") ParkingStat.setSUVsales(IsMem[3][7], CheckInTime[3][7], CheckOutTime[3][7]);
			else if(model[3][7] == "sports") ParkingStat.setSportsSales(IsMem[3][7], CheckInTime[3][7], CheckOutTime[3][7]);
		}else if((JButton)e.getSource() == (parkingLot[3][8])){
			System.out.println("Car removed!");
			parkingLot[3][8].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][8].setEnabled(false);
			CheckOutTime[3][8] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[8].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][8], model[3][8], CheckInTime[3][8], CheckOutTime[3][8]);
			if(model[3][8] == "sedan") ParkingStat.setSedanSales(IsMem[3][8], CheckInTime[3][8], CheckOutTime[3][8]);
			else if(model[3][8] == "suv") ParkingStat.setSUVsales(IsMem[3][8], CheckInTime[3][8], CheckOutTime[3][8]);
			else if(model[3][8] == "sports") ParkingStat.setSportsSales(IsMem[3][8], CheckInTime[3][8], CheckOutTime[3][8]);
		}else if((JButton)e.getSource() == (parkingLot[3][9])){
			System.out.println("Car removed!");
			parkingLot[3][9].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[3][9].setEnabled(false);
			CheckOutTime[3][9] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[3].getSlots()[9].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[3][9], model[3][9], CheckInTime[3][9], CheckOutTime[3][9]);
			if(model[3][9] == "sedan") ParkingStat.setSedanSales(IsMem[3][9], CheckInTime[3][9], CheckOutTime[3][9]);
			else if(model[3][9] == "suv") ParkingStat.setSUVsales(IsMem[3][9], CheckInTime[3][9], CheckOutTime[3][9]);
			else if(model[3][9] == "sports") ParkingStat.setSportsSales(IsMem[3][9], CheckInTime[3][9], CheckOutTime[3][9]);
		}
		
		
		else if((JButton)e.getSource() == (parkingLot[4][0])){
			System.out.println("Car removed!");
			parkingLot[4][0].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][0].setEnabled(false);
			CheckOutTime[4][0] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[0].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][0], model[4][0], CheckInTime[4][0], CheckOutTime[4][0]);
			if(model[4][0] == "sedan") ParkingStat.setSedanSales(IsMem[4][0], CheckInTime[4][0], CheckOutTime[4][0]);
			else if(model[4][0] == "suv") ParkingStat.setSUVsales(IsMem[4][0], CheckInTime[4][0], CheckOutTime[4][0]);
			else if(model[4][0] == "sports") ParkingStat.setSportsSales(IsMem[4][0], CheckInTime[4][0], CheckOutTime[4][0]);
		}else if((JButton)e.getSource() == (parkingLot[4][1])){
			System.out.println("Car removed!");
			parkingLot[4][1].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][1].setEnabled(false);
			CheckOutTime[4][1] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[1].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][1], model[4][1], CheckInTime[4][1], CheckOutTime[4][1]);
			if(model[4][1] == "sedan") ParkingStat.setSedanSales(IsMem[4][1], CheckInTime[4][1], CheckOutTime[4][1]);
			else if(model[4][1] == "suv") ParkingStat.setSUVsales(IsMem[4][1], CheckInTime[4][1], CheckOutTime[4][1]);
			else if(model[4][1] == "sports") ParkingStat.setSportsSales(IsMem[4][1], CheckInTime[4][1], CheckOutTime[4][1]);
		}else if((JButton)e.getSource() == (parkingLot[4][2])){
			System.out.println("Car removed!");
			parkingLot[4][2].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][2].setEnabled(false);
			CheckOutTime[4][2] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[2].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][2], model[4][2], CheckInTime[4][2], CheckOutTime[4][2]);
			if(model[4][2] == "sedan") ParkingStat.setSedanSales(IsMem[4][2], CheckInTime[4][2], CheckOutTime[4][2]);
			else if(model[4][2] == "suv") ParkingStat.setSUVsales(IsMem[4][2], CheckInTime[4][2], CheckOutTime[4][2]);
			else if(model[4][2] == "sports") ParkingStat.setSportsSales(IsMem[4][2], CheckInTime[4][2], CheckOutTime[4][2]);
		}else if((JButton)e.getSource() == (parkingLot[4][3])){
			System.out.println("Car removed!");
			parkingLot[4][3].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][3].setEnabled(false);
			CheckOutTime[4][3] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[3].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][3], model[4][3], CheckInTime[4][3], CheckOutTime[4][3]);
			if(model[4][3] == "sedan") ParkingStat.setSedanSales(IsMem[4][3], CheckInTime[4][3], CheckOutTime[4][3]);
			else if(model[4][3] == "suv") ParkingStat.setSUVsales(IsMem[4][3], CheckInTime[4][3], CheckOutTime[4][3]);
			else if(model[4][3] == "sports") ParkingStat.setSportsSales(IsMem[4][3], CheckInTime[4][3], CheckOutTime[4][3]);
		}else if((JButton)e.getSource() == (parkingLot[4][4])){
			System.out.println("Car removed!");
			parkingLot[4][4].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][4].setEnabled(false);
			CheckOutTime[4][4] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[4].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][4], model[4][4], CheckInTime[4][4], CheckOutTime[4][4]);
			if(model[4][4] == "sedan") ParkingStat.setSedanSales(IsMem[4][4], CheckInTime[4][4], CheckOutTime[4][4]);
			else if(model[4][4] == "suv") ParkingStat.setSUVsales(IsMem[4][4], CheckInTime[4][4], CheckOutTime[4][4]);
			else if(model[4][4] == "sports") ParkingStat.setSportsSales(IsMem[4][4], CheckInTime[4][4], CheckOutTime[4][4]);
		}else if((JButton)e.getSource() == (parkingLot[4][5])){
			System.out.println("Car removed!");
			parkingLot[4][5].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][5].setEnabled(false);
			CheckOutTime[4][5] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[5].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][5], model[4][5], CheckInTime[4][5], CheckOutTime[4][5]);
			if(model[4][5] == "sedan") ParkingStat.setSedanSales(IsMem[4][5], CheckInTime[4][5], CheckOutTime[4][5]);
			else if(model[4][5] == "suv") ParkingStat.setSUVsales(IsMem[4][5], CheckInTime[4][5], CheckOutTime[4][5]);
			else if(model[4][5] == "sports") ParkingStat.setSportsSales(IsMem[4][5], CheckInTime[4][5], CheckOutTime[4][5]);
		}else if((JButton)e.getSource() == (parkingLot[4][6])){
			System.out.println("Car removed!");
			parkingLot[4][6].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][6].setEnabled(false);
			CheckOutTime[4][6] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[6].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][6], model[4][6], CheckInTime[4][6], CheckOutTime[4][6]);
			if(model[4][6] == "sedan") ParkingStat.setSedanSales(IsMem[4][6], CheckInTime[4][6], CheckOutTime[4][6]);
			else if(model[4][6] == "suv") ParkingStat.setSUVsales(IsMem[4][6], CheckInTime[4][6], CheckOutTime[4][6]);
			else if(model[4][6] == "sports") ParkingStat.setSportsSales(IsMem[4][6], CheckInTime[4][6], CheckOutTime[4][6]);
		}else if((JButton)e.getSource() == (parkingLot[4][7])){
			System.out.println("Car removed!");
			parkingLot[4][7].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][7].setEnabled(false);
			CheckOutTime[4][7] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[7].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][7], model[4][7], CheckInTime[4][7], CheckOutTime[4][7]);
			if(model[4][7] == "sedan") ParkingStat.setSedanSales(IsMem[4][7], CheckInTime[4][7], CheckOutTime[4][7]);
			else if(model[4][7] == "suv") ParkingStat.setSUVsales(IsMem[4][7], CheckInTime[4][7], CheckOutTime[4][7]);
			else if(model[4][7] == "sports") ParkingStat.setSportsSales(IsMem[4][7], CheckInTime[4][7], CheckOutTime[4][7]);
		}else if((JButton)e.getSource() == (parkingLot[4][8])){
			System.out.println("Car removed!");
			parkingLot[4][8].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][8].setEnabled(false);
			CheckOutTime[4][8] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[8].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][8], model[4][8], CheckInTime[4][8], CheckOutTime[4][8]);
			if(model[4][8] == "sedan") ParkingStat.setSedanSales(IsMem[4][8], CheckInTime[4][8], CheckOutTime[4][8]);
			else if(model[4][8] == "suv") ParkingStat.setSUVsales(IsMem[4][8], CheckInTime[4][8], CheckOutTime[4][8]);
			else if(model[4][8] == "sports") ParkingStat.setSportsSales(IsMem[4][8], CheckInTime[4][8], CheckOutTime[4][8]);
		}else if((JButton)e.getSource() == (parkingLot[4][9])){
			System.out.println("Car removed!");
			parkingLot[4][9].setIcon(new ImageIcon("images/lot.png"));
			parkingLot[4][9].setEnabled(false);
			CheckOutTime[4][9] = PRSGameGUI.timer;
			r.setCheckOutCount(r.getCheckOutCount()+1);
			Tester.floors[4].getSlots()[9].setIsAvailable(true);
			r.setTotalSalesByMembership(IsMem[4][9], model[4][9], CheckInTime[4][9], CheckOutTime[4][9]);
			if(model[4][9] == "sedan") ParkingStat.setSedanSales(IsMem[4][9], CheckInTime[4][9], CheckOutTime[4][9]);
			else if(model[4][9] == "suv") ParkingStat.setSUVsales(IsMem[4][9], CheckInTime[4][9], CheckOutTime[4][9]);
			else if(model[4][9] == "sports") ParkingStat.setSportsSales(IsMem[4][9], CheckInTime[4][9], CheckOutTime[4][9]);
		}
		
		
		
		
		else if((JButton)e.getSource() == first){
			try{
				gameBackground.setIcon(new ImageIcon("images/1f.png"));
				for(k=0; k<10; k++){
					gameBackground.remove(parkingLot[1][k]);
					gameBackground.remove(parkingLot[2][k]);
					gameBackground.remove(parkingLot[3][k]);
					gameBackground.remove(parkingLot[4][k]);
					gameBackground.add(parkingLot[0][k]);
				}
				parkingContainer.add(gameBackground);
			}
			catch(Exception e1){
				System.out.println("Cannot load first floor.");
			}
		}		
		else if((JButton)e.getSource() == second){
			try{
				gameBackground.setIcon(new ImageIcon("images/2f.png"));
			 	for(k=0; k<10; k++){
			 		gameBackground.remove(parkingLot[0][k]);
			 		gameBackground.remove(parkingLot[2][k]);
			 		gameBackground.remove(parkingLot[3][k]);
			 		gameBackground.remove(parkingLot[4][k]);
					gameBackground.add(parkingLot[1][k]);
				}
				parkingContainer.add(gameBackground);
			}
			catch (Exception e2){
				System.out.println("Cannot load second floor.");
			}
		}
		else if((JButton)e.getSource() == third){
			try{
				gameBackground.setIcon(new ImageIcon("images/3f.png"));
				for(k=0; k<10; k++){
					gameBackground.remove(parkingLot[0][k]);
					gameBackground.remove(parkingLot[1][k]);
					gameBackground.remove(parkingLot[3][k]);
					gameBackground.remove(parkingLot[4][k]);
					gameBackground.add(parkingLot[2][k]);
				}
				parkingContainer.add(gameBackground);
			}
			catch (Exception e3){
				System.out.println("Cannot load third floor.");
			}
		}
		else if((JButton)e.getSource() == fourth){
			try{
				gameBackground.setIcon(new ImageIcon("images/4f.png"));
				for(k=0; k<10; k++){
					gameBackground.remove(parkingLot[0][k]);
					gameBackground.remove(parkingLot[1][k]);
					gameBackground.remove(parkingLot[2][k]);
					gameBackground.remove(parkingLot[4][k]);
					gameBackground.add(parkingLot[3][k]);
				}
				parkingContainer.add(gameBackground);
			}
			catch (Exception e4){
				System.out.println("Cannot load fourth floor.");
			}
		}
		else if((JButton)e.getSource() == fifth){
			try{
				gameBackground.setIcon(new ImageIcon("images/5f.png"));
				for(k=0; k<10; k++){
					gameBackground.remove(parkingLot[0][k]);
					gameBackground.remove(parkingLot[1][k]);
					gameBackground.remove(parkingLot[2][k]);
					gameBackground.remove(parkingLot[3][k]);
					gameBackground.add(parkingLot[4][k]);
				}
				parkingContainer.add(gameBackground);
			}
			catch (Exception e1){
				System.out.println("Cannot load fifth floor.");
			}
		}

	}
	
	public void run(){
		timer = 0;
		int counter = 0;
		while(Tester.flag==false){
			try{
				queue.setText("   Remaining cars in queue: "+r.getQueuedCarCount());
				currentCash.setText("   Current Cash: "+r.getCurrentCash());
				numOfFloors.setText("   Number of Floors: "+r.getFloorCount());
				numOfCheckIns.setText("   Check-ins: "+r.getCheckInCount());
				numOfCheckOuts.setText("   Check-outs: "+r.getCheckOutCount());
				totalMaintCost.setText("   Total Maintenance Cost: "+r.getTotalMaintainanceCost());
				aveEarnPerMin.setText("   Earnings/min: "+r.computeAverageEarningsPerMinute());
				salesPerSec.setText("   Sales/sec: "+r.computeSalesPerSecond());
				sedanSale.setText("   Sales from Sedan: "+r.getTotalSalesByCarModel("sedan"));
				suvSale.setText("   Sales from SUV: "+r.getTotalSalesByCarModel("suv"));
				SportsSale.setText("   Sales from Sports: "+r.getTotalSalesByCarModel("sports"));
				MemberSale.setText("   Sales from Members: "+r.getTotalSalesByMembership(true));
				NonMemberSale.setText("   Sales from Non-Members: "+r.getTotalSalesByMembership(false));
				
				timer++;
				Thread.sleep(1000);
				
				if(timer%1 == 0){
					counter += 1;
					Car.AddCar();
					if(counter == 12){
						Car.AddCar();
						counter = 0;
					}
					if(timer>2)
						parkingLot[Tester.QueuedCars[(Tester.current)-1].getFloor()][Tester.QueuedCars[(Tester.current)-1].getSlot()].setEnabled(true);
				}if(timer%2 == 0){
					Tester.QueuedCars[Tester.current].ParkCar();
					if(Tester.QueuedCars[Tester.current].getModel().equals("sedan") && Tester.QueuedCars[Tester.current].getIsMember()==false){
						parkingLot[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()].setIcon(new ImageIcon("images/sedan.jpg"));
						model[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = "sedan";
						IsMem[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = false;
						CheckInTime[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = timer;
					}else if(Tester.QueuedCars[Tester.current].getModel().equals("sedan") && Tester.QueuedCars[Tester.current].getIsMember()==true){
						parkingLot[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()].setIcon(new ImageIcon("images/sedanVIP.jpg"));
						model[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = "sedan";
						IsMem[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = true;
						CheckInTime[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = timer;
					}else if(Tester.QueuedCars[Tester.current].getModel().equals("suv") && Tester.QueuedCars[Tester.current].getIsMember()==false){
						parkingLot[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()].setIcon(new ImageIcon("images/suv.jpg"));
						model[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = "suv";
						IsMem[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = false;
						CheckInTime[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = timer;
					}else if(Tester.QueuedCars[Tester.current].getModel().equals("suv") && Tester.QueuedCars[Tester.current].getIsMember()==true){
						parkingLot[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()].setIcon(new ImageIcon("images/suvVIP.jpg"));
						model[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = "suv";
						IsMem[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = true;
						CheckInTime[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = timer;
					}else if(Tester.QueuedCars[Tester.current].getModel().equals("sports") && Tester.QueuedCars[Tester.current].getIsMember()==false){
						parkingLot[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()].setIcon(new ImageIcon("images/sports.jpg"));
						model[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = "sports";
						IsMem[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = false;
						CheckInTime[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = timer;
					}else if(Tester.QueuedCars[Tester.current].getModel().equals("sports") && Tester.QueuedCars[Tester.current].getIsMember()==true){
						parkingLot[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()].setIcon(new ImageIcon("images/sportsVIP.jpg"));
						model[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = "sports";
						IsMem[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = true;
						CheckInTime[Tester.QueuedCars[Tester.current].getFloor()][Tester.QueuedCars[Tester.current].getSlot()] = timer;
					}
					Tester.current++;
				}
			}
			catch(Exception e){
				System.out.print("");
			}
		}
	}

}
