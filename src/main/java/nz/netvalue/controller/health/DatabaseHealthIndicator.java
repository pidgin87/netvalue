package nz.netvalue.controller.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Healthcheck for database alive in actuator
 */
@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource ds;

    public DatabaseHealthIndicator(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public Health health() {
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("select 1 from dual");
        } catch (SQLException ex) {
            return Health.down(ex).build();
        }
        return Health.up().build();
    }
}
