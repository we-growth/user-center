package cn.wegrowth.usercenter.configuration

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun apiGroup(): GroupedOpenApi =
        GroupedOpenApi
            .builder()
            .group("dso")
            .pathsToMatch("/api/**")
            .build()

    @Bean
    fun apiInfo(): OpenAPI {
        val securitySchemeName = "bearerAuth"
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(
                Components().addSecuritySchemes(securitySchemeName,
                    SecurityScheme().name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                )
            )
            .info(Info()
                .title("User Center Rest Api")
                .description("Rest Api for User-center.")
                .version("1.0")
            )
    }


}