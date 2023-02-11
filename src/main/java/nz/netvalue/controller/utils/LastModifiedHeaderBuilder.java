package nz.netvalue.controller.utils;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Builder for POST request LastModified header
 */
@Component
public class LastModifiedHeaderBuilder {
    public ZonedDateTime build(LocalDateTime lastModifiedDate) {
        return ZonedDateTime.of(lastModifiedDate, ZoneId.systemDefault());
    }
}
