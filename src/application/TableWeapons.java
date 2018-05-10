package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableWeapons {

	private final SimpleStringProperty name;
	private final SimpleStringProperty attack;
	private final SimpleStringProperty element;
	private final SimpleStringProperty affinity;
	private final SimpleStringProperty slots;
	private final SimpleStringProperty rarity;
	
	public TableWeapons(String name, String attack, String element, String affinity, String slots, String rarity) {
		
		this.name = new SimpleStringProperty(name);
		this.attack = new SimpleStringProperty(attack);
		this.element = new SimpleStringProperty(element);
		this.affinity = new SimpleStringProperty(affinity);
		this.slots = new SimpleStringProperty(slots);
		this.rarity = new SimpleStringProperty(rarity);
	}

	
	//name
	public void setName(String weaponName) {
		name.set(weaponName);
	}
	
	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		// TODO Auto-generated method stub
		return name;
	}
	
	//attack
	public void setAttack(String weaponAttack) {
		attack.set(weaponAttack);
	}
	
	public String getAttack() {
		return attack.get();
	}
	
	public StringProperty attackProperty() {
		// TODO Auto-generated method stub
		return attack;
	}
	
	//element
	public void setElement(String weaponElement) {
		element.set(weaponElement);
	}
	
	public String getElement() {
		return element.get();
	}
	
	public StringProperty elementProperty() {
		return element;
	}

	//affinity
	public void setAffinity(String weaponAffinity) {
		affinity.set(weaponAffinity);
	}
	
	public String getAffinity() {
		return affinity.get();
	}
	
	public StringProperty affinityProperty() {
		return affinity;
	}
	
	//slots
	public void setSolts(String weaponSlots) {
		slots.set(weaponSlots);
	}
	
	public String getSlots() {
		return slots.get();
	}
	
	public StringProperty slotsProperty() {
		return slots;
	}
	
	//Rarity
	public void setRarity(String weaponRarity) {
		rarity.set(weaponRarity);
	}
	public String getRarity() {
		return rarity.get();
	}
	
	public StringProperty rarityProperty() {
		return rarity;
	}


	
}
