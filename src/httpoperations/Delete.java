package httpoperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.UrlUtil;

public class Delete {
	private String baseUrl;
	private String authorization;
	static final Logger LOGGER = LogManager.getLogger(Delete.class.getName());
	public Delete(JSONObject properties) throws JSONException
	{
		this.authorization = "Zoho-"+properties.getString("authType")+"token "+properties.getString("authToken");
		this.baseUrl = UrlUtil.constructBaseUrl(properties.getString("baseUrl"), properties.getString("apiVersion"));
	}

	private void executeDelete(HttpDelete request) throws Exception
	{
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		LOGGER.debug("\n\n::::::::RESPONSE:::::::::    "+response.toString()+"     :::::::::::::::\n\n");
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOGGER.debug("\n\n::::::::RESPONSE BODY:::::::::\n\n\n"+result+"\n\n\n:::::::::::::::\n\n\n");
		
	}
	public void deleteIds(String module, String[] ids) throws Exception
	{
		String url = this.baseUrl+"/"+module;
		int len = ids.length;
		int count = 0;
		ArrayList<String> currIds = new ArrayList<String>();
		ArrayList<String[]> deleteIds = new ArrayList<String[]>();
		for(int i=0; i<len; i++)
		{
			if(count==100)
			{
				count = 0;
				String[] delIds = new String[currIds.size()];
				delIds = currIds.toArray(delIds);
				deleteIds.add(delIds);
				currIds = new ArrayList<String>();
			}
			currIds.add(ids[i]);
			count++;
		}
		if(currIds.size()!=0)
		{
			String[] delIds = new String[currIds.size()];
			delIds = currIds.toArray(delIds);
			deleteIds.add(delIds);
		}
		for(String[] delIds : deleteIds)
		{
			String delUrl = UrlUtil.addParam(url, "ids", UrlUtil.makeValuesCommaSeparated(delIds));
			LOGGER.debug("\n\n::::::::URL:::::::::    "+delUrl+"     :::::::::::::::\n\n");
			HttpDelete request = new HttpDelete(delUrl);
			request.addHeader("Authorization", this.authorization);
			executeDelete(request);
			
		}
		
	}
}
