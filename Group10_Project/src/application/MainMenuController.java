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

public class MainMenuController implements Initializable {
	@FXML
	
	public void goToCreateMode(ActionEvent evect) throws Exception{
		Stage primaryStage = new Stage();
		Parent root1 = FXMLLoader.load(getClass().getResource("CreatMode.fxml"));
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

	@FXML ImageView iv1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		File file = new File("/Users/Nicky/Library/Mobile Documents/com~apple~CloudDocs/Group10_Project/src/image/image.png");
		iv1.setImage(new Image(file.toURI().toString()));
	}	
}
