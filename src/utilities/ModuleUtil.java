package utilities;

import java.util.ArrayList;
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
	public static HashMap<String, String> getFieldLabelApiNameMap(JSONObject moduleData) throws Exception
	{
		HashMap<String,String> map = new HashMap<String,String>();
		JSONArray fields = moduleData.getJSONArray("fields");
		for(int i=0; i<fields.length(); i++)
		{
			JSONObject field = fields.getJSONObject(i);
			String fieldLabel = field.getString("field_label");
			String apiName = field.getString("api_name");
			map.put(fieldLabel, apiName);
		}
		return map;
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
	public static String[] getRecordIds(ArrayList<JSONObject> data) throws Exception
	{
		ArrayList<String> idsArray = new ArrayList<String>();
		for(JSONObject dataObj : data)
		{
			JSONArray dataArr = dataObj.getJSONArray("data");
			for(int i=0; i<dataArr.length(); i++)
			{
				JSONObject record = dataArr.getJSONObject(i);
				String id = record.getString("id");
				idsArray.add(id);
			}
		}
		String[] ids = new String[idsArray.size()];
		return idsArray.toArray(ids);
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
