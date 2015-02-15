package battle.units;

import java.util.ArrayList;
import java.util.Random;

import battle.BattleSim;

public class Unit {
	
	public int melee_power, ranged_power;
	public int team, armour, m_speed, a_speed, health, fullhealth;
	public String name, type;
	
	private Random ran = new Random();
	
	private int wait = 0;
	
	public Unit (UnitType type, int team) {
		this.type = type.type;
		name = type.name;
		armour = type.armour;
		m_speed = type.m_speed;
		a_speed = type.a_speed;
		health = type.health;
		fullhealth = type.health;
		melee_power = type.melee_power;
		ranged_power = type.ranged_power;
		wait = a_speed;
	}

	public void tick() {
		//update code
		//if the unit is ready (wait long enough to recharge) then run attack function
		if (wait == a_speed) {
			wait = 0;
			attack();
		}
		wait++;
	}
	
	private void attack() {
		//init defender variable (this stores which enemy unit the current unit is attacking)
		Unit defender = null;
		
		//checks team of current unit (won't be needed in later stages)
		if (team == 0) {
			//init variables which store the current choice for who the unit is going to attack
			int num2 = 0, melee_power = 999, defender_power = 0;
			ArrayList<Integer> defenders = new ArrayList<Integer>();
			for (Unit u : BattleSim.team2) {
				if (type.equals("Ranged")) {
					//init and assign value to power variable which stores power of possible defender
					int power = 0;
					power = u.melee_power;
					//checks if possible defender's power is higher than previous defender choice
					if (power > defender_power) {
						//removes current defender choice and adds new one
						defenders.add(num2);
						defender_power = power;
					}
				} else {
					//init ratio variable
					double r = 0;
					if (u.type.equals("Ranged")) {
						//if possible defender is a ranged unit, calculate
						r = (double)(u.ranged_power)/(double)fullhealth;
						r = r/0.125;
					} else if (u.type.equals("Melee")) {
						//if possible defender is a melee unit, find ratio of current units melee power to possible defender's melee power.
						r = ((double)melee_power/(double)u.melee_power);
						//modify and round the ratio.
						r = ((r / 0.25));
						r = Math.round(r);
						//checks whether or not unmodified ratio is greater than 4.
						if (r > 4) {
							//adjusts ratin with health of possible defender
							r += 1 - (int)(100*((double)u.health/(double)u.fullhealth)/51);
						} else {
							//adjusts ratio with health of possible defender
							r -= (int)(100*((double)u.health/(double)u.fullhealth)/51);
						}
						//checks how far away from 5 the ratio is.
						r = Math.abs(r - 5);
					}
					
					//checks current ratio to the current possible defenders
					if (r < melee_power) {
						//if ratio is better, then clear the old possible defender and add the new one
						defenders.clear();
						defenders.add(num2);
						melee_power = (int)r;
					} else if (r == melee_power) {
						//if ratio is equal, add it to the possible defender arraylist
						defenders.add(num2);
					}
				}
				num2++;
			}
			
			//if there are units left on the team, choose a random unit from the possible defender arraylist and turn it into the actual defender
			if (num2 != 0) {
				defender = BattleSim.team2.get(defenders.get(ran.nextInt(defenders.size())));
			}
		} else {
			//the team2 code is the same as the team1 code
			//init variables which store the current choice for who the unit is going to attack
			int num2 = 0, melee_power = 999, defender_power = 0;
			ArrayList<Integer> defenders = new ArrayList<Integer>();
			for (Unit u : BattleSim.team1) {
				if (type.equals("Ranged")) {
					//init and assign value to power variable which stores power of possible defender
					int power = 0;
					power = u.melee_power;
					//checks if possible defender's power is higher than previous defender choice
					if (power > defender_power) {
						//removes current defender choice and adds new one
						defenders.add(num2);
						defender_power = power;
					}
				} else {
					//init ratio variable
					double r = 0;
					if (u.type.equals("Ranged")) {
						//if possible defender is a ranged unit, calculate
						r = (double)(u.ranged_power)/(double)fullhealth;
						r = r/0.125;
					} else if (u.type.equals("Melee")) {
						//if possible defender is a melee unit, find ratio of current units melee power to possible defender's melee power.
						r = ((double)melee_power/(double)u.melee_power);
						//modify and round the ratio.
						r = ((r / 0.25));
						r = Math.round(r);
						//checks whether or not unmodified ratio is greater than 4.
						if (r > 4) {
							//adjusts ratin with health of possible defender
							r += 1 - (int)(100*((double)u.health/(double)u.fullhealth)/51);
						} else {
							//adjusts ratio with health of possible defender
							r -= (int)(100*((double)u.health/(double)u.fullhealth)/51);
						}
						//checks how far away from 5 the ratio is.
						r = Math.abs(r - 5);
					}
					
					//checks current ratio to the current possible defenders
					if (r < melee_power) {
						//if ratio is better, then clear the old possible defender and add the new one
						defenders.clear();
						defenders.add(num2);
						melee_power = (int)r;
					} else if (r == melee_power) {
						//if ratio is equal, add it to the possible defender arraylist
						defenders.add(num2);
					}
				}
				num2++;
			}
			
			//if there are units left on the team, choose a random unit from the possible defender arraylist and turn it into the actual defender
			if (num2 != 0) {
				defender = BattleSim.team1.get(defenders.get(ran.nextInt(defenders.size())));
			}
		}
		
		//if there are no defenders on opposing team, output that the current unit's team won the game
		if (defender == null) {
			System.out.println("Team " + team + " won!!!");
			return;
		}
		
		//checks classification of attacking unit
		if (type.equals("Melee")) {
			//if possible defender is a melee unit, find ratio of current units melee power to possible defender's melee power.
			double r = ((double)melee_power/(double)defender.melee_power);
			//init variables which track the min and max damage that can be dealt by the defender and the attacker
			double y_min_d, y_max_d, t_min_d, t_max_d;
			
			//checks if attacker of defender is more powerful
			if (melee_power > defender.melee_power) {
				//calculate min and max damage that can be dealt by the defender and the attacker
				y_min_d = 4.0 * r;
				y_max_d = y_min_d * 2;
				t_min_d = 4.0 / r;
				t_max_d = t_min_d * 2;
			} else {
				//calculate min and max damage that can be dealt by the defender and the attacker
				y_min_d = 4.0 / r;
				y_max_d = y_min_d * 2;
				t_min_d = 4.0 * r;
				t_max_d = t_min_d * 2;
			}
			
			//init variables which determine actual damage given off by the attacker and defender
			double y_d, t_d;
			//calculate damage dealt by attacker by using min and max values
			y_d = y_min_d + (y_max_d - y_min_d) * ran.nextDouble();
			//calculate damage dealt by defender by using min and max values and the readiness of the defender
			t_d = t_min_d + (t_max_d - t_min_d) * ran.nextDouble() * ((double)defender.wait / (double)defender.a_speed);
			//decrease the readiness of the defender and the health of the defender and attacker
			defender.wait /= 2;
			defender.health -= y_d;
			health -= t_d;
			//System.out.print("Team " + team + " " + name + " attacked " + defender.name + " for " + y_d + " damage.");
			//System.out.println(" They attacked back for " + t_d + ".");
			/*System.out.print(name + " vs " + defender.name + "(" + r + "): '" + (int)y_min_d + " " + (int)y_d + " " + (int)y_max_d);
			System.out.print("' vs '" + (int)t_min_d + " " + (int)t_d + " " + (int)t_max_d + "'");
			System.out.println("(" + defender.wait + "/" + defender.a_speed + " = " + (int)((double)defender.wait / (double)defender.a_speed) + ")");*/

		} else if (type.equals("Ranged")) {
			//if unit is a ranged unit, calculate the damage deal and then deal it.
			double y_min_d = (4/5) * ranged_power;
			double y_max_d = (6/5) * ranged_power;
			double y_d = y_min_d + (y_max_d - y_min_d) * ran.nextDouble();
			defender.health -= y_d;
			//System.out.println("Team " + team + " " + name + " attacked " + defender.name + " for " + (int)y_d + " damage.");
		}
		
		//check if defender is dead
		if (defender.health <= 0) {
			if (defender.team == 0) {
				//remove dead unit
				BattleSim.team1.remove(defender);
			} else {
				//remove dead unit
				BattleSim.team2.remove(defender);
			}
			
			//System.out.println("Team " + defender.team + " " + defender.name + " died!");
		}
	}

}
