package example.user;

import example.Client;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    private static final String PATH = "api/auth";

    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH + "/register")
                .then()
                ;
    }
    public ValidatableResponse login(UserCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH + "/login")
                .then()
                ;
    }
    public ValidatableResponse delete(String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .delete(PATH + "/user")
                .then()
                ;
    }

    public ValidatableResponse updateEmail(String accessToken, String newEmail) {
        String json = String.format("{\"email\": \"%s\"}", newEmail);
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .body(json)
                .when()
                .patch(PATH + "/user")
                .then()
                ;
    }

    public ValidatableResponse updateName(String accessToken, String newName) {
        String json = String.format("{\"name\": \"%s\"}", newName);
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .body(json)
                .when()
                .patch(PATH + "/user")
                .then()
                ;
    }

    public ValidatableResponse update(String newEmail, String newName) {
        String json = String.format("{\"email\": \"%s\", \"name\": \"%s\"}",newEmail, newName);
        return given()
                .spec(getSpec())
                .body(json)
                .when()
                .patch(PATH + "/user")
                .then()
                ;
    }

}
