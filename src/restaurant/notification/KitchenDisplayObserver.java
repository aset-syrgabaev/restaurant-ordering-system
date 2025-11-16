package restaurant.notification;

public class KitchenDisplayObserver implements OrderObserver {
    @Override
    public void onOrderEvent(OrderEvent event) {
        System.out.println("[KITCHEN] Order " + event.orderId() + " event: " + event.type() + " status: " + event.status());
    }
}
