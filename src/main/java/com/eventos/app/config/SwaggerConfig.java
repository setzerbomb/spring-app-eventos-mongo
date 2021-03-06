package com.eventos.app.config;

import com.eventos.app.config.swagger.SwaggerManualApiPlugin;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@AllArgsConstructor
public class SwaggerConfig extends WebMvcConfigurationSupport {

    private final SwaggerManualApiPlugin  swaggerManualApiPlugin;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.eventos.app.controller.resources"))
                .paths(PathSelectors.any()).build().apiInfo(metaData())
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .tags(new Tag("authentication-controller", "OAuth"))
                .apiInfo(metaData())
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    @Bean
    public ApiListingScannerPlugin listingScanner() {
        return swaggerManualApiPlugin;
    }

    @Bean
    @ConfigurationProperties("oauth2.client")
    public OAuth2ProtectedResourceDetails authDetails() {
        return new AuthorizationCodeResourceDetails();
    }

    private ApiInfo metaData() {
        Contact contato0 = new Contact("Set", null, "set@localhost");

        return new ApiInfoBuilder().title("Eventos APP")
                .description("Documentação dos End-Points.")
                .version("Alpha 0.1.0")
                .license("Open Source")
                .licenseUrl("https://github.com/setzerbomb/spring-app-eventos-mongo/blob/master/LICENSE.md")
                .contact(contato0)
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer Token", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex( "^(?!\\/register).*")).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Bearer Token",
                authorizationScopes));
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations")
        };
        return scopes;
    }

}