package org.system.library.frames.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.IJFrame;
import org.system.library.frames.component.panel.JPanelContainer;
import org.system.library.frames.component.panel.button.JButtonComponentContainer;
import org.system.library.frames.component.panel.label.JLabelContainer;
import org.system.library.frames.component.panel.text.JTextComponentContainer;

import javax.annotation.PostConstruct;
import javax.swing.*;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PasswordForgoten extends JFrame implements IJFrame {

  private final MessageLibrary message;
  private final JTextComponentContainer textComponentContainer;
  private final JLabelContainer labelContainer;
  private final JButtonComponentContainer buttonContainer;
  private final JPanelContainer panelContainer;

  @PostConstruct
  private void build() {
  }
}