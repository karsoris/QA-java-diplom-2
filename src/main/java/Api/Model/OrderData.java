package Api.Model;

import lombok.Data;

import java.util.List;

@Data

public class OrderData {
    private List<String> ingredients;

    public OrderData() {
    }

    public OrderData(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }


}
