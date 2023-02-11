package nz.netvalue.domain.service.session;

import lombok.RequiredArgsConstructor;
import nz.netvalue.persistence.model.ChargingSessionEntity;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class GetSessionServiceImpl implements GetSessionService {

    private final ChargingSessionRepository repository;

    @Override
    public List<ChargingSessionEntity> getChargeSessions(LocalDate dateFrom, LocalDate dateTo, LocalDateTime lastSeenDate) {
        return repository.findByDatePeriod(
                prepareFrom(dateFrom),
                prepareTo(dateTo),
                lastSeenDate
        );
    }

    private LocalDateTime prepareFrom(LocalDate dateFrom) {
        if (isNull(dateFrom)) {
            return null;
        }
        return dateFrom.atStartOfDay();
    }

    private LocalDateTime prepareTo(LocalDate dateTo) {
        if (isNull(dateTo)) {
            return null;
        }
        return dateTo.atTime(LocalTime.MAX);
    }
}
