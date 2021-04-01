package org.system.library.frames;

import lombok.RequiredArgsConstructor;
import org.system.library.configuration.messages.MessageLibrary;

import javax.swing.*;
import java.awt.*;

@RequiredArgsConstructor
@org.springframework.stereotype.Component
public class Dialogs {

  private final MessageLibrary message;

  public void dialogErrorMessage(Component parent, String propertyMessage) {
    JOptionPane.showConfirmDialog(parent,
                                  message.getMessage(propertyMessage),
                                  message.getMessage("dialog.error.title"),
                                  JOptionPane.CLOSED_OPTION,
                                  JOptionPane.ERROR_MESSAGE);
  }
}