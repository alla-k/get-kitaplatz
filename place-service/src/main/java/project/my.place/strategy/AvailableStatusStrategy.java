package project.my.place.strategy;

import lombok.extern.slf4j.Slf4j;
import project.my.place.event.KitaPlaceChangedEvent;
import project.my.place.message.output.PlaceReservedMessage;
import project.my.place.producer.ReservePlaceProducer;
import project.my.place.service.QueueAssignmentService;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class AvailableStatusStrategy implements KitaPlaceStatusStrategy {

    private final QueueAssignmentService queueAssignmentService;
    private final ReservePlaceProducer reservePlaceProducer;

    public AvailableStatusStrategy(QueueAssignmentService queueAssignmentService,
                                   ReservePlaceProducer reservePlaceProducer) {
        this.queueAssignmentService = queueAssignmentService;
        this.reservePlaceProducer = reservePlaceProducer;
    }

    @Override
    public void processKitaPlaceStatus(KitaPlaceChangedEvent kitaPlaceChangedEvent) {

        log.info("Processing kita place created event with status AVAILABLE");

        LocalDate startDate = LocalDate.now().withMonth(LocalDate.now().getMonthValue()+1).withDayOfMonth(1);
        String applicationId = queueAssignmentService.checkQueueForMonthAndKita(kitaPlaceChangedEvent.getKitaId(),
                startDate);

        if(applicationId!=null) {
            //reserve place for the application

            PlaceReservedMessage placeReservedMessage = PlaceReservedMessage.builder()
                    .id(UUID.randomUUID().toString())
                    .placeId(kitaPlaceChangedEvent.getPlaceId())
                    .kitaId(kitaPlaceChangedEvent.getKitaId())
                    .applicationId(applicationId)
                    .startDate(startDate.toString())
                    .endDate(startDate.plusMonths(36).toString()) //TODO add business logic
                    .build();

            reservePlaceProducer.sendMessage(placeReservedMessage);

            queueAssignmentService.removeApplicationFromQueue(applicationId, kitaPlaceChangedEvent.getKitaId());

        }
                    log.info("Place would be assigned from {} to application {}",
                    startDate, applicationId);

    }
}
