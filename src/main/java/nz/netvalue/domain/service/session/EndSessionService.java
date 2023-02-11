package nz.netvalue.domain.service.session;

import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.persistence.model.ChargingSessionEntity;

/**
 * Service ends charging session
 */
public interface EndSessionService {

    /**
     * End charging session
     *
     * @param sessionId    charging session ID
     * @param request      end session request
     * @param ifMatchValue
     */
    ChargingSessionEntity endSession(Long sessionId, EndSessionRequest request, Long ifMatchValue);
}
