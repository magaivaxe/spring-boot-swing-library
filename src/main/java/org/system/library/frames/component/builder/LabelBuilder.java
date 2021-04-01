package org.system.library.frames.component.builder;

import org.system.library.frames.IFrame;

import javax.swing.*;
import java.awt.*;

public enum LabelBuilder implements ComponentBuilder<JLabel> {
  LABEL {
    @Override
    public JLabel buildComponent(String text, Dimension dimension) {
      var textFiltered = text.startsWith(IFrame.VOID_LABEL) ? "" : text;
      final var label = new JLabel(textFiltered, JLabel.TRAILING);
      label.setName(textFiltered);
      return label;
    }
  }

}