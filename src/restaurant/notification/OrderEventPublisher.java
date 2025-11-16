package restaurant.notification;

import java.util.ArrayList;
import java.util.List;

public class OrderEventPublisher {
    private final List<OrderObserver> observers = new ArrayList<>();

    public void registerObserver(OrderObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer must not be null");
        }
        observers.add(observer);
    }

    public void publish(OrderEvent event) {
        for (OrderObserver observer : observers) {
            observer.onOrderEvent(event);
        }
    }
}
