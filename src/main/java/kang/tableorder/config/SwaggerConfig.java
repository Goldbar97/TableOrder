package kang.tableorder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI()
        .info(new Info()
            .title("TableOrder API")
            .description("SJK Table Order API Documents")
            .version("v.0.0.1")
            .contact(new Contact()
                .email("ehrm1357@gmail.com")
                .url("https://www.google.com"))
        );
  }
}
