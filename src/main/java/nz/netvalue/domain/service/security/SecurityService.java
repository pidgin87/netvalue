package nz.netvalue.domain.service.security;

import nz.netvalue.persistence.model.UserEntity;

public interface SecurityService {
    UserEntity getCurrentUser();
}
