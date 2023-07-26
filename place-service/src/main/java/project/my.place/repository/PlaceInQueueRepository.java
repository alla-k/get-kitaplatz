package project.my.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.my.place.entity.PlaceInQueue;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Optional;

@Repository
public interface PlaceInQueueRepository extends JpaRepository<PlaceInQueue, String> {

}
