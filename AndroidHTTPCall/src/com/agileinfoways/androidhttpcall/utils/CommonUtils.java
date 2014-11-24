package com.agileinfoways.androidhttpcall.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import android.graphics.Point;
import android.view.Display;
import android.widget.EditText;


public class CommonUtils {

	   
    /** this method is for getting Display 
     *   Dimensions Weather this method is Deprecated or not**/
    public static Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }
    
    public static String getFileSize(long Size)
    {
    	long bytes = Size;
    	long kilobytes = (bytes / 1024);
    	/*long megabytes = (kilobytes / 1024);
    	long gigabytes = (megabytes / 1024);
    	long terabytes = (gigabytes / 1024);
    	long petabytes = (terabytes / 1024);
    	long exabytes = (petabytes / 1024);
    	long zettabytes = (exabytes / 1024);
    	long yottabytes = (zettabytes / 1024);*/
    	
    	return String.valueOf(kilobytes);
    }
    

    public static String  arrayToString(Header  array[])
    {        
    	if (array.length == 0) return "";
    	StringBuilder sb = new StringBuilder();
    	for (Header  n : array) { 
    		if (sb.length() > 0) sb.append(",\n");
    		sb.append("\"").append(n).append("\"");
    	}
    	return sb.toString();
    }



	public static String getDateDifference(Date thenDate){
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        now.setTime(new Date());
        then.setTime(thenDate);

        
        // Get the represented date in milliseconds
        long nowMs = now.getTimeInMillis();
        long thenMs = then.getTimeInMillis();
        
        // Calculate difference in milliseconds
        long diff = nowMs - thenMs;
        
        // Calculate difference in seconds
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

		if (diffMinutes<60){
			if (diffMinutes==1)
				return diffMinutes + " minute ago";
			else
				return diffMinutes + " minutes ago";
		} else if (diffHours<24){
			if (diffHours==1)
				return diffHours + " hour ago";
			else
				return diffHours + " hours ago";
		}else if (diffDays<30){
			if (diffDays==1)
				return diffDays + " day ago";
			else
				return diffDays + " days ago";
		}else {
			return "a long time ago..";
		}
	}
	
	

	
	public static Calendar getDatePart(Date date){
	    Calendar cal = Calendar.getInstance();       // get calendar instance
	    cal.setTime(date);      
	    cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
	    cal.set(Calendar.MINUTE, 0);                 // set minute in hour
	    cal.set(Calendar.SECOND, 0);                 // set second in minute
	    cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

	    return cal;                                  // return the date part
	}
	
	
	public static boolean isdays120(String startDate, String endDate)
	{
		  Date d1 = new Date(startDate);
		  Date d2 = new Date(endDate);
					  
		  if(daysBetween(d1, d2) > 120)
		  {
			  return true;
		  }
		  else
		  {
			  return false;
		  }			
	}
	
	
	public static boolean is2SecoundAfter(String startDate, String endDate)
	{
		  Date d1 = new Date(startDate);
		  Date d2 = new Date(endDate);
					  
		  if(secoundsBetween(d1, d2) > 3)
		  {
			  return true;
		  }
		  else
		  {
			  return false;
		  }			
	}
	
	/**
	 * This method also assumes endTime >= startTime
	**/
	public static long secoundsBetween(Date startDate, Date endDate) {
		
	  Calendar sDate = getDatePart(startDate);
	  Calendar eDate = getDatePart( endDate);
	
	//in milliseconds
		long diff = sDate.getTimeInMillis() - eDate.getTimeInMillis();

		long diffSeconds = diff / 1000 % 60;
		
		return diffSeconds;
	}

	
	
	/**
	 * This method also assumes endDate >= startDate
	**/
	public static long daysBetween(Date startDate, Date endDate) {
		
	  Calendar sDate = getDatePart(startDate);
	  Calendar eDate = getDatePart( endDate);

	  long daysBetween = 0;
	  while (sDate.before(eDate)) {
	      sDate.add(Calendar.DAY_OF_MONTH, 1);
	      daysBetween++;
	  }
	  return daysBetween;
	}


    /**
     * @param view EditText
     * @return true/false
     * @category EditText Validation
     * 
     */
    public static boolean isEditValid(EditText view) {
        boolean isValid = false;

        EditText mEditText = view;

        if (mEditText.getText().toString().trim().length() > 0) {
            isValid = true;
        }

        return isValid;
    }


    /**
     * @param email as a String
     * @return true/false
     * @category Email Validation
     * 
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    
	public static String MillisecondsToDate(long millis) {
		String returnTime;
	//	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  DateFormat df = new SimpleDateFormat("hh:mm a");
	//	formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		returnTime = df.format(c.getTime());
		return returnTime;
	}

	/**
	 * Compare start Date is before start Date 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	
	
	public static boolean isDateBefore(String startDate,String endDate)
    {
        try
        {
            String myFormatString = "yyyy-MM-dd"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);
	  	  
            if (startingDate.before(date1))
                return true;
            else
                return false;
        }
        catch (Exception e) 
        {
           e.printStackTrace();
            return false;
        }
    }

	
	/**
	 * Return date in specified format.
	 * @param milliSeconds Date in milliseconds
	 * @param dateFormat Date format 
	 * @return String representing date in specified format
	 */
	public static String MillisecondsToHeaderDate(long milliSeconds, String dateFormat)
	{
	    // Create a DateFormatter object for displaying date in specified format.
	    DateFormat formatter = new SimpleDateFormat(dateFormat);
	
	    // SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");		
		
	    // Create a calendar object that will convert the date and time value in milliseconds to date. 
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(milliSeconds);
	     return formatter.format(calendar.getTime());
	}
    
}
