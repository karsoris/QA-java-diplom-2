package Api.Model;

public class Order {
    private String ingredients;

    public Order(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public static Order getOrder(Ingredient ingredient, int index) {
        return new Order(ingredient.getData().get(index).get_id());
    }
}
