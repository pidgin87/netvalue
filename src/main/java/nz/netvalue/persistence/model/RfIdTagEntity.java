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
 * RFID tags that uses during the charge session of a vehicle
 */
@Getter
@Setter
@Entity
@Table(name = "rfid_tags")
@EntityListeners(AuditingEntityListener.class)
public class RfIdTagEntity extends BaseEntity {

    /**
     * RFID tag name
     */
    @Length(max = 255)
    @Column(name = "tag_name", nullable = false)
    private String tagName;

    /**
     * RFID tag global unique number
     */
    @Column(name = "tag_number", nullable = false)
    private String tagNumber;

    /**
     * Customer that owned RFID tag
     */
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity customer;
}
