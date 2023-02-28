package order;

import example.order.Order;
import example.order.OrderAssertions;
import example.order.OrderClient;
import example.order.OrderGenerator;
import example.user.User;
import example.user.UserClient;
import example.user.UserCredentials;
import io.qameta.allure.junit4.DisplayName;
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
    public void setUp() {
        userClient = new UserClient();
        randomUser = getRandomUser();
        orderClient = new OrderClient();
        check = new OrderAssertions();
        order = OrderGenerator.getDefaultOrder();
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Create order with authorization successfully")
    public void createOrderWithAuthorizationSuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse createResponse = orderClient.create(accessToken, order);
        check.createSuccessfully(createResponse);
    }

    @Test
    @DisplayName("Create order without authorization unsuccessfully")
    public void createOrderWithoutAuthorizationUnsuccessfully() {
        ValidatableResponse createResponse = orderClient.create(order);
        check.createUnsuccessfully(createResponse);
    }

    @Test
    @DisplayName("Create order without ingredients unsuccessfully")
    public void createOrderWithoutIngredientsUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        order.setIngredients(null);
        ValidatableResponse createResponse = orderClient.create(accessToken, order);
        check.createReturnBadRequest(createResponse);
    }

    @Test
    @DisplayName("Create order with invalid ingredients unsuccessfully")
    public void createOrderWithInvalidIngredientUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        order.setIngredients(new String[]{"Invalid Hash Ingredient"});
        ValidatableResponse createResponse = orderClient.create(accessToken, order);
        check.createReturnInternalServerError(createResponse);
    }
}
