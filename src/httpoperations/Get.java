package httpoperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.ModuleUtil;
import utilities.UrlUtil;

public class Get {
	
	private String baseUrl;
	private String authorization;
	static final Logger LOGGER = LogManager.getLogger(Get.class.getName());
	public Get(JSONObject properties) throws JSONException
	{
		this.authorization = "Zoho-"+properties.getString("authType")+"token "+properties.getString("authToken");
		this.baseUrl = UrlUtil.constructBaseUrl(properties.getString("baseUrl"), properties.getString("apiVersion"));
	}
	private JSONObject executeGet(String url, String rootElementName) throws Exception
	{
		LOGGER.debug("\n\n::::::::URL:::::::::    "+url+"     :::::::::::::::\n\n");
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", this.authorization);
		HttpResponse response = client.execute(request);
		LOGGER.debug("\n\n::::::::RESPONSE:::::::::    "+response.toString()+"     :::::::::::::::\n\n");
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode==200)
		{
			LOGGER.debug("Module data received successfully!!!!");
			BufferedReader rd = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			try
			{
				JSONObject resultObj = new JSONObject(result.toString());
				return resultObj;
			}
			catch(JSONException e)
			{
				LOGGER.debug("\n\n\n\n:::::::: NO PROPER RESPONSE RECEIVED:::::::::::::::\n\n\n");
			}
		}
		return (new JSONObject()).put(rootElementName, new JSONArray());
	}
	public JSONObject getModuleFields(String module) throws Exception
	{
		String url = this.baseUrl+"/settings/fields";
		url = UrlUtil.addParam(url, "module", module);
		
		return executeGet(url, "modules");
	}
	public ArrayList<JSONObject> getRecords(String module) throws Exception
	{
		ArrayList<JSONObject> records = new ArrayList<JSONObject>();
		String url = this.baseUrl+"/"+module;
		boolean moreRecords = true;
		Integer page = 1;
		while(moreRecords)
		{
			String getUrl = UrlUtil.addParam(url, "per_page", "99");
			getUrl = UrlUtil.addParam(getUrl, "page", page.toString());
			page++;
			JSONObject respData = executeGet(getUrl, "data");
			if(ModuleUtil.isDataEmpty(respData, "data"))
			{
				break;
			}
			records.add(respData);
			if(respData.has("info"))
			{
				JSONObject info = respData.getJSONObject("info");
				moreRecords = (info.getString("more_records")).equals("true");
			}
			else
			{
				break;
			}
		}
		return records; 
	}

}
