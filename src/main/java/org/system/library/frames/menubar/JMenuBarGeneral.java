package org.system.library.frames.menubar;

import lombok.Getter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuBarGeneral extends JMenuBar {

  private final Set<JMenu> jMenus = new LinkedHashSet<>();

  public JMenuBarGeneral build() {
    initComponents();
    return this;
  }

  public JMenuBarGeneral addMenu(JMenu menu) {
    jMenus.add(menu);
    return this;
  }

  private void initComponents() {
    jMenus.forEach(this::add);
  }

  private void setLayouts() {

  }

  public void setListeners() {

  }
}