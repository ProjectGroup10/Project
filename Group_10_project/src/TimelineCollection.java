import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimelineCollection implements displayInterface
{
	private ArrayList<Timeline> listTimeline;
	
	public TimelineCollection ()
	{
		listTimeline  = new ArrayList<Timeline>() ;
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Date d1 = new Date ("04/10/1995");
		Date d2 = new Date ("04/10/1998");

		TimelineCollection timeCollec = new TimelineCollection();
	    SimpleDateFormat ft = new SimpleDateFormat ("1995-10-04"); 
	    SimpleDateFormat fte = new SimpleDateFormat ("1997-10-04"); 		
	    Timeline t = new Timeline("test timeline",d1, d2);
	      
	    timeCollec.addTimeline(t);
	    timeCollec.display();

	}

	public ArrayList<Timeline> getListTimeline() {
		return listTimeline;
	}

	public void setListTimeline(ArrayList<Timeline> listTimeline) {
		this.listTimeline = listTimeline;
	}

	@Override
	public void display() 
	{
		for (Timeline timeline : listTimeline) 
		{
		    timeline.display();
		}	
	}
	
	public void addTimeline(Timeline t)
	{
		if(validateTimeTimeline(t))
		{
			listTimeline.add(t);
		}
		else
		{
			System.out.println("Error about the date");
		}
	}
	
	public void deleteTimeline(Timeline t)
	{
		listTimeline.remove(t);
	}
	
	public void saveTimeline()
	{
		// FILE READER 
	}
	
	public void loadTimeline(Path p)
	{
		
	}
	
	public boolean validateTimeTimeline(Timeline t )
	{
		if (t.getStartTime().compareTo(t.getEndTime()) < 0 )
		{
			return true ;
		}
		else
			return false ;
	}

}
