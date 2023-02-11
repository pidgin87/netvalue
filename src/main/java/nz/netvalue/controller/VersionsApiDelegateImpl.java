package nz.netvalue.controller;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.mapper.VersionMapper;
import nz.netvalue.controller.model.VersionResponse;
import nz.netvalue.domain.service.version.VersionService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class VersionsApiDelegateImpl implements VersionsApiDelegate {

    private static final CacheControl CACHE_CONTROL =
            CacheControl.maxAge(Duration.ofDays(1)).cachePublic().mustRevalidate();

    private final VersionService versionService;
    private final VersionMapper versionMapper;

    @Override
    public ResponseEntity<VersionResponse> getVersion() {
        VersionResponse dto = versionMapper.toResponse(versionService.get());
        return ResponseEntity.ok()
                .cacheControl(CACHE_CONTROL)
                .body(dto);
    }
}
