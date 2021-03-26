package org.system.library.frames.component.panel.button;

import org.system.library.frames.IJFrame;
import org.system.library.frames.component.IJComponentType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

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
    button.addMouseListener(new DefaultMouseHyperLinkListener());
  }

  abstract public AbstractButton buildJButtonComponent(String text, Dimension dimension);

  public static AbstractButton buildByType(String text, Dimension dimension, JButtonComponentType type) {
    return type.buildJButtonComponent(text, dimension);
  }
}

class DefaultMouseHyperLinkListener extends MouseAdapter {

  private Font fontOriginal;

  @Override
  public void mouseEntered(MouseEvent event) {
    fontOriginal = event.getComponent().getFont();
    Map attributes = fontOriginal.getAttributes();
    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    event.getComponent().setFont(fontOriginal.deriveFont(attributes));
  }

  @Override
  public void mouseExited(MouseEvent event) {
    event.getComponent().setFont(fontOriginal);
  }
}