package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @role This class represent the Timeline, it's an abstract class
 * @author Meng Li, Frapper Colin
 * @date : 05/22/2017
 */
public abstract class Timeline 
{

	protected int id = 0 ; 
	protected String title ; 
	protected LocalDate startDate ; 
	protected LocalDate endDate ;

	// List of events
	protected ArrayList<Event> listEvent ;
	// LineChart who represents the "graphic timeline"
	protected LineChart<String, Number> lineChart ;
	
	private BufferedImage bufferedImage;
	private String imageType;


	/**
	 * Constructor 
	 * @param title
	 * @param startTime
	 * @param endTime
	 */
	public Timeline (String title, LocalDate startTime, LocalDate endTime)
	{
		this.title = title ;
		this.startDate = startTime ;
		this.endDate = endTime ;
	}
	
	/**
	 * Getter 
	 * @return startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}


	/**
	 * setter
	 * @param startDate
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	/**
	 * Getter 
	 * @return endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * setter
	 * @param endDate
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	/**
	 * Getter 
	 * @return lineChart
	 */
	public LineChart<String, Number> getLineChart() {
		return lineChart;
	}

	/**
	 * setter
	 * @param lineChart
	 */
	public void setLineChart(LineChart<String, Number> lineChart) {
		this.lineChart = lineChart;
	}
	
	
	/**
	 * Getter 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * setter
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter 
	 * @return endDate
	 */
	public ArrayList<Event> getListEvent() {
		return listEvent;
	}

	/**
	 * setter
	 * @param listEvent
	 */
	public void setListEvent(ArrayList<Event> listEvent) {
		this.listEvent = listEvent;
	}

	/**
	 * @role this method intialize the lineCart depending on the startDate and the endDate
	 */
	protected abstract void initLineChart();

	
	
