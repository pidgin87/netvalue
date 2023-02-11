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
 * Vehicles that need to be charged
 */
@Getter
@Setter
@Entity
@Table(name = "vehicles")
@EntityListeners(AuditingEntityListener.class)
public class VehicleEntity extends BaseEntity {

    /**
     * Vehicles name
     */
    @Length(max = 255)
    @Column(name = "vehicle_name", nullable = false)
    private String vehicleName;

    /**
     * Vehicles registration plate
     */
    @Length(max = 15)
    @Column(name = "registration_plate", nullable = false)
    private String registrationPlate;
}
