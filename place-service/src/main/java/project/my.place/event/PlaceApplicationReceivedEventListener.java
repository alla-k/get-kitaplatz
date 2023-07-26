package project.my.place.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import project.my.place.service.QueueAssignmentService;

@Slf4j
@Component
public class PlaceApplicationReceivedEventListener implements ApplicationListener<PlaceApplicationReceivedEvent> {

    private final QueueAssignmentService queueAssignmentService;

    @Autowired
    public PlaceApplicationReceivedEventListener(QueueAssignmentService queueAssignmentService) {
        this.queueAssignmentService = queueAssignmentService;
    }

    @Override
        public void onApplicationEvent(PlaceApplicationReceivedEvent placeApplicationReceivedEvent) {
            log.info("Received new place application");

            //check for immediate availability of the place TODO
//        for (String kitaId : placeApplicationReceivedEvent.getKitaIds()) {
//            log.info("Checking availability for kita {}", kitaId);
//
//        }

            //next put to the queue

            for (String kitaId : placeApplicationReceivedEvent.getKitaIds()) {
                log.info("Putting application {} in queue for kita {}",
                        placeApplicationReceivedEvent.getId(), kitaId);
                queueAssignmentService.addToQueue(kitaId,
                       placeApplicationReceivedEvent.getMinEntranceDate(),
                        placeApplicationReceivedEvent.getId(),
                        placeApplicationReceivedEvent.getBirthDate());
            }
        }
}
