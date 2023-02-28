package user;

import example.user.User;
import example.user.UserAssertions;
import example.user.UserClient;
import example.user.UserCredentials;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static example.user.UserGenerator.*;

public class ChangeUserDataTest {
    private User randomUser;
    private User anotherRandomUser;
    private UserClient userClient;
    private UserAssertions check;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        check = new UserAssertions();
        randomUser = getRandomUser();
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Update user's email with authorization successfully")
    public void updateUserEmailWithAuthorizationSuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse updateResponse = userClient.updateEmail(accessToken, getRandomEmail());
        check.updateSuccessfully(updateResponse);
    }

    @Test
    @DisplayName("Update user's email to busy email with authorization unsuccessfully")
    public void updateUserEmailToBusyEmailWithAuthorizationUnsuccessfully() {
        anotherRandomUser = getRandomUser();
        userClient.create(anotherRandomUser);
        String busyEmail = anotherRandomUser.getEmail();

        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse updateResponse = userClient.updateEmail(accessToken, busyEmail);
        check.updateReturnForbidden(updateResponse);
    }

    @Test
    @DisplayName("Update user's name with authorization successfully")
    public void updateUserNameWithAuthorizationSuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse updateResponse = userClient.updateName(accessToken, getRandomName());
        check.updateSuccessfully(updateResponse);
    }

    @Test
    @DisplayName("Update without authorization unsuccessfully")
    public void updateWithoutAuthorizationUnsuccessfully() {
        userClient.create(randomUser);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
        ValidatableResponse updateResponse = userClient.update(getRandomEmail(), getRandomName());
        check.updateReturnUnauthorized(updateResponse);
    }
}
