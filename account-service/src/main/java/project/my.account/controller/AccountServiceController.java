package project.my.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.my.account.entity.KitaPlaceApplication;
import project.my.account.service.ReservationService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class AccountServiceController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/sendReservation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> sendKitaPlaceApplication(@RequestBody KitaPlaceApplication kitaPlaceApplication) {
        try {
            kitaPlaceApplication.setId(UUID.randomUUID().toString());
            String applicationId = reservationService.createReservation(kitaPlaceApplication);
            return ResponseEntity.ok("Application with id " + applicationId + " was created");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


    @GetMapping("/confirmReservation?reservationId={reservationId}&confirmationId={confirmationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> confirmReservation(@PathVariable String reservationId,
                                                     @PathVariable String confirmationId) {
        try {
            String applicationId = reservationService.claimPlace(reservationId, confirmationId);
            return ResponseEntity.ok("Place assignment for application " + applicationId + " is confirmed");
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}

