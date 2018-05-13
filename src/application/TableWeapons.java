package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**@author remjamd
 * Class for table on main page of the window */
public class TableWeapons {

	private final SimpleStringProperty colName;
	private final SimpleStringProperty colAttack;
	private final SimpleStringProperty colElement;
	private final SimpleStringProperty colAffinity;
	private final SimpleStringProperty colSlots;
	private final SimpleStringProperty colRarity;
	
	public TableWeapons(String name, String attack, String element, String affinity, String slots, String rarity) {
		
		this.colName = new SimpleStringProperty(name);
		this.colAttack = new SimpleStringProperty(attack);
		this.colElement = new SimpleStringProperty(element);
		this.colAffinity = new SimpleStringProperty(affinity);
		this.colSlots = new SimpleStringProperty(slots);
		this.colRarity = new SimpleStringProperty(rarity);
	}

	
	//name
	public void setColName(String weaponName) {
		colName.set(weaponName);
	}
	
	public String getColName() {
		return colName.get();
	}

	public StringProperty nameProperty() {
		// TODO Auto-generated method stub
		return colName;
	}
	
	//attack
	public void setColAttack(String weaponAttack) {
		colAttack.set(weaponAttack);
	}
	
	public String getColAttack() {
		return colAttack.get();
	}
	
	public StringProperty attackProperty() {
		// TODO Auto-generated method stub
		return colAttack;
	}
	
	//element
	public void setColElement(String weaponElement) {
		colElement.set(weaponElement);
	}
	
	public String getColElement() {
		return colElement.get();
	}
	
	public StringProperty elementProperty() {
		return colElement;
	}

	//affinity
	public void setColAffinity(String weaponAffinity) {
		colAffinity.set(weaponAffinity);
	}
	
	public String getColAffinity() {
		return colAffinity.get();
	}
	
	public StringProperty affinityProperty() {
		return colAffinity;
	}
	
	//slots
	public void setColSolts(String weaponSlots) {
		colSlots.set(weaponSlots);
	}
	
	public String getSlots() {
		return colSlots.get();
	}
	
	public StringProperty slotsProperty() {
		return colSlots;
	}
	
	//Rarity
	public void setColRarity(String weaponRarity) {
		colRarity.set(weaponRarity);
	}
	public String getColRarity() {
		return colRarity.get();
	}
	
	public StringProperty rarityProperty() {
		return colRarity;
	}


	
}
