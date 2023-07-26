package project.my.place.strategy;

import project.my.place.event.KitaPlaceChangedEvent;

public interface KitaPlaceStatusStrategy {
    void processKitaPlaceStatus(KitaPlaceChangedEvent kitaPlaceChangedEvent);
}
