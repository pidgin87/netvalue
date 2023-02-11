package nz.netvalue.e2e;

import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.persistence.model.RfIdTagEntity;
import nz.netvalue.persistence.model.VehicleEntity;
import nz.netvalue.persistence.repository.RfidTagRepository;
import nz.netvalue.persistence.repository.UserRepository;
import nz.netvalue.persistence.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.ETAG;
import static org.springframework.http.HttpHeaders.LAST_MODIFIED;
import static org.springframework.http.HttpHeaders.LOCATION;

class StartSessionTest extends BaseE2ETestRunner {

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
        StartSessionRequest request = createStartSessionRq();

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request),
                Void.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldReturnForbidden() {
        StartSessionRequest request = createStartSessionRq();

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, createAuthHeader("admin")),
                Void.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequest() {
        StartSessionRequest request = createStartSessionRq();
        request.setStartMeter(null);

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, createAuthHeader("customer")),
                Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnResourceNotFoundWhenNotExistVehicle() {
        StartSessionRequest request = createStartSessionRq();
        request.setVehicleRegistrationPlate("1");

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, createAuthHeader("customer")),
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnResourceNotFoundWhenNotExistRFIDTag() {
        StartSessionRequest request = createStartSessionRq();
        request.setRfIdTagNumber("1");

        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, createAuthHeader("customer")),
                Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnCreated() {
        StartSessionRequest request = createStartSessionRq();
        ResponseEntity<Void> response = testRestTemplate.exchange(getUri(),
                HttpMethod.POST,
                new HttpEntity<>(request, createAuthHeader("customer")),
                Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get(ETAG));
        assertNotNull(response.getHeaders().get(LOCATION));
        assertNotNull(response.getHeaders().get(LAST_MODIFIED));
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

    private URI getUri() {
        return getBasePath().resolve("/charging-sessions");
    }
}
