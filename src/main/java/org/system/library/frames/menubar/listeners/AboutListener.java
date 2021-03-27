package org.system.library.frames.menubar.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
@Component
class AboutListener implements ActionListener {

  private final MessageLibrary messageLibrary;

  @Override
  public void actionPerformed(ActionEvent e) {
    JOptionPane.showMessageDialog(null,
                                  messageLibrary.getMessage("dialog.about.text"),
                                  messageLibrary.getMessage("dialog.about.title"),
                                  JOptionPane.INFORMATION_MESSAGE);
  }
}