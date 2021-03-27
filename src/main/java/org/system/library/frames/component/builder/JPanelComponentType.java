package org.system.library.frames.component.builder;

import org.system.library.frames.utils.SpringLayoutUtils;

import javax.swing.*;
import java.awt.*;

/**
 * {@link JPanelComponentType#SPRING_LAYOUT} doesn't set dimension. It is set into {@link SpringLayoutUtils} at methods
 * type makeGrids.
 */
public enum JPanelComponentType implements IJComponentType<JPanel> {

  SPRING_LAYOUT {
    @Override
    public JPanel buildComponent(String name, Dimension dimension) {
      var panel = new JPanel();
      panel.setName(name);
      panel.setLayout(new SpringLayout());
      return panel;
    }
  },
  BOX_LAYOUT {
    @Override
    public JPanel buildComponent(String name, Dimension dimension) {
      var panel = buildPanel(name, dimension);
      panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
      return panel;
    }
  },
  FLOW_LAYOUT {
    @Override
    public JPanel buildComponent(String name, Dimension dimension) {
      var panel = buildPanel(name, dimension);
      panel.setLayout(new FlowLayout());
      return panel;
    }
  },
  GRID_BAG_LAYOUT {
    @Override
    public JPanel buildComponent(String name, Dimension dimension) {
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

}