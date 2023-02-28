package example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class OrderAssertions {
    @Step("Check create order return OK")
    public void createSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
        ;
    }

    @Step("Check create order return Unauthorized")
    public void createUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
        ;
    }

    @Step("Check create order return Bad Request")
    public void createReturnBadRequest(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
        ;
    }

    @Step("Check create order return Internal server error")
    public void createReturnInternalServerError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
        ;
    }

    @Step("Check get user's order return OK")
    public void getSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
        ;
    }

    @Step("Check get user's order return Unauthorized")
    public void getUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
        ;
    }

}
