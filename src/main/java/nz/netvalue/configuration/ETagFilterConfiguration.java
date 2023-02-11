package nz.netvalue.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

/**
 * Configuration for creation eTag for GET requests
 * and control 304 http code
 */
@Configuration
public class ETagFilterConfiguration {
    @Bean
    public Filter filter() {
        return new ShallowEtagHeaderFilter();
    }
}
