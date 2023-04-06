package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tacos.jms.kitchen.JmsOrderReceiver;
import tacos.jms.messaging.JmsOrderMessagingService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JmsTemplateTest {

    @Autowired
    JmsOrderMessagingService orderMessagingService;
    @Autowired
    JmsOrderReceiver receiver;

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
