package Api.Model;

import lombok.Data;

import java.util.List;


@Data

public class Ingredient {
    private boolean success;
    private List<IngredientData> data;

    public Ingredient() {
    }

    public Ingredient(Boolean success, List<IngredientData> data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<IngredientData> getData() {
        return data;
    }

    public void setData(List<IngredientData> data) {
        this.data = data;
    }

}
