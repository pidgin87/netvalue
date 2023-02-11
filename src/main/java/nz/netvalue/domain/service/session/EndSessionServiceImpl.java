package nz.netvalue.domain.service.session;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.domain.service.security.SecurityService;
import nz.netvalue.exception.ForbiddenException;
import nz.netvalue.exception.PreconditionFailedException;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargingSessionEntity;
import nz.netvalue.persistence.model.UserEntity;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class EndSessionServiceImpl implements EndSessionService {

    private final ChargingSessionRepository repository;
    private final SecurityService securityService;

    @Override
    public ChargingSessionEntity endSession(Long sessionId, EndSessionRequest request, Long version) {
        ChargingSessionEntity session = getOrThrow(sessionId, version);
        canAccessSession(session);

        if (hasText(request.getErrorMessage())) {
            session.setErrorMessage(request.getErrorMessage());
        }
        if (nonNull(request.getEndMeter())) {
            session.setEndTime(LocalDateTime.now());
            session.setEndMeter(request.getEndMeter());
        }

        return repository.save(session);
    }

    private void canAccessSession(ChargingSessionEntity session) {
        UserEntity customer = session.getRfIdTag().getCustomer();
        if (!securityService.getCurrentUser().equals(customer)) {
            throw new ForbiddenException("Session belongs to another client");
        }
    }

    private ChargingSessionEntity getOrThrow(Long sessionId, Long version) {
        Optional<ChargingSessionEntity> optional = repository.findById(sessionId);
        ChargingSessionEntity session = optional.orElseThrow(
                () -> new ResourceNotFoundException(format("Charging session with id [%s] not exists", sessionId))
        );
        if (!session.getVersion().equals(version)) {
            throw new PreconditionFailedException("resource is old");
        }
        return session;
    }
}
