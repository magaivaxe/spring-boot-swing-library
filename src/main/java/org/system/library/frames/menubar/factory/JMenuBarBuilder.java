package org.system.library.frames.menubar.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.frames.menubar.JMenuGeneralContainer;
import org.system.library.frames.menubar.listeners.JMenuActionListenersContainer;

import javax.swing.*;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuBarBuilder {

  private final JMenuGeneralContainer menus;
  private final JMenuGeneralContainer childsMenus;
  private final JMenuActionListenersContainer listeners;

  public JMenuBar buildMenuBarByType(JMenuBarType type) {
    return JMenuBarType.buildMenuBarByType(type, menus, childsMenus, listeners);
  }
}