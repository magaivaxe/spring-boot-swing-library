package org.system.library.configuration.messages;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class MessageLibrary {

  private final MessageSource messageSource;
  private final Locale locale;

  public String getMessage(String property) {
    return property.isEmpty() ? "" : messageSource.getMessage(property, null, locale);
  }
}