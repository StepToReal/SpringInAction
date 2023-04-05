package tacos.jms.messaging;

import tacos.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
