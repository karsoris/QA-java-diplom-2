package Api.Model;

import lombok.Data;

@Data
public class NegativeUserOrders {
    private boolean success;
    private String message;

    public NegativeUserOrders() {
    }

    public NegativeUserOrders(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
