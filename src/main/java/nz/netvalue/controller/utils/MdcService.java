package nz.netvalue.controller.utils;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.security.SecurityService;
import nz.netvalue.persistence.model.UserEntity;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class MdcService {

    private final SecurityService securityService;

    public void create(Map<String, Object> mdc) {
        create();
        if (nonNull(mdc)) {
            mdc.keySet().forEach(key ->
                    MDC.put(key, Objects.toString(mdc.get(key)))
            );
        }
    }

    public void create() {
        UserEntity user = securityService.getCurrentUser();
        if (nonNull(user)) {
            MDC.put("username", user.getUsername());
            MDC.put("role", user.getRoleEntity().getRoleName());
        }
    }
}
