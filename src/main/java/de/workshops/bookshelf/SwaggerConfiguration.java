package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class SwaggerConfiguration {
  @Bean
  public OpenAPI api(SwaggerProperties swaggerProperties) {
    return new OpenAPI()
        .info(
            new Info()
                .title(swaggerProperties.getTitle())
                .version(swaggerProperties.getVersion())
                .description("A simple API for a bookshelf for %d book".formatted(swaggerProperties.getSize()))
                .license(new License()
                    .name(swaggerProperties.getLicense().getName())
                    .url("https://opensource.org/licenses/MIT")
                )
        );
  }
}
