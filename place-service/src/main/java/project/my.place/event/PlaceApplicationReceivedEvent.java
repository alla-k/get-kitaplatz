package project.my.place.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import project.my.place.message.input.ApplicationCreatedMessage;

import java.time.LocalDate;
import java.util.List;
@Getter
public class PlaceApplicationReceivedEvent extends ApplicationEvent {

    private final String id;
    private final List<String> kitaIds;
    private final String childName;
    private final LocalDate birthDate;
    private final LocalDate minEntranceDate;

    public PlaceApplicationReceivedEvent(ApplicationCreatedMessage applicationCreatedMessage){
        super(applicationCreatedMessage);
        this.id = applicationCreatedMessage.getId();
        this.kitaIds = applicationCreatedMessage.getKitaIds();
        this.childName = applicationCreatedMessage.getChildName();
        this.birthDate = applicationCreatedMessage.getBirthDate();
        this.minEntranceDate = applicationCreatedMessage.getMinEntranceDate();
    }
}
