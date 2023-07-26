package project.my.place.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.my.place.entity.ApplicationsQueue;
import project.my.place.entity.PlaceInQueue;
import project.my.place.repository.ApplicationsQueueRepository;
import project.my.place.repository.PlaceInQueueRepository;
import project.my.place.repository.PlaceInQueueRepositoryCustom;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QueueAssignmentService {

    private ApplicationsQueueRepository applicationsQueueRepository;
    private PlaceInQueueRepository placeInQueueRepository;
    private PlaceInQueueRepositoryCustom placeInQueueRepositoryCustom;
@Autowired
public QueueAssignmentService(ApplicationsQueueRepository applicationsQueueRepository,
                              PlaceInQueueRepository placeInQueueRepository, PlaceInQueueRepositoryCustom placeInQueueRepositoryCustom) {
        this.applicationsQueueRepository = applicationsQueueRepository;
        this.placeInQueueRepository = placeInQueueRepository;
    this.placeInQueueRepositoryCustom = placeInQueueRepositoryCustom;
}

    public void createQueueAssignment(String kitaId, LocalDate startDate, String applicationId) {
        // if there is queue for this month/kita, get id and add application to the end of queue
        //if not, create new queue and add application to the end of queue

        Optional<ApplicationsQueue> applicationsQueueOptional = applicationsQueueRepository
                .findByKitaIdAndStartDate(kitaId, startDate);
        ApplicationsQueue applicationsQueue;
        if(applicationsQueueOptional.isPresent()) {
            applicationsQueue = applicationsQueueOptional.get();

        } else {
            applicationsQueue = ApplicationsQueue.builder()
                    .id(UUID.randomUUID().toString())
                    .kitaId(kitaId)
                    .startDate(startDate)
                    .build();
            applicationsQueueRepository.save(applicationsQueue);
        }

        placeInQueueRepository.save(PlaceInQueue.builder()
                .queueId(applicationsQueue.getId())
                .id(UUID.randomUUID().toString())
                .position(placeInQueueRepositoryCustom.countByQueueId(applicationsQueue.getId()) + 1)
                .applicationId(applicationId)
                .build());
    }


    public void addToQueue(String kitaId, LocalDate startDate, String applicationId, LocalDate birthDate){

        //get birthdate and calculate max start time: 6years - 6 months
        LocalDate maxStartDate= birthDate.plusYears(6).minusMonths(6);

        //add to queue for each months until max start time
        while (startDate.isBefore(maxStartDate)) {
            createQueueAssignment(kitaId, startDate, applicationId);
            startDate = startDate.plusMonths(1);
        }
    }

    public String checkQueueForMonthAndKita(String kitaId, LocalDate startDate) {
        //check if there is queue for this month/kita
        Optional<ApplicationsQueue> queue = applicationsQueueRepository.findByKitaIdAndStartDate(kitaId, startDate);
        String applicationId = null;

        if (queue.isPresent()) {
            applicationId = placeInQueueRepositoryCustom
                    .findFirstInQueue(queue.get().getId());
        }
        //if yes, take the first place in queue and return application id

        return applicationId;

    }

    public void removeApplicationFromQueue(String applicationId, String kitaId) {
        //remove application from queue for particular kita
        Optional<PlaceInQueue> placeInQueueOptional = placeInQueueRepositoryCustom
                .findByApplicationIdAndKitaId(applicationId, kitaId);
        placeInQueueOptional.ifPresent(placeInQueue -> placeInQueueRepository.delete(placeInQueue));
    }

    public void removeApplicationFromAllQueues(String applicationId) {
        //remove application from queue for particular kita
       List<PlaceInQueue> placeInQueueList = placeInQueueRepositoryCustom
                .findByApplicationId(applicationId);
       if(placeInQueueList != null && !placeInQueueList.isEmpty()) {
           placeInQueueList.forEach(placeInQueue -> placeInQueueRepository.delete(placeInQueue));
      }
    }

    public int getPredictedPlace(String kitaId, LocalDate date) {
        int projectedPlace = 1;
        Optional<ApplicationsQueue> applicationsQueueOptional = applicationsQueueRepository
                .findByKitaIdAndStartDate(kitaId, date);
        ApplicationsQueue applicationsQueue;
        if(applicationsQueueOptional.isPresent()) {
            applicationsQueue = applicationsQueueOptional.get();
            projectedPlace = placeInQueueRepositoryCustom.countByQueueId(applicationsQueue.getId()) + 1;
        }
        return projectedPlace;
    }
}
