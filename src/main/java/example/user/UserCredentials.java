package example.user;

import io.qameta.allure.Step;

public class UserCredentials {
    private String email;
    private String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredentials() {
    }

    @Step("Create credential for log in")
    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    @Step("Set email in user's credentials")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @Step("Set password in user's credentials")
    public void setPassword(String password) {
        this.password = password;
    }
}