	/**
	 * This method is called when cliking on addEvent, a new window comes and ask to fill the 
	 * informations corresponding to create an event
	 */
	public void appearFormEvent()
	{	
		Stage stage = new Stage();
		stage.setTitle("Add new Event");
		GridPane GP = new GridPane();
		GP.setPadding(new Insets(10,10,10,10));
		GP.setVgap(5);
		GP.setHgap(10);
		
		Scene scene = new Scene(GP,350,360);
		stage.setScene(scene);
		CheckBox duration = new CheckBox();
	    duration.setText("event with duration ?");
	   
		TextField nameEvent = new TextField();
	    DatePicker startDatePickerEvent = new DatePicker();
	    DatePicker endDatePickerEvent = new DatePicker();
    	endDatePickerEvent.setDisable(true);

	    TextArea DescField = new TextArea();
	    Button addImage = new Button("Choose");
        Button submit = new Button("Submit");
        	    
	    
		GP.add(new Text("Title:"), 0, 0);
		GP.add(nameEvent, 1, 0);
        
	    GP.add(new Text("StartDate:"), 0, 1 );
	    GP.add(startDatePickerEvent, 1, 1 );
	    // method to handle the fact that the end date should be after the start date
        Text endDate = new Text("EndDate");
	    setDatePicker(startDatePickerEvent,endDatePickerEvent);
	    GP.add(duration, 1, 2 );
	    duration.setOnAction(new EventHandler<ActionEvent>()
	    {
	    	public void handle(ActionEvent arg0) 
			{
	    		if(duration.isSelected())
	    		{
	    			GP.add(endDate, 0, 3 );
	    		    GP.add(endDatePickerEvent, 1, 3 );
	    		}
	    		else
	    		{
	    			GP.getChildren().removeAll(endDate,endDatePickerEvent);
	    		}
	    		
			}
		});
		
	    
		GP.add(new Text("Description:"), 0, 4);
		GP.add(DescField, 1, 4);
		
		GP.add(new Text("Add Image:"), 0, 5);
		GP.add(addImage, 1, 5);
		
		submit.setPrefSize(150, 30);
		
		GP.add(submit,1,6);
		stage.show();
		
		
	    startDatePickerEvent.setOnAction(new EventHandler<ActionEvent>()
	    {
			public void handle(ActionEvent arg0) 
			{
		    	endDatePickerEvent.setDisable(false);
			}
	    	
	    });
	    
	    // listener when cliking on addImage
	    addImage.setOnAction(new EventHandler<ActionEvent>() 
	    {
	
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilterjpg =
		                    new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
		        FileChooser.ExtensionFilter extFilterpng = 
		                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		            fileChooser.getExtensionFilters()
		                    .addAll(extFilterjpg, extFilterpng);
	            File file = fileChooser.showOpenDialog(null);
	            if(file !=null)
	            {
	            	try {
						bufferedImage = ImageIO.read(file);
						imageType = file.getCanonicalPath().toLowerCase();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            else
	            {
	            	bufferedImage = null;
	            }
			}
	    });
	    
	    // Listener when cliking on submit
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e) 
            {
            	// call the method isDuration to determine if the event is an duration event 
            	// or an non duration event
        	    Event event ;
        	    if (!fill_value(nameEvent,startDatePickerEvent,endDatePickerEvent,duration.isSelected()) && check_date(startDatePickerEvent,endDatePickerEvent)) 
            	{
					if(duration.isSelected())
					{
	                   	event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent.getValue(),endDatePickerEvent.getValue(),true,bufferedImage,imageType);
					}
	        	    else
	                   	 event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent.getValue(),false,bufferedImage,imageType);

					addEvent(event);
	            	stage.close();
            	}
            }
        });
	    
	    bufferedImage = null;
	    imageType = "";
	}
	

	/**
	 * @role method addEvent who permit to add an event to the line chart, and to the list of 
	 * event corresponding at the special timeline
	 * @param event
	 */
	public abstract void addEvent(Event event);	
	public abstract void DisplayAddEvent(Event event);	

	
	/**
	 * @role method who "transform" the integer passed in parameters into the corresponding 
	 * month
	 * @param m
	 * @return month (string)
	 */
	
	public String chooseMonth(int m)
	{
		String month = "" ;
		// depending on which integer 
		switch(m)
    	{
    		case 1 : month = "Jan " ;
    			break ;
    		case 2 : month = "Feb " ;
    			break ;
    		case 3 : month = "Mar " ;
    			break ;
    		case 4 : month = "Apr " ;
    			break ;
    		case 5 : month = "May " ;
    			break ;
    		case 6 : month = "Jun " ;
    			break ;
    		case 7 : month = "Jul " ;
    			break ;
    		case 8 : month = "Aug " ;
    			break ;
    		case 9 : month = "Sep " ;
    			break ;
    		case 10 : month = "Oct " ;
    			break ;
    		case 11 : month = "Nov " ;
    			break ;
    		case 12 : month = "Dec " ;
    	}
		return month ;
	}
	
	/**
	 * @role method to determine if the event is a non duration event or a duration event
	 * @param sd
	 * @param ed
	 * @return true, or false
	 */
	public boolean isDuration(DatePicker sd, DatePicker ed)
	{
		if (sd.getValue().equals(ed.getValue()) ) 
			return false;
		else
			return true ;
	}
	
	/**
	 * @role method to handle the fact of deleting an event 
	 * @param e
	 */
	public void deleteEvent(Event e)
	{
		// remove the event (series) from the timeline (lineChart)
        lineChart.getData().remove(e.getSeries());
		// remove the event from the list event 
		listEvent.remove(e);	
	}
	
	/**
	 * @role : This method has been created to insure that a user can select an endDate after
	 * the startDate
	 * @param startDateTimeline
	 * @param endDateTimeline
	 */
	public void setDatePicker(DatePicker startDateEvent, DatePicker endDateEvent)
	{
		//startDateEvent.setValue(startDate.getValue());
		// To discuss
		//endDateEvent.setValue(startDate.getValue());
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() 
		{      
	        public DateCell call(final DatePicker datePicker) 
	        {
	            return new DateCell() 
	            {
	                public void updateItem(LocalDate item, boolean empty) 
	                {
	                    super.updateItem(item, empty);
	                    if (item.isBefore(startDate.plusDays(0))) 
	                    {
	                    	setDisable(true);
	                        setStyle("-fx-background-color: #ffc0cb;");
	                    }
	                    if(item.isAfter(endDate))
	                    {
	                    	setDisable(true);
	                        setStyle("-fx-background-color: #ffc0cb;");
	                    }
	                    if(startDateEvent.getValue()!=null)
	                    {
	                    	if(item.isBefore(startDateEvent.getValue().plusDays(1)))
		                    {
		                    	setDisable(true);
		                        setStyle("-fx-background-color: #ffc0cb;");
		                    }
		                    long days = ChronoUnit.DAYS.between(startDateEvent.getValue(), item);
		                    setTooltip(new Tooltip("You're choose " + days + " days"));
	                    }
	                }
	            };
	        }
		};
		final Callback<DatePicker, DateCell> dayCell = new Callback<DatePicker, DateCell>() 
		{      
	        public DateCell call(final DatePicker datePicker) 
	        {
	            return new DateCell() 
	            {
	                public void updateItem(LocalDate item, boolean empty) 
	                {
	                    super.updateItem(item, empty);
	                    if (item.isBefore(startDate.plusDays(0))) 
	                    {
	                    	setDisable(true);
	                        setStyle("-fx-background-color: #ffc0cb;");
	                    }
	                    if(item.isAfter(endDate))
	                    {
	                    	setDisable(true);
	                        setStyle("-fx-background-color: #ffc0cb;");
	                    }
	                    if(startDateEvent.getValue()!=null)
	                    {
	                    	if(item.isBefore(startDate.plusDays(1)))
		                    {
		                    	setDisable(true);
		                        setStyle("-fx-background-color: #ffc0cb;");
		                    }
		                    long days = ChronoUnit.DAYS.between(startDateEvent.getValue(), item);
		                    setTooltip(new Tooltip("You're choose " + days + " days"));
	                    }
	                }
	            };
	        }
		};
		startDateEvent.setDayCellFactory(dayCell);
		endDateEvent.setDayCellFactory(dayCellFactory);
	}


	/**
	 * @role method who permit to modify an event when clicking on the button modify event
	 * @param e
	 */
	public void modifyEvent(Event e)
	{
		addEventModified(e);
	}
	
	/**
	 * @role Method similar to addEvent except the fact that we set up the previous information
	 * of the modified event
	 * @param e
	 */
	public void addEventModified(Event e)
	{
		Stage stage = new Stage();
        stage.setTitle("Form add modified event");
	    GridPane GP = new GridPane();
	    Scene scene = new Scene(GP, 330, 330);
	    stage.setScene(scene);
	    
	    GP.setPadding(new Insets(10,10,10,10));
		GP.setVgap(5);
		GP.setHgap(10);
		CheckBox duration = new CheckBox();
	    duration.setText("event with duration ?");

        TextField nameEvent = new TextField();
        nameEvent.setText(e.getTitleEvent());
	    DatePicker startDatePickerEvent = new DatePicker();
	    startDatePickerEvent.setValue(e.getStartDatePickerEvent());
	    DatePicker endDatePickerEvent = new DatePicker();
	    TextArea DescField = new TextArea();
	    DescField.setText(e.getDescEvent());
		setDatePicker(startDatePickerEvent,endDatePickerEvent);
	    Button addImage = new Button("Choose");
        Button submit = new Button("Submit");
		GP.add(new Text("Event name: "), 0, 0 );
	    GP.add(nameEvent, 1, 0 );
	    GP.add(new Text("Start Date: "), 0, 1 );
	    GP.add(startDatePickerEvent, 1, 1 );
	    Text endDate = new Text("End Date: ");

	   
	    GP.add(duration, 1, 2 );



	    duration.setOnAction(new EventHandler<ActionEvent>()
	    {
	    	public void handle(ActionEvent arg0) 
			{
	    		if(duration.isSelected())
	    		{
	    		    GP.add(endDate, 0, 3);
	    		    GP.add(endDatePickerEvent, 1, 3 );
	    		    if(e.isDuration())
	    		    	endDatePickerEvent.setValue(e.getEndDatePickerEvent());
	    		}
	    		else
	    		{
	    			endDatePickerEvent.setValue(null);
	    			GP.getChildren().removeAll(endDate,endDatePickerEvent);
	    		}
	    		
			}
		});
	
	    GP.add(new Text("Description:"), 0, 4);
		GP.add(DescField, 1, 4);
		
		GP.add(new Text("Add Image:"), 0, 5);
		GP.add(addImage, 1, 5);
		
	    GP.add(submit, 1, 6);
	    stage.show();


        setDatePicker(startDatePickerEvent,endDatePickerEvent);
	    
	    // listener when cliking on addImage
	    addImage.setOnAction(new EventHandler<ActionEvent>() 
	    {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilterjpg = 
	                    new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
	            FileChooser.ExtensionFilter extFilterpng = 
	                    new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
	            fileChooser.getExtensionFilters()
	                    .addAll( extFilterjpg, extFilterpng);
				File file = fileChooser.showOpenDialog(null);
	            if(file != null)   
	            {
	            	try {
		                bufferedImage = ImageIO.read(file);
		                e.setImageEvent(bufferedImage);
		                e.setImageType(file.getCanonicalPath().toLowerCase());		                
		            } catch (IOException e) {
		            	e.getStackTrace();
		            }
	            }
	            else
	            {
	            	bufferedImage = null;
	            }
	            }	
			});
	    

	    // listener when cliking on submit
	    submit.setOnAction(new EventHandler<ActionEvent>() 
	    {
            @Override
            public void handle(ActionEvent e1) 
            {
            	// delete the previous event 
				Event event;
				if (!fill_value(nameEvent,startDatePickerEvent,endDatePickerEvent,duration.isSelected()) && check_date(startDatePickerEvent,endDatePickerEvent)) 
	            {
					if(duration.isSelected())
					{
	                   	event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent.getValue(),endDatePickerEvent.getValue(),true,e.getImageEvent(),e.getImageType());
					}
	        	    else
	                   	 event = new Event(nameEvent.getText(),DescField.getText(),startDatePickerEvent.getValue(),false,e.getImageEvent(),e.getImageType());

	            	deleteEvent(e);

					addEvent(event);

	            	stage.close();
        		}
            }
        });

	}

	protected boolean fill_value(TextField name, DatePicker startDateTimeline, DatePicker endDateTimeline, boolean checkbox) 
	{
		if (name.getText() == null || name.getText().trim().isEmpty()) 
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please give a name to your event!");
			alert.showAndWait();
			return true ;
		}
		if(startDateTimeline.getValue()==null)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Please select the start date");
			alert.showAndWait();
			return true ;
		}
		if(checkbox)
		{
			if(endDateTimeline.getValue()==null)
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Please select the end date");
				alert.showAndWait();
				return true ;
			}
		}
		
		
		return false;
	}

	protected boolean check_date(DatePicker startDateEvent, DatePicker endDateEvent) 
	{
		LocalDate start = startDateEvent.getValue();
		LocalDate end = endDateEvent.getValue();
		if(end !=null)
		{
			if(end.isEqual(start) || end.isBefore(start))
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Please check the date of you pick!");
				alert.showAndWait();
				return false ;
			}
		}
		
		return true;
	}


	protected String parseString(String descEvent) 
	{
		StringBuilder sb = new StringBuilder(descEvent);

		int i = 0;
		while ((i = sb.indexOf(" ", i + 30)) != -1) {
		    sb.replace(i, i + 1, "\n");
		}
		return sb.toString();
	}

}

	