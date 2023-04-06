package tacos.rabbitmq.send;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import tacos.Order;
import tacos.jms.messaging.OrderMessagingService;

@Service
public class RabbitOrderMessagingService {
    private RabbitTemplate rabbit;

    public RabbitOrderMessagingService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void sendOrder(Order order) {
        MessageConverter converter = rabbit.getMessageConverter();
        MessageProperties props = new MessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB"); //헤더 설정
        Message message = converter.toMessage(order, props);
        rabbit.send("tacocloud.orders", message);
    }

    public void sendOrder2(Order order) {
        rabbit.convertAndSend("tacocloud.orders", order,
                message -> {
                    MessageProperties props = message.getMessageProperties();
                    props.setHeader("X_ORDER_SOURCE", "WEB");
                    return message;
                });
    }
}
