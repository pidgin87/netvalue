package nz.netvalue.domain.service.version;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.model.version.Version;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final Version version;

    @Override
    public Version get() {
        return version;
    }
}
