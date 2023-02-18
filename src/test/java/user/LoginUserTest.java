package user;

import example.user.*;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
    private User randomUser;
    private UserClient userClient;
    private UserAssertions check;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        check = new UserAssertions();
        randomUser = UserGenerator.getRandomUser();
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    public void userCanLogIn() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        check.logInSuccessfully(loginResponse);
        accessToken = loginResponse.extract().path("accessToken");
    }

    @Test
    public void logInWithoutEmailUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        credentials.setEmail(null);
        loginResponse = userClient.login(credentials);
        check.logInUnsuccessfully(loginResponse);
    }

    @Test
    public void logInWithoutPasswordUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        credentials.setPassword(null);
        loginResponse = userClient.login(credentials);
        check.logInUnsuccessfully(loginResponse);
    }

    @Test
    public void logInWithInvalidPasswordUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        credentials.setPassword("InvalidPassword");
        loginResponse = userClient.login(credentials);
        check.logInUnsuccessfully(loginResponse);
    }

    @Test
    public void logInWithInvalidEmailUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        credentials.setEmail("InvalidEmail@yandex.ru");
        loginResponse = userClient.login(credentials);
        check.logInUnsuccessfully(loginResponse);
    }
}
