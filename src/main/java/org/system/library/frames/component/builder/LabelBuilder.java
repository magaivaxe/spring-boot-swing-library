package org.system.library.frames.component.builder;

import javax.swing.*;
import java.awt.*;

public enum LabelBuilder implements ComponentBuilder<JLabel> {
  LABEL {
    @Override
    public JLabel buildComponent(String text, Dimension dimension) {
      return new JLabel(text, JLabel.TRAILING);
    }
  }

}