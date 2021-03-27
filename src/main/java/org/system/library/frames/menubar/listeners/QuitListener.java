package org.system.library.frames.menubar.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;

import javax.swing.FocusManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class QuitListener implements ActionListener {

  private final MessageLibrary message;

  @Override
  public void actionPerformed(ActionEvent event) {
    var response = JOptionPane.showConfirmDialog(null,
                                                 message.getMessage("dialog.quit"),
                                                 message.getMessage("dialog.info.title"),
                                                 JOptionPane.YES_OPTION,
                                                 JOptionPane.WARNING_MESSAGE);

    if (response == 0) {
      findParentsFrames().forEach(Window::dispose);
      System.exit(0);
    }
  }

  private Set<Frame> findParentsFrames() {
    var frame = FocusManager.getCurrentManager().getActiveWindow();
    return Arrays.stream(JFrame.getFrames())
                 .filter(Window::isVisible)
                 .collect(Collectors.toUnmodifiableSet());
  }
}