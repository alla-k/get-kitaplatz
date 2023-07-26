package project.my.account.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import project.my.account.message.input.ConfirmationDataMessage;

import java.util.UUID;

@Getter
public class ConfirmationDataReceivedEvent extends ApplicationEvent {
    private String id;
    private String kitaId;
    private String applicationId;
    private String reservationId;
    private String confirmationToken;
    private String placeId;
    public ConfirmationDataReceivedEvent(ConfirmationDataMessage confirmationDataMessage){
        super(confirmationDataMessage);
        this.id = UUID.randomUUID().toString();
        this.kitaId = confirmationDataMessage.getKitaId();
        this.applicationId = confirmationDataMessage.getApplicationId();
        this.reservationId = confirmationDataMessage.getReservationId();
        this.confirmationToken = confirmationDataMessage.getConfirmationToken();
        this.placeId = confirmationDataMessage.getPlaceId();
    }
}
