package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Users
 */
@Getter
@Setter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BaseEntity {

    /**
     * User name
     */
    @Length(max = 300)
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * User login
     */
    @Length(max = 40)
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * User password
     */
    @Length(max = 72)
    @Column(name = "user_password", nullable = false)
    private String password;

    /**
     * User role
     */
    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity roleEntity;
}
