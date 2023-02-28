package example.order;

import example.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class OrderClient extends Client {
    private static final String PATH = "api/orders";

    @Step("Create order without authorization")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then()
                ;
    }

    @Step("Create order with authorization")
    public ValidatableResponse create(String accessToken, Order order) {
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .body(order)
                .when()
                .post(PATH)
                .then()
                ;
    }

    @Step("Get user's order with authorization")
    public ValidatableResponse get(String accessToken) {
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .get(PATH)
                .then()
                ;
    }

    @Step("Get user's order without authorization")
    public ValidatableResponse get() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then()
                ;
    }

}
