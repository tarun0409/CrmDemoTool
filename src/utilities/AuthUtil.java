package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class AuthUtil {
	
	private static final String clientId = "1000.Y6HRBGVXDSZ2753563NHSIAKRDHS3E";
	private static final String clientSecret = "a4e24519e2c2f25c8c536cda8957653ff701428d89";
	private static final String redirectUrl = "http://localhost:7000/CRMClientTool/StartProcess.jsp";
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
		//System.out.println(authJson.toString());
		String authToken = authJson.getString("access_token");
		return authToken;
		
	}

}
