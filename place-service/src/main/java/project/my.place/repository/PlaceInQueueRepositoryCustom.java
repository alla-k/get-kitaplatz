package project.my.place.repository;

import org.springframework.stereotype.Repository;
import project.my.place.entity.PlaceInQueue;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceInQueueRepositoryCustom  {
    int countByQueueId(String id);

    String findFirstInQueue(String id);

    Optional<PlaceInQueue> findByApplicationIdAndKitaId(String applicationId, String KitaId);
    List<PlaceInQueue> findByApplicationId(String applicationId);
}
