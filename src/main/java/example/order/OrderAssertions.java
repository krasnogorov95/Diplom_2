package example.order;

import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class OrderAssertions {
    public void createSuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
                ;
    }
    public void createUnsuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
                ;
    }
    public void createReturnBadRequest(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("success", is(false))
                ;
    }

    public void createReturnInternalServerError(ValidatableResponse response) {
        response.assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                ;
    }

    public void getSuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_OK)
                .body("success", is(true))
        ;
    }

    public void getUnsuccessfully(ValidatableResponse response){
        response.assertThat()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", is(false))
        ;
    }

}
