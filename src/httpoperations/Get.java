package httpoperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.UrlUtil;

public class Get {
	
	private String authToken;
	private String baseUrl;
	private String apiVersion;
	private String authType;
	static final Logger LOGGER = LogManager.getLogger(Get.class.getName());
	public Get(JSONObject properties) throws JSONException
	{
		this.authToken = properties.getString("authToken");
		this.baseUrl = properties.getString("baseUrl");
		this.apiVersion = properties.getString("apiVersion");
		this.authType = properties.getString("authType");
	}
	public JSONObject getModuleFields(String module) throws Exception
	{
		String url = UrlUtil.constructBaseUrl(baseUrl, apiVersion);
		url+="/settings/fields";
		url = UrlUtil.addParam(url, "module", module);
		LOGGER.debug("\n\n::::::::URL:::::::::    "+url+"     :::::::::::::::\n\n");
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Zoho-"+this.authType+"token "+authToken);
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
		
		return (new JSONObject()).put("modules", new JSONArray());
	}
	public JSONObject getRecords(String module) throws Exception
	{
		String url = UrlUtil.constructBaseUrl(baseUrl, apiVersion);
		url+="/"+module;
		LOGGER.debug("\n\n::::::::URL:::::::::    "+url+"     :::::::::::::::\n\n");
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Zoho-"+this.authType+"token "+authToken);
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
		return (new JSONObject()).put("data", new JSONArray()); 
	}

}
