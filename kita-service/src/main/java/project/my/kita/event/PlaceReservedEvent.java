package project.my.kita.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import project.my.kita.message.input.PlaceReservedMessage;

import java.util.UUID;

@Getter
public class PlaceReservedEvent extends ApplicationEvent {
    private String id;
    private String kitaId;
    private String applicationId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endDate;
    private int placeId;

    public PlaceReservedEvent(PlaceReservedMessage placeReservedMessage){
        super(placeReservedMessage);
        this.id = UUID.randomUUID().toString();
        this.placeId = placeReservedMessage.getPlaceId();
        this.kitaId = placeReservedMessage.getKitaId();
        this.applicationId = placeReservedMessage.getApplicationId();
        this.startDate = placeReservedMessage.getStartDate();
        this.endDate = placeReservedMessage.getEndDate();
    }
}
