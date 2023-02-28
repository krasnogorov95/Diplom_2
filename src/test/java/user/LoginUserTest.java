package user;

import example.user.*;
import io.qameta.allure.junit4.DisplayName;
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
    @DisplayName("User can login")
    public void userCanLogIn() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        check.logInSuccessfully(loginResponse);
        accessToken = loginResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Log in without email unsuccessfully")
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
    @DisplayName("Log in without password unsuccessfully")
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
    @DisplayName("Log in with invalid password unsuccessfully")
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
    @DisplayName("Log in with invalid email unsuccessfully")
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
