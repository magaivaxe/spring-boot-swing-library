package org.system.library.frames.menubar.factory;

import org.system.library.frames.menubar.JMenuComponentType;
import org.system.library.frames.menubar.JMenusContainer;
import org.system.library.frames.menubar.listeners.JMenuBeansContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public enum JMenuBarType {
  LOGIN_FRAME {
    @Override
    public JMenuBar build(JMenusContainer menus, JMenuBeansContainer beansContainer) {

      buildLibraryMenu(menus, beansContainer);
      buildSettingsMenu(menus, beansContainer);
      return buildMenuBar(menus);
    }
  },
  HOME_FRAME {
    @Override
    public JMenuBar build(JMenusContainer menus, JMenuBeansContainer listenersContainer) {
      return null;
    }
  };

  public abstract JMenuBar build(JMenusContainer menus, JMenuBeansContainer listenersContainer);

  public static JMenuBar buildMenuBarByType(JMenuBarType type, JMenusContainer menus,
                                            JMenuBeansContainer listenersContainer) {
    return type.build(menus, listenersContainer);
  }

  void buildSettingsMenu(JMenusContainer menus, JMenuBeansContainer beansContainer) {
    var language = menus.addToContainer("menu.settings", JMenuComponentType.MENU)
                        .addChildReturnChild(menus.buildChild("menu.language", JMenuComponentType.MENU));

    var languageEn = (JRadioButtonMenuItem) menus.buildChild("menu.language.en", JMenuComponentType.MENU_ITEM_RADIO);
    var languageFr = (JRadioButtonMenuItem) menus.buildChild("menu.language.fr", JMenuComponentType.MENU_ITEM_RADIO);
    languageEn.addItemListener(beansContainer.getLanguageListener());
    languageFr.addItemListener(beansContainer.getLanguageListener());
    languageEn.setSelected(beansContainer.getLocale().getLanguage().equals("en"));
    languageFr.setSelected(beansContainer.getLocale().getLanguage().equals("fr"));
    var group = new ButtonGroup();
    group.add(languageEn);
    group.add(languageFr);

    language.addChildReturnParent(languageEn).addChildReturnParent(languageFr);
  }

  void buildLibraryMenu(JMenusContainer menus, JMenuBeansContainer beansContainer) {
    var library = menus.addToContainer("menu.library", JMenuComponentType.MENU);
    var aboutItem = (JMenuItem) library
      .addChildReturnChild(menus.buildChild("menu.item.about", JMenuComponentType.MENU_ITEM))
      .getParentComponent();
    library.addChildReturnParent(menus.buildChild("", JMenuComponentType.SEPARATOR));
    var quitItem = (JMenuItem) library
      .addChildReturnChild(menus.buildChild("menu.item.quit", JMenuComponentType.MENU_ITEM)).getParentComponent();

    quitItem.addActionListener(beansContainer.getQuitListener());
    quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
    aboutItem.addActionListener(beansContainer.getAboutListener());
  }

  JMenuBar buildMenuBar(JMenusContainer menus) {
    var menuBar = new JMenuBar();
    menus.getAllParentsBuilded().forEach(menuBar::add);
    return menuBar;
  }
}