package com.pierceecom.blog;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories(Application.REPOSITORIES_ROOT_PACKAGE)
@EntityScan(Application.ENTITIES_ROOT_PACKAGE)
@SpringBootApplication(scanBasePackages = Application.COMPONENTS_ROOT_PACKAGE)
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

  public static final String REPOSITORIES_ROOT_PACKAGE = "com.pierceecom.blog.repositories";

  public static final String ENTITIES_ROOT_PACKAGE = "com.pierceecom.blog.domain";

  public static final String COMPONENTS_ROOT_PACKAGE = "com.pierceecom.blog";

  public static final String CONTROLLERS_ROOT_PACKAGE = "com.pierceecom.blog.controllers";

  private static final String API_TITLE = "Blog posts REST API";

  private static final String API_DESCRIPTION = "This is the definition of the API for code test";

  private static final String AUTHOR_NAME = "Mateusz Grab";

  private static final String AUTHOR_MAIL = "grab.mateusz@gmail.com";

  private static final String AUTHOR_URL = "https://github.com/grabmateusz";

  private static Class<Application> applicationClass = Application.class;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  @Bean
  public Mapper getMapper() {
    return DozerBeanMapperBuilder.buildDefault();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedOrigins("*")
            .allowedHeaders("*");
      }
    };
  }

  @Bean
  public HttpMessageConverters converters() {
    return new HttpMessageConverters(true, Arrays.asList(
        new Jaxb2RootElementHttpMessageConverter(),
        new MappingJackson2HttpMessageConverter())
    );
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage(CONTROLLERS_ROOT_PACKAGE))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(API_TITLE)
        .description(API_DESCRIPTION)
        .contact(new Contact(AUTHOR_NAME, AUTHOR_URL, AUTHOR_MAIL))
        .build();
  }
}