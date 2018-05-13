/**
 * 
 */
package application;

/**
 * @author remjamd
 *IMPORTANT
 *See line 218 for important note regarding the database
 */

import java.io.IOException;
import java.net.URL;
import java.sql.*;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tab;
import javafx.beans.property.SimpleStringProperty;

public class AppController implements Initializable {

	@FXML
    private TableView<TableWeapons> dataTable;

    @FXML
    private TableColumn<TableWeapons, String> columnName;
    
    @FXML
    private TableColumn<TableWeapons, String> columnAttack;

    @FXML
    private TableColumn<TableWeapons, String> columnElement;

    @FXML
    private TableColumn<TableWeapons, String> columnAffinity;

    @FXML
    private TableColumn<TableWeapons, String> columnSlots;

    @FXML
    private TableColumn<TableWeapons, String> columnRarity;

    @FXML
    private ComboBox<String> WeaponBox;

    @FXML
    private ComboBox<String> ElementBox;
    
    @FXML
    private Label infoLabel;

    @FXML
    private Button searchButton;

    @FXML
    private Tab detailPane;

    @FXML
    private TextField entryBox;

    @FXML
    private Button nameSearchButton;

    @FXML
    private ComboBox<String> augmentBox1;

    @FXML
    private ComboBox<String> augmentBox2;

    @FXML
    private ComboBox<String> augmentBox3;

    @FXML
    private Button calcButton;

    @FXML
    private Label attackLabel2;

    @FXML
    private Label affinityLabel2;

    @FXML
    private Label attackLabel1;

    @FXML
    private Label affinityLabel1;

    @FXML
    private Label elementLabel;

    @FXML
    private Label slotsLabel;

    @FXML
    private Label rarityLabel;
    
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label typeLabel;
    
    @FXML
    private Label specialLabel;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private TableView<TableMaterials> materialTable;

    @FXML
    private TableColumn<TableMaterials, String> columnMaterial;

    @FXML
    void calculateAtk(ActionEvent event) {

    	errorLabel.setVisible(false);
    	calculateAugments();
    }
    
    /**Action Event occurs when the search button on the 
     * weapon details tab is clicked */
    @FXML
    void nameSearch(ActionEvent event) throws IOException {

    	errorLabel.setVisible(false);
    	attackLabel2.setText("Attack:");
    	affinityLabel2.setText("Affinity:");
    	setLabels();
    	tableSetup();
    }
    

    
    
    //Action Event to fill tables
    @FXML
    void clicked(ActionEvent event) throws IOException, SQLException{
    	
    	infoLabel.setVisible(false);
    	searchDatabase();
    }
    

    //Initialize window
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		infoLabel.setVisible(false);
		errorLabel.setVisible(false);
		augmentBox1.setVisible(false);
		augmentBox2.setVisible(false);
		augmentBox3.setVisible(false);
		calcButton.setVisible(false);
		
		ObservableList<String> options1 = 
    		    FXCollections.observableArrayList(
    		    		"Great Sword",
    		    		"Long Sword",
    		    		"Sword",
    		    		"Dual Blades",
    		    		"Hammer",
    		    		"Hunting Horn",
    		    		"Lance",
    		    		"Gunlance",
    		    		"Switch Axe",
    		    		"Charge Blade",
    		    		"Insect Glaive",
    		    		"Light Bowgun",
    		    		"Heavy Bowgun",
    		    		"Bow"
    		    		);
    	
		ObservableList<String> options2 = 
    		    FXCollections.observableArrayList(
    		    		"n/a",
    		    		"Fire",
    		    		"Water",
    		    		"Thunder",
    		    		"Ice",
    		    		"Dragon",
    		    		"Poison",
    		    		"Paralysis",
    		    		"Sleep",
    		    		"Blast");
		
		ObservableList<String> options3 = 
    		    FXCollections.observableArrayList("Attack", "Affinity");
		
