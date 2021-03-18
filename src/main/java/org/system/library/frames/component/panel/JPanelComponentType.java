package org.system.library.frames.component.panel;

import org.system.library.frames.component.IJComponentType;

import javax.swing.*;

public enum JPanelComponentType implements IJComponentType {

  SPRING_LAYOUT {
    @Override
    public JPanel buildJPanelComponent(String name) {
//      var panel = new
      return null;
    }
  },
  BOX_LAYOUT {
    @Override
    public JPanel buildJPanelComponent(String name) {
      return null;
    }
  },
  FLOW_LAYOUT {
    @Override
    public JPanel buildJPanelComponent(String name) {
      return null;
    }
  };

  abstract public JPanel buildJPanelComponent(String name);

  public static JPanel buildByType(String name, JPanelComponentType type) {
    return type.buildJPanelComponent(name);
  }
}