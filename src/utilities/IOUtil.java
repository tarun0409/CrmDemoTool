package utilities;

import java.io.File;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

public class IOUtil {

	public static String trimString(String str)
	{
		str = str.replace("\"", "");
		return str;
	}
	public static JSONArray getRecordsFromCSV(HashMap<String,String> fieldTypes) throws Exception
	{
		JSONArray records = new JSONArray();
		File csvDataFile = new File("src/data/Leads2.csv");
		String[] headings = null;
		boolean firstLine = true;
		CSVParser csvRecords = CSVParser.parse(csvDataFile,Charset.defaultCharset() , CSVFormat.RFC4180);
		for (CSVRecord csvRecord : csvRecords)
		{
			int recordLength = 0;
			if(firstLine)
			{
				firstLine = false;
				recordLength = csvRecord.size();
				headings = new String[recordLength];
				for(int i=0; i<recordLength; i++)
				{
					headings[i] = trimString(csvRecord.get(i));
				}
				continue;
			}
			JSONObject record = new JSONObject();
			recordLength = csvRecord.size();
			for(int i=0; i<recordLength; i++)
			{
				String dataType = fieldTypes.get(headings[i]);
				if(dataType.equals("text") || dataType.equals("email"))
				{
					record.put(headings[i], trimString(csvRecord.get(i)));
				}
				else if(dataType.equals("integer"))
				{
					int value = Integer.parseInt(trimString(csvRecord.get(i)));
					record.put(headings[i], value);
				}
				else if(dataType.equals("boolean"))
				{
			    	record.put(headings[i], (trimString(csvRecord.get(i))).equals("true"));
				}
				else if(dataType.equals("currency"))
				{
					Double val = new Double(trimString(csvRecord.get(i)));
					Double db3 = Double.parseDouble(val.toString());
					Double temp = Math.pow(10.0, 2);
					Double db2 =(double)Math.round(db3 * temp) / temp;
					val = new Double(db2);
					record.put(headings[i], val);
				}
				else if(dataType.equals("date"))
				{
					String dateString = trimString(csvRecord.get(i));
					DateFormat dateParser = new SimpleDateFormat("yyyy-mm-dd");
			    	SimpleDateFormat dateFormatter= new SimpleDateFormat("yyyy-mm-dd");
			    	Date myDate = dateParser.parse(dateString);
			    	record.put(headings[i], dateFormatter.format(myDate));
				}
				else if(dataType.equals("datetime"))
				{
					SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
					String dateString = trimString(csvRecord.get(i));
					Date myDate = dateParser.parse(dateString);
			    	record.put(headings[i], dateFormatter.format(myDate));
				}
				else if(dataType.equals("bigint"))
				{
					long value = Long.parseLong(trimString(csvRecord.get(i)));
					record.put(headings[i], value);
				}
			}
			records.put(record);
		}
		return records;
	}
}
