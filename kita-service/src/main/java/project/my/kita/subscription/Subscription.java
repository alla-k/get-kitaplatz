package project.my.kita.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import project.my.kita.event.KitaPlaceClaimedEvent;
import project.my.kita.event.PlaceReservedEvent;
import project.my.kita.message.BaseMessage;
import project.my.kita.message.input.PlaceClaimedMessage;
import project.my.kita.message.input.PlaceReservedMessage;

@Component
@Slf4j
public class Subscription {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @KafkaListener(topics = "reserve.place", groupId = "kita_service")
    public void listenReservationRequestTopic(String message) {
        log.info("Received message -> {}", message);
        try{
            BaseMessage.OBJECT_MAPPER.findAndRegisterModules();
           PlaceReservedMessage reservePlaceMessage = BaseMessage.OBJECT_MAPPER
                    .readValue(message, PlaceReservedMessage.class);

            applicationEventPublisher.publishEvent(new PlaceReservedEvent(reservePlaceMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @KafkaListener(topics = "place.claim", groupId = "place_service")
    public void listenPlaceClaimTopic(String message) {
        log.info("Received message -> {}", message);
        try{
            BaseMessage.OBJECT_MAPPER.findAndRegisterModules();
            PlaceClaimedMessage placeClaimedMessage = BaseMessage.OBJECT_MAPPER
                    .readValue(message, PlaceClaimedMessage.class);

            applicationEventPublisher.publishEvent(new KitaPlaceClaimedEvent(placeClaimedMessage));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @PostConstruct
    private void start() {
        log.info("Start listening to topics");
    }
}
