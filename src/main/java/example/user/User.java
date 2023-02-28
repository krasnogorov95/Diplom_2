package example.user;

import io.qameta.allure.Step;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Get user's email")
    public String getEmail() {
        return email;
    }

    @Step("Set user's email")
    public void setEmail(String email) {
        this.email = email;
    }

    @Step("Get user's password")
    public String getPassword() {
        return password;
    }

    @Step("Set user's password")
    public void setPassword(String password) {
        this.password = password;
    }

    @Step("Get user's name")
    public String getName() {
        return name;
    }

    @Step("Set user's name")
    public void setName(String name) {
        this.name = name;
    }
}
