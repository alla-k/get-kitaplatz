package project.my.place.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import project.my.place.message.output.ConfirmationDataMessage;
import project.my.place.message.output.PlaceReservedMessage;

@Slf4j
@Component
public class ConfirmationDataProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topicName;

    @Value("${topic.output.confirmation-data}")
    public void setTopicName(String confirmationDataTopic) {
        this.topicName = confirmationDataTopic;
    }

    @Autowired
    public ConfirmationDataProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ConfirmationDataMessage message) {
        log.info("Publish -> {}", topicName);
        kafkaTemplate.send(topicName, message.toJson());
    }
}
