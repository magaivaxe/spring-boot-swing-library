package org.system.library.frames.component.panel.label;

import org.system.library.frames.component.IJComponentType;

import javax.swing.*;

public enum JLabelComponentType implements IJComponentType {
  LABEL {
    @Override
    public JLabel buildJLabelComponent(String text) {
      return new JLabel(text, JLabel.TRAILING);
    }
  };

  abstract public JLabel buildJLabelComponent(String text);

  public static JLabel buildByType(String text, JLabelComponentType type) {
    return type.buildJLabelComponent(text);
  }
}