package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargingSessionEntity;
import nz.netvalue.persistence.model.RfIdTagEntity;
import nz.netvalue.persistence.model.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository works with charging sessions
 */
@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSessionEntity, Long> {

    /**
     * Return lis of session filtered by perdio and date of last seen
     *
     * @param startPeriod  date of start selection period
     * @param endPeriod    date of end selection period
     * @param lastSeenDate date of last seen sessions
     * @return session list
     */
    @Query("select c from ChargingSessionEntity c " +
            "where  " +
            "(:startPeriod is null or (" +
            "   c.startTime between :startPeriod and :endPeriod and " +
            "   (c.endTime is null or c.endTime between :startPeriod and :endPeriod))" +
            ") and (:lastSeenDate is null or c.lastModifiedDate > :lastSeenDate)" +
            " order by c.createdDate desc")
    List<ChargingSessionEntity> findByDatePeriod(@Param("startPeriod") LocalDateTime startPeriod,
                                                 @Param("endPeriod") LocalDateTime endPeriod,
                                                 @Param("lastSeenDate") LocalDateTime lastSeenDate);

    /**
     * Check if exist open sessions by RFID tag and vehicle
     *
     * @param rfIdTagEntity RFID tag
     * @param vehicleEntity vehicle
     * @return true if exists, otherwise false
     */
    boolean existsByRfIdTagAndVehicleAndEndTimeIsNull(RfIdTagEntity rfIdTagEntity, VehicleEntity vehicleEntity);

}
