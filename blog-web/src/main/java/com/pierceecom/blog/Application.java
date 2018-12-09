package com.pierceecom.blog;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(Application.REPOSITORIES_ROOT_PACKAGE)
@EntityScan(Application.ENTITIES_ROOT_PACKAGE)
@SpringBootApplication(scanBasePackages = Application.COMPONENTS_ROOT_PACKAGE)
public class Application {

  public static final String REPOSITORIES_ROOT_PACKAGE = "com.pierceecom.blog.repositories";

  public static final String ENTITIES_ROOT_PACKAGE = "com.pierceecom.blog.domain";

  public static final String COMPONENTS_ROOT_PACKAGE = "com.pierceecom.blog";

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public Mapper getMapper() {
    return DozerBeanMapperBuilder.create()
        .withMappingFiles("mappings.xml")
        .build();
  }
}

