package org.system.library.frames.menubar;

import javax.swing.*;

public enum JMenuComponentType {
  MENU {
    @Override
    public JMenu build(String text) {
      return new JMenu(text);
    }
  },
  MENU_ITEM {
    @Override
    public JMenuItem build(String text) {
      return new JMenuItem(text);
    }
  },
  MENU_ITEM_RADIO {
    @Override
    public JRadioButtonMenuItem build(String text) {
      return new JRadioButtonMenuItem(text);
    }
  },
  MENU_ITEM_CHECKBOX {
    @Override
    public JCheckBoxMenuItem build(String text) {
      return new JCheckBoxMenuItem(text);
    }
  },
  SEPARATOR {
    @Override
    public JSeparator build(String text) {
      return new JSeparator();
    }
  };

  abstract public JComponent build(String text);

  public static JComponent buildByType(String text, JMenuComponentType type) {
    return type.build(text);
  }
}