package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.GregorianCalendar;

=======
>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
<<<<<<< HEAD
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
=======
>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController  {

<<<<<<< HEAD
	ArrayList<Timeline> TimelineCollection = new ArrayList<Timeline>();
=======
	private String title ;
	private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField name;
>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238
	@FXML VBox vBoxModules ;

	
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
        stage.setTitle("Form timeline");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 300, 300);
	    stage.setScene(scene);

<<<<<<< HEAD
	    DatePicker startDateTimeline = new DatePicker();
	    DatePicker endDateTimeline = new DatePicker();
        TextField name = new TextField();
        Button submit = new Button("Submit");
        setDatePicker(startDateTimeline,endDateTimeline);
=======
	    startDatePicker = new DatePicker();
	    startDatePicker.setValue(LocalDate.now());
	    endDatePicker = new DatePicker();
	    endDatePicker.setValue(LocalDate.now());

        name = new TextField();
        Button submit = new Button("Submit");
        setDatePicker();
>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238
	    vbox.getChildren().addAll(new Label("Timeline name : "),name);
	    vbox.getChildren().addAll(new Label("Start Date : "),startDateTimeline);
	    vbox.getChildren().addAll(new Label("End Date : "),endDateTimeline);
	    vbox.getChildren().add(submit);
	    stage.show();
	    
	    
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
<<<<<<< HEAD
            	String titleTimeline = name.getText();
            	Timeline timeline = new Timeline(titleTimeline,startDateTimeline,endDateTimeline);
            	TimelineCollection.add(timeline);
            	timeline.display(vBoxModules);

            	stage.close();
            }
        });  
	}
	
	private void setDatePicker(DatePicker startDateTimeline,DatePicker endDateTimeline  )
	{
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() 
		{
			public DateCell call(final DatePicker datePicker) 
			{
			    return new DateCell() 
			    {
			        public void updateItem(LocalDate item, boolean empty) 
			        {
			            super.updateItem(item, empty);
			            if (item.isBefore(startDateTimeline.getValue().plusDays(0))) 
			            {
			                    setDisable(true);
			                    setStyle("-fx-background-color: #ffc0cb;");
			            }
			            long days = ChronoUnit.DAYS.between(startDateTimeline.getValue(), item);
			            setTooltip(new Tooltip("You're choose " + days + " days"));
			        }
			    };
			}
		};
        endDateTimeline.setDayCellFactory(dayCellFactory);
=======
            	title = name.getText() + " \n" + startDatePicker.getValue().toString() +" To " + endDatePicker.getValue().toString();
            	Timeline timeline = new Timeline(title,startDatePicker,endDatePicker);
            	DisplayTimeLine dis = new DisplayTimeLine(timeline);
            	dis.display(vBoxModules);
            	stage.close();
            }
        });
	    
>>>>>>> 2424bad8dac71adf2054843d2ae52996769c2238
	}
	public Timeline GetTimeLine()
	{

		Timeline timeline = new Timeline(name.toString(),startDatePicker, endDatePicker);
		return timeline;
			
	}
	private void setDatePicker()
	{
		final Callback<DatePicker, DateCell> dayCellFactory = 
	            new Callback<DatePicker, DateCell>() {
	                @Override
	                public DateCell call(final DatePicker datePicker) {
	                    return new DateCell() {
	                        @Override
	                        public void updateItem(LocalDate item, boolean empty) {
	                            super.updateItem(item, empty);
	                            if (item.isBefore(
	                            		startDatePicker.getValue().plusDays(0))
	                                ) {
	                                    setDisable(true);
	                                    setStyle("-fx-background-color: #ffc0cb;");
	                            }
	                            long days = ChronoUnit.DAYS.between(
	                            		startDatePicker.getValue(), item
	                            );

	                            setTooltip(new Tooltip(
	                                "You're choose " + days + " days")
	                            );
	                            }
	                };
	            }
	        };
	        endDatePicker.setDayCellFactory(dayCellFactory);
	}
}
