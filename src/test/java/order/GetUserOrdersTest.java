package order;

import example.order.*;
import example.user.*;
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
    public void getUserOrdersSuccessfully(){
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        orderClient.create(accessToken, order);
        ValidatableResponse getResponse = orderClient.get(accessToken);
        check.getSuccessfully(getResponse);
    }
    @Test
    public void getUserOrdersWithoutAuthorizationUnsuccessfully(){
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        orderClient.create(accessToken, order);
        ValidatableResponse getResponse = orderClient.get();
        check.getUnsuccessfully(getResponse);
    }
}
