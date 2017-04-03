package utilities;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

public class UrlUtil {

	public static String constructBaseUrl(String baseUrl, String version)
	{
		return baseUrl+"/"+"crm/"+"v"+version;
	}
	public static String addParams(String url, HashMap<String, String> params)
	{
		Set<String> paramKeys = params.keySet();
		boolean start = true;
		if(!paramKeys.isEmpty())
		{
			url=url+"?";
		}
		for(String paramKey : paramKeys)
		{
			if(!start)
			{
				url=url+"&";
			}
			start=false;
			String value = params.get(paramKey);
			url=url+paramKey;
			url=url+"=";
			url=url+value;
		}
		return url;
	}
	public static String addParam(String url, String paramKey, String paramValue)
	{
		if(paramKey!=null && paramValue!=null)
		{
			if(!url.contains("?"))
			{
				url=url+"?";
			}
			else
			{
				url=url+"&";
			}
			url=url+paramKey;
			url=url+"=";
			url=url+paramValue;
		}
		return url;
	}
	
	public static String makeValuesCommaSeparated(String[] values)
	{
		String valueString = "";
		int len = values.length;
		for(int i=0; i<len; i++)
		{
			valueString+=values[i];
			if(i!=len-1)
			{
				valueString+=",";
			}
		}
		return valueString;
	}
}
