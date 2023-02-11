package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository works with users
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Search user by username
     *
     * @param username unique name of user
     * @return Found user or null
     */
    Optional<UserEntity> findByUsername(String username);
}
