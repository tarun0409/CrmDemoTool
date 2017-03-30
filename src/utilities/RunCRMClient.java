package utilities;

import java.util.ArrayList;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import httpoperations.Delete;
import httpoperations.Get;
import httpoperations.Post;

public class RunCRMClient {

	static final Logger LOGGER = LogManager.getLogger(RunCRMClient.class.getName());
	public static void main(String[] args) throws Exception{
		JSONObject properties = new JSONObject();
		properties.put("authType", "oauth");
		properties.put("authToken", "1000.02b3ad6c4e3596b576df233eb76ac8f0.72556d1d7e4ddb32280c21aa45e40ea3");
		//properties.put("authToken", "b1b7bf76f99c59006a04ab0dde264a8e");
		properties.put("baseUrl", "https://crm.zoho.com");
		//properties.put("baseUrl", "https://crmqa.localzoho.com");
		properties.put("apiVersion", "2");
		
		/*   POST DATA  */
//		Get getObj = new Get(properties);
//		JSONObject fields = getObj.getModuleFields("Leads");
//		JSONArray dataArray = IOUtil.getRecordsFromCSV(ModuleUtil.getFieldTypes(fields));
//		JSONObject data = new JSONObject();
//		data.put("data", dataArray);
//		Post postObj = new Post(properties);
//		postObj.postRecords(data.toString(), "Leads");
		
		
		/*   GET DATA    */
		Get getObj = new Get(properties);
		ArrayList<JSONObject> records =  getObj.getRecords("Leads");
		for(JSONObject record : records)
		{
			LOGGER.debug("\n\n\n\n\n\n"+record+"\n\n\n\n\n\n");
		}
		
		
		
		
//		Get getObj = new Get(properties);
//		String[] ids = ModuleUtil.getRecordIds(getObj.getRecords("Leads"));
//		Delete deleteObj = new Delete(properties);
//		deleteObj.deleteIds("Leads", ids);
		
		
	}

}
