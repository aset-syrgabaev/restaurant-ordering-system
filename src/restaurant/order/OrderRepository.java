package restaurant.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public void save(Order order) {
        orders.put(order.getId(), order);
    }

    public Order findById(String id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new OrderNotFoundException("Order not found: " + id);
        }
        return order;
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}

