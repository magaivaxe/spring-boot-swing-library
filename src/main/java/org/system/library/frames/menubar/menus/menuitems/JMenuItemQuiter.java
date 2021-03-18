package org.system.library.frames.menubar.menus.menuitems;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.listeners.QuitListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenuItemQuiter extends JMenuItem {

  private final MessageLibrary message;
  private final QuitListener quitListener;

  public JMenuItemQuiter(MessageLibrary message, QuitListener quitListener) {
    this.message = message;
    this.quitListener = quitListener;
    build();
  }

  private void build() {
    initComponents();
    setLayouts();
    setListeners();
  }

  private void initComponents() {
    setText(message.getMessage("menuitem.quit"));
  }

  private void setLayouts() {
    setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
  }

  public void setListeners() {
    addActionListener(quitListener);
  }
}