package project.my.kita.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.my.kita.entity.KitaPlace;
import project.my.kita.entity.Status;
import project.my.kita.message.output.KitaPlaceChangedMessage;
import project.my.kita.producer.KitaPlaceProducer;
import project.my.kita.repository.KitaPlaceRepository;

import java.time.LocalDate;
import java.time.Period;

@Service
public class KitaPlaceService {

    @Autowired
    private KitaPlaceRepository kitaPlaceRepository;
    @Autowired
    private KitaPlaceProducer kitaPlaceProducer;

    public int createPlace(KitaPlace kitaPlace) {
        LocalDate startDate = kitaPlace.getContractStartDate();
        LocalDate endDate = kitaPlace.getContractEndDate();
        checkDates(startDate, endDate);
        checkStatus(kitaPlace.getStatus(), startDate, endDate);

        KitaPlace saved = kitaPlaceRepository.save(kitaPlace);
        KitaPlaceChangedMessage message = KitaPlaceChangedMessage.builder()
                //.id(saved.getId())
                .kitaId(saved.getKitaId())
                .applicationId(saved.getApplicationId())
                .contractStartDate(saved.getContractStartDate())
                .contractEndDate(saved.getContractEndDate())
                .status(saved.getStatus())
                .build();
        kitaPlaceProducer.sendMessage(message);

        return saved.getId();
    }

    public KitaPlace updatePlace(String id, KitaPlace kitaPlaceUpdate) {
        KitaPlace kitaPlace = kitaPlaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kita place with id " + id + " not found"));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            objectMapper.updateValue(kitaPlace, kitaPlaceUpdate);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }

        LocalDate startDate = kitaPlace.getContractStartDate();
        LocalDate endDate = kitaPlace.getContractEndDate();
        checkDates(startDate, endDate);
        checkStatus(kitaPlace.getStatus(), startDate, endDate);

        return kitaPlaceRepository.save(kitaPlace);
    }


    private void checkDates(LocalDate startDate, LocalDate endDate){
        if(startDate == null && endDate != null){
            throw new RuntimeException("End date can't be set without start date");
        } else if (startDate != null && endDate == null){
            throw new RuntimeException("Start date can't be set without end date");
        }

        if(startDate != null){
            if (startDate.isAfter(endDate)) {
                throw new RuntimeException("Start date can't be after end date");
            }

            if (startDate.isBefore(LocalDate.now())) {
                throw new RuntimeException("Start date can't be in the past");
            }

            if(endDate.isBefore(LocalDate.now())){
                throw new RuntimeException("End date can't be in the past");
            }

            if(Period.between(startDate, endDate).getMonths()>48){
                throw new RuntimeException("The duration of the contrat can't be more than 48 months");
            }
        }
    }

    private void checkStatus(Status status, LocalDate startDate, LocalDate endDate){

        if(startDate == null && endDate == null && status == Status.RESERVED || status == Status.ASSIGNED){
                throw new RuntimeException("Status can't be set without dates in case of RESERVED or ASSIGNED");
        }

        if(startDate != null && endDate != null && status == Status.AVAILABLE){
                throw new RuntimeException("Status can't be AVAILABLE with dates set");
        }

    }
}
