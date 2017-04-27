package application.Group10_Project.src.application;
	
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
			// load the fxml file (start page)
			Parent root1 = FXMLLoader.load(getClass().getResource("MainTemplate.fxml"));
			Scene scene = new Scene(root1, 1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
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
