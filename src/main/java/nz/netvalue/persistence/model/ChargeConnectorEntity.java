package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Connectors in charge point
 */
@Getter
@Setter
@Entity
@Table(name = "charge_connectors")
@EntityListeners(AuditingEntityListener.class)
public class ChargeConnectorEntity extends BaseEntity {
    /**
     * Charge connector number in point
     */
    @Column(name = "connector_number", nullable = false)
    private Long connectorNumber;

    /**
     * Charge point that owns the connector
     */
    @ManyToOne
    @JoinColumn(name = "charge_point_id")
    private ChargePointEntity chargePoint;
}
