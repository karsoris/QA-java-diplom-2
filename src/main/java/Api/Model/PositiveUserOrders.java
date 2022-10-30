package Api.Model;

import lombok.Data;

import java.util.List;
@Data
public class PositiveUserOrders {
    private boolean success;
    private List<OrdersData> orders;
    private int total;
    private int totalToday;

    public PositiveUserOrders() {
    }

    public PositiveUserOrders(boolean success, List<OrdersData> orders, int total, int totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<OrdersData> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersData> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }
}
