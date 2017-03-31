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
		properties.put("authToken", "1000.5a310c79a43e1cc9f68bc84353849333.57007ef1f816261f718e21a9ffca4fd5");
		//properties.put("authToken", "b1b7bf76f99c59006a04ab0dde264a8e");
		properties.put("baseUrl", "https://crm.zoho.com");
		//properties.put("baseUrl", "https://crmqa.localzoho.com");
		properties.put("apiVersion", "2");
		
		/*   POST DATA  */
		Get getObj = new Get(properties);
		JSONObject fields = getObj.getModuleFields("Events");
		JSONArray dataArray = IOUtil.getRecordsFromCSV(ModuleUtil.getFieldTypes(fields), ModuleUtil.getFieldLabelApiNameMap(fields));
		JSONObject data = new JSONObject();
		data.put("data", dataArray);
		Post postObj = new Post(properties);
		postObj.postRecords(data, "Events");
		
		
		/*   GET DATA    */
//		Get getObj = new Get(properties);
//		ArrayList<JSONObject> records =  getObj.getRecords("Leads");
//		for(JSONObject record : records)
//		{
//			LOGGER.debug("\n\n\n\n\n\n"+record+"\n\n\n\n\n\n");
//		}
		
		
		
		/*   DELETE DATA   */
//		Get getObj = new Get(properties);
//		String[] ids = ModuleUtil.getRecordIds(getObj.getRecords("Leads"));
//		Delete deleteObj = new Delete(properties);
//		deleteObj.deleteIds("Leads", ids);
		
		
	}

}
