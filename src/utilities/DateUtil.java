package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
public class DateUtil {
	

	


		public static final String DATEFORMAT = "yyyy-MM-dd";    
		public static final String TODAY = "today";
		public static final String TOMORROW = "tomorrow";
		public static final String YESTERDAY = "yesterday";
		public static final String THISWEEK= "thisweek";
		public static final String NEXTWEEK= "nextweek";
		public static final String LASTWEEK= "lastweek";
		public static final String THISMONTH = "thismonth";
		public static final String NEXTMONTH = "nextmonth";
		public static final String LASTMONTH = "lastmonth";
		public static final String NEXT7DAYS = "next7days";
		public static final String NEXT30DAYS = "next30days";
		public static final String NEXT60DAYS = "next60days";
		public static final String NEXT90DAYS = "next90days";
		public static final String NEXT120DAYS = "next120days";
		public static final String NEXT6MONTHS = "next6months"; 
		public static final String NEXT12MONTHS = "next12months"; 
		public static final String LAST7DAYS = "last7days";
		public static final String LAST30DAYS = "last30days";    
		public static final String LAST60DAYS = "last60days";
		public static final String LAST90DAYS = "last90days";
		public static final String LAST120DAYS = "last120days";
		public static final String LAST6MONTHS = "last6months"; 
		public static final String LAST12MONTHS = "last12months"; 
		private static final String THISYEAR = "thisyear"; 
		private static final String NEXTYEAR = "nextyear"; 
		private static final String LASTYEAR = "lastyear"; 
		public static final String THISFY = "thisfy";
		public static final String PREVFY = "prevfy";
		public static final String NEXTFY = "nextfy";
		public static final String THISFQ = "thisfq";
		public static final String PREVFQ = "prevfq";
		public static final String NEXTFQ = "nextfq";
		public static final String THISANDNEXTFQ = "thisandnextfq";
		public static final String THISANDNEXTFY = "thisandnextfy";
		public static final String THISANDPREVIOUSFQ = "thisandpreviousfq"; 
		public static final String THISANDPREVIOUSFY = "thisandpreviousfy"; 
		public static final int fiscalMonth = 3; 
		public static final String timeZone = "IST";


