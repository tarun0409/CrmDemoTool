package utilities;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import httpoperations.Delete;
import httpoperations.Get;
import httpoperations.Post;

public class RunCRMClient {

	static final Logger LOGGER = LogManager.getLogger(RunCRMClient.class.getName());
	public static int reserve = 0;
	public static boolean isReserved()
	{
		return reserve==1?true:false;
	}
	public void startProcess(String authToken) throws Exception{
		reserve=1;
		JSONObject properties = IOUtil.getProperties();
		properties.put("authToken", authToken);
		System.out.println(properties.toString());
		JSONArray modules = properties.getJSONArray("modules");
		LOGGER.debug("\n\n\n:::::::::::::::::MODULES OBTAINED:::::::::::::::::::\n\n"+modules.toString()+"\n\n::::::::::::::::::::::::::\n\n");
		JSONArray operations = properties.getJSONArray("operations");
		LOGGER.debug("\n\n\n:::::::::::::::::OPERATIONS TO BE PERFORMED:::::::::::::::::::\n\n"+operations.toString()+"\n\n::::::::::::::::::::::::::\n\n");
		DataUtil dataObj = new DataUtil();
		for(int i=0; i<modules.length(); i++)
		{
			String module = modules.getString(i);
			for(int j=0; j<operations.length(); j++)
			{
				String operation = operations.getString(j);
				if(operation.equals("insert"))
				{
					LOGGER.debug("\n\n\n:::::::::::::::PERFORMING INSERT ON "+module+" ::::::::::::::::\n\n\n");
					Get getObj = new Get(properties);
					JSONObject fields = getObj.getModuleFields(module);
					dataObj.setLookupMap(module, ModuleUtil.getLookupFields(fields));
					JSONArray dataArray = IOUtil.getRecordsFromCSV(module, ModuleUtil.getFieldTypes(fields), ModuleUtil.getFieldLabelApiNameMap(fields), dataObj);
					JSONObject data = new JSONObject();
					data.put("data", dataArray);
					Post postObj = new Post(properties);
					postObj.postRecords(data, module, dataObj);
				}
				else if(operation.equals("delete"))
				{
					LOGGER.debug("\n\n\n:::::::::::::::PERFORMING DELETE ON "+module+" ::::::::::::::::\n\n\n");
					Get getObj = new Get(properties);
					String[] ids = ModuleUtil.getRecordIds(getObj.getRecords(module));
					Delete deleteObj = new Delete(properties);
					deleteObj.deleteIds(module, ids);
				}
			}
		}
		reserve=0;
		
		
	}

}
