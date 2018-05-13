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
import java.util.List;
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
		
		
    	WeaponBox.getItems().addAll(options1);
    	ElementBox.getItems().addAll(options2);
    	
    	
    	ArrayList<Weapon> weapons = new ArrayList<Weapon>();
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
	        		
	        		if (selectedWep.equals("great-sword") || selectedWep.equals("long-sword") || selectedWep.equals("sword") || selectedWep.equals("dual-blades") || selectedWep.equals("hammer") || selectedWep.equals("lance"))
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
			Statement drop_stmt = MyConn.createStatement();
			String drop = "DROP TABLE WeaponsTable";
			drop_stmt.executeUpdate(drop);
			System.out.println("Deleted table in given database");
			
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
    		Connection MyConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    		Statement stmt = MyConn.createStatement();
    		
    		String wepChoice = WeaponBox.getValue();
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
				
				sample.add(new TableWeapons(pickedWep, pickedAtk, pickedElm, pickedAffn, pickedSlots, pickedRare));
			}
			
			rs.close();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    	
    	
    	return sample;
    }
    }














