package project.my.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.my.place.entity.ApplicationsQueue;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ApplicationsQueueRepository extends JpaRepository<ApplicationsQueue, String> {
    Optional<ApplicationsQueue> findByKitaIdAndStartDate(String kitaId, LocalDate startDate);
}
