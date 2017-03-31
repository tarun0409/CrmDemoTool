package utilities;

import java.util.ArrayList;

public class DataTypes {

	public static final ArrayList<String> textTypes = new ArrayList<String>();
	public static final ArrayList<String> jsonArrayTypes = new ArrayList<String>();
	static {
		textTypes.add("text");
		textTypes.add("mediumtext");
		textTypes.add("textarea");
		textTypes.add("website");
		textTypes.add("email");
		textTypes.add("phone");
		textTypes.add("percent");
		textTypes.add("ownerlookup");
		textTypes.add("lookup");
		
		jsonArrayTypes.add("modules");
		jsonArrayTypes.add("operations");
		
	}
	
}
