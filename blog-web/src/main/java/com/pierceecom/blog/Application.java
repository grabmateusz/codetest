package com.pierceecom.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.pierceecom.blog.dto.PostsListDto;
import com.pierceecom.blog.json.PostsListDtoSerializer;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableJpaRepositories(Application.REPOSITORIES_ROOT_PACKAGE)
@EntityScan(Application.ENTITIES_ROOT_PACKAGE)
@SpringBootApplication(scanBasePackages = Application.COMPONENTS_ROOT_PACKAGE)
public class Application extends SpringBootServletInitializer {

  public static final String REPOSITORIES_ROOT_PACKAGE = "com.pierceecom.blog.repositories";

  public static final String ENTITIES_ROOT_PACKAGE = "com.pierceecom.blog.domain";

  public static final String COMPONENTS_ROOT_PACKAGE = "com.pierceecom.blog";

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
  public ObjectMapper jsonObjectMapper() {
    SimpleModule collectionTypeSerializerModule = new SimpleModule();
    collectionTypeSerializerModule.addSerializer(new PostsListDtoSerializer(PostsListDto.class));
    return Jackson2ObjectMapperBuilder.json()
        .modules(collectionTypeSerializerModule)
        .build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedOrigins("*")
            .allowedHeaders("*");
      }
    };
  }

  @Bean
  public HttpMessageConverters converters() {
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.setObjectMapper(jsonObjectMapper());
    return new HttpMessageConverters(true, Arrays.asList(
        mappingJackson2HttpMessageConverter,
        new Jaxb2RootElementHttpMessageConverter())
    );
  }
}