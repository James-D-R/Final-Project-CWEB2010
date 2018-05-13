/**
 * 
 */
package application;

/**
 * @author remjamd
 *
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
    private TableColumn<?, ?> columnMaterial;

    @FXML
    void calculateAtk(ActionEvent event) {

    }
    
    
    @FXML
    void nameSearch(ActionEvent event) {

    	setLabels();
    }
    

    
    
    //Action Event to fill tables
    @FXML
    void clicked(ActionEvent event) throws IOException, SQLException{
    	
    	//Initialize columns
    	columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	columnAttack.setCellValueFactory(cellData -> cellData.getValue().attackProperty());
    	columnElement.setCellValueFactory(cellData -> cellData.getValue().elementProperty());
    	columnAffinity.setCellValueFactory(cellData -> cellData.getValue().affinityProperty());
    	columnSlots.setCellValueFactory(cellData -> cellData.getValue().slotsProperty());
    	columnRarity.setCellValueFactory(cellData -> cellData.getValue().rarityProperty());
    	
    	ObservableList<TableWeapons> sample = FXCollections.observableArrayList();
    	sample = searchDatabase();
    	
    	dataTable.setItems(sample);
    }
    

    //Initialize window
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		infoLabel.setVisible(false);
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
    	//un-comment the following section if database needs to be re-created
    	//CAUTION!!! This may take 10+ minutes to complete.
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
		int noSlots = slotsMinus.size();
		//System.out.println(noSlots);
		
		int totalSlots = largeSlots + medSlots + smallSlots;
		String slotsValue = Integer.toString(totalSlots);
		return slotsValue;
	}
    
    public String checkAffinity(Document wepDoc) 
    {
    	String check = "false";
    	Elements pos = wepDoc.getElementsByClass("text-success");
    	Elements neg = wepDoc.getElementsByClass("text-danger");
    	int a = pos.size();
    	int b = neg.size();
    	if (a == 1 || b == 1) 
    	{
    		check = "true";
    		
    	}
		return check;
    }
    
    public ArrayList<Weapon> createObjects() throws IOException{

    	ArrayList<Weapon> weapons = new ArrayList<Weapon>(); 
    	String[] choices = {"great-sword","long-sword","sword","dual-blades","hammer","hunting-horn","lance","gunlance",
    							"switch-axe","charge-blade","insect-glaive","light-bowgun", "heavy-bowgun", "bow"};
    	
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
    				String check;
        		
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
    	return weapons;
    }
    
    
    
    public void createDatabase(ArrayList<Weapon> weapons) throws SQLException{
    	
    	String DB_URL = "jdbc:mysql://db4free.net:3306/mhw_weapons";
    	String USERNAME = "jdremer";
    	String PASSWORD = "testpassword";
    	
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
    
    
    
    public ObservableList<TableWeapons> searchDatabase() throws SQLException{
    	
    	ObservableList<TableWeapons> sample = FXCollections.observableArrayList();
    	
    	String DB_URL = "jdbc:mysql://db4free.net:3306/mhw_weapons";
    	String USERNAME = "jdremer";
    	String PASSWORD = "testpassword";
    	try {
    	try {
    		Connection MyConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    		Statement stmt = MyConn.createStatement();
    		
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
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	}
    	catch(Exception e) {
    		System.out.println("Connection error has occured. Please wait and try again.");
    	}
    	
    	return sample;
    }
    
    public void setLabels(){
    	
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
    		
    		String select = "SELECT wepType, name, attack, element, affinity, slots, rarity FROM WeaponsTable WHERE name = '" + enteredName + "'" ;
			ResultSet rs = stmt.executeQuery(select);
			
			while (rs.next()) {
				
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
    }














