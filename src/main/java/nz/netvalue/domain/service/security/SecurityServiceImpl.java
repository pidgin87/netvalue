package nz.netvalue.domain.service.security;

import nz.netvalue.domain.model.ApplicationUser;
import nz.netvalue.persistence.model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof String) {
            //anonymous user without authentification
            return null;
        }
        ApplicationUser applicationUser = (ApplicationUser) principal;
        if (isNull(applicationUser)) {
            return null;
        }
        return applicationUser.getUser();
    }
}
