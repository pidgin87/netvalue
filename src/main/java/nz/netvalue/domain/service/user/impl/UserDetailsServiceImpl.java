package nz.netvalue.domain.service.user.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.model.ApplicationUser;
import nz.netvalue.persistence.model.UserEntity;
import nz.netvalue.persistence.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        return new ApplicationUser(
                optional.orElseThrow(
                        () -> new UsernameNotFoundException(format("user with username [%s] not found", username))
                )
        );
    }
}
