package nz.netvalue.domain.service.session;

import nz.netvalue.persistence.model.ChargingSessionEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service gets charging sessions
 */
public interface GetSessionService {

    /**
     * Get list of all charging session. Can be filtered by period
     *
     * @param dateFrom     start period
     * @param dateTo       end period
     * @param lastSeenDate
     * @return list of charging session
     */
    List<ChargingSessionEntity> getChargeSessions(LocalDate dateFrom, LocalDate dateTo, LocalDateTime lastSeenDate);
}
