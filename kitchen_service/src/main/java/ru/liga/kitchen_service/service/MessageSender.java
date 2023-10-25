package ru.liga.kitchen_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MessageSender {

    private final RabbitMQProducerServiceImpl rabbitMQProducerService;

    public void sendOrder(String messageModel, String address) {
        switch (findWordBeforeDistrict(address)) {
            case "Советский":
                rabbitMQProducerService.sendMessage(messageModel, "district.sovetsky");
                break;
            case "Нижегородский":
                rabbitMQProducerService.sendMessage(messageModel, "district.nizhegorodsky");
                break;
            case "Приокский":
                rabbitMQProducerService.sendMessage(messageModel, "district.prioksky");
                break;
        }
    }

    public String findWordBeforeDistrict(String address) {
        Pattern pattern = Pattern.compile("(\\S+)\\s+район");
        Matcher matcher = pattern.matcher(address);
        return matcher.find() ? matcher.group(1) : "";
    }

}
