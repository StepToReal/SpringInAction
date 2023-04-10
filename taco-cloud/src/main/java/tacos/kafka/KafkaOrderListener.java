package tacos.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tacos.Order;

@Component
@Slf4j
public class KafkaOrderListener {

    @KafkaListener(topics = "tacocloud.orders.topic", groupId = "group1")
    public void handle(Order order) {
        log.info("Order info : " + order);
    }

//    @KafkaListener(topics = "tacocloud.orders.topic")
//    public void handle(Order order, Message<Order> message) {
//        MessageHeaders headers = message.getHeaders();
//        log.info("Received from partition {} with timestamp {}",
//                headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
//                headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
//        log.info("Order info : " + order);
//    }
}
