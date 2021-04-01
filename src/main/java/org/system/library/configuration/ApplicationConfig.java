package org.system.library.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.system.library.business.model.UserLibrary;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "org.system.library")
public class ApplicationConfig {

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setBasename("messages");
    source.setUseCodeAsDefaultMessage(true);
    return source;
  }

  @Bean
  public Locale localeLanguage() {
    // TODO: change language dinamicaly into application
    return new Locale(Locale.CANADA_FRENCH.getLanguage(), Locale.CANADA.getCountry());
  }

  @Bean
  public UserLibrary userLibraryBean() {
    return UserLibrary.builder().build();
  }

}