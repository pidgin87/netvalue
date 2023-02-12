package nz.netvalue.controller.mapper;

import nz.netvalue.controller.model.ChargingSessionResponse;
import nz.netvalue.persistence.model.ChargingSessionEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map version information to version response
 */
@Mapper
public interface ChargingSessionMapper {

    /**
     * Get list of charging session for response
     *
     * @param list - session list from db
     * @return list of charging session
     */
    List<ChargingSessionResponse> toResponseList(List<ChargingSessionEntity> list);
}
