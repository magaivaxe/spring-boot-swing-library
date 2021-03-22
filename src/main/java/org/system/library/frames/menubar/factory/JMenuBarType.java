package org.system.library.frames.menubar.factory;

import org.system.library.frames.menubar.JMenuComponentType;
import org.system.library.frames.menubar.JMenusContainer;
import org.system.library.frames.menubar.listeners.JMenuListenersContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public enum JMenuBarType {
  LOGIN_FRAME {
    @Override
    public JMenuBar build(JMenusContainer menus, JMenusContainer childMenus,
                          JMenuListenersContainer listenersContainer) {

      buildLibraryMenu(menus, childMenus, listenersContainer);
      buildSettingsMenu(menus, childMenus, listenersContainer);

      return buildMenuBar(menus);
    }
  },
  HOME_FRAME {
    @Override
    public JMenuBar build(JMenusContainer menus, JMenusContainer childMenus,
                          JMenuListenersContainer listenersContainer) {
      return null;
    }
  };

  public abstract JMenuBar build(JMenusContainer menus, JMenusContainer childMenus,
                                 JMenuListenersContainer listenersContainer);

  public static JMenuBar buildMenuBarByType(JMenuBarType type, JMenusContainer menus, JMenusContainer childMenus,
                                            JMenuListenersContainer listenersContainer) {
    return type.build(menus, childMenus, listenersContainer);
  }

  void buildSettingsMenu(JMenusContainer menus, JMenusContainer childMenus, JMenuListenersContainer listenersContainer) {
    childMenus.addToContainer("menu.language", JMenuComponentType.MENU);
    var languageEn = (JRadioButtonMenuItem) childMenus.addChildToContainerElement("menu.language",
      "menu.language.en", JMenuComponentType.MENU_ITEM_RADIO);
    var languageFr = (JRadioButtonMenuItem) childMenus.addChildToContainerElement("menu.language",
      "menu.language.fr", JMenuComponentType.MENU_ITEM_RADIO);
    languageEn.addItemListener(listenersContainer.getLanguageListener());
    languageFr.addItemListener(listenersContainer.getLanguageListener());

    menus.addToContainer("menu.settings", JMenuComponentType.MENU);
    menus.addChildsToContainerElement("menu.settings", childMenus.getAllAndClearContainer());
  }

  void buildLibraryMenu(JMenusContainer menus, JMenusContainer childMenus, JMenuListenersContainer listenersContainer) {
    var aboutItem = (JMenuItem) childMenus.addToContainer("menu.item.about", JMenuComponentType.MENU_ITEM);
    aboutItem.addActionListener(listenersContainer.getAboutListener());

    childMenus.addToContainer("", JMenuComponentType.SEPARATOR);

    var quitItem = (JMenuItem) childMenus.addToContainer("menu.item.quit", JMenuComponentType.MENU_ITEM);
    quitItem.addActionListener(listenersContainer.getQuitListener());
    quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

    menus.addToContainer("menu.library", JMenuComponentType.MENU);
    menus.addChildsToContainerElement("menu.library", childMenus.getAllAndClearContainer());
  }

  JMenuBar buildMenuBar(JMenusContainer menus) {
    var menuBar = new JMenuBar();
    menus.getAllAndClearContainer().forEach(menuBar::add);
    return menuBar;
  }
}