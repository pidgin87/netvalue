package nz.netvalue.domain.service.session;

import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.persistence.model.ChargingSessionEntity;

/**
 * Service starts charging session
 */
public interface StartSessionService {

    /**
     * Create charging session
     *
     * @param request start session request
     * @return created session object
     */
    ChargingSessionEntity startSession(StartSessionRequest request);
}
