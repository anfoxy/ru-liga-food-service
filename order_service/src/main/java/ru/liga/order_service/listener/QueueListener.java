package ru.liga.order_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.commons.dto.NotificationDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueListener {

    @RabbitListener(queues = "customerQueue")
    public void customerQueue(String message) {
        log.info("Received for customer: " + message);
    }

}