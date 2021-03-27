package org.system.library.frames.login;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.IJFrame;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.panel.JPanelComponentType;
import org.system.library.frames.component.panel.JPanelContainer;
import org.system.library.frames.component.panel.button.JButtonComponentContainer;
import org.system.library.frames.component.panel.button.JButtonComponentType;
import org.system.library.frames.component.panel.label.JLabelComponentType;
import org.system.library.frames.component.panel.label.JLabelContainer;
import org.system.library.frames.component.panel.text.JTextComponentContainer;
import org.system.library.frames.component.panel.text.JTextComponentType;
import org.system.library.frames.menubar.factory.JMenuBarBuilder;
import org.system.library.frames.menubar.factory.JMenuBarType;
import org.system.library.frames.utils.SpringLayoutUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.List;

@RequiredArgsConstructor
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class LoginFrame extends JFrame implements IJFrame {

  private final MessageLibrary message;
  private final JMenuBarBuilder menuBarBuilder;
  private final JTextComponentContainer textComponentContainer;
  private final JLabelContainer labelContainer;
  private final JButtonComponentContainer buttonContainer;
  private final JPanelContainer panelContainer;

  @PostConstruct
  private void build() {
    buildHeaderPanel();
    buildBodyPanel();
    buildLinkPanel();
    buildFooterPanel();
    buildFrame();
  }

  private void buildHeaderPanel() {
    var panelHeader = panelContainer.addToContainer("panelHeader", HEADER_DIMENSION, Position.ONE, JPanelComponentType.GRID_BAG_LAYOUT);
    setComponentsHeaderPanel(panelHeader);
  }

  private void setComponentsHeaderPanel(JPanel panelHeader) {
    var labelHeader = labelContainer.addToContainer("loginframe.panel.title", null, Position.ONE, JLabelComponentType.LABEL);
    labelHeader.setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
    addComponentsByPosition(panelHeader, List.of(labelContainer.getJComponentsIndexed()));
  }

  private void buildLinkPanel() {
    var panelLink = panelContainer.addToContainer("panelLink", LINK_DIMENSION, Position.THREE, JPanelComponentType.BOX_LAYOUT);
    setComponentsLinkPanel(panelLink);
  }

  private void setComponentsLinkPanel(JPanel panelLink) {
    var passwordForgoten = buttonContainer.addToContainer("loginframe.passwordforgoten", LINK_DIMENSION, Position.ONE, JButtonComponentType.BUTTON_HYPER_LINK);
    var createAccount = buttonContainer.addToContainer("loginframe.create.account", LINK_DIMENSION, Position.TWO, JButtonComponentType.BUTTON_HYPER_LINK);
    passwordForgoten.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    createAccount.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    addComponentsByPosition(panelLink, List.of(buttonContainer.getJComponentsIndexed()));
  }

  private void buildFooterPanel() {
    var panelFooter = panelContainer.addToContainer("panelFooter", HEADER_DIMENSION, Position.FOUR, JPanelComponentType.GRID_BAG_LAYOUT);
    setComponentsFooterPanel(panelFooter);
  }

  private void setComponentsFooterPanel(JPanel panelFooter) {
    buttonContainer.addToContainer("loginframe.button.connect", BUTTON_DIMENSION, Position.ONE, JButtonComponentType.BUTTON);
    addComponentsByPosition(panelFooter, List.of(buttonContainer.getJComponentsIndexed()));
  }

  private void buildBodyPanel() {
    var panelBody = panelContainer.addToContainer("panelBody", null, Position.TWO, JPanelComponentType.SPRING_LAYOUT);
    buildTextFieldsBodyPanel();
    buildLabelsBodyPanel();
    buildBodyLayoutPanel(panelBody);
  }

  private void buildBodyLayoutPanel(JPanel panelBody) {
    addComponentsByPosition(panelBody,
      List.of(
        textComponentContainer.getJComponentsIndexed(),
        labelContainer.getJComponentsIndexed()
      ));
    SpringLayoutUtils.makeCompactGrid(panelBody, 2, 2, 0, 0, DEFAULT_PADDING,
      DEFAULT_PADDING);
  }

  private void buildLabelsBodyPanel() {
    labelContainer.addToContainer("application.user", null, Position.ONE,
      JLabelComponentType.LABEL);
    labelContainer.addToContainer("application.password", null, Position.THREE,
      JLabelComponentType.LABEL);
    labelContainer.setLabelFor(textComponentContainer.getJComponentsFromContainer()); //TODO: see if setLabel works, keys from text and labels components
  }

  private void buildTextFieldsBodyPanel() {
    textComponentContainer.addToContainer("application.user", LOGIN_TEXT_FIELD_DIMENSION, Position.TWO,
      JTextComponentType.TEXT_FIELD);
    textComponentContainer.addToContainer("application.password", LOGIN_TEXT_FIELD_DIMENSION, Position.FOUR,
      JTextComponentType.PASSWORD_FIELD);
  }

  private void buildFrame() {
    setTitle(message.getMessage("loginframe.title"));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setJMenuBar(menuBarBuilder.buildMenuBarByType(JMenuBarType.LOGIN_FRAME));
    setLayoutFrame();
  }

  private void setLayoutFrame() {
    var mainPane = getContentPane();
    setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
    addComponentsByPosition(mainPane, List.of(panelContainer.getJComponentsIndexed()));

    setSize(setDimensionBySizeComponents(mainPane));
    setDefaultLookAndFeelDecorated(true);
    setResizable(false);
    setLocationRelativeTo(CENTER_POSITION);
  }


  public void setListeners() {
  }
}