/**
 * 
 */
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * @author remjamd
 *
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AppController implements Initializable {

	
	
	@FXML
    private TableView<?> dataTable;

    @FXML
    private TableColumn<?, ?> columnName;

    @FXML
    private TableColumn<?, ?> columnElement;

    @FXML
    private TableColumn<?, ?> columnAffinity;

    @FXML
    private TableColumn<?, ?> columnSlots;

    @FXML
    private TableColumn<?, ?> columnRarity;

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
    private Button button;

    @FXML
    void clickMe(ActionEvent event) {

    	String selectedW = WeaponBox.getValue();
    	System.out.println(selectedW);
    	String selectedE = ElementBox.getValue();
    	System.out.println(selectedE);
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
    		    FXCollections.observableArrayList("1","2","3","4","5","6","7","8");
		
		ObservableList<String> options4 = 
    		    FXCollections.observableArrayList("+","-","no affinity");
		
		ObservableList<String> options5 = 
    		    FXCollections.observableArrayList("0","1","2","3");
		
    	WeaponBox.getItems().addAll(options1);
    	ElementBox.getItems().addAll(options2);
    	RarityBox.getItems().addAll(options3);
    	AffinityBox.getItems().addAll(options4);
    	SlotsBox.getItems().addAll(options5);
	}
    
    
}
