package lt.vu.mif.dmsti.webaml.configuration;

import com.fasterxml.jackson.databind.Module;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"lt.vu.mif.dmsti.webaml.api", "lt.vu.mif.dmsti.webaml.controllers"})
public @SpringBootApplication class WebAMLConfig {

    public static void main(String[] args) {
        new SpringApplication(WebAMLConfig.class).run(args);
    }

    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("WebAML API")
                        .description("WebAML solver and converter services")
                        .version("v1.0.0")
                        .contact(new Contact().name("Vaidas Jusevicius").email("vaidas.jusevicius@mif.vu.lt"))
                        .license(new License().name("MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentation and research work")
                        .url("https://github.com/vaidasj/webaml"));
    }
}
