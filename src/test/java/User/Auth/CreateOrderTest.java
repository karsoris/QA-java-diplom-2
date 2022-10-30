package User.Auth;

import Api.Model.*;
import Api.Client.UserClient;
import Api.Client.OrderClient;
import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateOrderTest {

    static User user;
    static UserClient userClient;
    static String accessToken;

    @BeforeClass
    public static void setup() throws JsonException, IOException {
        user = User.getRandomUser();
        userClient = new UserClient();
        userClient.createUser(user)
                .extract().path("success");
        UserCredentials creds = UserCredentials.from(user);
        accessToken = userClient.login(creds, 200)
                .extract().path("accessToken");
    }

    @AfterClass
    public static void teardown() {
        userClient.delete(accessToken)
                .statusCode(202)
                .extract().path("message");
    }

    @Test
    @DisplayName("Create order with token and ingredients")
    @Description("Create order with token and ingredients")
    public void createOrderTestWithTokenAndIngredientsTest() throws JsonException, IOException {
        OrderClient orderClient = new OrderClient();
        Boolean isOk = orderClient.createOrderWithToken(accessToken)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Create order without token and ingredients")
    @Description("Create order without token and ingredients")
    public void createOrderTestWithoutTokenAndIngredientsTest() throws JsonException, IOException {
        OrderClient orderClient = new OrderClient();
        Boolean isOk = orderClient.createOrderWithoutToken()
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Create order without token and ingredients")
    @Description("Create order without token and ingredients")
    public void createOrderTestWithTokenAndWithoutIngredientsTest() throws JsonException, IOException {
        OrderClient orderClient = new OrderClient();
        Boolean isOk = orderClient.createOrderWithToken(accessToken)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("Create order with token and without ingredients")
    @Description("Create order with token and without ingredients")
    public void createOrderWithTokenAndWithoutIngredientsTest() throws JsonException, IOException {
        OrderClient orderClient = new OrderClient();
        OrderData orderData = new OrderData();
        Boolean notOk = orderClient.createOrderWithTokenAndWithoutIngredients(orderData, accessToken)
                .statusCode(400)
                .extract().path("success");
        assertFalse(notOk);
    }

    @Test
    @DisplayName("Create order with invalid hash ingredients")
    @Description("Create order with invalid hash ingredients")
    public void createOrderWithTokenAndInvalidHash() throws JsonException, IOException {
        OrderClient orderClient = new OrderClient();
        orderClient.createOrderWithInvalidHash(accessToken)
                .statusCode(500);

    }

}
