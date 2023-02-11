package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * Points where vehicles is charging
 */
@Getter
@Setter
@Entity
@Table(name = "charge_points")
@EntityListeners(AuditingEntityListener.class)
public class ChargePointEntity extends BaseEntity {

    /**
     * Charge point name
     */
    @Length(max = 255)
    @Column(name = "point_name", nullable = false)
    private String pointName;

    /**
     * Charge point serial number
     */
    @Length(max = 100)
    @Column(name = "serial_number", nullable = false)
    private String serialNumber;
}
