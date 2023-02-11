package nz.netvalue.domain.service.rfidtag;

import lombok.RequiredArgsConstructor;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.RfIdTagEntity;
import nz.netvalue.persistence.repository.RfidTagRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class RfidTagServiceImpl implements RfidTagService {

    private final RfidTagRepository repository;

    @Override
    public RfIdTagEntity getByTagNumber(String tagNumber) {
        Optional<RfIdTagEntity> optional = repository.findByTagNumber(tagNumber);
        return optional.orElseThrow(
                () -> new ResourceNotFoundException(format("RFID tag with number = [%s] not exists", tagNumber))
        );
    }
}
