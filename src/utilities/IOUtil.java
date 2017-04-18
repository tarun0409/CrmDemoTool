package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	public static JSONObject getProperties() throws Exception
	{
		JSONObject properties = new JSONObject();
		
		try(BufferedReader br = new BufferedReader(new FileReader("/home/test/TarunProjects/Projects/CRMClientTool/src/properties/app_properties.txt"))) {
		    for(String line; (line = br.readLine()) != null; ) 
		    {
		        String[] property = line.split("=");
		        Object finalValue;
		        String key = property[0];
		        String value = property[1];
		        finalValue = value;
		        if(DataTypes.jsonArrayTypes.contains(key))
		        {
		        	String[] values = value.split(",");
		        	JSONArray vals = new JSONArray();
		        	for(int i=0; i<values.length; i++)
		        	{
		        		vals.put(values[i]);
		        	}
		        	finalValue = vals;
		        }
		        properties.put(key, finalValue);
		    }
		}
		return properties;
	}
	public static JSONArray getRecordsFromCSV(String module, HashMap<String,String> fieldTypes, HashMap<String, String> fieldLabelApiNameMap, DataUtil dataUtilObj) throws Exception
	{
		System.out.println("*****************************MODULE = "+module+" ******************************");
		System.out.println("**************************\n"+fieldTypes+"\n********************");
		System.out.println("**************************\n"+fieldLabelApiNameMap+"\n********************");
		JSONArray records = new JSONArray();
		File csvDataFile = new File("/home/test/TarunProjects/Projects/CRMClientTool/src/data/"+module+".csv");
		String[] headings = null;
		boolean firstLine = true;
		CSVParser csvRecords = CSVParser.parse(csvDataFile,Charset.defaultCharset() , CSVFormat.RFC4180);
		ArrayList<String> lookupFields = new ArrayList<String>();
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
					String titleString = trimString(csvRecord.get(i));

					headings[i] = fieldLabelApiNameMap.get(titleString);
					String dataType = fieldTypes.get(headings[i]);
					if(dataType!=null && dataType.equals("lookup"))
					{
						lookupFields.add(headings[i]);
					}
				}
				continue;
			}
			JSONObject record = new JSONObject();
			recordLength = csvRecord.size();
			JSONArray lookups = dataUtilObj.getLookups(module);
			if(lookupFields.size()>0)
			{
				int idsIter = 0;
				for(int l=0; l<lookups.length(); l++)
				{
					JSONObject lookup = lookups.getJSONObject(l);
					if(lookup.has("module") && !((lookup.getString("module")).equals("se_module")) && lookupFields.contains(lookup.getString("api_name")))
					{
						ArrayList<Long> ids = dataUtilObj.getIds(lookup.getString("module"));
						if(ids!=null && !ids.isEmpty() && ids.size()>0)
						{
							if(idsIter>=ids.size())
							{
								idsIter=0;
							}
							Long id = ids.get(idsIter);
							record.put(lookup.getString("api_name"), id);
							idsIter++;
						}
					}
				}
			}
			for(int i=0; i<recordLength; i++)
			{
				if(headings[i]==null)
				{
					continue;
				}
				String dataType = fieldTypes.get(headings[i]);
				System.out.println(headings[i]+"======>"+dataType);
				if(DataTypes.timeTypes.contains(headings[i]))
				{
					continue;
				}
				else if(dataType!=null && dataType.equals("lookup"))
				{
					String seModule = trimString(csvRecord.get(i));
					if(seModule==null || seModule.isEmpty() || seModule.equals("nil"))
					{
						continue;
					}
					ArrayList<Long> ids = dataUtilObj.getIds(seModule);
					if(ids!=null && ids.size()>0)
					{
						int idsIter = 0;
						for(int l = 0; l<lookups.length(); l++)
						{
							JSONObject lookup = lookups.getJSONObject(l);
							if(lookup.has("module") && (lookup.getString("module")).equals("se_module") && (lookup.getString("api_name")).equals(headings[i]))
							{
								if(idsIter>=ids.size())
								{
									idsIter=0;
								}
								Long id = ids.get(idsIter);
								record.put(lookup.getString("api_name"), id);
								record.put("se_module", seModule);
								idsIter++;
							}
						}
					}
					
				}
				else if(DataTypes.textTypes.contains(dataType))
				{
					record.put(headings[i], trimString(csvRecord.get(i)));
				}
				else if(dataType.equals("ownerlookup"))
				{
					String str = trimString(csvRecord.get(i));
					str = str != null? str = str.trim() : str;
					try
					{
						long value = Long.parseLong(str);
						record.put(headings[i], value);
					}
					catch(Exception e)
					{
						try
						{
							String[] temps = str.split("_");
							if(temps.length>1)
							{
								long value = Long.parseLong(temps[1]);
								record.put(headings[i], value);
							}
						}
						catch(Exception e1)
						{
							continue;
						}
					}
				}
				else if(dataType.equals("integer")  || dataType.equals("autonumber"))
				{
					String str = trimString(csvRecord.get(i));
					str = str != null? str = str.trim() : str;
					if(str==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
					try
					{
						int value = Integer.parseInt(str);
						record.put(headings[i], value);
					}
					catch(Exception e)
					{
						record.put(headings[i], JSONObject.NULL);
					}
				}
				else if(dataType.equals("boolean"))
				{
					String str = trimString(csvRecord.get(i));
					str = str != null? str = str.trim() : str;
					if(str==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
			    	record.put(headings[i], str.equals("true"));
				}
				else if(dataType.equals("currency"))
				{
					try
					{
						Double val = new Double(trimString(csvRecord.get(i)));
						Double db3 = Double.parseDouble(val.toString());
						Double temp = Math.pow(10.0, 2);
						Double db2 =(double)Math.round(db3 * temp) / temp;
						val = new Double(db2);
						record.put(headings[i], val);
					}
					catch(Exception e)
					{
						record.put(headings[i], JSONObject.NULL);
					}
				}
				else if(dataType.equals("date"))
				{
					String dateString = trimString(csvRecord.get(i));
					dateString = dateString != null? dateString = dateString.trim() : dateString;
					if(dateString==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
					if(dateString==null || dateString.equals(""))
					{
						record.put(headings[i], JSONObject.NULL);
					}
					else
					{
						try
						{
							String value = null;
				    		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yy");
				    		Date date = format1.parse(dateString);
				    		value = DateUtil.getFormattedDate(date, "yyyy-MM-dd");
				    		record.put(headings[i], value);
						}
						catch(Exception e)
						{
							String value = null;
							System.out.println("***********heading*******"+headings[i]+"***********");
							System.out.println("***********datestring*******"+dateString+"***********");

							value = DateUtil.getDate(dateString, "yyyy-MM-dd");
					    	record.put(headings[i], value);
						}
					}
				}
				else if(dataType.equals("datetime"))
				{
					String dateString = trimString(csvRecord.get(i));
					dateString = dateString != null? dateString = dateString.trim() : dateString;
					if(dateString==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
			    	if(dateString==null || dateString.equals(""))
					{
						record.put(headings[i], JSONObject.NULL);
					}
			    	else
			    	{

			    		try
			    		{
			    			String value = null;
				    		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yy hh:mm");
				    		Date date = format1.parse(dateString);
				    		value = DateUtil.getFormattedDate(date, "yyyy-MM-dd'T'HH:mm:ssXXX");
				    		record.put(headings[i], value);
			    		}
			    		catch(Exception e)
			    		{
				    		String value = null;
					    	System.out.println("***********heading*******"+headings[i]+"***********");
					    	value = DateUtil.getDate(dateString, "yyyy-MM-dd'T'HH:mm:ssXXX");
					    	record.put(headings[i], value);
			    		}
			    		
			    	}
				}
				else if(dataType.equals("bigint") || dataType.equals("long"))
				{
					String str = trimString(csvRecord.get(i));
					str = str != null? str = str.trim() : str;
					if(str==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
					try
					{
						long value = Long.parseLong(str);
						record.put(headings[i], value);
					}
					catch(Exception e)
					{
						record.put(headings[i], JSONObject.NULL);
					}
				}
				else if(dataType.equals("double"))
				{
					String str = trimString(csvRecord.get(i));
					str = str != null? str = str.trim() : str;
					if(str==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
					try
					{
						double value = Double.parseDouble(str);
						record.put(headings[i], value);
					}
					catch(Exception e)
					{
						record.put(headings[i], JSONObject.NULL);
					}
				}
				else if(dataType.equals("jsonobject"))
				{
					String str = trimString(csvRecord.get(i));
					str = str != null? str = str.trim() : str;
					if(str==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
					try
					{
						JSONObject value = new JSONObject(str);
						record.put(headings[i], value);
					}
					catch(JSONException e)
					{
						record.put(headings[i], trimString(csvRecord.get(i)));
					}
				}
				else if(dataType.equals("jsonarray") || dataType.equals("multiselectpicklist"))
				{
					String str = trimString(csvRecord.get(i));
					str = str != null? str = str.trim() : str;
					if(str==null)
					{
						record.put(headings[i], JSONObject.NULL);
					}
					try
					{
						JSONArray value = new JSONArray(str);
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
