package tacos.rabbitmq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tacos.Order;
import tacos.rabbitmq.receive.RabbitOrderReceiver;
import tacos.rabbitmq.send.RabbitOrderMessagingService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RabbitMqTest {
    @Autowired RabbitOrderMessagingService sender;

    @Autowired RabbitOrderReceiver receiver;

    @Test
    public void testRabbitMessage() {
        Order order = new Order();
        order.setId(1L);
        order.setDeliveryName("test");

        sender.sendOrder(order);
        Order receivedOrder = receiver.receivOrder();

        assertEquals(order.getId(), receivedOrder.getId());
        assertEquals(order.getDeliveryName(), receivedOrder.getDeliveryName());
    }
}
