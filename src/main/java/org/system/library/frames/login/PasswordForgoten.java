package org.system.library.frames.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.IFrame;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.AbstractButtonBuilder;
import org.system.library.frames.component.builder.LabelBuilder;
import org.system.library.frames.component.builder.PanelBuilder;
import org.system.library.frames.component.builder.TextFieldBuilder;
import org.system.library.frames.component.container.ComponentContainer;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.List;

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

  private void buildHeaderPanel() {
    var panelHeader = panelContainer.addToContainer("panelHeader",
                                                    HEADER_DIMENSION,
                                                    Position.ONE,
                                                    PanelBuilder.GRID_BAG_LAYOUT);
    setComponentsHeaderPanel(panelHeader);
  }

  private void setComponentsHeaderPanel(JPanel panelHeader) {
    var labelHeader = labelContainer.addToContainer("passwordframe.panel.title",
                                                    null,
                                                    Position.ONE,
                                                    LabelBuilder.LABEL);
    labelHeader.setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
    addComponentsByPosition(panelHeader, List.of(labelContainer.getAllAndClear(panelHeader.getName())));
  }

  private void buildEmailPanel() {
    var emailPanel = panelContainer.addToContainer("emailPanel",
                                                   FIELD_AND_BUTTON_DIMENSION,
                                                   Position.TWO,
                                                   PanelBuilder.GROUP_LAYOUT);
    setComponentsEmailPanel(emailPanel);
  }

  private void setComponentsEmailPanel(JPanel emailPanel) {
    var emailLabel = labelContainer.addToContainer("application.email",
                                                   null,
                                                   Position.ONE,
                                                   LabelBuilder.LABEL);
    var layout = (GroupLayout) emailPanel.getLayout();
    var emailText = textComponentContainer.addToContainer("emailText",
                                                          TEXT_FIELD_DIMENSION,
                                                           Position.TWO,
                                                           TextFieldBuilder.TEXT_FIELD);
    var buttonSendCode = buttonContainer.addToContainer("passwordframe.button.send",
                                                        BUTTON_DIMENSION,
                                                        Position.THREE,
                                                        AbstractButtonBuilder.BUTTON);
  }

  private void buildPasswordPanel() {
  }

  private void buildFrame() {
  }
}