package httpoperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.DataUtil;
import utilities.UrlUtil;

public class Post {

	private String baseUrl;
	private String authorization;
	static final Logger LOGGER = LogManager.getLogger(Post.class.getName());
	public Post(JSONObject properties) throws JSONException
	{
		this.authorization = "Zoho-"+properties.getString("authType")+"token "+properties.getString("authToken");
		this.baseUrl = UrlUtil.constructBaseUrl(properties.getString("baseUrl"), properties.getString("apiVersion"));
	}
	private String executePost(HttpPost httpPost, JSONObject data) throws Exception
	{
		HttpClient client = HttpClientBuilder.create().build();
		LOGGER.debug("\n\n::::::::SENDING DATA:::::::::\n\n\n"+data+"\n\n\n:::::::::::::::\n\n");
		httpPost.setEntity(new StringEntity(data.toString()));
		HttpResponse response = client.execute(httpPost);
		LOGGER.debug("\n\n::::::::RESPONSE:::::::::    "+response.toString()+"     :::::::::::::::\n\n");
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		LOGGER.debug("\n\n::::::::RESPONSE BODY:::::::::\n\n\n"+result+"\n\n\n:::::::::::::::\n\n\n");
		return result.toString();
	}
	public void postRecords(JSONObject data, String module, DataUtil dataUtilObj) throws Exception
	{
		String url = this.baseUrl+"/"+module;
		LOGGER.debug("\n\n::::::::URL:::::::::    "+url+"     :::::::::::::::\n\n");
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Authorization", this.authorization);
		JSONArray dataArr = data.getJSONArray("data");
		int len = dataArr.length();
		ArrayList<JSONObject> dataObj = new ArrayList<JSONObject>();
		int count = 0;
		JSONArray currArr = new JSONArray();
		for(int i=0; i<len; i++)
		{
			if(count==100)
			{
				count = 0;
				JSONObject currObj = new JSONObject();
				currObj.put("data", currArr);
				dataObj.add(currObj);
				currArr = new JSONArray();
			}
			currArr.put(dataArr.getJSONObject(i));
			count++;
		}
		if(currArr.length()!=0)
		{
			JSONObject currObj = new JSONObject();
			currObj.put("data", currArr);
			dataObj.add(currObj);
		}
		for(JSONObject recData : dataObj)
		{
			String result = executePost(httpPost, recData);
			dataUtilObj.collectIds(module, result);
		}
	}
}
