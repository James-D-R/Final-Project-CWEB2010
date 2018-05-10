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
    	
    	//String selectedW = WeaponBox.getValue();
    	//System.out.println(selectedW);
    	
    	Document doc = Jsoup.connect("https://mhworld.kiranico.com/great-sword").get();  
        Elements links = doc.select("a[href]");  
        for (Element link : links) {  
            //System.out.println("\nlink : " + link.attr("href"));
        	if (link.attr("href").contains("weapon"))
        	{
        		String wepURL = (link.attr("href"));
        		Document wepDoc = Jsoup.connect(wepURL).get();
        		//order: attack, affinity, slots, element, sharpness, rare
        		Elements stats = wepDoc.getElementsByClass("lead");
        		int total = stats.length();
        		sample.add(new TableWeapons(link.text(),"100","200 water","+30%","0","7"));
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
    		    		"Sword & Shield",
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
    		    		"No Element",
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
    
    
}
