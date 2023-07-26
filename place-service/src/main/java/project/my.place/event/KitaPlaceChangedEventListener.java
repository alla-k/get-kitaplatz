package project.my.place.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import project.my.place.producer.ConfirmationDataProducer;
import project.my.place.producer.ReservePlaceProducer;
import project.my.place.service.QueueAssignmentService;
import project.my.place.strategy.*;

@Slf4j
@Component
public class KitaPlaceChangedEventListener implements ApplicationListener<KitaPlaceChangedEvent> {

    private final KitaPlaceStatusProcessor kitaPlaceStatusProcessor;

    private final QueueAssignmentService queueAssignmentService;
    private final ReservePlaceProducer reservePlaceProducer;
    private final ConfirmationDataProducer confirmationDataProducer;


    @Autowired
    public KitaPlaceChangedEventListener(KitaPlaceStatusProcessor kitaPlaceStatusProcessor,
                                         QueueAssignmentService queueAssignmentService,
                                         ReservePlaceProducer reservePlaceProducer,
                                         ConfirmationDataProducer confirmationDataProducer) {
        this.kitaPlaceStatusProcessor = kitaPlaceStatusProcessor;
        this.queueAssignmentService = queueAssignmentService;
        this.reservePlaceProducer = reservePlaceProducer;
        this.confirmationDataProducer = confirmationDataProducer;
    }

    @Override
    public void onApplicationEvent(KitaPlaceChangedEvent kitaPlaceChangedEvent) {
        log.info("Received KitaPlaceChangedEvent: {}", kitaPlaceChangedEvent);

        //check for place status
        String placeStatus = kitaPlaceChangedEvent.getStatus();

        if(placeStatus.equals("AVAILABLE")) {
            kitaPlaceStatusProcessor.setKitaPlaceStatusStrategy(new AvailableStatusStrategy(
                    queueAssignmentService, reservePlaceProducer));
        } else if (placeStatus.equals("RESERVED")) {
            kitaPlaceStatusProcessor.setKitaPlaceStatusStrategy(new ReservedStatusStrategy(
                    confirmationDataProducer));
        } else if (placeStatus.equals("ASSIGNED")) {
            kitaPlaceStatusProcessor.setKitaPlaceStatusStrategy(new AssignedStatusStrategy(
                    queueAssignmentService, reservePlaceProducer
            ));
        } else if (placeStatus.equals("DELETED")) {
            kitaPlaceStatusProcessor.setKitaPlaceStatusStrategy(new DeletedStatusStrategy());
        } else {
            log.info("Unknown status");
        }

        kitaPlaceStatusProcessor.processKitaPlaceStatus(kitaPlaceChangedEvent);

        }
}
