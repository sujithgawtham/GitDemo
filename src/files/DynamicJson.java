package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test (dataProvider = "BooksData")
    // this will search the file for dataprovider and it will check for how may arrays are present
    // so if there are three arrays this will run for three times
    public void addBook(String isbn, String aisle){
        // in order for the dataprovider to pass inside this method, we need to pass the arguments/parameters isbn and aisle
        //

        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all()
                .header("Content-Type","application/json")
                .body(payload.AddBook(isbn,aisle))
                .when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = ReusableMethods.rawToJson(response);
        String id= js.get("ID");
        System.out.println(id);
    }

    @DataProvider(name="BooksData")
    // this dataprovider is used to provide multiplae data or dynamic data if there are more than one data of a book for example: if there are more than
    //one data of a book this dataprovider can be used to a test method by calling its name "BooksData" and thats why a name is provided to the dataprovider.
    // array - collection of elements inside curly braces {}
    // multidimensional array - collection of arrays ---> {{},{},{},{}...}
    // if only one square bracket----> Object[] then it is singledimensional array {}
    // if two square bracket ----> Object[][] then it is a multidimensional array {{},{},{}}
    public Object[][] getData() {
        return new Object[][] {{"dskhnq","456"},{"thgbr","543"},{"jkutr","789"}};
    }
}
