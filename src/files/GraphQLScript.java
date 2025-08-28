package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class GraphQLScript {

    public static void main(String[] args){
        RestAssured.useRelaxedHTTPSValidation();
        int characterID = 17301;
        int episodeID = 16340;
        String response = given().log().all().header("content-type", "application/json")
                .body("{\"query\":\"query($characterId: Int!, $episodeId: Int!)\\n{  \\n  character(characterId: $characterId) \\n  {\\n    name\\n    gender\\n  }\\n  \\n  location(locationId: 24037){\\n    name\\n    dimension\\n  }\\n  episode(episodeId: $episodeId){\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name:\\\"Rahul\\\"}){\\n    info{\\n      count\\n    }\\n    result{\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters:{episode: \\\"hulu\\\"}){\\n    \\n    info{\\n      count\\n    }\\n    result{\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n    \\n  }\\n  \\n}\\n\\n\\n\",\"variables\":{\"characterId\":"+characterID+",\"episodeId\":"+episodeID+"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String characterName = js.getString("data.character.name");
        Assert.assertEquals(characterName, "Higgins");
        System.out.println(characterName);

        //Mutations
        String newCharacter = "George";
        String mutationResponse = given().log().all().header("content-type", "application/json")
                .body("{\"query\":\"mutation($locationName:String!, $characterName:String!, $episodeName:String!) {\\n  \\n  \\n  createLocation(location: {name: $locationName, type: \\\"SouthZone\\\", dimension: \\\"234\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"LittleMan\\\", status: \\\"Active\\\", species: \\\"Sapien\\\", gender: \\\"Male\\\", image: \\\"png\\\", locationId: 24037, originId: 24037}) {\\n    id\\n  }\\n  createEpisode(episode: {name:$episodeName, air_date: \\\"21-may-2010\\\", episode:\\\"hulu\\\"}){\\n    id\\n  }\\n  \\n}\\n\",\"variables\":{\"locationName\":\"Hungary\",\"characterName\":\""+newCharacter+"\",\"episodeName\":\"The wild travel\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();
                 System.out.println(mutationResponse);
    }
}
