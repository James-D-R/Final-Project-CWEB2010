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

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;
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
    private ComboBox<String> RarityBox;

    @FXML
    private ComboBox<String> AffinityBox;

    @FXML
    private ComboBox<String> SlotsBox;

    @FXML
    private Button searchButton;

    @FXML
    void clicked(ActionEvent event) throws IOException{

    	
    	//Initialize columns
    	columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	columnAttack.setCellValueFactory(cellData -> cellData.getValue().attackProperty());
    	columnElement.setCellValueFactory(cellData -> cellData.getValue().elementProperty());
    	columnAffinity.setCellValueFactory(cellData -> cellData.getValue().affinityProperty());
    	columnSlots.setCellValueFactory(cellData -> cellData.getValue().slotsProperty());
    	columnRarity.setCellValueFactory(cellData -> cellData.getValue().rarityProperty());
    	
    	ObservableList<TableWeapons> sample = FXCollections.observableArrayList();
    	
    	//create url
    	String selectedWep = WeaponBox.getValue();
    	selectedWep = selectedWep.toLowerCase();
    	selectedWep = selectedWep.replace(' ', '-');
    	String url = "https://mhworld.kiranico.com/" + selectedWep;
    	
    	//Connect with Jsoup
    	Document doc = Jsoup.connect(url).get();  
        Elements links = doc.select("a[href]");  
        for (Element link : links) {  
            //System.out.println("\nlink : " + link.attr("href"));
        	if (link.attr("href").contains("weapon"))
        	{
        		String atk = "n/a";
        		String affn = "0%";
        		String slt = "n/a";
        		String elm = " ";
        		String rare = "n/a";
        		String check;
        		
        		String wepURL = (link.attr("href"));
        		Document wepDoc = Jsoup.connect(wepURL).get();
        		//order: attack, affinity, slots, element, rare
        		Elements stats = wepDoc.getElementsByClass("lead");
        		int total = stats.size();
        		
        		atk = stats.first().text();
        		slt = findSlots(wepDoc); //method
        		rare = stats.last().text();
        		//System.out.println(total);
        		if(selectedWep.equals("hunting-horn") || selectedWep.equals("gunlance") || selectedWep.equals("switch-axe") || selectedWep.equals("charge-blade") || selectedWep.equals("insect-glaive"))
        		{
        			if (total == 4) 
        			{
        				sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
        			}
        			
        			else if (total == 5) 
        			{
        				String checkAffn = stats.get(1).text();
            			if(checkAffn.contains("%")) 
            			{
            				affn = stats.get(1).text();
            			}
            			elm = stats.get(2).text();
        				sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
        			}
        			
        			else if (total == 6) 
        			{
        				String checkAffn = stats.get(1).text();
            			if(checkAffn.contains("%")) 
            			{
            				affn = stats.get(1).text();
            			}
            			elm = stats.get(3).text();
        				sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
        			}
        			
        			else if (total == 7) 
        			{
        				affn = stats.get(2).text();
        				elm = stats.get(4).text();
        				sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
        			}
        			else 
        			{
        				sample.add(new TableWeapons(link.text(),"filler","filler","filler","filler","filler"));
        			}
        		}
        		
        		if (selectedWep.equals("light-bowgun") || selectedWep.equals("heavy-bowgun")) 
        		{
        			if (total == 5) 
        			{
        				sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
        			}
        			
        			else if (total == 6) 
        			{
        				String checkAffn = stats.get(1).text();
            			if(checkAffn.contains("%")) 
            			{
            				affn = stats.get(1).text();
            			}
        				sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
        			}
        			
        			else if (total == 7) 
        			{
        				affn = stats.get(2).text();
        				sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
        			}
        			
        			else 
        			{
        				sample.add(new TableWeapons(link.text(),"filler","filler","filler","filler","filler"));
        			}
        		}
        		
        		else 
        		{
        			if(total == 3) 
            		{
            			sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
            		}
            		else if(total == 4) 
            		{
            			
            			String checkAffn = stats.get(1).text();
            			if(checkAffn.contains("%")) 
            			{
            				affn = stats.get(1).text();
            			}
            			elm = stats.get(2).text();
            			sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
            		}
            		else if(total == 5) 
            		{
            			String checkAffn = stats.get(1).text();
            			if(checkAffn.contains("%")) 
            			{
            				affn = stats.get(1).text();
            			}
            			elm = stats.get(3).text();
            			sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
            		}
            		else if(total == 6) 
            		{
            			affn = stats.get(2).text();
            			elm = stats.get(4).text();
            			sample.add(new TableWeapons(link.text(), atk, elm, affn, slt, rare));
            		}
            		else 
            		{
            			sample.add(new TableWeapons(link.text(),"filler","filler","filler","filler","filler"));
            		}
        		}
        		
        		
        	}
            
        }  
        dataTable.setItems(sample);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
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
    		    FXCollections.observableArrayList(" ","1","2","3","4","5","6","7","8");
		
		ObservableList<String> options4 = 
    		    FXCollections.observableArrayList("No Affinity","+","-");
		
		ObservableList<String> options5 = 
    		    FXCollections.observableArrayList("0","1","2","3");
		
    	WeaponBox.getItems().addAll(options1);
    	ElementBox.getItems().addAll(options2);
    	RarityBox.getItems().addAll(options3);
    	AffinityBox.getItems().addAll(options4);
    	SlotsBox.getItems().addAll(options5);
    	
    	
	}
	
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
    
    //public ArrayList
}
