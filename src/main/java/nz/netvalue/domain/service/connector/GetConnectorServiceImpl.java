package nz.netvalue.domain.service.connector;

import lombok.RequiredArgsConstructor;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargeConnectorEntity;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GetConnectorServiceImpl implements GetConnectorService {

    private final ChargeConnectorRepository repository;

    @Override
    public ChargeConnectorEntity getConnector(String pointSerialNumber, Long connectorNumber) {
        Optional<ChargeConnectorEntity> optional = repository.findByChargePointAndNumber(pointSerialNumber, connectorNumber);
        return optional.orElseThrow(
                () -> new ResourceNotFoundException(format("Charge connector with number = [%s] not exists", connectorNumber))
        );
    }
}
