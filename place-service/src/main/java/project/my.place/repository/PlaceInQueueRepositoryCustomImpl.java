package project.my.place.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.my.place.entity.PlaceInQueue;

import java.util.List;
import java.util.Optional;

@Component
public class PlaceInQueueRepositoryCustomImpl implements PlaceInQueueRepositoryCustom{

    private final EntityManager entityManager;

    @Autowired
    public PlaceInQueueRepository placeInQueueRepository;


    @Autowired
    public PlaceInQueueRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public int countByQueueId(String id) {
        Query query = entityManager.createQuery("SELECT COUNT(q) FROM PlaceInQueue q WHERE q.queueId = :id");
        query.setParameter("id", id);
        Long result = (Long) query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public String findFirstInQueue(String id) {
        Query query = entityManager.createQuery("SELECT q FROM PlaceInQueue q WHERE q.queueId = :id " +
                "ORDER BY q.position ASC");
        query.setParameter("id", id);
        PlaceInQueue  place = (PlaceInQueue) query.getResultList().get(0);
        return place.getApplicationId();
    }

    @Override
    public Optional<PlaceInQueue> findByApplicationIdAndKitaId(String applicationId, String kitaId) {
        Query query = entityManager.createQuery("SELECT q FROM PlaceInQueue q " +
                "WHERE q.applicationId = :applicationId AND q.kitaId = :kitaId" +
                "ORDER BY q.position ASC");
        query.setParameter("applicationId", applicationId);
        query.setParameter("kitaId", kitaId);
        PlaceInQueue place = (PlaceInQueue) query.getResultList().get(0);
        return Optional.ofNullable(place);
    }


        @Override
        public List<PlaceInQueue> findByApplicationId(String applicationId) {
            Query query = entityManager.createQuery("SELECT q FROM PlaceInQueue q " +
                    "WHERE q.applicationId = :applicationId" +
                    "ORDER BY q.position ASC");
            query.setParameter("applicationId", applicationId);
            return (List<PlaceInQueue>) query.getResultList();
        }
}
