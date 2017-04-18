package utilities;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataUtil {

	HashMap<String,ArrayList<Long>> recordIds;
	public HashMap<String, JSONArray> lookupMap;
	public void setLookupMap(String module, JSONArray lookups)
	{
		if(lookupMap==null)
		{
			lookupMap = new HashMap<String, JSONArray>();
			lookupMap.put(module, lookups);
		}
		else
		{
			lookupMap.put(module, lookups);
		}
	}
	public JSONArray getLookups(String module)
	{
		return lookupMap.get(module);
	}
	public void collectIds(String module, String result)
	{
		ArrayList<Long> ids = new ArrayList<Long>();
		try
		{
			JSONObject resultObj = new JSONObject(result);
			if(resultObj.has("data"))
			{
				JSONArray data = resultObj.getJSONArray("data");
				for(int i=0; i<data.length(); i++)
				{
					JSONObject dataObj = data.getJSONObject(i);
					if(dataObj.has("details") && (dataObj.getJSONObject("details")).has("id"))
					{
						Long id = Long.parseLong((dataObj.getJSONObject("details")).getString("id"));
						ids.add(id);
					}
				}
				if(recordIds==null)
				{
					recordIds = new HashMap<String,ArrayList<Long>>();
					recordIds.put(module, ids);
				}
				else if(recordIds.get(module)==null)
				{
					recordIds.put(module, ids);
				}
				else
				{
					ids.addAll(recordIds.get(module));
					recordIds.put(module, ids);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public ArrayList<Long> getIds(String module)
	{
		if(recordIds==null || !recordIds.containsKey(module))
		{
			return new ArrayList<Long>();
		}
		return recordIds.get(module);
	}
}
