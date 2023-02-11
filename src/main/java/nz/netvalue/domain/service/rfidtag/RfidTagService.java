package nz.netvalue.domain.service.rfidtag;

import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.RfIdTagEntity;

/**
 * Service works with charge connectors
 */
public interface RfidTagService {

    /**
     * Get RFID tag by number
     *
     * @param tagNumber RFID tag's number
     * @return RFID tag
     * If RFID tag not exists, throws {@link ResourceNotFoundException}
     */
    RfIdTagEntity getByTagNumber(String tagNumber);
}
