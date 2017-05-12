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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DayTimeline extends Timeline
{

	public DayTimeline(String title, DatePicker startTime, DatePicker endTime) 
	{
		super(title, startTime, endTime);
		//autoincrement id when creating a timeline
		this.id = id++ ;
		this.listEvent = new ArrayList<Event>() ;
		// method to initialize the line chart of the corresponding timeline
		initLineChart();
	}

		@Override
		protected void initLineChart() 
		{
			String startDateTimeline = startDate.getValue().toString();
	    	String[] splitStartDateTimeline = startDateTimeline.split("-");
	    
	    	int startDay = Integer.parseInt(splitStartDateTimeline[2]);
	    	String endDateTimeline = endDate.getValue().toString();
	    	String[] splitEndDateTimeline = endDateTimeline.split("-");
	    	int endDay = Integer.parseInt(splitEndDateTimeline[2]);
	    	
	    	int diffdays = endDay-startDay ;
	    	
	    	ObservableList<String> days = FXCollections.observableArrayList() ;
	    	for(int i = startDay ; i<= endDay ; i++)
	    	{
	    		days.add(Integer.toString(i));
	    	}

	        CategoryAxis xAxis = new CategoryAxis(days);
	        //xAxis.setMinorTickVisible(false);
	        xAxis.setLabel("Days");

	    			
	        // yAxis represente the days
	        NumberAxis yAxis = new NumberAxis(0,31,5);
	        yAxis.setTickLabelsVisible(false);	 
	        yAxis.setMinorTickVisible(false);
	        yAxis.setVisible(false);	        

	        	        
	        lineChart = new LineChart<String, Number>(xAxis, yAxis);
	        lineChart.setTitle(title + "  " + chooseMonth(Integer.parseInt(splitStartDateTimeline[1])) +" " +  splitStartDateTimeline[0]);
	        lineChart.setMinHeight(450);
	        lineChart.setMinWidth((diffdays +1) *100);
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
	    	
	    	String StartDate = event.getStartDatePickerEvent().getValue().toString();
			String EndDate = "" ;
	    	String[] splitStartDate = StartDate.split("-");
	    	int startDay = Integer.parseInt(splitStartDate[2]);
	    	// call the method chooseMonth depending on which month is selected
	    		
	    	// if it's a duration then add an endDate
			if(event.isDuration())
			{
				EndDate = event.getEndDatePickerEvent().getValue().toString();
		    	String[] splitEndDate = EndDate.split("-");
		    	int endDay = Integer.parseInt(splitEndDate[2]);


		    	// add the event to the lineChart, using a series from the class event
		        event.getSeries().setName(event.getTitleEvent());
		        event.getSeries().getData().add(new XYChart.Data<String, Number>(Integer.toString(startDay), listEvent.size() + 2));
		        event.getSeries().getData().add(new XYChart.Data<String, Number>(Integer.toString(endDay), listEvent.size() + 2));
			}
			else // it's a non duration event
			{
				 event.getSeries().setName(event.getTitleEvent());
				 event.getSeries().getData().add(new XYChart.Data<String, Number>(Integer.toString(startDay), listEvent.size() + 2));
			}
			
			// add the series to the lineChart
	        lineChart.getData().add(event.getSeries());	
	        
	        // for each data for a series

	        for (XYChart.Data<String, Number> ss :  event.getSeries().getData()) 
	        {
	        	
	        	////////------------------------------- TODODOODODODO ------------------------------
			    // tooltip permit to display something when just passing the mouse over the event

	        	ss.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,new EventHandler<MouseEvent>() 
				{

					public void handle(MouseEvent e) 
					{
					    String text = null ;

						for(int i = 0 ; i< listEvent.size() ; i++)
			        	{

							
							LocalDate a = event.getStartDatePickerEvent().getValue();
		        			LocalDate d = a ;
							if(event.isDuration())
		        				d = event.getEndDatePickerEvent().getValue();

		        			LocalDate b = listEvent.get(i).getStartDatePickerEvent().getValue();
		        			
	        				LocalDate c = b ;
							if (listEvent.get(i).isDuration())
		        				c = listEvent.get(i).getEndDatePickerEvent().getValue();

							if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
			        		{
								if(listEvent.get(i).isDuration())
								{

									text = new String("Title Event :  " + listEvent.get(i).getTitleEvent() + "\n" +
											"Start date event : " + StartDate + "\n" + 
											"End date event : " + listEvent.get(i).getEndDatePickerEvent().getValue().toString() + "\n" );
								}
								else
								{

									text = new String("Title Event :  " + listEvent.get(i).getTitleEvent() + "\n" +
											"Start date event : " + StartDate + "\n" );
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
	    				    if(event.getImageEvent() != null)
	    				    {
	    					    im = SwingFXUtils.toFXImage(listEvent.get(i).getImageEvent(), null);
	    				    }
	    				    else
	    				    {
	    				    	im = null;
	    				    }
	    				    ImageView image = new ImageView();
	    				    image.setImage(im);
	    				    image.setFitHeight(40);
	    				    image.setFitWidth(40);

	        				//popOver.setContentNode(ss.getNode());
	        		        PopOver popover = new PopOver();     
	        				
	        				Event ev = listEvent.get(i);
		        			LocalDate a = event.getStartDatePickerEvent().getValue();
		        			LocalDate d = a ;
							if(event.isDuration())
		        				d = event.getEndDatePickerEvent().getValue();

		        			LocalDate b = listEvent.get(i).getStartDatePickerEvent().getValue();
		        			
	        				LocalDate c = b ;
							if (listEvent.get(i).isDuration())
		        				c = listEvent.get(i).getEndDatePickerEvent().getValue();


					        Button close = new Button("Close");
					        Button delete = new Button("Delete");
					        Button modify = new Button("Modify");


			        		if(a.isEqual(b) || a.isEqual(c) || b.isEqual(d) || c.isEqual(d))
			        		{
							    // Create three different buttons
							    VBox vbox = new VBox();
							    vbox.setPadding(new Insets(10,10,10,10));
							    //Scene scene = new Scene(vbox, 400, 400);
							    StackPane s = new StackPane(vbox);
							    
							    //stage.setScene(scene);*/
							    Label text = null ;
						    	String description = parseString(listEvent.get(i).getDescEvent());

						    	if(description.isEmpty())
						    	{
						    		description = "none" ;
						    	}
							    // if the event is a duration event
							    if(listEvent.get(i).isDuration())
								{
							    	
									text = new Label("Title Event :  " + listEvent.get(i).getTitleEvent() + "\n" +
											"Start date event : " + StartDate + "\n" + 
											"End date event : " + listEvent.get(i).getEndDatePickerEvent().getValue().toString() + "\n" + 
											"Description : " + description );
									text.setTextFill(Color.WHITE);
									
									
									if(listEvent.get(i).getImageEvent() != null)
									{
										vbox.getChildren().add(image);
									}
									vbox.getChildren().add(text);
									vbox.getChildren().addAll(close,modify,delete);
				        	        vbox.setStyle("-fx-background-color: coral;");

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
									
									text.setTextFill(Color.WHITE);
									if(listEvent.get(i).getImageEvent() != null)
									{
										vbox.getChildren().add(image);
									}
									vbox.getChildren().add(text);
									vbox.getChildren().addAll(close,modify,delete);
				        	        vbox.setStyle("-fx-background-color: coral;");

									
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
	        }	
		}
	}

