package org.system.library.frames.component.panel;

import org.system.library.frames.component.IJComponentType;

import org.system.library.utils.SpringUtilities;
import javax.swing.*;
import java.awt.*;

/**
 * {@link JPanelComponentType#SPRING_LAYOUT} doesn't set dimension.
 * It is set into {@link SpringUtilities} at methods type makeGrids.
 */
public enum JPanelComponentType implements IJComponentType {

  SPRING_LAYOUT {
    @Override
    public JPanel buildJPanelComponent(String name, Dimension dimension) {
      var panel = new JPanel();
      panel.setName(name);
      panel.setLayout(new SpringLayout());
      return panel;
    }
  },
  BOX_LAYOUT {
    @Override
    public JPanel buildJPanelComponent(String name, Dimension dimension) {
      return null;
    }
  },
  FLOW_LAYOUT {
    @Override
    public JPanel buildJPanelComponent(String name, Dimension dimension) {
      var panel = buildPanel(name, dimension);
      panel.setLayout(new FlowLayout());
      return panel;
    }
  },
  GRID_BAG_LAYOUT {
    @Override
    public JPanel buildJPanelComponent(String name, Dimension dimension) {
      var panel = buildPanel(name, dimension);
      panel.setLayout(new GridBagLayout());
      return panel;

    }
  };

  JPanel buildPanel(String name, Dimension dimension) {
    var panel = new JPanel();
    panel.setName(name);
    panel.setSize(dimension);
    return panel;
  }

  abstract public JPanel buildJPanelComponent(String name, Dimension dimension);

  public static JPanel buildByType(String name, JPanelComponentType type, Dimension dimension) {
    return type.buildJPanelComponent(name, dimension);
  }
}