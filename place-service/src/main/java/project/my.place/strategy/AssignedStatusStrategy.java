package project.my.place.strategy;

import lombok.extern.slf4j.Slf4j;
import project.my.place.event.KitaPlaceChangedEvent;
import project.my.place.producer.ReservePlaceProducer;
import project.my.place.service.QueueAssignmentService;

@Slf4j
public class AssignedStatusStrategy implements KitaPlaceStatusStrategy{

    private final QueueAssignmentService queueAssignmentService;
    private final ReservePlaceProducer reservePlaceProducer;

    public AssignedStatusStrategy(QueueAssignmentService queueAssignmentService,
                                  ReservePlaceProducer reservePlaceProducer) {
        this.queueAssignmentService = queueAssignmentService;
        this.reservePlaceProducer = reservePlaceProducer;
    }

    @Override
    public void processKitaPlaceStatus(KitaPlaceChangedEvent kitaPlaceChangedEvent) {
        log.info("Processing kita place created event with status ASSIGNED");

        queueAssignmentService.removeApplicationFromAllQueues(kitaPlaceChangedEvent.getApplicationId());
    }
}
