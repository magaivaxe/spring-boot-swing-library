package org.system.library.frames.component.panel.button;

import org.system.library.frames.component.IJComponentType;

import javax.swing.*;
import java.awt.*;

public enum JButtonComponentType implements IJComponentType {

  BUTTON {
    @Override
    public JButton buildJButtonComponent(String text, Dimension dimension) {
      var button = new JButton(text);
      button.setMaximumSize(dimension);
      return button;
    }
  };

  abstract public AbstractButton buildJButtonComponent(String text, Dimension dimension);

  public static AbstractButton buildByType(String text, Dimension dimension, JButtonComponentType type) {
    return type.buildJButtonComponent(text, dimension);
  }
}