package org.system.library.frames.menubar.menus.menuitems;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.listeners.AboutListener;

import javax.swing.*;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuItemAbout extends JMenuItem {

  private final MessageLibrary message;
  private final AboutListener aboutListener;

  public JMenuItemAbout(MessageLibrary message, AboutListener aboutListener) {
    this.message = message;
    this.aboutListener = aboutListener;
    build();
  }

  private void build() {
    initComponents();
    setListeners();
  }

  private void initComponents() {
    setText(message.getMessage("menuitem.about"));
  }

  private void setLayouts() {
  }

  public void setListeners() {
    addActionListener(aboutListener);
  }
}