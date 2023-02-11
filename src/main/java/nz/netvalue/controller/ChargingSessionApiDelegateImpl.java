package nz.netvalue.controller;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.auth.HasAdminRole;
import nz.netvalue.controller.auth.HasCustomerRole;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.controller.model.ChargingSessionResponse;
import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.controller.model.FilterChargingSessionRequest;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.controller.utils.IfMatchHeaderService;
import nz.netvalue.controller.utils.LastModifiedHeaderBuilder;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.controller.utils.MdcService;
import nz.netvalue.domain.service.session.EndSessionService;
import nz.netvalue.domain.service.session.GetSessionService;
import nz.netvalue.domain.service.session.StartSessionService;
import nz.netvalue.exception.BadRequestException;
import nz.netvalue.exception.NotModifiedException;
import nz.netvalue.persistence.model.BaseEntity;
import nz.netvalue.persistence.model.ChargingSessionEntity;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class ChargingSessionApiDelegateImpl implements ChargingSessionsApiDelegate {

    private static final FastDateFormat IF_MODIFIED_SINCE_FORMAT = FastDateFormat.getInstance("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

    private final GetSessionService getChargeSessionService;
    private final StartSessionService startSessionService;
    private final EndSessionService endSessionService;
    private final ChargingSessionMapper sessionMapper;
    private final LocationHeaderBuilder locationHeaderBuilder;
    private final LastModifiedHeaderBuilder lastModifiedHeaderBuilder;
    private final IfMatchHeaderService ifMatchHeaderService;
    private final MdcService mdcService;

    @Override
    @HasCustomerRole
    public ResponseEntity<Void> startSession(StartSessionRequest request) {
        mdcService.create(singletonMap("rfid.tag.number", request.getRfIdTagNumber()));

        ChargingSessionEntity createdSession = startSessionService.startSession(request);
        URI location = locationHeaderBuilder.build(createdSession.getId());

        return ResponseEntity
                .created(location)
                .eTag(Long.toString(createdSession.getVersion()))
                .lastModified(lastModifiedHeaderBuilder.build(createdSession.getLastModifiedDate()))
                .build();
    }

    @Override
    @HasCustomerRole
    public ResponseEntity<Void> endSession(Long sessionId, String ifMatchHeader, EndSessionRequest endSessionRequest) {
        mdcService.create(singletonMap("charge.session", sessionId));

        Long version = ifMatchHeaderService.getTrimmedLongValue(ifMatchHeader);

        ChargingSessionEntity endedSession = endSessionService.endSession(
                sessionId, endSessionRequest, version
        );

        return ResponseEntity
                .noContent()
                .eTag(Long.toString(endedSession.getVersion()))
                .lastModified(lastModifiedHeaderBuilder.build(endedSession.getLastModifiedDate()))
                .build();
    }

    @Override
    @HasAdminRole
    public ResponseEntity<List<ChargingSessionResponse>> filterChargeSessions(String ifModifiedSince, FilterChargingSessionRequest request) {
        mdcService.create();

        LocalDateTime lastSeenDate = getIfModifiedSince(ifModifiedSince);
        List<ChargingSessionEntity> sessions = filterSessions(request, lastSeenDate);
        if (sessions.isEmpty()) {
            if (nonNull(lastSeenDate)) {
                throw new NotModifiedException();
            }
            return ResponseEntity.ok(emptyList());
        }

        List<ChargingSessionResponse> responses = sessionMapper.toResponseList(sessions);

        ChargingSessionEntity lastModifiedEntity = sessions.stream()
                .max(Comparator.comparing(BaseEntity::getLastModifiedDate)).orElseThrow();

        return ResponseEntity
                .ok()
                .lastModified(lastModifiedHeaderBuilder.build(lastModifiedEntity.getLastModifiedDate()))
                .body(responses);
    }

    private List<ChargingSessionEntity> filterSessions(FilterChargingSessionRequest request, LocalDateTime lastSeenDate) {
        Optional<FilterChargingSessionRequest> optional = Optional.ofNullable(request);

        return getChargeSessionService.getChargeSessions(
                optional.map(FilterChargingSessionRequest::getDateFrom).orElse(null),
                optional.map(FilterChargingSessionRequest::getDateTo).orElse(null),
                lastSeenDate
        );
    }

    private static LocalDateTime getIfModifiedSince(String ifModifiedSince) {
        if (isNull(ifModifiedSince)) {
            return null;
        }
        try {
            return IF_MODIFIED_SINCE_FORMAT.parse(ifModifiedSince).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (ParseException e) {
            throw new BadRequestException(e);
        }
    }
}
