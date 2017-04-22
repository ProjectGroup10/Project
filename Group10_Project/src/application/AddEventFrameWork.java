package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class AddEventFrameWork 
{
	private ButtonType submitAddTimeline;
	private DatePicker StartDatePicker;
	private DatePicker EndDatePicker;
	private TextField TitleField;
	private TextArea DescField;
	private Button image;
	private Image Addimage;

	private Dialog<Event> dialogue;
	
	
	public void addtimeline_Page()
	{
		dialogue = new Dialog<>();
		dialogue.setTitle("Add new TimeLine");
		
		submitAddTimeline = new ButtonType("Submit",ButtonData.OK_DONE);
		dialogue.getDialogPane().getButtonTypes().addAll(submitAddTimeline);
		
		GridPane GP = new GridPane();
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
		setImageButton();
	    dialogue.getDialogPane().setContent(GP);
	}
	
	public void setImageButton()
	{
		   image.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FileChooser fileChooser = new FileChooser();
	             
	            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
	              
	            File file = fileChooser.showOpenDialog(null);
	                       
	            try {
	                BufferedImage bufferedImage = ImageIO.read(file);
	               Addimage = SwingFXUtils.toFXImage(bufferedImage, null);
	            } catch (IOException ex) {
	            }
			}});
	}
	
	
	public ButtonType getbuttonType()
	{
		return submitAddTimeline;
	}
	public Dialog<Event> getDialog()
	{
		return dialogue;
	}

	public boolean IsDuration()
	{
		if(!(EndDatePicker.getValue().getMonth().equals(StartDatePicker.getValue().getMonth())))
		{
			return true;
		}
		else
		{
			if(EndDatePicker.getValue().getDayOfMonth() - StartDatePicker.getValue().getDayOfMonth() != 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
	}
	public DatePicker getStartDateper(){
		return StartDatePicker;
	}
	public DatePicker getEndDateper(){
		return EndDatePicker;
	}
	public Event getEvent()
	{
		
		Event event = new Event(TitleField.getText(),DescField.getText(),StartDatePicker.getValue(),IsDuration(),EndDatePicker.getValue(),Addimage);
		return event;
	}

}
