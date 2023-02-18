package order;

import example.order.*;
import example.user.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static example.user.UserGenerator.getRandomUser;

public class CreateOrderTest {

    private Order order;
    private User randomUser;
    private UserClient userClient;
    private OrderClient orderClient;
    private OrderAssertions check;
    private String accessToken;


    @Before
    public void setUp(){
        userClient = new UserClient();
        randomUser = getRandomUser();
        orderClient = new OrderClient();
        check = new OrderAssertions();
        order = OrderGenerator.getDefaultOrder();
    }

    @After
    public void cleanUp(){
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    public void createOrderWithAuthorizationSuccessfully(){
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse createResponse = orderClient.create(accessToken, order);
        check.createSuccessfully(createResponse);
    }

    @Test
    public void createOrderWithoutAuthorizationUnsuccessfully(){
        ValidatableResponse createResponse = orderClient.create(order);
        check.createUnsuccessfully(createResponse);
    }

    @Test
    public void createOrderWithoutIngredientsUnsuccessfully(){
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        order.setIngredients(null);
        ValidatableResponse createResponse = orderClient.create(accessToken, order);
        check.createReturnBadRequest(createResponse);
    }

    @Test
    public void createOrderWithInvalidIngredientUnsuccessfully(){
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        order.setIngredients(new String[]{"Invalid Hash Ingredient"});
        ValidatableResponse createResponse = orderClient.create(accessToken, order);
        check.createReturnInternalServerError(createResponse);
    }
}
