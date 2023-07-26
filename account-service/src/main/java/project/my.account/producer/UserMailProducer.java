package project.my.account.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import project.my.account.message.output.UserMailMessage;

@Slf4j
@Component
public class UserMailProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topicName;

    @Value("${topic.output.user-mail}")
    public void setTopicName(String userMailTopic) {
        this.topicName = userMailTopic;
    }

    @Autowired
    public UserMailProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(UserMailMessage message) {
        log.info("Publish -> {}", topicName);
        kafkaTemplate.send(topicName, message.toJson());
    }
}
