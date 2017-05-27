package application;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @role This class is the controller class who handle the listeners for the buttons from the 
 * MainMenu.fxml
 * @author Meng Li, Frapper Colin
 * @date 05/23/2017
 * @note : You have to include the Json packages to your java project to make it work, the 
 * org.json package and the org.simple.json package
 */
public class MainMenuController 
{
	/**
	 * @role : Method to make appear the CreateMode.fxml when clicking on the button create mode
	 * @param evect
	 * @throws Exception
	 */
	public void goToCreateMode(ActionEvent evect) throws Exception
	{
		Stage primaryStage = new Stage();
		Parent root1 = FXMLLoader.load(getClass().getResource("CreateMode.fxml"));
		Scene scene = new Scene(root1, 1000,800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Create Mode");
		primaryStage.show();
	}
	/**
	 * @role : Method to make appear the DisplayMode.fxml when clicking on the button display mode
	 * @param evect
	 * @throws Exception
	 */
	public void goToDisplayMode(ActionEvent evect) throws Exception
	{
		Stage primaryStage = new Stage();
		Parent root1 = FXMLLoader.load(getClass().getResource("DisplayMode.fxml"));
		Scene scene = new Scene(root1, 1000,800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Display Mode");
		primaryStage.show();
	}
}
