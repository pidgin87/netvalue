package nz.netvalue.e2e;

import nz.netvalue.controller.model.ConnectorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.ETAG;
import static org.springframework.http.HttpHeaders.LAST_MODIFIED;
import static org.springframework.http.HttpHeaders.LOCATION;

class CreateConnectorTest extends BaseE2ETestRunner {

    @Test
    void shouldReturnUnauthorized() {
        ConnectorRequest connectorRequest = createRequest(3L);

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(connectorRequest),
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldReturnForbidden() {
        ConnectorRequest connectorRequest = createRequest(3L);

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(connectorRequest, createAuthHeader("customer")),
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequest() {
        ConnectorRequest connectorRequest = createRequest(null);

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(connectorRequest, createAuthHeader("admin")),
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnConflictIfAlreadyCreated() {
        ConnectorRequest connectorRequest = createRequest(1L);

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(connectorRequest, createAuthHeader("admin")),
                Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void shouldReturnCreated() {
        ConnectorRequest connectorRequest = createRequest(3L);

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(connectorRequest, createAuthHeader("admin")),
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get(LOCATION));
        assertNotNull(response.getHeaders().get(ETAG));
        assertNotNull(response.getHeaders().get(LAST_MODIFIED));
    }

    private URI getUri() {
        return getBasePath().resolve("/charge-points/number1/connectors");
    }

    private static ConnectorRequest createRequest(Long connectorNumber) {
        ConnectorRequest connectorRequest = new ConnectorRequest();
        connectorRequest.setConnectorNumber(connectorNumber);
        return connectorRequest;
    }
}
