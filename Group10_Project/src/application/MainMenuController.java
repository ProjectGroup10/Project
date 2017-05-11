package application;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

<<<<<<< HEAD
public class MainMenuController {
=======
public class MainMenuController implements Initializable {
>>>>>>> 3cd75d1b9af9a013c4eee48992abda74c5b6f3c0
	@FXML
	
	public void goToCreateMode(ActionEvent evect) throws Exception{
		Stage primaryStage = new Stage();
<<<<<<< HEAD
		Parent root1 = FXMLLoader.load(getClass().getResource("CreateMode.fxml"));
=======
		Parent root1 = FXMLLoader.load(getClass().getResource("CreatMode.fxml"));
>>>>>>> 3cd75d1b9af9a013c4eee48992abda74c5b6f3c0
		Scene scene = new Scene(root1, 1000,800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void goToDisplayMode(ActionEvent evect) throws Exception{
		Stage primaryStage = new Stage();
		Parent root1 = FXMLLoader.load(getClass().getResource("DisplayMode.fxml"));
		Scene scene = new Scene(root1, 1000,800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

<<<<<<< HEAD
	
=======
	@FXML ImageView iv1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		File file = new File("/Users/Nicky/Library/Mobile Documents/com~apple~CloudDocs/Group10_Project/src/image/image.png");
		iv1.setImage(new Image(file.toURI().toString()));
	}	
>>>>>>> 3cd75d1b9af9a013c4eee48992abda74c5b6f3c0
}
