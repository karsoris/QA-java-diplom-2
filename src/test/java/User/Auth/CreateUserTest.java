package User.Auth;

import Api.Model.User;
import Api.Client.UserClient;
import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CreateUserTest {

    User user;

    UserClient userClient;

    @Test
    @DisplayName("Create user")
    @Description("Create random user")
    public void createUserTest() throws JsonException, IOException {
        user = User.getRandomUser();
        userClient = new UserClient();
        Boolean isOk = userClient.createUser(user)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
    }

    @Test
    @DisplayName("duplicate user")
    @Description("Create duplicate user")
    public void createDuplicateUserTest() throws JsonException, IOException {
        user = User.getRandomUser();
        userClient = new UserClient();
        Boolean isOk = userClient.createUser(user)
                .statusCode(200)
                .extract().path("success");
        assertTrue(isOk);
        Boolean  notOk = userClient.createFailed(user,403)
                .extract().path("success");

        assertEquals(false,notOk);
    }


}
