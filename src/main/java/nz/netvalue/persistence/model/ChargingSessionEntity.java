package nz.netvalue.persistence.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Sessions of charging vehicles
 */
@Getter
@Setter
@Entity
@Table(name = "charging_sessions")
@EntityListeners(AuditingEntityListener.class)
public class ChargingSessionEntity extends BaseEntity {

    /**
     * Date and time when session starts
     */
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    /**
     * The meter value of charges Wh of energy
     */
    @Column(name = "start_meter")
    private Integer startMeter;

    /**
     * Date and time when session ends
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * The meter value of charges Wh of energy
     */
    @Column(name = "end_meter")
    private Integer endMeter;

    /**
     * Connection that used during the session
     */
    @OneToOne
    @JoinColumn(name = "charge_connector_id", nullable = false)
    private ChargeConnectorEntity chargeConnector;

    /**
     * RFID tag that used during the session
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rfid_tag_id", nullable = false)
    private RfIdTagEntity rfIdTag;

    /**
     * Error message when charge session can not complete
     */
    @Length(max = 500)
    @Column(name = "error_message")
    private String errorMessage;

    /**
     * Linked vehicle to RFID tag
     */
    @OneToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;
}
