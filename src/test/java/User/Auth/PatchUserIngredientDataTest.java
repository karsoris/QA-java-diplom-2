package User.Auth;

import Api.Model.User;
import Api.Client.UserClient;
import Api.Model.UserCredentials;
import Api.Model.UserInformation;
import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PatchUserIngredientDataTest {
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

    @DisplayName("Patch user data with token")
    @Description("Successful patch user data with token")
    @Test
    public void patchUserTest() {

        UserInformation userInformation = new UserInformation(RandomStringUtils.randomAlphanumeric(5) + "@test.com", RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(5));
        Boolean actual = userClient.patchWithToken(accessToken, userInformation)
                .statusCode(200)
                .extract().path("success");

        assertEquals(true, actual);
    }
    @DisplayName("Patch user data without token")
    @Description("Successful patch user data without token")
    @Test
    public void patchUserWithoutTokenTest() {

        UserInformation userInformation = new UserInformation(RandomStringUtils.randomAlphanumeric(5) + "@test.com", RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(5));

        Boolean success = userClient.patch(accessToken, userInformation)
                .statusCode(401)
                .extract().path("success");

        assertEquals(false, success);
    }

}
