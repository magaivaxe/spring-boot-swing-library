package org.system.library.frames.menubar.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

@RequiredArgsConstructor
@Component
class LanguageListener implements ItemListener {

  private final ResourceBundleMessageSource messageSource;

  @Override
  public void itemStateChanged(ItemEvent e) {
    //TODO: change language save on DB and loading it on configurations
    messageSource.setDefaultLocale(Locale.US);
  }
}