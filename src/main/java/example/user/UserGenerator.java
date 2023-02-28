package example.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

public class UserGenerator {

    @Step("Get full random user's data")
    public static User getRandomUser() {
        Faker faker = new Faker();
        return new User(faker.bothify("????##@yandex.ru"), "password", faker.name().fullName());
    }

    @Step("Get random user's email")
    public static String getRandomEmail() {
        Faker faker = new Faker();
        return faker.bothify("????##@yandex.ru");
    }

    @Step("Get random user's name")
    public static String getRandomName() {
        Faker faker = new Faker();
        return faker.name().fullName();
    }
}
