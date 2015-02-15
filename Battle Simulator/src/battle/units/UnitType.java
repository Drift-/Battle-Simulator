package battle.units;

public class UnitType {
	
	public int melee_power, ranged_power;
	public int armour, m_speed, a_speed, health;
	public String name, type;
	
	public UnitType(String name, String type, int m_speed, int a_speed, int melee_power) {
		//init Melee classification of UnitType
		this.name = name;
		this.type = type;
		this.melee_power = melee_power;
		this.m_speed = m_speed;
		this.a_speed = a_speed;
		this.health = 25;
	}
	
	public UnitType(String name, String type, int m_speed, int a_speed, int ranged_power, int melee_power) {
		//init ranged classification of UnitType
		this.name = name;
		this.type = type;
		this.melee_power = melee_power;
		this.ranged_power = ranged_power;
		this.m_speed = m_speed;
		this.a_speed = a_speed;
		this.health = 25;
	}

}
