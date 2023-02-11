package nz.netvalue.domain.service.point;

import lombok.RequiredArgsConstructor;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargePointEntity;
import nz.netvalue.persistence.repository.ChargePointRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class ChargePointServiceImpl implements ChargePointService {

    private final ChargePointRepository chargePointRepository;

    @Override
    public ChargePointEntity getChargePoint(String serialNumber) {
        Optional<ChargePointEntity> optional = chargePointRepository.findBySerialNumber(serialNumber);
        return optional.orElseThrow(
                () -> new ResourceNotFoundException(format("Charge point with serial number = [%s] not exists", serialNumber))
        );
    }
}
