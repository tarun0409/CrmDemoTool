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


		public static String getFormattedDate(Date date, String datePattern)
		{
			DateFormat dtpat=new SimpleDateFormat(datePattern);
			String dateString=dtpat.format(date);
			return dateString;
		}
		public static String getDateRange( String type, String datePattern ) throws Exception 
		{
			int FYearStartMonth =fiscalMonth-1;
			TimeZone tz = TimeZone.getTimeZone(timeZone);
			Calendar cal = Calendar.getInstance(tz);
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
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
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
				String date = getFormattedDate(cal.getTime(), datePattern);

				return date;
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
				String date = getFormattedDate(cal.getTime(), datePattern);

				return date;
			}        
			else if( type.equals( THISFY ) )
			{
				if( curMonth < FYearStartMonth )
				{
					cal.add( Calendar.YEAR, -1 );
				}
				cal.set( Calendar.DATE, 1 );
				cal.set( Calendar.MONTH, FYearStartMonth );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if(type.equals(THISANDNEXTFY))
			{
				if(curMonth < FYearStartMonth)
				{
					cal.add(Calendar.YEAR, -1);
				}
				cal.set(Calendar.DATE, 1);
				cal.set(Calendar.MONTH, FYearStartMonth);
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
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
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
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
				String date = getFormattedDate(cal.getTime(), datePattern);
				
				return date;
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
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
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
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
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
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( TODAY ) )
			{
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( YESTERDAY ) )
			{
				cal.add( Calendar.DATE, -1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( TOMORROW ) )
			{
				cal.add( Calendar.DATE, 1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( THISWEEK ) )
			{
				int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
				cal.add( Calendar.DATE, -dayOfWeek + 1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( NEXTWEEK ) )
			{
				int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
				cal.add( Calendar.DATE, -dayOfWeek + 8 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( LASTWEEK ) )
			{
				int dayOfWeek = cal.get( Calendar.DAY_OF_WEEK );
				cal.add( Calendar.DATE, -dayOfWeek - 6 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( THISMONTH ) )
			{
				cal.set( Calendar.DATE, 1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( LASTMONTH ) )
			{
				cal.set( Calendar.DATE, 1 );
				cal.add( Calendar.MONTH, -1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( NEXTMONTH ) )
			{
				cal.set( Calendar.DATE, 1 );
				cal.add( Calendar.MONTH, 1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}        
			else if( type.equals( LAST7DAYS ) )        
			{
				return getRangeForNDays(-7, datePattern);
			}
			else if( type.equals( LAST30DAYS ) )        
			{
				return getRangeForNDays(-30, datePattern);
			}
			else if( type.equals( LAST60DAYS ) )        
			{
				return getRangeForNDays(-60, datePattern);
			}
			else if( type.equals( LAST90DAYS ) )        
			{
				return getRangeForNDays(-90, datePattern);
			}
			else if( type.equals( LAST120DAYS ) )        
			{
				return getRangeForNDays(-120, datePattern);
			}
			else if( type.equals( LAST6MONTHS ) )        
			{
				cal.set( Calendar.DATE, 1 );
				cal.add( Calendar.MONTH, -6 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( LAST12MONTHS ) )        
			{
				cal.set( Calendar.DATE, 1 );
				cal.add( Calendar.MONTH, -12 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( NEXT7DAYS ) )        
			{
				return getRangeForNDays(7, datePattern);
			}
			else if( type.equals( NEXT30DAYS ) )        
			{
				return getRangeForNDays(30, datePattern);
			}
			else if( type.equals( NEXT60DAYS ) )        
			{
				return getRangeForNDays(60, datePattern);
			}
			else if( type.equals( NEXT90DAYS ) )        
			{
				return getRangeForNDays(90, datePattern);
			}
			else if( type.equals( NEXT120DAYS ) )        
			{
				return getRangeForNDays(120, datePattern);
			}
			else if( type.equals( NEXT6MONTHS ) )        
			{
				cal.set( Calendar.DATE, 1 );
				cal.add( Calendar.MONTH, 1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if( type.equals( NEXT12MONTHS ) )        
			{
				cal.set( Calendar.DATE, 1 );
				cal.add( Calendar.MONTH, 1 );
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;
			}
			else if(THISYEAR.equals(type))
			{
				cal.set(Calendar.DAY_OF_YEAR, 1);    
				String date = getFormattedDate(cal.getTime(), datePattern);
				return date;

			}
			return null;
		}
			
		private static String getRangeForNDays( int n , String datePattern) throws Exception
		{
			Calendar cal = Calendar.getInstance();
			if( n > 0 )
			{
				cal.add( Calendar.DATE, n - 1 );
			}
			else
			{
				cal.add( Calendar.DATE, n + 1 );
			}

			String date = getFormattedDate(cal.getTime(), datePattern);
			return date;
		}    


}
