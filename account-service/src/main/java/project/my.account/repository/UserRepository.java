package project.my.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.my.account.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
