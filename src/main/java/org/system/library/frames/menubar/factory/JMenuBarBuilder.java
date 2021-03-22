package org.system.library.frames.menubar.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.frames.menubar.JMenusContainer;
import org.system.library.frames.menubar.listeners.JMenuListenersContainer;

import javax.swing.*;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuBarBuilder {

  private final JMenusContainer menus;
  private final JMenusContainer childsMenus;
  private final JMenuListenersContainer listeners;

  public JMenuBar buildMenuBarByType(JMenuBarType type) {
    return JMenuBarType.buildMenuBarByType(type, menus, childsMenus, listeners);
  }
}