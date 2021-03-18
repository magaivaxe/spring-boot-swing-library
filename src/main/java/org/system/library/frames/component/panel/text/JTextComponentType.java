package org.system.library.frames.component.panel.text;


import lombok.RequiredArgsConstructor;
import org.system.library.frames.component.IJComponentType;

import javax.swing.*;
import java.awt.*;

@RequiredArgsConstructor
public enum JTextComponentType implements IJComponentType {
  TEXT_FIELD {
    @Override
    public JTextField buildJTextComponent(String name, Dimension dimension) {
      var textField = new JTextField();
      textField.setName(name);
      textField.setMaximumSize(dimension);
      return textField;
    }
  },
  PASSWORD_FIELD {
    @Override
    public JPasswordField buildJTextComponent(String name, Dimension dimension) {
      var passwordField = new JPasswordField();
      passwordField.setName(name);
      passwordField.setMaximumSize(dimension);
      return passwordField;
    }
  },
  TEXT_AREA {
    @Override
    public JTextArea buildJTextComponent(String name, Dimension dimension) {
      return null;
    }
  },
  TEXT_PANE {
    @Override
    public JTextPane buildJTextComponent(String name, Dimension dimension) {
      return null;
    }
  },
  EDITOR_PANE {
    @Override
    public JEditorPane buildJTextComponent(String name, Dimension dimension) {
      return null;
    }
  },
  FORMATED_TEXT_FIELD {
    @Override
    public JFormattedTextField buildJTextComponent(String name, Dimension dimension) {
      return null;
    }
  };

  abstract public JComponent buildJTextComponent(String name, Dimension dimension);

  public static JComponent buildByType(String name, Dimension dimension, JTextComponentType type) {
    return type.buildJTextComponent(name, dimension);
  }
}