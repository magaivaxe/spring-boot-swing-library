package org.system.library.frames.menubar.factory;

import org.system.library.frames.menubar.JMenuBarGeneral;
import org.system.library.frames.menubar.menus.JMenuContainer;
import org.system.library.frames.menubar.menus.menuitems.JMenuItemContainer;

import javax.swing.*;

public enum JMenuBarTypeBuilder {
  LOGIN_FRAME {
    @Override
    public JMenuBar buildMenuBar(JMenuBarGeneral menuBar, JMenuContainer menuContainer, JMenuItemContainer menuItemContainer) {
      var menuLibrary = menuContainer.getLibraryMenu();
      menuLibrary
        .addMenuItem(menuItemContainer.getAboutMenuItem())
        .addMenuItem(menuItemContainer.getQuiterMenuItem());
      return menuBar
        .addMenu(menuLibrary.build())
        .build();
    }
  },
  HOME_FRAME {
    @Override
    public JMenuBar buildMenuBar(JMenuBarGeneral menuBar, JMenuContainer menuContainer, JMenuItemContainer menuItemContainer) {
      return null;
    }
  };

  public abstract JMenuBar buildMenuBar(JMenuBarGeneral menuBar, JMenuContainer menuContainer, JMenuItemContainer menuItemContainer);
}