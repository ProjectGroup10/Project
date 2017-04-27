package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * @role This class represent add event dialog
 * @author Meng Li, Frapper Colin
 * @date : 04/25/2017
 */
public class AddEventFrameWork 
{
	private Button submitAddEvent;
	private DatePicker StartDatePicker;
	private DatePicker EndDatePicker;
	private TextField TitleField;
	private TextArea DescField;
	private Button image;
	private Image Addimage;
	private Label label;
	private Stage stage;
	
	/**
	 * Add new event dialog
	 */
	public void addtimeline_Page()
	{
		stage = new Stage();
		stage.setTitle("Add new Event");
		GridPane GP = new GridPane();
		GP.setPadding(new Insets(10,10,10,10));
		GP.setVgap(5);
		GP.setHgap(10);
		
		Scene scene = new Scene(GP,350,400);
		stage.setScene(scene);
		
		Text title = new Text("Title:");
		TitleField = new TextField();
		GP.add(title, 0, 0);
		GP.add(TitleField, 1, 0);
		
		Text StartDate = new Text("StartDate:");
	    StartDatePicker = new DatePicker();
	    GP.add(StartDate, 0, 1 );
	    GP.add(StartDatePicker, 1, 1 );

		Text EndDate = new Text("EndDate");
		EndDatePicker = new DatePicker();
		EndDatePicker.setValue(LocalDate.now());

		GP.add(EndDate, 0, 2 );
	    GP.add(EndDatePicker, 1, 2 );
	    
	    Text description = new Text("Description:");
	    DescField = new TextArea();
		GP.add(description, 0, 3);
		GP.add(DescField, 1, 3);
		
	    Text Addimage = new Text("Add Image:");
		image = new Button("Choose");
		GP.add(Addimage, 0, 4);
		GP.add(image, 1, 4);
		
		submitAddEvent = new Button("Submit");
		submitAddEvent.setPrefSize(150, 30);
		label = new Label();
		label.setFont(new Font("Sans Serif",18));
		label.setTextFill(Color.RED);
		GP.add(label, 1, 5);
		GP.add(submitAddEvent,1,6);
		setImageButton();
		stage.show();
	}
	/**
	 * set image
	 */
	public void setImageButton()
	{
		   image.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FileChooser fileChooser = new FileChooser();
	             
				FileChooser.ExtensionFilter extFilterJPG = 
		                    new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
		        FileChooser.ExtensionFilter extFilterjpg = 
		                    new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
		        FileChooser.ExtensionFilter extFilterpng = 
		                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		            fileChooser.getExtensionFilters()
		                    .addAll(extFilterJPG, extFilterjpg, extFilterpng);
	            File file = fileChooser.showOpenDialog(null);
	                       
	            try {
	                BufferedImage bufferedImage = ImageIO.read(file);
	               Addimage = SwingFXUtils.toFXImage(bufferedImage, null);
	            } catch (IOException ex) {
	            }
			}});
	}
	
	/**
	 * Getter
	 * @return submitAddTimeline
	 */
	public Button getbuttonType()
	{
		return submitAddEvent;
	}
	/**
	 * Getter
	 * @return stage
	 */
	public Stage getStage()
	{
		return stage;
	}
	/**
	 * Getter
	 * @return true or false
	 */
	public boolean IsDuration()
	{
		if(EndDatePicker.getValue().equals(StartDatePicker.getValue()))
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	/**
	 * Getter
	 * @return TitleField
	 */
	public TextField getTextfield()
	{
		return TitleField ;
	}
	/**
	 * Getter
	 * @return label
	 */
	public Label getLabel()
	{
		return label;
	}
	/**
	 * Getter
	 * @return StartDatePicker
	 */
	public DatePicker getStartDateper(){
		return StartDatePicker;
	}
	/**
	 * Getter
	 * @return EndDatePicker
	 */
	public DatePicker getEndDateper(){
		return EndDatePicker;
	}
	/**
	 * Getter
	 * @return event
	 */
	public Event getEvent()
	{
		
		Event event = new Event(TitleField.getText(),DescField.getText(),StartDatePicker.getValue(),IsDuration(),EndDatePicker.getValue(),Addimage);
		return event;
	}

}
