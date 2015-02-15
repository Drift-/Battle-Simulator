package battle;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import battle.units.Unit;
import battle.units.UnitManager;

public class BattleSim {
	
	private static int output_counter = 7;
	public static File res;
	private static Random ran = new Random();
	
	public static ArrayList<Unit> team1 = new ArrayList<Unit>();
	public static ArrayList<Unit> team2 = new ArrayList<Unit>();
	
	public static void main(String[] args) {
		File file = new File("dir");
		res = new File(file.getAbsolutePath().toString().split("\\\\dir")[0] + "\\" + "res");
		UnitManager.loadUnits();
		
		//init units
		for (int i = 0; i < 10; i++) {
			team1.add(new Unit(UnitManager.units.get(ran.nextInt(3)), 0));
		}
		for (int i = 0; i < 10; i++) {
			team2.add(new Unit(UnitManager.units.get(ran.nextInt(3)), 1));
		}
		
		//run update loop every second
		while (true) {
			simBattle();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void simBattle() {
		//check if seven seconds have passed
		if (output_counter == 7) {
			//init variables that count the number of each type of unit that each team has.
			int sw = 0, ar = 0, ho = 0;
			//loop through team and count types of units.
			for (int i = 0; i < team1.size(); i++) {
				if (team1.get(i).name.equals("Archer")) ar ++;
				else if (team1.get(i).name.equals("Swordsman")) sw ++;
				else if (team1.get(i).name.equals("Horseman")) ho ++;
			}
			System.out.println("Team 0 has " + sw + " swordsman, " + ho + " horseman, and " + ar + "archers");
			
			//same as above, but for team2
			//init variables that count the number of each type of unit that each team has.
			sw = 0; ar = 0; ho = 0;
			//loop through team and count types of units.
			for (int i = 0; i < team2.size(); i++) {
				if (team2.get(i).name.equals("Archer")) ar ++;
				else if (team2.get(i).name.equals("Swordsman")) sw ++;
				else if (team2.get(i).name.equals("Horseman")) ho ++;
			}
			System.out.println("Team 1 has " + sw + " swordsman, " + ho + " horseman, and " + ar + "archers");
			
			output_counter = 0;
		}
		
		//checks whether or not team is still alive
		if (team1.size() >= 1) {
			for (Unit u : team1) {
				//loops through units in team and runs the tick function
				u.tick();
			}
		}
		
		//same as above but for team2
		if (team2.size() >= 1) {
			for (Unit u : team2) {
				//loops through units in team and runs the tick function
				u.tick();
			}
		}
		
		output_counter++;
	}

}
