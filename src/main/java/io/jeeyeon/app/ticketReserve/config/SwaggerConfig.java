package io.jeeyeon.app.ticketReserve.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("공연 좌석 예매 API")
                        .description("공연 좌석을 예매하는 기능을 제공합니다.")
                        .version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi concertApi() {
        return GroupedOpenApi.builder()
                .group("concertApi")
                .pathsToMatch("/api/**")
                .build();
    }

}
