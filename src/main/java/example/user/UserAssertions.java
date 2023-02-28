package example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;


public class UserAssertions {
    @Step("Check create return OK")
    public void createSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
        ;
    }

    @Step("Check create return Forbidden")
    public void createUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
        ;
    }

    @Step("Check log in return OK")
    public void logInSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
        ;
    }

    @Step("Check log in return Unauthorized")
    public void logInUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
        ;
    }

    @Step("Check return OK")
    public void updateSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
        ;
    }

    @Step("Check update return Forbidden")
    public void updateReturnForbidden(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
        ;
    }

    @Step("Check update return Unauthorized")
    public void updateReturnUnauthorized(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"))
        ;
    }
}
