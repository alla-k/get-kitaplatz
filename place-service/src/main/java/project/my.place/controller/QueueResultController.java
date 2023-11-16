package project.my.place.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.my.place.service.QueueAssignmentService;
import project.my.place.entity.QueuePlaceRequest;
@Slf4j
@RestController
@RequestMapping("/api/v1/place")
public class QueueResultController {

    @Autowired
    QueueAssignmentService queueAssignmentService;

    @PostMapping("/getPlaceQueue")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> getPredictedPlace(@RequestBody QueuePlaceRequest queuePlaceRequest) {
        try {
            int placeNumber = queueAssignmentService.getPredictedPlace(queuePlaceRequest.getKitaId(),
                    queuePlaceRequest.getStartDate());
            String responseStr = "Predicted place number for kita " + queuePlaceRequest.getKitaId() +
                    " on date " + queuePlaceRequest.getStartDate().toString() + " is " +
                    placeNumber;
            return ResponseEntity.ok(responseStr);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
