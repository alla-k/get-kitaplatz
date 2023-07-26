package project.my.kita.event;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.my.kita.entity.KitaPlace;
import project.my.kita.entity.Status;
import project.my.kita.service.KitaPlaceService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class PlaceReservedEventListener {
    private KitaPlaceService kitaPlaceService;

    @Autowired
    public PlaceReservedEventListener(KitaPlaceService kitaPlaceService) {
        this.kitaPlaceService = kitaPlaceService;
    }

    public void onApplicationEvent(PlaceReservedEvent placeReservedEvent) {
        log.info("Received new place reservation");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        KitaPlace kitaPlaceUpdate = KitaPlace.builder()
                .applicationId(placeReservedEvent.getApplicationId())
                .kitaId(placeReservedEvent.getKitaId())
                .status(Status.RESERVED)
                .contractStartDate(LocalDate.parse(placeReservedEvent.getStartDate(), formatter))
                .contractEndDate(LocalDate.parse(placeReservedEvent.getEndDate(), formatter))
                .id(placeReservedEvent.getPlaceId())
                .build();

        kitaPlaceService.updatePlace(placeReservedEvent.getId(),
        kitaPlaceUpdate);
        log.info("Place reservation processed");
    }
}
