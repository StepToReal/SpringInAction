package tacos.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tacos.Order;

@SpringBootTest
public class KafkaTest {
    @Autowired KafkaOrderMessagingService service;
    @Autowired KafkaOrderListener listener;

    @Test
    public void testSendMessage() {
        Order order = new Order();
        order.setId(1l);
        order.setDeliveryName("Test Delivery");

        service.sendOrder(order);
    }
}
