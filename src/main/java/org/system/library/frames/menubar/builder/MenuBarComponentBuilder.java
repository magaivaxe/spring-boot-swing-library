package org.system.library.frames.menubar.builder;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.frames.menubar.MenuContainer;
import org.system.library.frames.menubar.listeners.MenuBeansContainer;

import javax.swing.*;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MenuBarComponentBuilder {

  private final MenuContainer menus;
  private final MenuBeansContainer listeners;

  public JMenuBar buildMenuBarByType(MenuBarBuilder type) {
    return MenuBarBuilder.buildMenuBarByType(type, menus, listeners);
  }
}