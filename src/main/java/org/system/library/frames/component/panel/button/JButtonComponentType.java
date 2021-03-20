package org.system.library.frames.component.panel.button;

import org.system.library.frames.IJFrame;
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
  },
  BUTTON_HYPER_LINK {
    @Override
    public JButton buildJButtonComponent(String text, Dimension dimension) {
      var button = new JButton(text);
      setHyperLinkFormat(button);
      return button;
    }
  };

  void setHyperLinkFormat(JButton button) {
    button.setOpaque(false);
    button.setBorderPainted(false);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setForeground(IJFrame.LINK_UNVISITED);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
  }

  abstract public AbstractButton buildJButtonComponent(String text, Dimension dimension);

  public static AbstractButton buildByType(String text, Dimension dimension, JButtonComponentType type) {
    return type.buildJButtonComponent(text, dimension);
  }
}