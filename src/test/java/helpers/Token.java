package helpers;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.TokenRequest;
import models.TokenResponse;

import static io.restassured.RestAssured.given;

public class Token {
    public static String retrieveToken(String login, String pwd){
        String token = "";
        var credentials = new TokenRequest(login,pwd);
        var requestBody = new Gson().toJson(credentials);

        Response response = given()
                .baseUri("https://clarity.dexcom.com/")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        var responseBody = jsonPath.getObject("$", TokenResponse.class);
        token = responseBody.token;
        return token;
    }
}
