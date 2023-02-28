package example.user;

import example.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {
    private static final String PATH = "api/auth";

    @Step("Create user")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH + "/register")
                .then()
                ;
    }

    @Step("Log in user")
    public ValidatableResponse login(UserCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH + "/login")
                .then()
                ;
    }

    @Step("Delete user")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .delete(PATH + "/user")
                .then()
                ;
    }

    @Step("Update email")
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

    @Step("Update name")
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

    @Step("Update email and name")
    public ValidatableResponse update(String newEmail, String newName) {
        String json = String.format("{\"email\": \"%s\", \"name\": \"%s\"}", newEmail, newName);
        return given()
                .spec(getSpec())
                .body(json)
                .when()
                .patch(PATH + "/user")
                .then()
                ;
    }
}
