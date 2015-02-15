package battle.units;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import battle.BattleSim;

public class UnitManager {
	
	public static ArrayList<UnitType> units = new ArrayList<UnitType>();
	
	public static void loadUnits() {
		//get list of files in units folder
		File[] files = new File(BattleSim.res + "\\units").listFiles();
		
		//loop through files list
		for (int i = 0; i < files.length; i++) {
			//init input stream from file
			InputStream fis = null;
			try {
				fis = new FileInputStream(files[i]);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			//init buffered reader from input stream
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			
			//init variables which will hold the stats of unit until a new UnitType variable is created.
			String line = null, name = null, type = null;
			int m_speed = 0, a_speed = 0, melee_power = 0, ranged_power = 0;
			try {
				//loop through the unit file
				while ((line = br.readLine()) != null) {
					//assign values to stat variables if they are in the unit file
					if (line.contains("Name: ")) name = line.split("Name: ")[1];
					else if (line.contains("Type: ")) type = line.split("Type: ")[1];
					else if (line.contains("Movement Speed: ")) m_speed = Integer.parseInt(line.split("Movement Speed: ")[1]);
					else if (line.contains("Attack Speed: ")) a_speed = Integer.parseInt(line.split("Attack Speed: ")[1]);
					else if (line.contains("Melee Power: ")) melee_power = Integer.parseInt(line.split("Melee Power: ")[1]);
					else if (line.contains("Ranged Power: ")) ranged_power = Integer.parseInt(line.split("Ranged Power: ")[1]);
				}
				
				//checks classification of the UnitType that is being created
				if (type.equals("Ranged")) units.add(new UnitType(name, type, m_speed, a_speed, ranged_power, melee_power));
				else if (type.equals("Melee")) units.add(new UnitType(name, type, m_speed, a_speed, melee_power));
				
				//close the buffered reader
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
