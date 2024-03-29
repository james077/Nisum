package com.nisum.nisumapi.config;

import com.nisum.nisumapi.NisumApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer swaggerProperties() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("Api")
                .globalResponseMessage(RequestMethod.GET, Collections.singletonList(
                        new ResponseMessageBuilder().code(503).message("Temporarily unavailable service").build()))
                .globalResponseMessage(RequestMethod.POST, Collections.singletonList(
                        new ResponseMessageBuilder().code(503).message("Temporarily unavailable service").build()))
                .apiInfo(metaData())
                .select()
                .apis(RequestHandlerSelectors.basePackage(NisumApi.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaData() {
        return new ApiInfo("nisumapi", "API to manage purchases ", getClass().getPackage().getImplementationVersion(), "© " + LocalDate.now().getYear() + "  nisum", new Contact("nisumapi API", "", "jamesmartinez077@gmail.com"), "", "licenciaURL", Collections.emptyList());
    }

}
