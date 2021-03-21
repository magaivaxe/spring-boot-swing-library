package org.system.library.frames.menubar.factory;

import org.system.library.frames.menubar.JMenuComponentType;
import org.system.library.frames.menubar.JMenuGeneralContainer;
import org.system.library.frames.menubar.listeners.JMenuActionListenersContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public enum JMenuBarType {
  LOGIN_FRAME {
    @Override
    public JMenuBar buildMenuBar(JMenuGeneralContainer menus, JMenuGeneralContainer childMenus,
                                 JMenuActionListenersContainer listenersContainer) {

      var aboutItem = (JMenuItem) childMenus.addToContainer("menuitem.about", JMenuComponentType.MENU_ITEM);
      aboutItem.addActionListener(listenersContainer.getAboutListener());
      childMenus.addToContainer("", JMenuComponentType.SEPARATOR);
      var quitItem = (JMenuItem) childMenus.addToContainer("menuitem.quit", JMenuComponentType.MENU_ITEM);
      quitItem.addActionListener(listenersContainer.getQuitListener());
      quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

      menus.addToContainer("menu.library", JMenuComponentType.MENU);
      menus.addChildsToContainerElement("menu.library", childMenus.getAllAndClearContainer());

      menus.addToContainer("menu.settings", JMenuComponentType.MENU);
      //TODO: Set language options here

      var menuBar = new JMenuBar();
      menus.getAllAndClearContainer().forEach(menuBar::add);
      return menuBar;
    }
  },
  HOME_FRAME {
    @Override
    public JMenuBar buildMenuBar(JMenuGeneralContainer menus, JMenuGeneralContainer childMenus,
                                 JMenuActionListenersContainer listenersContainer) {
      return null;
    }
  };

  public abstract JMenuBar buildMenuBar(JMenuGeneralContainer menus, JMenuGeneralContainer childMenus,
                                        JMenuActionListenersContainer listenersContainer);

  public static JMenuBar buildMenuBarByType(JMenuBarType type, JMenuGeneralContainer menus, JMenuGeneralContainer childMenus,
                                            JMenuActionListenersContainer listenersContainer) {
    return type.buildMenuBar(menus, childMenus, listenersContainer);
  }
}