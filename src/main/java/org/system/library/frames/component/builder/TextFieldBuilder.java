package org.system.library.frames.component.builder;


import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

@RequiredArgsConstructor
public enum TextFieldBuilder implements ComponentBuilder<JComponent> {
  TEXT_FIELD {
    @Override
    public JTextField buildComponent(String name, Dimension dimension) {
      var textField = new JTextField();
      textField.setName(name);
      textField.setMaximumSize(dimension);
      return textField;
    }
  },
  PASSWORD_FIELD {
    @Override
    public JPasswordField buildComponent(String name, Dimension dimension) {
      var passwordField = new JPasswordField();
      passwordField.setName(name);
      passwordField.setMaximumSize(dimension);
      return passwordField;
    }
  },
  TEXT_AREA {
    @Override
    public JTextArea buildComponent(String name, Dimension dimension) {
      return null;
    }
  },
  TEXT_PANE {
    @Override
    public JTextPane buildComponent(String name, Dimension dimension) {
      return null;
    }
  },
  EDITOR_PANE {
    @Override
    public JEditorPane buildComponent(String name, Dimension dimension) {
      return null;
    }
  },
  FORMATED_TEXT_FIELD {
    @Override
    public JFormattedTextField buildComponent(String name, Dimension dimension) {
      return null;
    }
  }

}