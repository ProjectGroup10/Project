package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController  {

	private String title ;
	private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private TextField name;
	@FXML VBox vBoxModules ;

	
	public void appearFormTimeline()
	{
		Stage stage = new Stage();
        stage.setTitle("Form timeline");
	    VBox vbox = new VBox(20);
	    Scene scene = new Scene(vbox, 300, 300);
	    stage.setScene(scene);

	    startDatePicker = new DatePicker();
	    startDatePicker.setValue(LocalDate.now());
	    endDatePicker = new DatePicker();
	    endDatePicker.setValue(LocalDate.now());

        name = new TextField();
        Button submit = new Button("Submit");
        setDatePicker();
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
            	title = name.getText() + " \n" + startDatePicker.getValue().toString() +" To " + endDatePicker.getValue().toString();
            	Timeline timeline = new Timeline(title,startDatePicker,endDatePicker);
            	DisplayTimeLine dis = new DisplayTimeLine(timeline);
            	dis.display(vBoxModules);
            	stage.close();
            }
        });
	    
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
