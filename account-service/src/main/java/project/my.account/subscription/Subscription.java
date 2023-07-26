package project.my.account.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import project.my.account.message.BaseMessage;
import project.my.account.event.ConfirmationDataReceivedEvent;
import project.my.account.message.input.ConfirmationDataMessage;
;

@Component
@Slf4j
public class Subscription {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = "confirmation.data", groupId = "account_service")
    public void listenReservationRequestTopic(String message) {
        log.info("Received message -> {}", message);
        try{
            BaseMessage.OBJECT_MAPPER.findAndRegisterModules();
          ConfirmationDataMessage  confirmationDataMessage = BaseMessage.OBJECT_MAPPER
                    .readValue(message, ConfirmationDataMessage.class);

            applicationEventPublisher.publishEvent(new ConfirmationDataReceivedEvent(confirmationDataMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @PostConstruct
    private void start() {
        log.info("Start listening to topics");
    }
}
