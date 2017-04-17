package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainController  {

	String title ;
	GregorianCalendar StartDate ;
	GregorianCalendar EndDate ;
	ArrayList<Timeline> TimelineCollection = new ArrayList<Timeline>();
	@FXML VBox vBoxModules ;

	
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
        stage.setTitle("Form timeline");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 400, 400);
	    stage.setScene(scene);

	    DatePicker startDatePicker = new DatePicker();
	    DatePicker endDatePicker = new DatePicker();
        TextField name = new TextField();
        Button submit = new Button("Submit");
	    
	    vbox.getChildren().addAll(new Label("Timeline name : "),name);
	    vbox.getChildren().addAll(new Label("Start Date : "),startDatePicker);
	    vbox.getChildren().addAll(new Label("End Date : "),endDatePicker);
	    vbox.getChildren().add(submit);

	    
	    stage.show();
	    
	    
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	title = name.getText();
            	System.out.println("Timeline name : " + name.getText());
            	System.out.println("Start date : " + startDatePicker.getValue());
            	System.out.println("End date : " + endDatePicker.getValue());
            	Timeline timeline = new Timeline(title,startDatePicker,endDatePicker);
            	TimelineCollection.add(timeline);
            	timeline.display(vBoxModules);

            	stage.close();
            }
        });
	    
	    
	   
	}

}
