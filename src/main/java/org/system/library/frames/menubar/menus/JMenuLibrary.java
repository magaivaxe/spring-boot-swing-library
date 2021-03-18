package org.system.library.frames.menubar.menus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuLibrary extends JMenu {

  private final MessageLibrary messageLibrary;
  private final Set<JMenuItem> jMenuItems = new LinkedHashSet<>();

  public JMenuLibrary build() {
    initComponents();
    return this;
  }

  public JMenuLibrary addMenuItem(JMenuItem menuItem) {
    jMenuItems.add(menuItem);
    return this;
  }

  private void initComponents() {
    jMenuItems.forEach(this::add);
    setText(messageLibrary.getMessage("menu.library"));
  }

  private void setLayouts() {

  }

  public void setListeners() {

  }
}