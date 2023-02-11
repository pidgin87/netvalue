package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.RfIdTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository works with RFID tags
 */
@Repository
public interface RfidTagRepository extends JpaRepository<RfIdTagEntity, Long> {

    /**
     * Search RFID tag by tag number
     *
     * @param tagNumber RFID tag number
     * @return Found RFID tag or null
     */
    Optional<RfIdTagEntity> findByTagNumber(String tagNumber);
}
