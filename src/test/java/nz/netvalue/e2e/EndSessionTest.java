package nz.netvalue.e2e;

import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.persistence.model.RfIdTagEntity;
import nz.netvalue.persistence.model.VehicleEntity;
import nz.netvalue.persistence.repository.RfidTagRepository;
import nz.netvalue.persistence.repository.UserRepository;
import nz.netvalue.persistence.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Objects;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.ETAG;
import static org.springframework.http.HttpHeaders.LAST_MODIFIED;

class EndSessionTest extends BaseE2ETestRunner {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private RfidTagRepository rfidTagRepository;

    @Autowired
    private UserRepository userRepository;

    private VehicleEntity vehicle;
    private RfIdTagEntity rfIdTag;

    @BeforeEach
    void beforeEach() {
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleName(random(15));
        vehicle.setRegistrationPlate(random(15));

        this.vehicle = vehicleRepository.save(vehicle);

        RfIdTagEntity rfIdTag = new RfIdTagEntity();
        rfIdTag.setTagName(random(15));
        rfIdTag.setTagNumber(random(15));
        rfIdTag.setCustomer(userRepository.findByUsername("customer").orElseThrow());

        this.rfIdTag = rfidTagRepository.save(rfIdTag);
    }

    @Test
    void shouldReturnUnauthorized() {
        EndSessionRequest request = createRequest();

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.PATCH,
                new HttpEntity<>(request),
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldReturnForbidden() {
        ResponseEntity<Void> createSessionResponse = createSession();
        HttpHeaders headersResponse = createSessionResponse.getHeaders();
        Assertions.assertNotNull(headersResponse);

        EndSessionRequest request = createRequest();
        HttpHeaders headers = createAuthHeader("admin");
        headers.setIfMatch(Objects.requireNonNull(headersResponse.getETag()));

        ResponseEntity<Void> response = testRestTemplate.exchange(headersResponse.getLocation(),
                HttpMethod.PATCH,
                new HttpEntity<>(request, headers),
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldReturnResourceNotFoundWhenSessionNotExist() {
        EndSessionRequest request = createRequest();
        URI uri = getUri().resolve("/charging-sessions/9999999");
        HttpHeaders headers = createAuthHeader("customer");
        headers.setIfMatch("1");

        ResponseEntity<Void> response = testRestTemplate.exchange(uri,
                HttpMethod.PATCH,
                new HttpEntity<>(request, headers),
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnPreconditionFailedIfNotMatch() {
        ResponseEntity<Void> createSessionResponse = createSession();
        URI uri = createSessionResponse.getHeaders().getLocation();

        EndSessionRequest request = createRequest();
        HttpHeaders headers = createAuthHeader("customer");
        headers.setIfMatch("-1");

        ResponseEntity<Void> response = testRestTemplate.exchange(uri,
                HttpMethod.PATCH,
                new HttpEntity<>(request, headers),
                Void.class);

        assertEquals(HttpStatus.PRECONDITION_FAILED, response.getStatusCode());
    }

    @Test
    void shouldReturnNoContentWhenPatchStartedSession() {
        ResponseEntity<Void> createSessionResponse = createSession();
        HttpHeaders headersResponse = createSessionResponse.getHeaders();
        Assertions.assertNotNull(headersResponse);

        EndSessionRequest request = createRequest();
        HttpHeaders headers = createAuthHeader("customer");
        headers.setIfMatch(Objects.requireNonNull(headersResponse.getETag()));

        ResponseEntity<Void> response = testRestTemplate.exchange(headersResponse.getLocation(),
                HttpMethod.PATCH,
                new HttpEntity<>(request, headers),
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNotNull(response.getHeaders().get(ETAG));
        assertNotNull(response.getHeaders().get(LAST_MODIFIED));
    }

    private ResponseEntity<Void> createSession() {
        StartSessionRequest startRequest = createStartSessionRq();

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(startRequest, createAuthHeader("customer")),
                Void.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        return response;
    }

    private StartSessionRequest createStartSessionRq() {
        StartSessionRequest request = new StartSessionRequest();
        request.setConnectorNumber(1L);
        request.setPointSerialNumber("number1");
        request.setVehicleRegistrationPlate(vehicle.getRegistrationPlate());
        request.setRfIdTagNumber(rfIdTag.getTagNumber());
        request.setStartMeter(0);
        return request;
    }

    private static EndSessionRequest createRequest() {
        EndSessionRequest request = new EndSessionRequest();
        request.setEndMeter(10);
        return request;
    }

    private URI getUri() {
        return getBasePath().resolve("/charging-sessions");
    }
}
