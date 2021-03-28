package org.system.library.frames.component.builder;

import org.system.library.frames.IFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;

public enum AbstractButtonBuilder implements ComponentBuilder<AbstractButton> {

  BUTTON {
    @Override
    public JButton buildComponent(String text, Dimension dimension) {
      var button = new JButton(text);
      button.setMaximumSize(dimension);
      return button;
    }
  },
  BUTTON_HYPER_LINK {
    @Override
    public JButton buildComponent(String text, Dimension dimension) {
      var button = new JButton(text);
      setHyperLinkFormat(button);
      return button;
    }
  };

  void setHyperLinkFormat(JButton button) {
    button.setOpaque(false);
    button.setBorderPainted(false);
    button.setHorizontalAlignment(SwingConstants.CENTER);
    button.setForeground(IFrame.LINK_UNVISITED);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.addMouseListener(new DefaultMouseHyperLinkListener());
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