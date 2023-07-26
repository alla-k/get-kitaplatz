package project.my.kita.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import project.my.kita.message.input.PlaceClaimedMessage;

@Getter
public class KitaPlaceClaimedEvent extends ApplicationEvent {

    private String id;
    private String kitaId;
    private String applicationId;
    private String placeId;

    public KitaPlaceClaimedEvent(PlaceClaimedMessage placeClaimedMessage){
        super(placeClaimedMessage);
        this.id = placeClaimedMessage.getId();
        this.kitaId = placeClaimedMessage.getKitaId();
        this.applicationId = placeClaimedMessage.getApplicationId();
        this.placeId = placeClaimedMessage.getPlaceId();
    }
}
