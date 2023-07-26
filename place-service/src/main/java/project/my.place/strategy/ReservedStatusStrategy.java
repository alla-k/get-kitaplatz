package project.my.place.strategy;

import project.my.place.event.KitaPlaceChangedEvent;
import project.my.place.message.output.ConfirmationDataMessage;
import project.my.place.producer.ConfirmationDataProducer;

import java.util.UUID;

public class ReservedStatusStrategy implements KitaPlaceStatusStrategy{

    private final ConfirmationDataProducer confirmationDataProducer;

    public ReservedStatusStrategy(ConfirmationDataProducer confirmationDataProducer) {
        this.confirmationDataProducer = confirmationDataProducer;
    }

    @Override
    public void processKitaPlaceStatus(KitaPlaceChangedEvent kitaPlaceChangedEvent) {
        String reservationId = kitaPlaceChangedEvent.getApplicationId() + UUID.randomUUID();
        ConfirmationDataMessage confirmationDataMessage = ConfirmationDataMessage.builder()
                .placeId(kitaPlaceChangedEvent.getPlaceId())
                .kitaId(kitaPlaceChangedEvent.getKitaId())
                .applicationId(kitaPlaceChangedEvent.getApplicationId())
                .confirmationToken(UUID.randomUUID().toString())
                .reservationId(reservationId)
                .build();

        confirmationDataProducer.sendMessage(confirmationDataMessage);
    }
}
