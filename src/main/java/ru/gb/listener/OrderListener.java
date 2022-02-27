package ru.gb.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.gb.api.events.OrderEvent;
import ru.gb.config.JmsConfig;
import ru.gb.services.MailService;

@RequiredArgsConstructor
@Component
public class OrderListener {

    private final MailService mailService;

    @JmsListener(destination = JmsConfig.ORDER_CHANGED)
    public void listen(@Payload OrderEvent orderEvent){
        System.out.println(orderEvent);
        mailService.sendMail("smarttest.gg@gmail.com",
                "Order changed", orderEvent.getOrderDto().toString());
    }
}