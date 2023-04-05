package tacos.jms.kitchen;

import tacos.Order;

public interface OrderReceiver {
    Order receiveOrder() throws Exception;

    Order receiveOrderConvert();
}
