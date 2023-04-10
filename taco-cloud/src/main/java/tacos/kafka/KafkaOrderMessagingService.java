package tacos.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tacos.Order;

@Service
public class KafkaOrderMessagingService {
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Autowired
    public KafkaOrderMessagingService(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrder(Order order) {
        kafkaTemplate.send("tacocloud.orders.topic", order);

        // yml 파일에 spring.kafka.template.default-topic 값을 설정 할 수 있다.
        // default-topic 값을 설정하면 sendDefault()를 통해 객체만 인자로 전달하여 메시지를 전송할 수 있다.
//        kafkaTemplate.sendDefault(order);
    }
}
