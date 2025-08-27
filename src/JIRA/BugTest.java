package JIRA;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class BugTest {

    public static void main(String[] args){

        RestAssured.useRelaxedHTTPSValidation(); // if there is any sun certificate issue use this syntax
        RestAssured.baseURI = "https://sujithgawtham.atlassian.net";
        String bugCreationResponse = given().log().all()
                .header("Content-Type","application/json")
                .header("Authorization","Basic Z2F3dGhhbTE2MUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbjF2ODRWN0Z1bGVxR25yRHNJZ2JsMlJpamRKbWtSMVRaV0pmYXl1Tm82dnR0MGpSZWV3V2NSdGhvVEV3OFdQVlhxX21ZdmJaazBsTjhHdlJKdHFiOGp1ZV8xc1h3a0pYSDNWS1p4UnZLdXYzSE5qWlFFd2pMRkJjazZ4R25ySXZjVl9Wb0JTeTNlbU1aTHBnM0JVTVZWNGVwTThyejlKdk4wRXhjd0dtUm5zPUYwRjY3NDU4")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"SCRUM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Link is not working - Automation.\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .log().all()
                .post("rest/api/3/issue").then().log().all()
                .assertThat().statusCode(201).contentType("application/json")
                .extract().response().asString();

        JsonPath js = new JsonPath(bugCreationResponse);
        String bugID = js.getString("id");
        System.out.println(bugID);


    }

}
