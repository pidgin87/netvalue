package nz.netvalue.domain.service.connector;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.point.ChargePointService;
import nz.netvalue.exception.ResourceConflictException;
import nz.netvalue.persistence.model.ChargeConnectorEntity;
import nz.netvalue.persistence.model.ChargePointEntity;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CreateConnectorServiceImpl implements CreateConnectorService {

    private final ChargePointService chargePointService;
    private final ChargeConnectorRepository repository;

    @Override
    public ChargeConnectorEntity addConnectorToPoint(String serialNumber, Long connectorNumber) {
        ChargePointEntity chargePointEntity = chargePointService.getChargePoint(serialNumber);

        checkConnectorExists(chargePointEntity, connectorNumber);
        return createAndSaveConnector(chargePointEntity, connectorNumber);
    }

    private void checkConnectorExists(ChargePointEntity chargePointEntity, Long connectorNumber) {
        if (repository.existsByChargePointAndConnectorNumber(chargePointEntity, connectorNumber)) {
            throw new ResourceConflictException(
                    format("Connector with number [%s] and point serial number [%s] already exists", connectorNumber, chargePointEntity.getSerialNumber())
            );
        }
    }

    private ChargeConnectorEntity createAndSaveConnector(ChargePointEntity chargePointEntity, Long connectorNumber) {
        ChargeConnectorEntity connector = createConnector(connectorNumber, chargePointEntity);
        return repository.save(connector);
    }

    private static ChargeConnectorEntity createConnector(Long connectorNumber, ChargePointEntity chargePointEntity) {
        ChargeConnectorEntity connector = new ChargeConnectorEntity();
        connector.setConnectorNumber(connectorNumber);
        connector.setChargePoint(chargePointEntity);
        return connector;
    }
}
