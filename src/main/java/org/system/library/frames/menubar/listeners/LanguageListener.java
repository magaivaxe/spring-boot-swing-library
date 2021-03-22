package org.system.library.frames.menubar.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

@RequiredArgsConstructor
@Component
public class LanguageListener implements ItemListener {

  private final MessageLibrary messageLibrary;

  @Override
  public void itemStateChanged(ItemEvent e) {

  }
}