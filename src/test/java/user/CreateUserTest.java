package user;

import example.user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {

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
    @DisplayName("User can be created")
    public void userCanBeCreated() {
        ValidatableResponse createResponse = userClient.create(randomUser);
        check.createSuccessfully(createResponse);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Identical courier can't be created")
    public void identicalCourierCanNotBeCreated() {
        userClient.create(randomUser);
        ValidatableResponse createResponse = userClient.create(randomUser);
        check.createUnsuccessfully(createResponse);
        UserCredentials credentials = UserCredentials.from(randomUser);
        ValidatableResponse loginResponse = userClient.login(credentials);
        accessToken = loginResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("User without email can't be created")
    public void userWithoutEmailCanNotBeCreated() {
        randomUser.setEmail(null);
        ValidatableResponse createResponse = userClient.create(randomUser);
        check.createUnsuccessfully(createResponse);
    }

    @Test
    @DisplayName("User without password can't be created")
    public void userWithoutPasswordCanNotBeCreated() {
        randomUser.setPassword(null);
        ValidatableResponse createResponse = userClient.create(randomUser);
        check.createUnsuccessfully(createResponse);
    }

    @Test
    @DisplayName("User without name can't be created")
    public void userWithoutNameCanNotBeCreated() {
        randomUser.setName(null);
        ValidatableResponse createResponse = userClient.create(randomUser);
        check.createUnsuccessfully(createResponse);
    }
}
