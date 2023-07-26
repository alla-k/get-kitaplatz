package project.my.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.my.account.entity.ConfirmationData;

@Repository
public interface ReservationRepository extends JpaRepository<ConfirmationData, String> {
}