		public static boolean isLeapYear(int year)
		{
			return ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)));
		}
		public static String getFormattedDate(Date date, String datePattern)
		{
			DateFormat dtpat=new SimpleDateFormat(datePattern);
			String dateString=dtpat.format(date);
			return dateString;
		}
		public static String getDateByDefaultFormat(String type, String datePattern) throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone(timeZone);
			Calendar cal = Calendar.getInstance(tz);
			Date date = (setCalendar(cal, type, false)).getTime();
			String dateString = getFormattedDate(date, datePattern);
			return dateString;
		}
		public static String getDateByCustomFormat(String customDate, String datePattern) throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone(timeZone);
			Calendar cal = Calendar.getInstance(tz);
			String[] dateString = customDate.split("-");
			if(dateString==null || dateString.length==0)
			{
				return getDateByDefaultFormat(TODAY, datePattern);
			}
			String dayString="", weekString="", monthString="", yearString="";
			int i=0;
			dayString = dateString[i++];
			if(dateString.length>3)
			{
				weekString = dateString[i++];
			}
			monthString = dateString[i++];
			yearString = dateString[i++];
			try
			{
				int year = Integer.parseInt(yearString);
				cal.set(Calendar.YEAR, year);
			}
			catch(Exception e)
			{
				cal = setCalendar(cal,yearString, true);	
			}
			try
			{
				int month = Integer.parseInt(monthString);
				cal.set(Calendar.MONTH, month);
			}
			catch(Exception e)
			{
				cal = setCalendar(cal,monthString, true);	
			}
			if(dateString.length>3)
			{
				cal = setCalendar(cal,weekString, true);
			}
			try
			{
				int day = Integer.parseInt(dayString);
				cal.set(Calendar.DATE, day);
			}
			catch(Exception e)
			{
				cal = setCalendar(cal,dayString, true);
			}
			Date date = cal.getTime();
			String returnDate = getFormattedDate(date, datePattern);
			return returnDate;
		}
		public static Calendar setCalendar(Calendar cal, String type, boolean fromCurrentDate) throws Exception 
		{
			int FYearStartMonth =fiscalMonth-1;
			int curMonth = cal.get( Calendar.MONTH );        
			if( type.equals( THISFQ ) )
			{
				int diff = curMonth - FYearStartMonth;
				if( curMonth < FYearStartMonth )
				{
					diff = 12 - FYearStartMonth + curMonth;
					cal.add( Calendar.YEAR, -1 );
				}
				int fq = diff /3;
				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				cal.add( Calendar.MONTH, fq * 3 );
				return cal;
			}
			else if( type.equals( PREVFQ ) )
			{
				int diff = curMonth - FYearStartMonth;
				if( curMonth < FYearStartMonth )
				{
					diff = 12 - FYearStartMonth + curMonth;
					cal.add( Calendar.YEAR, -1 );
				}
				int fq = diff /3;

				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				cal.add( Calendar.MONTH, (fq-1) * 3 );            
				return cal;
			}        
			else if( type.equals( NEXTFQ ) )
			{
				int diff = curMonth - FYearStartMonth;
				if( curMonth < FYearStartMonth )
				{
					diff = 12 - FYearStartMonth + curMonth;
					cal.add( Calendar.YEAR, -1 );
				}
				int fq = diff /3;

				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				cal.add( Calendar.MONTH, (fq + 1) * 3 );            
				return cal;
			}        
			else if( type.equals( THISFY ) )
			{
				if( curMonth < FYearStartMonth )
				{
					cal.add( Calendar.YEAR, -1 );
				}
				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				return cal;
			}
			else if(type.equals(THISANDNEXTFY))
			{
				if(curMonth < FYearStartMonth)
				{
					cal.add(Calendar.YEAR, -1);
				}
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, FYearStartMonth);
				return cal;
			}
			else if(type.equals(THISANDPREVIOUSFY))
			{
				if(curMonth < FYearStartMonth)
				{
					cal.add(Calendar.YEAR, -1);
				}
				cal.add(Calendar.YEAR, -1);
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, FYearStartMonth);
				return cal;
			}
			else if( type.equals( PREVFY ) )
			{
				if( curMonth < FYearStartMonth )
				{
					cal.add( Calendar.YEAR, -1 );
				}
				cal.add( Calendar.YEAR, -1 );
				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				return cal;
			}
			else if( type.equals( NEXTFY ) )
			{
				if( curMonth < FYearStartMonth )
				{
					cal.add( Calendar.YEAR, -1 );
				}
				cal.add( Calendar.YEAR, 1 );
				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				return cal;
			}        
			else if(type.equals(THISANDNEXTFQ))
			{
				int diff = curMonth - FYearStartMonth;
				if( curMonth < FYearStartMonth )
				{
					diff = 12 - FYearStartMonth + curMonth;
					cal.add( Calendar.YEAR, -1 );
				}
				int fq = diff /3;

				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				cal.add( Calendar.MONTH, fq * 3 );            
				return cal;
			}
			else if(type.equals(THISANDPREVIOUSFQ))
			{
				int diff = curMonth - FYearStartMonth;
				if( curMonth < FYearStartMonth )
				{
					diff = 12 - FYearStartMonth + curMonth;
					cal.add( Calendar.YEAR, -1 );
				}
				int fq = diff /3;

				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				cal.add( Calendar.MONTH, (fq-1) * 3 );            
				return cal;
			}
			else if( type.equals( TODAY ) )
			{
				return cal;
			}
			else if( type.equals( YESTERDAY ) )
			{
				cal.add( Calendar.DATE, -1 );
				return cal;
			}
			else if( type.equals( TOMORROW ) )
			{
				cal.add( Calendar.DATE, 1 );
				return cal;
			}
			else if( type.equals( THISWEEK ) )
			{
				if(!fromCurrentDate)
				{
					int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
					cal.add( Calendar.DATE, -dayOfWeek + 1 );
				}
				return cal;
			}
			else if( type.equals( NEXTWEEK ) )
			{
				if(!fromCurrentDate)
				{
					int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
					cal.add( Calendar.DATE, -dayOfWeek + 8 );
				}
				else
				{
					cal.add( Calendar.DATE, 7 );
				}
				return cal;
			}
			else if( type.equals( LASTWEEK ) )
			{
				if(!fromCurrentDate)
				{
					int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
					cal.add( Calendar.DATE, -dayOfWeek - 6 );
				}
				else
				{
					cal.add( Calendar.DATE, -7 );
				}
				return cal;
			}
			else if( type.equals( THISMONTH ) )
			{
				if(!fromCurrentDate)
				{
					cal.set( Calendar.DATE, 1 );
				}
				return cal;
			}
			else if( type.equals( LASTMONTH ) )
			{
				if(!fromCurrentDate)
				{
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, -1 );
				}
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, -1 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}
			else if( type.equals( NEXTMONTH ) )
			{
				if(!fromCurrentDate)
				{
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, 1 );
				}
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, 1 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}        
			else if( type.equals( LAST7DAYS ) )        
			{
				return getDateForNDays( cal, -7);
			}
			else if( type.equals( LAST30DAYS ) )        
			{
				return getDateForNDays(cal, -30);
			}
			else if( type.equals( LAST60DAYS ) )        
			{
				return getDateForNDays(cal, -60);
			}
			else if( type.equals( LAST90DAYS ) )        
			{
				return getDateForNDays(cal, -90);
			}
			else if( type.equals( LAST120DAYS ) )        
			{
				return getDateForNDays(cal, -120);
			}
			else if( type.equals( LAST6MONTHS ) )        
			{
				if(!fromCurrentDate)
				{
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, -6 );
				}
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, -6 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}
			else if( type.equals( LAST12MONTHS ) )        
			{
				if(!fromCurrentDate)
				{
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, -12 );
				}
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, -12 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}
			else if( type.equals( NEXT7DAYS ) )        
			{
				return getDateForNDays(cal, 7);
			}
			else if( type.equals( NEXT30DAYS ) )        
			{
				return getDateForNDays(cal, 30);
			}
			else if( type.equals( NEXT60DAYS ) )        
			{
				return getDateForNDays(cal, 60);
			}
			else if( type.equals( NEXT90DAYS ) )        
			{
				return getDateForNDays(cal, 90);
			}
			else if( type.equals( NEXT120DAYS ) )        
			{
				return getDateForNDays(cal, 120);
			}
			else if( type.equals( NEXT6MONTHS ) )        
			{
				if(!fromCurrentDate)
				{
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, 1 );
				}
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, 6 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}
			else if( type.equals( NEXT12MONTHS ) )        
			{
				if(!fromCurrentDate)
				{
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, 1 );
				}
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.MONTH, 12 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}
			else if(THISYEAR.equals(type))
			{
				if(!fromCurrentDate)
				{
					cal.set(Calendar.DAY_OF_YEAR, 1);
				}    
				return cal;
			}
			else if(LASTYEAR.equals(type))
			{
				if(!fromCurrentDate)
				{
					cal.set(Calendar.DAY_OF_YEAR, 1);
					cal.add(Calendar.YEAR, -1);
				}   
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.YEAR, -1 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}
			else if(NEXTYEAR.equals(type))
			{
				if(!fromCurrentDate)
				{
					cal.set(Calendar.DAY_OF_YEAR, 1);
					cal.add(Calendar.YEAR, 1);
				}   
				else
				{
					int day = cal.get(Calendar.DATE);
					cal.set( Calendar.DATE, 1 );
					cal.add( Calendar.YEAR, 1 );
					int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
					cal.set(Calendar.DATE, (day<=maxDay? day : maxDay));
				}
				return cal;
			}
			return null;
		}
			
		private static Calendar getDateForNDays(Calendar cal, int n ) throws Exception
		{
			if( n > 0 )
			{
				cal.add( Calendar.DATE, n - 1 );
			}
			else
			{
				cal.add( Calendar.DATE, n + 1 );
			}

			return cal;
		}    


}
