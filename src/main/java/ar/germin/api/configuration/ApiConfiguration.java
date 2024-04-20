package ar.germin.api.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GerminarConfiguration.class)
public class ApiConfiguration {

}
