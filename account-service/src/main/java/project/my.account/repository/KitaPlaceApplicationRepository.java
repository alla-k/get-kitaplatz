package project.my.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.my.account.entity.KitaPlaceApplication;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitaPlaceApplicationRepository extends JpaRepository<KitaPlaceApplication, String> {
    public Optional<List<KitaPlaceApplication>> findByAccountId(String accountId);
}
