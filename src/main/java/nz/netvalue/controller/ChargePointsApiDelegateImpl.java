package nz.netvalue.controller;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.auth.HasAdminRole;
import nz.netvalue.controller.model.ConnectorRequest;
import nz.netvalue.controller.utils.LastModifiedHeaderBuilder;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.controller.utils.MdcService;
import nz.netvalue.domain.service.connector.CreateConnectorService;
import nz.netvalue.persistence.model.ChargeConnectorEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ChargePointsApiDelegateImpl implements ChargePointsApiDelegate {

    private final CreateConnectorService connectorService;
    private final MdcService mdcService;
    private final LocationHeaderBuilder locationHeaderBuilder;
    private final LastModifiedHeaderBuilder lastModifiedHeaderBuilder;

    @Override
    @HasAdminRole
    public ResponseEntity<Void> addConnector(String serialNumber, ConnectorRequest connectorRequest) {
        mdcService.create();

        ChargeConnectorEntity createdConnector = connectorService.addConnectorToPoint(serialNumber, connectorRequest.getConnectorNumber());
        URI location = locationHeaderBuilder.build(createdConnector.getId());

        return ResponseEntity
                .created(location)
                .eTag(Long.toString(createdConnector.getVersion()))
                .lastModified(lastModifiedHeaderBuilder.build(createdConnector.getLastModifiedDate()))
                .build();
    }
}
