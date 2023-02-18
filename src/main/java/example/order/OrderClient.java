package example.order;

import example.Client;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;


public class OrderClient extends Client {
    private static final String PATH = "api/orders";

    public ValidatableResponse create(Order order){
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then()
                ;
    }

    public ValidatableResponse create(String accessToken, Order order){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .body(order)
                .when()
                .post(PATH)
                .then()
                ;
    }

    public ValidatableResponse get(String accessToken){
        return given()
                .spec(getSpec())
                .header("authorization", accessToken)
                .when()
                .get(PATH)
                .then()
                ;
    }

    public ValidatableResponse get(){
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then()
                ;
    }

}
