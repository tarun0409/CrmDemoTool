package httpoperations;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.UrlUtil;

public class Post {

	private String authToken;
	private String baseUrl;
	private String apiVersion;
	private String authType;
	static final Logger LOGGER = LogManager.getLogger(Post.class.getName());
	public Post(JSONObject properties) throws JSONException
	{
		this.authToken = properties.getString("authToken");
		this.baseUrl = properties.getString("baseUrl");
		this.apiVersion = properties.getString("apiVersion");
		this.authType = properties.getString("authType");
	}
	public void postRecords(String data, String module) throws Exception
	{
		HttpClient client = HttpClientBuilder.create().build();
		String url = UrlUtil.constructBaseUrl(baseUrl, apiVersion);
		url=url+"/"+module;
		LOGGER.debug("\n\n::::::::URL:::::::::    "+url+"     :::::::::::::::\n\n");
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Authorization", "Zoho-"+this.authType+"token "+authToken);
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
	}
}
