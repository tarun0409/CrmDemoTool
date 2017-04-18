package utilities;

import java.util.ArrayList;

public class DataTypes {

	public static final ArrayList<String> textTypes = new ArrayList<String>();
	public static final ArrayList<String> jsonArrayTypes = new ArrayList<String>();
	public static final ArrayList<String> timeTypes = new ArrayList<String>();
	public static final ArrayList<String> ID = new ArrayList<String>();
	static {
		textTypes.add("text");
		textTypes.add("mediumtext");
		textTypes.add("textarea");
		textTypes.add("website");
		textTypes.add("email");
		textTypes.add("phone");
		textTypes.add("percent");
		
		jsonArrayTypes.add("modules");
		jsonArrayTypes.add("operations");
		
		timeTypes.add("Last_Activity_Time");
		timeTypes.add("Modified_Time");
		timeTypes.add("Created_Time");
		
		ID.add("id");
		ID.add("ID");
		ID.add("Id");
		
		
		
	}
	
}
