package nz.netvalue.e2e;

import nz.netvalue.controller.model.VersionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.HttpHeaders.ETAG;

class GetVersionTest extends BaseE2ETestRunner {

    @Test
    void shouldReturnOK() {
        ResponseEntity<VersionResponse> response = testRestTemplate.getForEntity(
                getUri(),
                VersionResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().get(ETAG));
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getApplication());
        assertNotNull(response.getBody().getDatabase());
        assertNotNull(response.getHeaders().get(CACHE_CONTROL));
    }

    private URI getUri() {
        return getBasePath().resolve("/versions");
    }
}
