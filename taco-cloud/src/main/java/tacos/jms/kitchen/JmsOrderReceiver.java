package tacos.jms.kitchen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.Order;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class JmsOrderReceiver {
    private JmsTemplate jms;
    private MessageConverter converter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
        this.converter = converter;
    }

    public Order receiveOrder() throws JMSException {
        Message message = jms.receive("tacocloud.order.queue");
        return (Order) converter.fromMessage(message);
    }

    public Order receiveOrderConvert() {
        return (Order) jms.receiveAndConvert("tacocloud.order.queue");
    }
}
