package project.my.place.strategy;

import org.springframework.stereotype.Component;
import project.my.place.event.KitaPlaceChangedEvent;

@Component
public class KitaPlaceStatusProcessor {

    private KitaPlaceStatusStrategy kitaPlaceStatusStrategy;

    public void setKitaPlaceStatusStrategy(KitaPlaceStatusStrategy kitaPlaceStatusStrategy) {
        this.kitaPlaceStatusStrategy = kitaPlaceStatusStrategy;
    }

    public void processKitaPlaceStatus(KitaPlaceChangedEvent kitaPlaceChangedEvent) {
        kitaPlaceStatusStrategy.processKitaPlaceStatus(kitaPlaceChangedEvent);
    }
}
