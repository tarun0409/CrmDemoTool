package utilities;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IOUtil {

	public static String trimString(String str)
	{
		str = str.replace("\"", "");
		return str;
	}
	public static JSONArray getRecordsFromCSV(HashMap<String,String> fieldTypes, HashMap<String, String> fieldLabelApiNameMap) throws Exception
	{
		JSONArray records = new JSONArray();
		File csvDataFile = new File("src/data/Events.csv");
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
					headings[i] = fieldLabelApiNameMap.get(trimString(csvRecord.get(i)));
				}
				continue;
			}
			JSONObject record = new JSONObject();
			recordLength = csvRecord.size();
			for(int i=0; i<recordLength; i++)
			{
				String dataType = fieldTypes.get(headings[i]);
				if(DataTypes.textTypes.contains(dataType))
				{
					record.put(headings[i], trimString(csvRecord.get(i)));
				}
				else if(dataType.equals("integer")  || dataType.equals("autonumber"))
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
					String value = DateUtil.getDateRange(dateString, "yyyy-mm-dd");
			    	record.put(headings[i], value);
				}
				else if(dataType.equals("datetime"))
				{
			    	String dateString = trimString(csvRecord.get(i));
					String value = DateUtil.getDateRange(dateString, "yyyy-MM-dd'T'HH:mm:ssXXX");
			    	record.put(headings[i], value);
				}
				else if(dataType.equals("bigint") || dataType.equals("long"))
				{
					long value = Long.parseLong(trimString(csvRecord.get(i)));
					record.put(headings[i], value);
				}
				else if(dataType.equals("double"))
				{
					double value = Double.parseDouble(trimString(csvRecord.get(i)));
					record.put(headings[i], value);
				}
				else if(dataType.equals("jsonobject"))
				{
					try
					{
						JSONObject value = new JSONObject(trimString(csvRecord.get(i)));
						record.put(headings[i], value);
					}
					catch(JSONException e)
					{
						record.put(headings[i], trimString(csvRecord.get(i)));
					}
				}
				else if(dataType.equals("jsonarray") || dataType.equals("multiselectpicklist"))
				{
					try
					{
						JSONArray value = new JSONArray(trimString(csvRecord.get(i)));
						record.put(headings[i], value);
					}
					catch(JSONException e)
					{
						record.put(headings[i], trimString(csvRecord.get(i)));
					}
				}
				else if(dataType.equals("picklist") || dataType.equals("formula"))
				{
					Object value = null;
					try
					{
						value = Integer.parseInt(trimString(csvRecord.get(i)));
					}
					catch(Exception e)
					{
						try
						{
							value = Long.parseLong(trimString(csvRecord.get(i)));
						}
						catch(Exception e1)
						{
							try
							{
								value = Float.parseFloat(trimString(csvRecord.get(i)));
							}
							catch(Exception e3)
							{
								try
								{
									value = Double.parseDouble(trimString(csvRecord.get(i)));
								}
								catch(Exception e4)
								{
									value = trimString(csvRecord.get(i));
								}
							}
						}
					}
					record.put(headings[i], value);
				}
			}
			records.put(record);
		}
		return records;
	}
}
