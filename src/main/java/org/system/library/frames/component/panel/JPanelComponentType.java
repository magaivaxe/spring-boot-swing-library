package org.system.library.frames.component.panel;

import org.system.library.frames.component.IJComponentType;

import javax.swing.*;
import java.awt.*;

public enum JPanelComponentType implements IJComponentType {

  SPRING_LAYOUT {
    @Override
    public JPanelGeneral buildJPanelComponent(String name) {
      var panel = new JPanelGeneral();
      panel.setLayout(new SpringLayout());
      panel.setName(name);
      return panel;
    }
  },
  BOX_LAYOUT {
    @Override
    public JPanelGeneral buildJPanelComponent(String name) {
      return null;
    }
  },
  FLOW_LAYOUT {
    @Override
    public JPanelGeneral buildJPanelComponent(String name) {
      var panel = new JPanelGeneral();
      panel.setLayout(new FlowLayout());
      panel.setName(name);
      return panel;
    }
  };

  abstract public JPanelGeneral buildJPanelComponent(String name);

  public static JPanelGeneral buildByType(String name, JPanelComponentType type) {
    return type.buildJPanelComponent(name);
  }
}