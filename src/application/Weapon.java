/**
 * 
 */
package application;

/**
 * @author remjamd
 *
 */
public class Weapon {

	protected String wepType;
	protected String name;
	protected String attack;
	protected String element;
	protected String affinity;
	protected String slots;
	protected String rarity;
	
	public Weapon(String wepType, String name, String attack, String element, String affinity, String slots, String rarity) {
		
		this.wepType = wepType;
		this.name = name;
		this.attack = attack;
		this.element = element;
		this.affinity = affinity;
		this.slots = slots;
		this.rarity = rarity;
	}

	public String getWepType() {
		return wepType;
	}

	public void setWepType(String wepType) {
		this.wepType = wepType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttack() {
		return attack;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getAffinity() {
		return affinity;
	}

	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}

	public String getSlots() {
		return slots;
	}

	public void setSlots(String slots) {
		this.slots = slots;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	
	
}
