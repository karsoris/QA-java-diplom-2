package Api.Client;

import Api.Model.Ingredient;
import Api.Model.IngredientData;
import Api.Model.OrderData;
import com.github.cliftonlabs.json_simple.JsonException;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private final String INGREDIENTS = "ingredients/";
    private final String ORDERS = "orders/";

    public OrderClient() throws IOException, JsonException {
    }

    @Step("Get Ingredients")
    public Response getIngredients() {
        return given().spec(getSpec())
                .when()
                .get(INGREDIENTS);
    }

    @Step("Get id Ingredient")
    public List<String> getRandomIngredients(Ingredient ingredient) throws JsonException, IOException {

        List<IngredientData> data = ingredient.getData();
        List<String> ingredientsId = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            String id = data.get(random.nextInt(data.size()))
                    .get_id();
            ingredientsId.add(id);
        }

        return ingredientsId;
    }

    @Step("Create Order with token")
    public ValidatableResponse createOrderWithToken(String accessToken) throws JsonException, IOException {
        Ingredient ingredient = getIngredient();

        OrderData orderData = new OrderData(getRandomIngredients(ingredient));
        return getSpecWithToken(accessToken)
                .body(orderData)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Create Order without token")
    public ValidatableResponse createOrderWithoutToken() throws JsonException, IOException {
        Ingredient ingredient = getIngredient();

        OrderData orderData = new OrderData(getRandomIngredients(ingredient));
        return getSpec()
                .body(orderData)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Create Order with invalid hash")
    public ValidatableResponse createOrderWithInvalidHash(String accessToken) throws JsonException, IOException {
        List<String> invalidIngredientsList = Arrays.asList(RandomStringUtils.randomAlphanumeric(24));

        OrderData orderData = new OrderData(invalidIngredientsList);
        return getSpecWithToken(accessToken)
                .body(orderData)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Create Order with token")
    public ValidatableResponse createOrderWithTokenAndWithoutIngredients(OrderData orderData, String accessToken) throws JsonException, IOException {
        return getSpecWithToken(accessToken)
                .body(orderData)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Get ingredient- object after response")
    private Ingredient getIngredient() {
        Ingredient ingredient = getIngredients()
                .then()
                .statusCode(200)
                .extract()
                .as(Ingredient.class);
        return ingredient;
    }
    @Step("Get user orders")
    public  Response getUsersOrdersWithToken(String accessToken) {
        return given()
                .spec(getSpecWithToken(accessToken))
                .when()
                .get(ORDERS);
    }
    @Step("Get user orders")
    public  Response getUsersOrdersWithoutToken() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDERS);
    }
}
