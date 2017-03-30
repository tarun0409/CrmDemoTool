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
	static final Logger LOGGER = LogManager.getLogger(Get.class.getName());
	public Get(JSONObject properties) throws JSONException
	{
		this.authToken = properties.getString("authToken");
		this.baseUrl = properties.getString("baseUrl");
		this.apiVersion = properties.getString("apiVersion");
	}
	public JSONObject getModulesData(String module) throws Exception
	{
		String url = UrlUtil.constructBaseUrl(baseUrl, apiVersion);
		url+="/settings/modules/"+module;
		LOGGER.debug("\n\n::::::::URL:::::::::    "+url+"     :::::::::::::::\n\n");
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", "Zoho-oauthtoken "+authToken);
		HttpResponse response = client.execute(request);
		LOGGER.debug("\n\n::::::::RESPONSE:::::::::    "+response+"     :::::::::::::::\n\n");
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
			JSONObject resultObj = new JSONObject(result.toString());
			LOGGER.debug("\n\n"+result+"\n\n");
			return resultObj;
		}
		
		return (new JSONObject()).put("modules", new JSONArray());
	}

}
