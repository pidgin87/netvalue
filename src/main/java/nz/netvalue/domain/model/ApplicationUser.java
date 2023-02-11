package nz.netvalue.domain.model;

import nz.netvalue.persistence.model.RoleEntity;
import nz.netvalue.persistence.model.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

public class ApplicationUser implements UserDetails {

    private static final long serialVersionUID = 1905122041950251207L;

    private final transient UserEntity userEntity;
    private final Set<ApplicationGrantedAuthority> authorities = new HashSet<>();

    public ApplicationUser(UserEntity userEntity) {
        this.userEntity = userEntity;
        RoleEntity roleEntity = userEntity.getRoleEntity();
        if (roleEntity != null) {
            authorities.add(new ApplicationGrantedAuthority(roleEntity.getRoleName()));
        }
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public Set<ApplicationGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserEntity getUser() {
        return userEntity;
    }
}
