package nz.netvalue.domain.service.session;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.domain.service.connector.GetConnectorService;
import nz.netvalue.domain.service.rfidtag.RfidTagService;
import nz.netvalue.domain.service.vehicle.VehicleService;
import nz.netvalue.exception.ResourceConflictException;
import nz.netvalue.persistence.model.ChargeConnectorEntity;
import nz.netvalue.persistence.model.ChargingSessionEntity;
import nz.netvalue.persistence.model.RfIdTagEntity;
import nz.netvalue.persistence.model.VehicleEntity;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class StartSessionServiceImpl implements StartSessionService {

    private final ChargingSessionRepository repository;
    private final GetConnectorService getConnectorService;
    private final RfidTagService rfidTagService;
    private final VehicleService vehicleService;

    @Override
    public ChargingSessionEntity startSession(StartSessionRequest request) {
        RfIdTagEntity rfIdTagEntity = rfidTagService.getByTagNumber(request.getRfIdTagNumber());
        VehicleEntity vehicleEntity = vehicleService.getByRegistrationPlate(request.getVehicleRegistrationPlate());

        if (repository.existsByRfIdTagAndVehicleAndEndTimeIsNull(rfIdTagEntity, vehicleEntity)) {
            throw new ResourceConflictException(
                    format("Vehicle with reg.plate [%s] is already charging", request.getVehicleRegistrationPlate())
            );
        }
        ChargeConnectorEntity connector = getConnectorService.getConnector(request.getPointSerialNumber(), request.getConnectorNumber());

        return repository.save(
                createSession(request, connector, rfIdTagEntity, vehicleEntity)
        );
    }

    private static ChargingSessionEntity createSession(StartSessionRequest request,
                                                       ChargeConnectorEntity connector,
                                                       RfIdTagEntity rfIdTagEntity,
                                                       VehicleEntity vehicleEntity) {
        ChargingSessionEntity session = new ChargingSessionEntity();
        session.setChargeConnector(connector);
        session.setStartTime(LocalDateTime.now());
        session.setStartMeter(request.getStartMeter());
        session.setRfIdTag(rfIdTagEntity);
        session.setVehicle(vehicleEntity);
        return session;
    }
}
