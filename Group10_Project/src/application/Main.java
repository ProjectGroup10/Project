package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
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

<<<<<<< HEAD
		try 
		{
			Parent root1 = FXMLLoader.load(getClass().getResource("/application/MainMenu.fxml"));
			Scene scene = new Scene(root1, 500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	
		
		
		/*
		try 
		{
			// load the fxml file (start page)
			Parent root1 = FXMLLoader.load(getClass().getResource("MainTemplate.fxml"));
			// responsive design
		    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
		    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
			Scene scene = new Scene(root1, screenWidth,screenHeight);
=======
		try 
		{
			Parent root1 = FXMLLoader.load(getClass().getResource("/application/MainMenu.fxml"));
			Scene scene = new Scene(root1, 500,400);
>>>>>>> 3cd75d1b9af9a013c4eee48992abda74c5b6f3c0
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}*/
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
