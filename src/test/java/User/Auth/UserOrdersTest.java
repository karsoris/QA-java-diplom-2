package User.Auth;

import Api.Client.OrderClient;
import Api.Client.UserClient;
import Api.Model.*;
import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class UserOrdersTest {

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
    @DisplayName("get user orders with token")
    @Description("get user orders with token")
    public void getUserOrderWithToken() throws JsonException, IOException {
        OrderClient orderClient = new OrderClient();
        orderClient.createOrderWithToken(accessToken)
                .statusCode(200);

        PositiveUserOrders response = orderClient.getUsersOrdersWithToken(accessToken)
                .then()
                .statusCode(200)
                .extract()
                .as(PositiveUserOrders.class);
        Assert.assertTrue(response.isSuccess());
    }
    @Test
    @DisplayName("get user orders with token")
    @Description("get user orders with token")
    public void getUserOrderWithoutToken() throws JsonException, IOException {
        OrderClient orderClient = new OrderClient();
        orderClient.createOrderWithoutToken()
                .statusCode(200);

        NegativeUserOrders response = orderClient.getUsersOrdersWithoutToken()
                .then()
                .statusCode(401)
                .extract()
                .as(NegativeUserOrders.class);
        Assert.assertFalse(response.isSuccess());
    }
}
