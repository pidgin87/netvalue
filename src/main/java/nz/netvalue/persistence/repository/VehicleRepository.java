package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository works with vehicles
 */
@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    /**
     * Search vehicle by registration plate
     *
     * @param regPlate registration plate
     * @return Found vehicle or null
     */
    Optional<VehicleEntity> findByRegistrationPlate(String regPlate);
}
