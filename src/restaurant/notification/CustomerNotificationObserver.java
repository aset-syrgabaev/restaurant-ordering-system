package restaurant.notification;

public class CustomerNotificationObserver implements OrderObserver {
    @Override
    public void onOrderEvent(OrderEvent event) {
        System.out.println("[CUSTOMER] Order " + event.orderId() + " is now " + event.status());
    }
}
