package org.system.library.frames.menubar.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.frames.menubar.JMenuBarGeneral;
import org.system.library.frames.menubar.menus.JMenuContainer;
import org.system.library.frames.menubar.menus.menuitems.JMenuItemContainer;

import javax.swing.*;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuBarFactory {

  private final JMenuBarGeneral generalMenuBar;
  private final JMenuContainer menuContainer;
  private final JMenuItemContainer menuItemContainer;

  public JMenuBar buildMenuBar(JMenuBarTypeBuilder typeBuilder) {
    return typeBuilder.buildMenuBar(generalMenuBar, menuContainer, menuItemContainer);
  }
}