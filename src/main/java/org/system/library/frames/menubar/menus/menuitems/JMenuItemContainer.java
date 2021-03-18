package org.system.library.frames.menubar.menus.menuitems;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuItemContainer {
  private final JMenuItemQuiter quiterMenuItem;
  private final JMenuItemAbout aboutMenuItem;
}