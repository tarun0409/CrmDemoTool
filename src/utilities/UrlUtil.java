package utilities;

public class UrlUtil {

	public static String constructBaseUrl(String baseUrl, String version)
	{
		return baseUrl+"/"+"crm/"+"v"+version;
	}
}
