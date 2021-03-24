package org.system.library.frames.menubar.listeners;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuBeansContainer {

  private final QuitListener quitListener;
  private final AboutListener aboutListener;
  private final LanguageListener languageListener;
  private final Locale locale;
}