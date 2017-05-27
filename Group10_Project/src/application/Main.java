package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @role This class represent the main
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public class Main extends Application 
{
	public void start(Stage primaryStage) 
	{
		try 
		{
			Parent root1 = FXMLLoader.load(getClass().getResource("/application/MainMenu.fxml"));
			Scene scene = new Scene(root1, 500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Start Page");
			primaryStage.show();

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
