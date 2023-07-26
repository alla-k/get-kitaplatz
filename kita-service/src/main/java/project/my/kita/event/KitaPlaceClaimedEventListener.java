package project.my.kita.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import project.my.kita.entity.KitaPlace;
import project.my.kita.entity.Status;
import project.my.kita.service.KitaPlaceService;

@Slf4j
@Component
public class KitaPlaceClaimedEventListener implements ApplicationListener<KitaPlaceClaimedEvent> {

    private final KitaPlaceService kitaPlaceService;
    @Autowired
    public KitaPlaceClaimedEventListener(KitaPlaceService kitaPlaceService) {
        this.kitaPlaceService = kitaPlaceService;
    }


    @Override
    public void onApplicationEvent(KitaPlaceClaimedEvent kitaPlaceClaimedEvent) {
        log.info("Place was claimed by the user");
        KitaPlace kitaPlaceStatusUpdate = KitaPlace.builder()
                .status(Status.ASSIGNED)
                .build();
        kitaPlaceService.updatePlace(kitaPlaceClaimedEvent.getPlaceId(), kitaPlaceStatusUpdate);
    }
}
