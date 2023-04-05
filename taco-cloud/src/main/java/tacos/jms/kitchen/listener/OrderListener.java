package tacos.jms.kitchen.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tacos.Order;

@Component
public class OrderListener {
    @JmsListener(destination = "tacocloud.order.queue")
    public Order receiveOrder(Order order) {
        return order;
    }
}
