package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainMenuController 
{
	public void goToCreateMode(ActionEvent evect) throws Exception
	{
		Stage primaryStage = new Stage();
		Parent root1 = FXMLLoader.load(getClass().getResource("CreateMode.fxml"));
		Scene scene = new Scene(root1, 1000,800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void goToDisplayMode(ActionEvent evect) throws Exception
	{
		Stage primaryStage = new Stage();
		Parent root1 = FXMLLoader.load(getClass().getResource("DisplayMode.fxml"));
		Scene scene = new Scene(root1, 1000,800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
