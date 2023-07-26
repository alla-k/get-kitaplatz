package project.my.place.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import project.my.place.event.KitaPlaceChangedEvent;
import project.my.place.event.PlaceApplicationReceivedEvent;
import project.my.place.message.BaseMessage;
import project.my.place.message.input.ApplicationCreatedMessage;
import project.my.place.message.input.KitaPlaceCreatedMessage;

@Component
@Slf4j
public class Subscription {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = "reservation.request", groupId = "place_service")
    public void listenReservationRequestTopic(String message) {
        log.info("Received message -> {}", message);
        try{
            BaseMessage.OBJECT_MAPPER.findAndRegisterModules();
            ApplicationCreatedMessage applicationCreatedMessage = BaseMessage.OBJECT_MAPPER
                    .readValue(message, ApplicationCreatedMessage.class);

            applicationEventPublisher.publishEvent(new PlaceApplicationReceivedEvent(applicationCreatedMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @KafkaListener(topics = "kita.place", groupId = "place_service")
    public void listenKitaPlaceTopic(String message) {
        log.info("Received message -> {}", message);
        try{
            BaseMessage.OBJECT_MAPPER.findAndRegisterModules();
            KitaPlaceCreatedMessage kitaPlaceCreatedMessage = BaseMessage.OBJECT_MAPPER
                    .readValue(message, KitaPlaceCreatedMessage.class);

            applicationEventPublisher.publishEvent(new KitaPlaceChangedEvent(kitaPlaceCreatedMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @PostConstruct
    private void start() {
        log.info("Start listening to reservation.request topic");
    }
}
