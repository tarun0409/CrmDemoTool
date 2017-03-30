package utilities;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class ModuleUtil {
	
	public static HashMap<String, String> getFieldTypes(JSONObject moduleData) throws Exception
	{
		JSONArray fields = moduleData.getJSONArray("fields");
		HashMap<String,String> fieldTypes = new HashMap<String,String>();
		for(int i=0; i<fields.length(); i++)
		{
			JSONObject field = fields.getJSONObject(i);
			String apiName = field.getString("api_name");
			String dataType = field.getString("data_type");
			fieldTypes.put(apiName, dataType);
		}
		return fieldTypes;
	}
	public static String[] getRecordIds(JSONObject data) throws Exception
	{
		String[] ids = null;
		JSONArray dataArr = data.getJSONArray("data");
		int noOfRecords = dataArr.length();
		ids = new String[noOfRecords];
		for(int i=0; i<noOfRecords; i++)
		{
			JSONObject record = dataArr.getJSONObject(i);
			String id = record.getString("id");
			ids[i] = id;
		}
		return ids;
	}
	public static boolean isDataEmpty(JSONObject data, String rootElementName) throws Exception
	{
		if(data.has(rootElementName))
		{
			JSONArray dataArr = data.getJSONArray(rootElementName);
			if(dataArr.length()==0)
			{
				return true;
			}
		}
		return false;
	}

}
