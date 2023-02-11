package nz.netvalue.e2e;

import nz.netvalue.controller.model.ChargingSessionResponse;
import nz.netvalue.controller.model.FilterChargingSessionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterSessionTest extends BaseE2ETestRunner {

    @Test
    void shouldReturnUnauthorized() {
        FilterChargingSessionRequest request = createRequestBeforeToday();

        ResponseEntity<List<ChargingSessionResponse>> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldReturnForbidden() {
        FilterChargingSessionRequest request = createRequestBeforeToday();
        HttpHeaders headers = createAuthHeader("customer");

        ResponseEntity<List<ChargingSessionResponse>> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldReturnNotModified() {
        FilterChargingSessionRequest request = new FilterChargingSessionRequest();
        HttpHeaders headers = createAuthHeader("admin");
        headers.setIfModifiedSince(Instant.now().plus(1, ChronoUnit.HOURS));

        ResponseEntity<List<ChargingSessionResponse>> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.NOT_MODIFIED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldReturnOkAndLastModifiedDateIfSessionsExists() {
        FilterChargingSessionRequest request = new FilterChargingSessionRequest();
        HttpHeaders headers = createAuthHeader("admin");

        ResponseEntity<List<ChargingSessionResponse>> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().getLastModified() >= 0);

        List<ChargingSessionResponse> body = response.getBody();
        assertNotNull(body);
        assertFalse(body.isEmpty());
        assertEquals(3, body.size());
    }

    @Test
    void shouldReturnOkIfSessionsEmpty() {
        FilterChargingSessionRequest request = createRequestBeforeToday();
        HttpHeaders headers = createAuthHeader("admin");

        ResponseEntity<List<ChargingSessionResponse>> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        assertEquals(-1, response.getHeaders().getLastModified());
    }


    private static FilterChargingSessionRequest createRequestBeforeToday() {
        FilterChargingSessionRequest request = new FilterChargingSessionRequest();
        request.setDateFrom(LocalDate.now().minusDays(2));
        request.setDateTo(LocalDate.now().minusDays(1));
        return request;
    }

    private URI getUri() {
        return getBasePath().resolve("/charging-sessions/filters");
    }

}
