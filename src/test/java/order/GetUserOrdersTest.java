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

public class GetUserOrdersTest {
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
    @DisplayName("Get user's order successfully")
    public void getUserOrdersSuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        orderClient.create(accessToken, order);
        ValidatableResponse getResponse = orderClient.get(accessToken);
        check.getSuccessfully(getResponse);
    }

    @Test
    @DisplayName("Get user's order without authorization unsuccessfully")
    public void getUserOrdersWithoutAuthorizationUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        orderClient.create(accessToken, order);
        ValidatableResponse getResponse = orderClient.get();
        check.getUnsuccessfully(getResponse);
    }
}
