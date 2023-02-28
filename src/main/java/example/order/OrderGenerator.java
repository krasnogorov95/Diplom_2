package example.order;

import io.qameta.allure.Step;

public class OrderGenerator {
    @Step("Get default order's data")
    public static Order getDefaultOrder() {
        return new Order(new String[]{"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6d"});
    }
}
