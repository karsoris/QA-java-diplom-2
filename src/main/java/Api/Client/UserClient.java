package Api.Client;

import Api.Model.User;
import Api.Model.UserCredentials;
import Api.Model.UserInformation;
import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.io.IOException;


public class UserClient extends BaseClient {

    private final String ROOT = "auth/register/";
    private final String LOGIN = "auth/login/";

    private final String USER = "auth/user/";

    public UserClient() throws IOException, JsonException {
    }
    @Step("Create User")
    public  ValidatableResponse createUser(User user) {
        return getSpec()
                .body(user)
                .when()
                .post(ROOT)
                .then().log().all();
    }
    @Step("Fail-Create User")
    public  ValidatableResponse createFailed(User user, int statusCode) {
        ValidatableResponse response = getSpec()
                .body(user)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(statusCode);
        if (statusCode == 403) {
            response.extract().path("success");
            response.extract().path("message");
        }
        return response;
    }
    @Step("Login User")
    public ValidatableResponse login(UserCredentials creds, int statusCode) {
        return getSpec()
                .body(creds)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(statusCode);
    }

    @Step("Delete User")
    public ValidatableResponse delete(String accessToken) {
        return getSpecWithToken(accessToken)
                .when()
                .delete(USER)
                .then().log().all();
    }

    @Step("Patch user with token")
    public ValidatableResponse patch(String accessToken, UserInformation userInformation) {
        return getSpec()
                .body(userInformation)
                .when()
                .patch(USER)
                .then().log().all();
    }
    @Step("Patch user without token")
    public ValidatableResponse patchWithToken(String accessToken, UserInformation userInformation) {
        return getSpecWithToken(accessToken)
                .body(userInformation)
                .when()
                .patch(USER)
                .then().log().all();
    }
}

