package utilities;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import httpoperations.Get;

public class RunCRMClient {

	static final Logger LOGGER = LogManager.getLogger(RunCRMClient.class.getName());
	public static void main(String[] args) throws Exception{
		JSONObject properties = new JSONObject();
		properties.put("authToken", "1000.31bf8532b713ef5519ee19faade70a91.09059018886370996667ffcde7bbcd40");
		properties.put("baseUrl", "https://crm.zoho.com");
		properties.put("apiVersion", "2");
		Get getObj = new Get(properties);
		getObj.getModulesData("Leads");
		//LOGGER.debug("This is working");
		

	}

}
