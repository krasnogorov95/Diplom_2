package example.user;

import com.github.javafaker.Faker;

public class UserGenerator {

    public static User getRandomUser(){
        Faker faker = new Faker();
        return new User(faker.bothify("????##@yandex.ru"), "password", faker.name().fullName());
    }
    public static String getRandomEmail(){
        Faker faker = new Faker();
        return faker.bothify("????##@yandex.ru");
    }
    public static String getRandomName(){
        Faker faker = new Faker();
        return faker.name().fullName();
    }
}
