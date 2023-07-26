package project.my.place.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import project.my.place.message.output.PlaceReservedMessage;

@Slf4j
@Component
public class ReservePlaceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topicName;

    @Value("${topic.output.reserve-place}")
    public void setTopicName(String reservePlaceTopic) {
        this.topicName = reservePlaceTopic;
    }

    @Autowired
    public ReservePlaceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(PlaceReservedMessage message) {
        log.info("Publish -> {}", topicName);
        kafkaTemplate.send(topicName, message.toJson());
    }
}
