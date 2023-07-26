package project.my.account.producer;

import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import project.my.account.message.output.PlaceApplicationCreatedMessage;

@Component
@Slf4j
public class KitaPlaceReservationProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topicName;

    @Value("${topic.output.reservation-request}")
    public void setTopicName(String reservePlaceTopic) {
        this.topicName = reservePlaceTopic;
    }

    @Autowired
    public KitaPlaceReservationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(PlaceApplicationCreatedMessage message) {
        log.info("Publish -> {}", topicName);
        kafkaTemplate.send(topicName, message.toJson());
    }
}