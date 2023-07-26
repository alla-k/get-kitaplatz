package project.my.kita.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.my.kita.entity.KitaPlace;
import project.my.kita.service.KitaPlaceService;

@Slf4j
@RestController
public class KitaPlacesController {

    @Autowired
    private KitaPlaceService kitaPlaceService;

    @PostMapping("/createPlace")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createAccount(@RequestBody KitaPlace kitaPlace) {
        try {
            String kitaPlaceId = kitaPlaceService.createPlace(kitaPlace);
            return ResponseEntity.ok("Kita place with id " + kitaPlaceId + " was created");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping("/updatePlace/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateAccount(@PathVariable String id, @RequestBody KitaPlace kitaPlace) {
        try {
            KitaPlace updatedPlace = kitaPlaceService.updatePlace(id, kitaPlace);
            return ResponseEntity.ok(updatedPlace.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
