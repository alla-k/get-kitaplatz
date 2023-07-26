package project.my.place.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import project.my.place.message.input.KitaPlaceCreatedMessage;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class KitaPlaceChangedEvent extends ApplicationEvent {
    private String id;
    private String kitaId;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private String applicationId;
    private String status;
    private String placeId;
   // private String timestamp;

    public KitaPlaceChangedEvent(KitaPlaceCreatedMessage kitaPlaceCreatedMessage){
        super(kitaPlaceCreatedMessage);
        this.id = UUID.randomUUID().toString();
        this.placeId = kitaPlaceCreatedMessage.getId();
        this.kitaId = kitaPlaceCreatedMessage.getKitaId();
        this.contractStartDate = kitaPlaceCreatedMessage.getContractStartDate();
        this.contractEndDate = kitaPlaceCreatedMessage.getContractEndDate();
        this.applicationId = kitaPlaceCreatedMessage.getApplicationId();
        this.status = kitaPlaceCreatedMessage.getStatus();
       // this.timestamp = kitaPlaceCreatedMessage.getTimestamp();
    }
}
