package project.my.account.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import project.my.account.message.output.UserMessage;

@Component
@Slf4j
public class UserAccountProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topicName;

    @Value("${topic.output.onboard-user}")
    public void setTopicName(String reservePlaceTopic) {
        this.topicName = reservePlaceTopic;
    }

    @Autowired
    public UserAccountProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserMessage message) {
        log.info("Publish -> {}", topicName);
        kafkaTemplate.send(topicName, message.toJson());
    }
}
