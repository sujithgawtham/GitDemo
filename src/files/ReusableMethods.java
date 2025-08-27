package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	// the reason for using static is that this method can be used inside a test.
//	the reason for 
	public static JsonPath rawToJson(String response) {
		
		JsonPath js1 = new JsonPath(response);
		return js1;
	}

}
