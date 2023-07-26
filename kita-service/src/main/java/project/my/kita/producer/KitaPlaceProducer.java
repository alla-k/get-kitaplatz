package project.my.kita.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import project.my.kita.message.output.KitaPlaceChangedMessage;

@Component
@Slf4j
public class KitaPlaceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topicName;

    @Value("${topic.output.kita-place}")
    public void setTopicName(String kitaPlaceTopic) {
        this.topicName = kitaPlaceTopic;
    }

    @Autowired
    public KitaPlaceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(KitaPlaceChangedMessage message) {
        log.info("Publish -> {}", topicName);
        kafkaTemplate.send(topicName, message.toJson());
    }

}
