package org.system.library.frames.menubar.builder;

import org.system.library.frames.menubar.MenuContainer;
import org.system.library.frames.menubar.listener.MenuBeansContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public enum MenuBarBuilder {
  LOGIN_FRAME {
    @Override
    public JMenuBar build(MenuContainer menus, MenuBeansContainer beansContainer) {

      buildLibraryMenu(menus, beansContainer);
      buildSettingsMenu(menus, beansContainer);
      return buildMenuBar(menus);
    }
  },
  HOME_FRAME {
    @Override
    public JMenuBar build(MenuContainer menus, MenuBeansContainer listenersContainer) {
      return null;
    }
  };

  public abstract JMenuBar build(MenuContainer menus, MenuBeansContainer listenersContainer);

  public static JMenuBar buildMenuBarByType(MenuBarBuilder type, MenuContainer menus,
                                            MenuBeansContainer listenersContainer) {
    return type.build(menus, listenersContainer);
  }

  void buildSettingsMenu(MenuContainer menus, MenuBeansContainer beansContainer) {
    var language = menus.addToContainer("menu.settings", MenuBuilder.MENU)
                        .addChildReturnChild(menus.buildChild("menu.language", MenuBuilder.MENU));

    var languageEn = (JRadioButtonMenuItem) menus.buildChild("menu.language.en", MenuBuilder.MENU_ITEM_RADIO);
    var languageFr = (JRadioButtonMenuItem) menus.buildChild("menu.language.fr", MenuBuilder.MENU_ITEM_RADIO);
    languageEn.addItemListener(beansContainer.getLanguageListener());
    languageFr.addItemListener(beansContainer.getLanguageListener());
    languageEn.setSelected(beansContainer.getLocale().getLanguage().equals("en"));
    languageFr.setSelected(beansContainer.getLocale().getLanguage().equals("fr"));
    var group = new ButtonGroup();
    group.add(languageEn);
    group.add(languageFr);

    language.addChildReturnParent(languageEn).addChildReturnParent(languageFr);
  }

  void buildLibraryMenu(MenuContainer menus, MenuBeansContainer beansContainer) {
    var library = menus.addToContainer("menu.library", MenuBuilder.MENU);
    var aboutItem = (JMenuItem) library
      .addChildReturnChild(menus.buildChild("menu.item.about", MenuBuilder.MENU_ITEM))
      .getParentComponent();
    library.addChildReturnParent(menus.buildChild("", MenuBuilder.SEPARATOR));
    var quitItem = (JMenuItem) library
      .addChildReturnChild(menus.buildChild("menu.item.quit", MenuBuilder.MENU_ITEM)).getParentComponent();

    quitItem.addActionListener(beansContainer.getQuitListener());
    quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
    aboutItem.addActionListener(beansContainer.getAboutListener());
  }

  JMenuBar buildMenuBar(MenuContainer menus) {
    var menuBar = new JMenuBar();
    menus.getAllParentsBuilded().forEach(menuBar::add);
    return menuBar;
  }
}