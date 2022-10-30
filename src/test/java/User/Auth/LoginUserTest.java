package User.Auth;

import Api.Model.User;
import Api.Client.UserClient;
import Api.Model.UserCredentials;
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

public class LoginUserTest {
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
    @DisplayName("user can log in")
    @Description("Successful authorization of a randomly generated user")
    public void userLoginTest() {
        UserCredentials creds = UserCredentials.from(user);
        accessToken = userClient.login(creds, 200)
                .extract().path("accessToken");
        assertNotEquals(null, accessToken);
    }

    @Test
    @DisplayName("Log in without login")
    @Description("Log in with an empty string")
    public void userLoginWithoutLoginTest() {

        UserCredentials creds = new UserCredentials("", user.getPassword());
        String message = userClient.login(creds, 401)
                .statusCode(401)
                .extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

    @Test
    @DisplayName("Log in without login")
    @Description("Log in with an null string")
    public void userLoginWithNullLoginTest() {

        UserCredentials creds = new UserCredentials(null, user.getPassword());
        String message = userClient.login(creds, 401)
                .statusCode(401)
                .extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

    @Test
    @DisplayName("Log in without password")
    @Description("Log in with an empty password")
    public void userLoginWithoutPasswordTest() {

        UserCredentials creds = new UserCredentials(user.getEmail(), "");
        String message = userClient.login(creds, 401)
                .statusCode(401)
                .extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

    @Test
    @DisplayName("Log in without password")
    @Description("Log in with an null password")
    public void userLoginWithNullPasswordTest() {

        UserCredentials creds = new UserCredentials(user.getEmail(), null);
        String message = userClient.login(creds, 401)
                .statusCode(401)
                .extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

    @Test
    @DisplayName("Log of a non-existent user")
    @Description("Log of a non-existent user")
    public void userLoginWithWrongLoginTest() {

        UserCredentials creds = new UserCredentials(RandomStringUtils.randomAlphanumeric(5) + "@test.com", RandomStringUtils.randomAlphanumeric(5));
        String message = userClient.login(creds, 401)
                .statusCode(401)
                .extract().path("message");
        assertEquals("email or password are incorrect", message);
    }

    @Test
    @DisplayName("Log in with wrong password")
    @Description("Log in with wrong password")
    public void userLoginWithWrongPasswordTest() {
        UserCredentials creds = new UserCredentials(user.getEmail(), RandomStringUtils.randomAlphanumeric(5));
        String message = userClient.login(creds, 401)
                .statusCode(401)
                .extract().path("message");
        assertEquals("email or password are incorrect", message);

    }
}
