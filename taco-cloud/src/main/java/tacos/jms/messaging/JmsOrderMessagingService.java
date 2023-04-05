package tacos.jms.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import tacos.Order;

import javax.jms.Destination;
import javax.jms.Message;


@Service
public class JmsOrderMessagingService implements OrderMessagingService{
    private JmsTemplate jms;
    private Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue) {
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(Order order) {
        jms.send(orderQueue, session -> session.createObjectMessage(order));
        //send에 도착지를 지정하지 않으면 spring.jms.template.default-destination 에 설정된 경로로 메시지가 전송 됨
        //첫 인자에 Destination 넣으면 해당 경로로 메시지 전송

//        jms.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
        //위 처럼 도착지 정보를 문자열로 설정도 가능하다
    }

    public void sendOrder2(Order order) {
        jms.convertAndSend("tacocloud.order.queue", order);
        //그냥 send와 다르게 객체만 넣으면 자동으로 Message 객체로 변환되어 전송, 도착지 생략 시 기본 도착지로 전송
    }

    public void sendOrderAfterTreat(Order order) {
        //커스텀 헤더 추가
        jms.send("tacocloud.order.queue",
                session -> {
                    Message message = session.createObjectMessage(order);
                    message.setStringProperty("X_ORDER_SOURCE", "WEB");
                    return message;
                });

        //convertAndSend 사용 시 커스텀 헤더 추가 -> MessagePostProcessor 인자로 전달.
        jms.convertAndSend("tacocloud.order.queue", order,
                message -> {
                    message.setStringProperty("X_ORDER_SOURCE", "WEB");
                    return message;
                });
    }
}
