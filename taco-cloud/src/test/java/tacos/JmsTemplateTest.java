package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tacos.jms.kitchen.OrderReceiver;
import tacos.jms.messaging.OrderMessagingService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JmsTemplateTest {

    @Autowired
    OrderMessagingService orderMessagingService;
    @Autowired
    OrderReceiver receiver;

    @Test
    public void messageSendReceiveTest() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDeliveryName("deliver");

        orderMessagingService.sendOrder(order);
        Order receiveOrder = receiver.receiveOrder();

        assertEquals(order.getId(), receiveOrder.getId());
        assertEquals(order.getDeliveryName(), receiveOrder.getDeliveryName());
    }
}
