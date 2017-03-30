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
	public static void main(String[] args) throws Exception{
		JSONObject properties = new JSONObject();
		properties.put("authType", "oauth");
		properties.put("authToken", "1000.c70b2f4d9ee2015f6aeb7bf28cf2d6ca.e438729f10a6a4c3a4c326cc3e2dc4b2");
		//properties.put("authToken", "b1b7bf76f99c59006a04ab0dde264a8e");
		properties.put("baseUrl", "https://crm.zoho.com");
		//properties.put("baseUrl", "https://crmqa.localzoho.com");
		properties.put("apiVersion", "2");
//		Get getObj = new Get(properties);
//		JSONObject fields = getObj.getModuleFields("Leads");
//		JSONArray dataArray = IOUtil.getRecordsFromCSV(ModuleUtil.getFieldTypes(fields));
//		JSONObject data = new JSONObject();
//		data.put("data", dataArray);
//		Post postObj = new Post(properties);
//		postObj.postRecords(data.toString(), "Leads");
		Get getObj = new Get(properties);
		String[] ids = ModuleUtil.getRecordIds(getObj.getRecords("Leads"));
		Delete deleteObj = new Delete(properties);
		deleteObj.deleteIds("Leads", ids);
	}

}
