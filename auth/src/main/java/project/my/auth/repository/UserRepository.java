package project.my.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import project.my.auth.entity.UserAccount;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByEmail(String email);

}
