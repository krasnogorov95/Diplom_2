package example.user;

import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;


public class UserAssertions {
    public void createSuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                ;
    }
    public void createUnsuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                ;
    }
    public void logInSuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                ;
    }
    public void logInUnsuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                ;
    }
    public void updateSuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                ;
    }
    public void updateReturnForbidden(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_FORBIDDEN)
                .body("success", is(false))
                ;
    }
    public void updateReturnUnauthorized(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"))
                ;
    }
}
