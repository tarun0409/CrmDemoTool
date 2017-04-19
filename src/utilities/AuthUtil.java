package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthUtil {
	
	private static final String clientId = "1000.Y6HRBGVXDSZ2753563NHSIAKRDHS3E";
	private static final String clientSecret = "a4e24519e2c2f25c8c536cda8957653ff701428d89";
	private static final String redirectUrl = "http://zcrm-u12-opti.csez.zohocorpin.com:7000/CRMClientTool/Confirmation.jsp";
	public static String getAuthtoken(String grantToken) throws Exception
	{
		String url = "https://accounts.zoho.com/oauth/v2/token?code="+grantToken+"&redirect_uri="+redirectUrl+"&client_id="+clientId+"&client_secret="+clientSecret+"&grant_type=authorization_code";
		HttpPost httpPost = new HttpPost(url);
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(httpPost);
		BufferedReader rd = new BufferedReader(
		        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject authJson = new JSONObject(result.toString());
		System.out.println(authJson);
		//System.out.println(authJson.toString());
		String authToken = authJson.getString("access_token");
		return authToken;
		
	}
	public static JSONObject getCurrentUserDetails(String authtoken) throws Exception
	{
		JSONObject properties = IOUtil.getProperties();
		String baseUrl = UrlUtil.constructBaseUrl(properties.getString("baseUrl"), properties.getString("apiVersion"));
		String url = baseUrl+"/users?type=CurrentUser";
		String authorization = "Zoho-"+properties.getString("authType")+"token "+authtoken;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("Authorization", authorization);
		HttpResponse response = client.execute(request);
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode==200)
		{
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
				if(resultObj.has("users"))
				{
					JSONArray userdetails = resultObj.getJSONArray("users");
					if(userdetails.length()==1)
					{
						JSONObject userdetail = userdetails.getJSONObject(0);
						JSONObject user = new JSONObject();
						if(userdetail.has("email") && userdetail.getString("email")!=null)
						{
							user.put("email", userdetail.getString("email"));
						}
						if(userdetail.has("full_name") && userdetail.getString("full_name")!=null)
						{
							user.put("full_name", userdetail.getString("full_name"));
						}
						return user;
					}
				}
			}
			catch(JSONException e)
			{
				System.out.println("\n\n\n\n:::::::: NO PROPER RESPONSE RECEIVED:::::::::::::::\n\n\n");
			}
		}
		return new JSONObject();
	}

}
