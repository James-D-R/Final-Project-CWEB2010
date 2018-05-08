package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400)
			FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("DatabaseUI.fxml"));
			fxmlLoader.setController(new AppController());
			Parent root = fxmlLoader.load();
			//Parent root = FXMLLoader.load(getClass().getResource("DatabaseUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("DatabaseUI.fxml").toExternalForm());
			stage.setScene(scene);
			stage.setTitle("MHW: Weapons Database");
			
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}




	}


