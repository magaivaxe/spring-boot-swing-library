package org.system.library.frames.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.IFrame;
import org.system.library.frames.component.container.ComponentContainer;

import javax.annotation.PostConstruct;
import javax.swing.*;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PasswordForgoten extends JFrame implements IFrame {

  private final MessageLibrary message;
  private final ComponentContainer<JComponent> textComponentContainer;
  private final ComponentContainer<AbstractButton> buttonContainer;
  private final ComponentContainer<JPanel> panelContainer;
  private final ComponentContainer<JLabel> labelContainer;

  @PostConstruct
  private void build() {
    buildHeaderPanel();
    buildEmailPanel();
    buildPasswordPanel();
    buildFrame();
  }

  private void buildFrame() {
  }

  private void buildPasswordPanel() {
  }

  private void buildEmailPanel() {
  }

  private void buildHeaderPanel() {
  }
}