    	WeaponBox.getItems().addAll(options1);
    	ElementBox.getItems().addAll(options2);
    	augmentBox1.getItems().addAll(options3);
    	augmentBox2.getItems().addAll(options3);
    	augmentBox3.getItems().addAll(options3);
    	
    	ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    	
    	/**un-comment the following section of code if you would like to 
    	 * re-create the database. This will scrape all of the 
    	 * webpages, delete the old table, create a new table, and
    	 * finally populate the new table with the scraped information.
    	 * CAUTION! This may take 10+ minutes to complete.
    	 * */
    	
    	/*try {
    		
    	try {
			weapons = createObjects();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    	//System.out.println(weapons.get(1).name);
    	
    	//Insert scraped information to database
    	try {
			createDatabase(weapons);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    	}
    	catch (Exception i) {
    		System.out.println("Connectivity error. Please try loading again.");
    	}*/
	}

	
	
	//Methods
	/** Method to find the number of slots each weapon
	 * has by scraping the site
	 * @param wepDoc The url for the webpage of the weapon currently being scraped*/
	public String findSlots(Document wepDoc) 
	{
		Elements slots3 = wepDoc.getElementsByClass("zmdi zmdi-n-3-square");
		Elements slots2 = wepDoc.getElementsByClass("zmdi zmdi-n-2-square");
		Elements slots1 = wepDoc.getElementsByClass("zmdi zmdi-n-1-square");
		Elements slotsMinus = wepDoc.getElementsByClass("zmdi zmdi-minus");
		
		int largeSlots = slots3.size();
		//System.out.println(largeSlots);
		int medSlots = slots2.size();
		//System.out.println(medSlots);
		int smallSlots = slots1.size();
		//System.out.println(smallSlots);
		//int noSlots = slotsMinus.size();
		//System.out.println(noSlots);
		
		int totalSlots = largeSlots + medSlots + smallSlots;
		String slotsValue = Integer.toString(totalSlots);
		return slotsValue;
	}
    
    /**Creates and stores all the scraped weapon data to objects using the 
     * Weapon class and returns them to be stored in the database
     * @return ArrayList of the stored objects */
    public ArrayList<Weapon> createObjects() throws IOException{

    	ArrayList<Weapon> weapons = new ArrayList<Weapon>(); 
    	String[] choices = {"great-sword","long-sword","sword","dual-blades","hammer","hunting-horn","lance","gunlance",
    							"switch-axe","charge-blade","insect-glaive","light-bowgun", "heavy-bowgun", "bow"};
    	
    	try {
    		
    	
    	for(int i = 0; i < choices.length; i++) {
    		
    		//create url
    		String selectedWep = choices[i];
    		String url = "https://mhworld.kiranico.com/" + selectedWep;
    	
    		//Connect using Jsoup
    		Document doc = Jsoup.connect(url).get();  
    		Elements links = doc.select("a[href]");  
    		for (Element link : links) {  
    			//System.out.println("\nlink : " + link.attr("href"));
    			if (link.attr("href").contains("weapon"))
    			{
    				String attack = "n/a";
    				String affinity = "0%";
    				String slots = "n/a";
    				String element = " ";
    				String rarity = "n/a";
        		
    				String wepURL = (link.attr("href"));
    				Document wepDoc = Jsoup.connect(wepURL).get();
    				//order: attack, affinity, slots, element, rare
    				Elements stats = wepDoc.getElementsByClass("lead");
    				int total = stats.size();
        		
    				String wepType = selectedWep;
    				String name = link.text();
    				name = name.replace("'","-");
    				name = name.replace('"', '-');
    				attack = stats.first().text();
    				slots = findSlots(wepDoc); //method
    				rarity = stats.last().text();
	        		//System.out.println(total);
	        		if(selectedWep.equals("hunting-horn") || selectedWep.equals("gunlance") || selectedWep.equals("switch-axe") || selectedWep.equals("charge-blade") || selectedWep.equals("insect-glaive"))
	        		{
	        			if (total == 4) 
	        			{
	        				weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        			
	        			else if (total == 5) 
	        			{
	        				String checkAffn = stats.get(1).text();
	            			if(checkAffn.contains("%")) 
	            			{
	            				affinity = stats.get(1).text();
	            			}
	            			element = stats.get(2).text();
	            			weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        			
	        			else if (total == 6) 
	        			{
	        				String checkAffn = stats.get(1).text();
	            			if(checkAffn.contains("%")) 
	            			{
	            				affinity = stats.get(1).text();
	            			}
	            			element = stats.get(3).text();
	            			weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        			
	        			else if (total == 7) 
	        			{
	        				affinity = stats.get(2).text();
	        				element = stats.get(4).text();
	        				weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        			else 
	        			{
	        				weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        		}
	        		
	        		if (selectedWep.equals("light-bowgun") || selectedWep.equals("heavy-bowgun")) 
	        		{
	        			if (total == 5) 
	        			{
	        				weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        			
	        			else if (total == 6) 
	        			{
	        				String checkAffn = stats.get(1).text();
	            			if(checkAffn.contains("%")) 
	            			{
	            				affinity = stats.get(1).text();
	            			}
	            			weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        			
	        			else if (total == 7) 
	        			{
	        				affinity = stats.get(2).text();
	        				weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        			
	        			else 
	        			{
	        				weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	        			}
	        		}
	        		
	        		if (selectedWep.equals("great-sword") || selectedWep.equals("long-sword") || selectedWep.equals("sword") || selectedWep.equals("dual-blades") || selectedWep.equals("hammer") || selectedWep.equals("lance") || selectedWep.equals("bow"))
	        		{
	        			if(total == 3) 
	            		{
	        				weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	            		}
	            		else if(total == 4) 
	            		{
	            			
	            			String checkAffn = stats.get(1).text();
	            			if(checkAffn.contains("%")) 
	            			{
	            				affinity = stats.get(1).text();
	            			}
	            			element = stats.get(2).text();
	            			weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	            		}
	            		else if(total == 5) 
	            		{
	            			String checkAffn = stats.get(1).text();
	            			if(checkAffn.contains("%")) 
	            			{
	            				affinity = stats.get(1).text();
	            			}
	            			element = stats.get(3).text();
	            			weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	            		}
	            		else if(total == 6) 
	            		{
	            			affinity = stats.get(2).text();
	            			element = stats.get(4).text();
	            			weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	            		}
	            		else 
	            		{
	            			weapons.add(new Weapon(wepType, name, attack, element, affinity, slots, rarity));
	            		}
	        		}
	        	}
	        }  
    	}
    	}
    	catch (Exception e) {
    		System.out.println("Failed to Connect. Please re-load the program and try again.");
    		infoLabel.setVisible(true);
    		infoLabel.setText("Connection Error. Please close and re-load the program");
    	}
    	return weapons;
    }
    
    
    /**Creates an online database with db4free.net
     * @param the arraylist created from the createObjects method */
    public void createDatabase(ArrayList<Weapon> weapons) throws SQLException{
    	
    	String DB_URL = "jdbc:mysql://db4free.net:3306/mhw_weapons";
    	String USERNAME = "jdremer";
    	String PASSWORD = "testpassword";
    	try {
    		
    	
    	try {
			Connection MyConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			System.out.println("Connected");
			
			//delete previous table
			try {
				Statement drop_stmt = MyConn.createStatement();
				String drop = "DROP TABLE WeaponsTable";
				drop_stmt.executeUpdate(drop);
				System.out.println("Deleted table in given database");
			}
			catch(Exception i) {
				System.out.println("Could not find table to delete.");
			}
			
			//create new table
			Statement stmt = MyConn.createStatement();
			String create = "CREATE TABLE WeaponsTable" +
                 	" (number INTEGER not NULL, " + 
                 	" wepType VARCHAR(255), " +
                 	" name VARCHAR(255), " +
                 	" attack VARCHAR(255)," +
                 	" element VARCHAR(255)," +
                 	" affinity VARCHAR(255)," +
                 	" slots VARCHAR(255)," +
                 	" rarity VARCHAR(255)," +
                 	" PRIMARY KEY ( number ))";
			stmt.executeUpdate(create);
			System.out.println("Created table in the given database.");
			
			//write data to the new table
			for (int x = 0; x < weapons.size(); x++) {
				
				int number = x;
				String wepType = weapons.get(x).wepType;
				String name = weapons.get(x).name;
				String attack = weapons.get(x).attack;
				String element = weapons.get(x).element;
				String affinity = weapons.get(x).affinity;
				String slots = weapons.get(x).slots;
				String rarity = weapons.get(x).rarity;
				
				String insert = "INSERT INTO WeaponsTable " +
						"VALUES ('"+number+"' , '"+wepType+"' , '"+name+"' , '"+attack+"' , '"+element+"' , '"+affinity+"' , '"+slots+"' , '"+rarity+"' )";
					stmt.executeUpdate(insert);
			}
			
			System.out.println("Inserted records to the database.");
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
    	}
    	catch(Exception e) {
    		System.out.println("Failed to Connect. Please re-load the program and try again.");
    		infoLabel.setVisible(true);
    		infoLabel.setText("Connection Error. Please close and re-load the program");
    		
    	}
    }
    
    
    /**searches through the database based on criteria set by the user.
     * after searching, data is written to the table 
     * No parameters or return*/
    
    public void searchDatabase() throws SQLException{
    	
    	ObservableList<TableWeapons> sample = FXCollections.observableArrayList();
    	
    	//Initialize columns
    	columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	columnAttack.setCellValueFactory(cellData -> cellData.getValue().attackProperty());
    	columnElement.setCellValueFactory(cellData -> cellData.getValue().elementProperty());
    	columnAffinity.setCellValueFactory(cellData -> cellData.getValue().affinityProperty());
    	columnSlots.setCellValueFactory(cellData -> cellData.getValue().slotsProperty());
    	columnRarity.setCellValueFactory(cellData -> cellData.getValue().rarityProperty());
    	
    	String DB_URL = "jdbc:mysql://db4free.net:3306/mhw_weapons";
    	String USERNAME = "jdremer";
    	String PASSWORD = "testpassword";
    	try {
    	try {
    		Connection MyConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    		Statement stmt = MyConn.createStatement();
    		
    		//Gets weapon type from combo box for the following query
    		String wepChoice = WeaponBox.getValue();
    		String elmChoice = ElementBox.getValue();
    		wepChoice = wepChoice.toLowerCase();
    		wepChoice = wepChoice.replace(' ', '-');
    		
    		String select = "SELECT wepType, name, attack, element, affinity, slots, rarity FROM WeaponsTable WHERE wepType = '" + wepChoice + "'" ;
			ResultSet rs = stmt.executeQuery(select);
			
			while (rs.next()) {
				
				String pickedWep = rs.getString("name");
				String pickedAtk = rs.getString("attack");
				String pickedElm = rs.getString("element");
				String pickedAffn = rs.getString("affinity");
				String pickedSlots = rs.getString("slots");
				String pickedRare = rs.getString("rarity");
				
				//Sort data by inputed Element
				if(pickedElm.contains(elmChoice)) 
				{
					sample.add(new TableWeapons(pickedWep, pickedAtk, pickedElm, pickedAffn, pickedSlots, pickedRare));
				}
				if(elmChoice.equals("n/a")) 
				{
					sample.add(new TableWeapons(pickedWep, pickedAtk, pickedElm, pickedAffn, pickedSlots, pickedRare));
				}
			}
			
			rs.close();
			dataTable.setItems(sample);
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	}
    	catch(Exception e) {
    		/**Print error message if query fails. Error will occur if event occurs with default values 
    		 * in combo box*/
    		System.out.println("An error has occured. Please check your connection and\n ensure combo boxes are not blank");
    		infoLabel.setVisible(true);
    		infoLabel.setText("Error. Please select a weapon type/element and check your connection.");
    	}
    }
    
    /**Sets the labels on the weapon details page to the correct strings
     * based on the name entered by the user
     * No params or return */
    public void setLabels(){
    	
    	//re-hide boxes and button
    	infoLabel.setVisible(false);
		augmentBox1.setVisible(false);
		augmentBox2.setVisible(false);
		augmentBox3.setVisible(false);
		calcButton.setVisible(false);
    	
    	String DB_URL = "jdbc:mysql://db4free.net:3306/mhw_weapons";
    	String USERNAME = "jdremer";
    	String PASSWORD = "testpassword";
    	String enteredName = entryBox.getText();
    	enteredName = enteredName.replace("'", "-");
    	enteredName = enteredName.replace('"', '-');
    	
    	try {
    		Connection MyConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    		Statement stmt = MyConn.createStatement();
    		
    		//Looking for user inputed weapon name
    		String select = "SELECT wepType, name, attack, element, affinity, slots, rarity FROM WeaponsTable WHERE name = '" + enteredName + "'" ;
			ResultSet rs = stmt.executeQuery(select);
			
			while (rs.next()) {
				
				//Adjust labels to read correct information
				String pickedType = rs.getString("wepType");
				String pickedWep = rs.getString("name");
				String pickedAtk = rs.getString("attack");
				String pickedElm = rs.getString("element");
				String pickedAffn = rs.getString("affinity");
				String pickedSlots = rs.getString("slots");
				String pickedRare = rs.getString("rarity");
				
				pickedWep = pickedWep.replace("-", "'");
				pickedType = pickedType.replace('-', ' ');
				pickedType = pickedType.toUpperCase();
				typeLabel.setText(pickedType);
				nameLabel.setText("Name: " + pickedWep);
				attackLabel1.setText("Attack: " + pickedAtk);
				elementLabel.setText("Element: " + pickedElm);
				affinityLabel1.setText("Affinity: " + pickedAffn);
				slotsLabel.setText("Slots: " + pickedSlots);
				rarityLabel.setText("Rarity: " + pickedRare);
				
				//Show combo boxes if rarity is 6, 7, or 8
				if (pickedRare.equals("6")) 
				{
					augmentBox1.setVisible(true);
					augmentBox2.setVisible(true);
					augmentBox3.setVisible(true);
					calcButton.setVisible(true);
				}
				
				if (pickedRare.equals("7")) 
				{
					augmentBox1.setVisible(true);
					augmentBox2.setVisible(true);
					calcButton.setVisible(true);
				}
				
				if (pickedRare.equals("8")) 
				{
					augmentBox1.setVisible(true);
					calcButton.setVisible(true);
				}
			}
			
			
			
			rs.close();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	
  
    }
    
    /**Sets up the materials table in the weapon details tab
     * No params or return */
    public void tableSetup() throws IOException {
    	
    	//Initialize Column
    	columnMaterial.setCellValueFactory(cellData -> cellData.getValue().materialProperty());
    	ObservableList<TableMaterials> sample2 = FXCollections.observableArrayList();
    	
    	String name = entryBox.getText();
    	name = name.replace('"', '!');
    	name = name.replace("!", "'");
    	name = name.replace("'","");
    	name = name.replace(' ', '-');
    	name = name.toLowerCase();
    	
    	String url = "https://mhworld.kiranico.com/weapon/" + name;
    	try {
    		
    	Document doc = Jsoup.connect(url).get();
    	Elements table = doc.getElementsByClass("table table-sm");
    	Elements mat = table.select("tr");
    	

    		for (int x = 0; x < mat.size(); x++) 
        	{
        		String material = mat.get(x).text();
        		sample2.add(new TableMaterials(material));
        	}
    	materialTable.setItems(sample2);
    	
    	Elements special = doc.getElementsByClass("lead");
    	int classSize = special.size();
    	int a = classSize - 2;
    	String value = special.get(a).text();
    	specialLabel.setText("Special: " + value);
    	}	
    	// Throws error message in window if weapon is not found
    		catch(Exception e) {
    			System.out.println("Unable to find weapon. Check spelling and try again.");
    			errorLabel.setVisible(true);
    			errorLabel.setText("Error. Weapon Not Found.");
    		}
    }
    
    /**calculates the new stats of the weapon after
     * user inputed modifications */
    public void calculateAugments() {
    	
    	try {
    	//get attack values
    	String atk = attackLabel1.getText();
    	atk = atk.replace("Attack: ", "");
    	atk = atk.replace(" ", "");
    	String[] attack = atk.split("\\W+");
    	double bloat = Double.parseDouble(attack[0]);
    	int raw = Integer.parseInt(attack[1]);
    	
    	//get affinity values
    	String affn = affinityLabel1.getText();
    	affn = affn.replace("Affinity: ", "");
    	affn = affn.replace("+", "");
    	affn = affn.replace("%", "");
    	int affinity = Integer.parseInt(affn);
    	
    	//get rarity value
    	String rare = rarityLabel.getText();
    	String rarity = rare.replace("Rarity: ", "");
    	
    	//get weapon type
    	String wType = typeLabel.getText();
    	wType = wType.toLowerCase();
    	
    	//get number of augments to perform
    	int counter1 = 0;
    	int counter2 = 0;
    	
    	int affnToAdd = 0;
    	int atkToAdd = 0;
    	
    	if (rarity.equals("6")) {
    		
    		String aug1 = augmentBox1.getValue();
    		String aug2 = augmentBox2.getValue();
    		String aug3 = augmentBox3.getValue();
    		
    		if (aug1.equals("Attack")) 
    		{
    			counter1 = counter1 + 1;
    		}
    		if (aug1.equals("Affinity")) 
    		{
    			counter2 = counter2 + 1;
    		}
    		
    		if (aug2.equals("Attack")) 
    		{
    			counter1 = counter1 + 1;
    		}
    		if (aug2.equals("Affinity")) 
    		{
    			counter2 = counter2 + 1;
    		}
    		
    		if (aug3.equals("Attack")) 
    		{
    			counter1 = counter1 + 1;
    		}
    		if (aug3.equals("Affinity")) 
    		{
    			counter2 = counter2 + 1;
    		}
 
    	}
    	if (rarity.equals("7")) {
    		
    		String aug1 = augmentBox1.getValue();
    		String aug2 = augmentBox2.getValue();
    		
    		if (aug1.equals("Attack")) 
    		{
    			counter1 = counter1 + 1;
    		}
    		if (aug1.equals("Affinity")) 
    		{
    			counter2 = counter2 + 1;
    		}
    		
    		if (aug2.equals("Attack")) 
    		{
    			counter1 = counter1 + 1;
    		}
    		if (aug2.equals("Affinity")) 
    		{
    			counter2 = counter2 + 1;
    		}
    	}
    	if (rarity.equals("8")) {
    		
    		String aug1 = augmentBox1.getValue();
    		
    		if (aug1.equals("Attack")) 
    		{
    			counter1 = counter1 + 1;
    		}
    		if (aug1.equals("Affinity")) 
    		{
    			counter2 = counter2 + 1;
    		}
    	}
    	
    	if (counter2 == 1) {
    		affnToAdd = 10;
    	}
    	if (counter2 == 2) {
    		affnToAdd = 15;
    	}
    	if (counter2 == 3) {
    		affnToAdd = 20;
    	}
    	
    	atkToAdd = counter1 * 5;
    	
    	if (wType.equals("great sword")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 4.8;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("long sword")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 3.3;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("sword") || wType.equals("dual blades")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 1.4;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("hammer")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 5.2;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("hunting horn")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 4.2;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("lance") || wType.equals("gunlance")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 2.3;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("switch axe")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 3.5;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("charge blade")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 3.6;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("insect glaive")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 3.1;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("light bowgun")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 1.3;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("heavy bowgun")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 1.5;
    		bloat = Math.round(bloat);
    	}
    	if (wType.equals("bow")) {
    		raw = raw + atkToAdd;
    		bloat = raw * 1.2;
    		bloat = Math.round(bloat);
    	}
    	
    	//set labels
    	affinity = affinity + affnToAdd;
    	String affnToEnter = Integer.toString(affinity);
    	affnToEnter = affnToEnter + "%";
    	
    	int bloatEnter = (int)bloat;
    	attackLabel2.setText("Attack: " + bloatEnter + " | " + raw);
    	affinityLabel2.setText("Affinity: " + affnToEnter);
    	}
    	catch(Exception e) {
    		System.out.println("Please select an item for each box.");
    		errorLabel.setVisible(true);
    		errorLabel.setText("Please select an item for each box.");
    	}
    }
}
    
    














