package project.my.kita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.my.kita.entity.KitaData;

@Repository
public interface KitaDataRepository extends JpaRepository<KitaData, String> {
}
