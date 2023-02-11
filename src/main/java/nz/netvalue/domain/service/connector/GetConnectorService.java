package nz.netvalue.domain.service.connector;

import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargeConnectorEntity;

/**
 * Service gets charge connectors
 */
public interface GetConnectorService {

    /**
     * Get charge connector by connectorNumber
     *
     * @param pointSerialNumber charge point serial number
     * @param connectorNumber   charge connector number
     * @return connector
     * Can throw {@link ResourceNotFoundException} if charge connector not exists
     */
    ChargeConnectorEntity getConnector(String pointSerialNumber, Long connectorNumber);
}
