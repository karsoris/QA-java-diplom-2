package User.Auth;

import Api.Model.User;
import Api.Client.UserClient;
import com.github.cliftonlabs.json_simple.JsonException;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)

public class CreateUserParametrizedTest {
    private String email;
    private String password;
    private String name;

    User user;

    UserClient userClient;

    public CreateUserParametrizedTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    @Parameterized.Parameters(name = " login: {0} password: {1} firstName : {2}")
    public static Object[][] getCreateCourierData() {
        return new Object[][]{
                {null, "P@ssw0rd", RandomStringUtils.randomAlphabetic(10)},
                {"", "P@ssw0rd", RandomStringUtils.randomAlphabetic(10)},
                {RandomStringUtils.randomAlphabetic(10) + "@test.com", null, RandomStringUtils.randomAlphabetic(10)},
                {RandomStringUtils.randomAlphabetic(10) + "@test.com", "", RandomStringUtils.randomAlphabetic(10)},
                {null,null,null},
                {"","",""}
        };
    }
    @Test
    @DisplayName("Create user")
    @Description("Create user with all combinations")
    public void createUserWithoutFields() throws JsonException, IOException {
        user = User.getRandomUserWithoutAnyField(email, password, name);
        userClient = new UserClient();
        String notOk = userClient.createFailed(user, 403)
                .extract().path("message");
        assertEquals("Email, password and name are required fields", notOk);

    }


}


