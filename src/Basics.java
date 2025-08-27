import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		validate if add place API is working as expected
		
//		given - all input details
		//when - submit the API - http method, resource
		//Then - validate the response
		//Steps - 1.Add place -> 2.update place with new address -> 3.get place to validate if new address is present in response.
		// convert content of the file to string --> content of file can be converted into Byte and then byte data to string (use new String) -
		// so use readALlBytes(Path) method inside the  body instead of the payload
		
		// Add place
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all()
		.queryParam("key", "qaclick123")
		.header("content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\SujithR\\Downloads\\addPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)")
		
		//extracting the place iD from the response as a string response and converting them into json using JsonPath or in other
		// technical words we are sending the string response into json path to extract place ID value by parsing the json.
		.extract().response().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
        //update - PUT place
		String newAddress = "Summer walk, Africa";
		given().log().all().queryParam("key", "qaclick123")
		.header("content-type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200)
		// since we are getting the value we have to extract the response as a string.
		.extract().response().asString();
		
//		now that we have stored the response as a string in the above variable getPlaceResponse and as a next step we have to
		// convert that into Json format as below by passing the above variable which has the stored string .
		
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		// after converting the string into json format, we have to check the address value, so we are storing the address from the
		//json format into a new variable to cross check with the already stored address in the variable newAddress
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	
		
		
		
	}

	
}
