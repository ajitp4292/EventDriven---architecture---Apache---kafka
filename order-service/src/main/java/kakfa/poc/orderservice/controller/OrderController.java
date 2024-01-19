package kakfa.poc.orderservice.controller;


import kakfa.poc.basedomains.dto.Order;
import kakfa.poc.basedomains.dto.OrderEvent;
import kakfa.poc.orderservice.kafka.OrderProducer;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    private OrderProducer orderProducer;

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderId((UUID.randomUUID().toString()));
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending order");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);
        return "Orderplaced successfully";
    }


    }


