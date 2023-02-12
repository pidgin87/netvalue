package nz.netvalue.domain.service.vehicle;

import lombok.RequiredArgsConstructor;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.VehicleEntity;
import nz.netvalue.persistence.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    @Override
    public VehicleEntity getByRegistrationPlate(String regPlate) {
        Optional<VehicleEntity> optional = repository.findByRegistrationPlate(regPlate);
        return optional.orElseThrow(
                () -> new ResourceNotFoundException(format("Vehicle with registration plate = [%s] not exists", regPlate))
        );
    }
}
