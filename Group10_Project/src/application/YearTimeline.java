package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * @role This class represent the Timeline during years, it extends the class Timeline
 * @author Meng Li, Frapper Colin
 * @date : 05/22/2017
 */
public class YearTimeline extends Timeline {
    private Image im;

	public YearTimeline (String title, LocalDate startTime, LocalDate endTime)
	{
		super(title,startTime,endTime);
		//auto increment id when creating a timeline
		this.id = id++ ;
		this.listEvent = new ArrayList<Event>() ;
		
	}
	// method to initialize the line chart of the corresponding timeline
	public void initLineChart()
	{
		String startDateTimeline = startDate.toString();
    	String[] splitStartDateTimeline = startDateTimeline.split("-");
    	int startYear = Integer.parseInt(splitStartDateTimeline[0]);
    	int startMonth = Integer.parseInt(splitStartDateTimeline[1]);
    	String endDateTimeline = endDate.toString();
    	String[] splitEndDateTimeline = endDateTimeline.split("-");
    	int endYear = Integer.parseInt(splitEndDateTimeline[0]);
    	int endMonth = Integer.parseInt(splitEndDateTimeline[1]);
    	
    	// Used to define the size of the line chart
    	int diffyear = endYear-startYear ;
    	
    	
    	// representing the month
    	ObservableList<String> months = FXCollections.observableArrayList() ;
    	int date ;
    	for (int i = 0; i <= diffyear ; i++)
    	{
    		date = Integer.parseInt(splitStartDateTimeline[0])+i ; 
    		if(date== Integer.parseInt(splitStartDateTimeline[0]))
    		{
    		
				for(int j = startMonth ; j <=12 ; j++)
    			{
    				months.addAll(chooseMonth(j) + Integer.toString(date));
    			}	
    		}
    		else if(date == Integer.parseInt(splitEndDateTimeline[0]))
    		{
    			for(int j = 1 ; j <= endMonth ; j++)
    			{
    				months.addAll(chooseMonth(j) + Integer.toString(date));
    			}
    		}
    		else
    		{
    			for(int j = 1 ; j <= 12 ; j++)
    			{
    				months.addAll(chooseMonth(j) + Integer.toString(date));
    			}
    		}
        	
    	}
    	// xAxis 
        CategoryAxis xAxis = new CategoryAxis(months);
        xAxis.setLabel("Months (Years)");
        xAxis.setTickLabelRotation(90);
        
        // yAxis represente the days
        NumberAxis yAxis = new NumberAxis(0,31,5);
        yAxis.setMinorTickVisible(true);
        yAxis.setLabel("Days");
        
        lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle(title + " (From " + startDateTimeline + " to " + endDateTimeline + ")");
        lineChart.setMinHeight(450);
        lineChart.setMinWidth((diffyear+1) * 500);
	}
	
	
	/**
	 * @role method addEvent who permit to add an event to the line chart, and to the list of 
	 * event corresponding at the special timeline
	 * @param event
	 */
	public void addEvent(Event event)
	{
		// add the event to the list of event 
    	listEvent.add(event);
    	
    	String StartDate = event.getStartDatePickerEvent().toString();
		String EndDate = "" ;
    	String[] splitStartDate = StartDate.split("-");
    	String startYear = splitStartDate[0];
    	int startMonth = Integer.parseInt(splitStartDate[1]);
    	int startDay = Integer.parseInt(splitStartDate[2]);
    	// call the method chooseMonth depending on which month is selected
    	String monthStart = chooseMonth(startMonth); 
    	String axisXstart = monthStart + startYear ;
    		
    	// if it's a duration then add an endDate
		if(event.isDuration())
		{
			EndDate = event.getEndDatePickerEvent().toString();
	    	String[] splitEndDate = EndDate.split("-");
	    	int endDay = Integer.parseInt(splitEndDate[2]);
	    	int endMonth = Integer.parseInt(splitEndDate[1]);

	    	String endYear = splitEndDate[0];
			String monthEnd = chooseMonth(endMonth); 
	    	String axisXend = monthEnd + endYear ;

	    	// add the event to the lineChart, using a series from the class event
	        event.getSeries().setName(event.getTitleEvent());
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay /*listEvent.size()*/));
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXend, endDay /*listEvent.size()*/));
		    // tooltip permit to display something when just passing the mouse over the event
		}
		else // it's a non duration event
		{
			 event.getSeries().setName(event.getTitleEvent());
			 event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay /*listEvent.size()*/));
			 // tooltip permit to display something when just passing the mouse over the event
		}
		
		// add the series to the lineChart
        lineChart.getData().add(event.getSeries());	
        
        // for each data for a series

        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) 
        {
        	
		    // tooltip permit to display something when just passing the mouse over the event

        	ss.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>() 
			{

				public void handle(MouseEvent e) 
				{
				    String text = null ;

					for(int i = 0 ; i< listEvent.size() ; i++)
		        	{
						LocalDate a = event.getStartDatePickerEvent();
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = listEvent.get(i).getStartDatePickerEvent();
	        			
        				LocalDate c = b ;
						if (listEvent.get(i).isDuration())
	        				c = listEvent.get(i).getEndDatePickerEvent();

						if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
							if(listEvent.get(i).isDuration())
							{

								text = new String("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event: " + listEvent.get(i).getEndDatePickerEvent().toString() + "\n" );
							}
							else
							{

								text = new String("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" );
							}
		        		}
						
						Tooltip tool = new Tooltip(text);
						Tooltip.install( ss.getNode(), tool);

		        	}

				}
        		
			});
        	
        	// when clicking on the event 
        	ss.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
	    	{
        		public void handle(MouseEvent e) 
				{
        			for(int i = 0 ; i< listEvent.size() ; i++)
		        	{
        				//popOver.setContentNode(ss.getNode());
        		        PopOver popover = new PopOver();     
        				
        				Event ev = listEvent.get(i);
	        			LocalDate a = event.getStartDatePickerEvent();
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = listEvent.get(i).getStartDatePickerEvent();
	        			
        				LocalDate c = b ;
						if (listEvent.get(i).isDuration())
	        				c = listEvent.get(i).getEndDatePickerEvent();

						Button close = new Button("Close");
				        Button delete = new Button("Delete");
				        Button modify = new Button("Modify");
				        close.setPrefSize(90, 20);
				        delete.setPrefSize(90, 20);
				        modify.setPrefSize(90, 20);
				        
		        		if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
						    // Create three different buttons
						    VBox vbox = new VBox();
						    vbox.setPadding(new Insets(10,10,10,10));
						    StackPane s = new StackPane(vbox);
						    
						    Label text = null ;
					    	String description = listEvent.get(i).getDescEvent();

					    	if(description.isEmpty())
					    	{
					    		description = "none" ;
					    	}
						    // if the event is a duration event
						    if(listEvent.get(i).isDuration())
							{
						    	
								text = new Label("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event: " + listEvent.get(i).getEndDatePickerEvent().toString() + "\n" + 
										"Description: " + description );
								text.setTextFill(Color.BLACK);
								
								if(event.getImageEvent() != null)
								{
									 ImageView image = new ImageView();

				    				    if(listEvent.get(i).getImageEvent() != null)
				    				    {
				    					    im = SwingFXUtils.toFXImage(listEvent.get(i).getImageEvent(), null);
				        				    image.setImage(im);
				        				    image.setFitHeight(40);
				        				    image.setFitWidth(40);
				    				    }
				    				    else
				    				    {
				    				    	im = null;
				    				    }
									vbox.getChildren().addAll(image);
								}
								vbox.getChildren().addAll(text);
								HBox h = new HBox();
								h.getChildren().addAll(close,modify,delete);
								vbox.getChildren().add(h);
			        	        vbox.setStyle("-fx-background-color: white;");

			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);
		        		        popover.show(ss.getNode());
							}
							else
							{
								text = new Label("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"Description: " + description );
								
								text.setTextFill(Color.BLACK);
								if(listEvent.get(i).getImageEvent() != null)
								{
									 ImageView image = new ImageView();

				    				    if(listEvent.get(i).getImageEvent() != null)
				    				    {
				    					    im = SwingFXUtils.toFXImage(listEvent.get(i).getImageEvent(), null);
				        				    image.setImage(im);
				        				    image.setFitHeight(40);
				        				    image.setFitWidth(40);
				    				    }
				    				    else
				    				    {
				    				    	im = null;
				    				    }
									vbox.getChildren().add(image);
								}
								vbox.getChildren().add(text);
								HBox h = new HBox();
								h.getChildren().addAll(close,modify,delete);
								vbox.getChildren().add(h);
			        	        vbox.setStyle("-fx-background-color: white;");

								
			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);

		        				popover.show(ss.getNode());

							}
						    
						    // Listener for the close button
						    close.setOnAction(new EventHandler<ActionEvent>()
						    {
								public void handle(ActionEvent e) 
								{
									popover.hide();
								}
						    });
						    
						    // Listener for the delete button
						    delete.setOnAction(new EventHandler<ActionEvent>()
						    {
								public void handle(ActionEvent e) 
								{
									Alert alert = new Alert(AlertType.CONFIRMATION);
					            	alert.setTitle("Confirmation Dialog");
					            	alert.setContentText("Are you sure?");

					            	Optional<ButtonType> result = alert.showAndWait();
					            	if (result.get() == ButtonType.OK)
					            	{
								        deleteEvent(ev);
										popover.hide();
					            	}
								}
						    });
						    
						    // Listener for the modify button
						    modify.setOnAction(new EventHandler<ActionEvent>()
						    {
								public void handle(ActionEvent e) 
								{
									modifyEvent(ev);
									popover.hide();
								}
							});
		        		}
	
		        	}
				}
	    	});    
        im = null;
        }
	}

	/**
	 * @role method displayEvent who permit to display an event to the line chart, used in the display mode
	 * @param event
	 */
	public void DisplayAddEvent(Event event)
	{

		// add the event to the list of event 
    	listEvent.add(event);
    	
    	String StartDate = event.getStartDatePickerEvent().toString();
		String EndDate = "" ;
    	String[] splitStartDate = StartDate.split("-");
    	String startYear = splitStartDate[0];
    	int startMonth = Integer.parseInt(splitStartDate[1]);
    	int startDay = Integer.parseInt(splitStartDate[2]);
    	// call the method chooseMonth depending on which month is selected
    	String monthStart = chooseMonth(startMonth); 
    	String axisXstart = monthStart + startYear ;
    		
    	// if it's a duration then add an endDate
		if(event.isDuration())
		{
			EndDate = event.getEndDatePickerEvent().toString();
	    	String[] splitEndDate = EndDate.split("-");
	    	int endDay = Integer.parseInt(splitEndDate[2]);
	    	int endMonth = Integer.parseInt(splitEndDate[1]);

	    	String endYear = splitEndDate[0];
			String monthEnd = chooseMonth(endMonth); 
	    	String axisXend = monthEnd + endYear ;

	    	// add the event to the lineChart, using a series from the class event
	        event.getSeries().setName(event.getTitleEvent());
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay /*listEvent.size()*/));
	        event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXend, endDay /*listEvent.size()*/));
		    // tooltip permit to display something when just passing the mouse over the event
		}
		else // it's a non duration event
		{
			 event.getSeries().setName(event.getTitleEvent());
			 event.getSeries().getData().add(new XYChart.Data<String, Number>(axisXstart, startDay /*listEvent.size()*/));
			 // tooltip permit to display something when just passing the mouse over the event
		}
		
		// add the series to the lineChart
        lineChart.getData().add(event.getSeries());	
        
        // for each data for a series

        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) 
        {
        	
		    // tooltip permit to display something when just passing the mouse over the event

        	ss.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>() 
			{

				public void handle(MouseEvent e) 
				{
				    String text = null ;

					for(int i = 0 ; i< listEvent.size() ; i++)
		        	{
						LocalDate a = event.getStartDatePickerEvent();
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = listEvent.get(i).getStartDatePickerEvent();
	        			
        				LocalDate c = b ;
						if (listEvent.get(i).isDuration())
	        				c = listEvent.get(i).getEndDatePickerEvent();

						if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
							if(listEvent.get(i).isDuration())
							{

								text = new String("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event: " + listEvent.get(i).getEndDatePickerEvent().toString() + "\n" );
							}
							else
							{

								text = new String("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" );
							}
		        		}
						
						Tooltip tool = new Tooltip(text);
						Tooltip.install( ss.getNode(), tool);

		        	}

				}
        		
			});
        	
        	// when clicking on the event 
        	ss.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() 
	    	{
        		public void handle(MouseEvent e) 
				{
        			for(int i = 0 ; i< listEvent.size() ; i++)
		        	{
    				    Image im;
					    ImageView image = new ImageView();

    				    if(listEvent.get(i).getImageEvent() != null)
    				    {
    					    im = SwingFXUtils.toFXImage(listEvent.get(i).getImageEvent(), null);
        				    image.setImage(im);
        				    image.setFitHeight(40);
        				    image.setFitWidth(40);
    				    }
    				    else
    				    {
    				    	im = null;
    				    }
    				   

        				//popOver.setContentNode(ss.getNode());
        		        PopOver popover = new PopOver();     
        				
	        			LocalDate a = event.getStartDatePickerEvent();
	        			LocalDate d = a ;
						if(event.isDuration())
	        				d = event.getEndDatePickerEvent();

	        			LocalDate b = listEvent.get(i).getStartDatePickerEvent();
	        			
        				LocalDate c = b ;
						if (listEvent.get(i).isDuration())
	        				c = listEvent.get(i).getEndDatePickerEvent();
						Button close = new Button("Close");
						close.setPrefSize(90, 20);
				       
		        		if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
		        		{
						    // Create three different buttons
						    VBox vbox = new VBox();
						    vbox.setPadding(new Insets(10,10,10,10));
						    StackPane s = new StackPane(vbox);
						    
						    Label text = null ;
					    	String description = listEvent.get(i).getDescEvent();

					    	if(description.isEmpty())
					    	{
					    		description = "none" ;
					    	}
						    // if the event is a duration event
						    if(listEvent.get(i).isDuration())
							{
						    	
								text = new Label("Title Event:  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event: " + StartDate + "\n" + 
										"End date event: " + listEvent.get(i).getEndDatePickerEvent().toString() + "\n" + 
										"Description: " + description );
								text.setTextFill(Color.BLACK);
								
								if(listEvent.get(i).getImageEvent() != null)
								{
									vbox.getChildren().addAll(image);
								}
								vbox.getChildren().addAll(text,close);
			        	        vbox.setStyle("-fx-background-color: white;");

			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);
		        		        popover.show(ss.getNode());
							}
							else
							{
								text = new Label("Title Event :  " + listEvent.get(i).getTitleEvent() + "\n" +
										"Start date event : " + StartDate + "\n" + 
										"Description : " + description );
								
								text.setTextFill(Color.BLACK);
								if(listEvent.get(i).getImageEvent() != null)
								{
									vbox.getChildren().add(image);
								}
								vbox.getChildren().addAll(text,close);
			        	        vbox.setStyle("-fx-background-color: white;");

								
			        	        StackPane root = new StackPane();
			        	        root.getChildren().addAll(s);
			        	        root.setPadding(new Insets(10));
		        		        popover.setContentNode(s);
		        		        if(i%2 ==0)
		        		        	popover.setArrowLocation(ArrowLocation.LEFT_CENTER);
		        		        else
		        		        	popover.setArrowLocation(ArrowLocation.RIGHT_CENTER);

		        				popover.show(ss.getNode());

							}
						  						    
		        		}
		        		// Listener for the close button
					    close.setOnAction(new EventHandler<ActionEvent>()
					    {
							public void handle(ActionEvent e) 
							{
								popover.hide();
							}
					    });
		        	}
				}
	    	});    
        }	
	
	}
}