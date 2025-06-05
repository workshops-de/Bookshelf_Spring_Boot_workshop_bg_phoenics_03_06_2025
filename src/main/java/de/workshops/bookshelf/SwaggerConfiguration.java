package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
class SwaggerConfiguration {

  @Bean
  OpenAPI api(SwaggerRecordProperties swaggerRecordProperties) {
    return new OpenAPI()
        .info(
            new Info()
                .title(swaggerRecordProperties.title())
                .version(swaggerRecordProperties.version())
                .description("A simple API for a bookshelf for %d book".formatted(swaggerRecordProperties.size()))
                .license(new License()
                    .name(swaggerRecordProperties.license().name())
                    .url("https://opensource.org/licenses/MIT")
                )
        );
  }
}
