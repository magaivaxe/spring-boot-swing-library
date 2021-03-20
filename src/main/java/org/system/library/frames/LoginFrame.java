package org.system.library.frames;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.panel.JPanelComponentType;
import org.system.library.frames.component.panel.JPanelContainer;
import org.system.library.frames.component.panel.button.JButtonComponentContainer;
import org.system.library.frames.component.panel.button.JButtonComponentType;
import org.system.library.frames.component.panel.label.JLabelComponentType;
import org.system.library.frames.component.panel.label.JLabelContainer;
import org.system.library.frames.component.panel.text.JTextComponentContainer;
import org.system.library.frames.component.panel.text.JTextComponentType;
import org.system.library.frames.menubar.factory.JMenuBarFactory;
import org.system.library.frames.menubar.factory.JMenuBarTypeBuilder;
import org.system.library.utils.SpringUtilities;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

@RequiredArgsConstructor
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class LoginFrame extends JFrame implements IJFrame {

  private final MessageLibrary message;
  private final JMenuBarFactory menuBarFactory;
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
    labelContainer.addToContainer("loginframe.panel.title", null, JLabelComponentType.LABEL);
    labelContainer.getComponentFromContainer("loginframe.panel.title")
      .setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
    setComponentsHeaderPanel();
  }

  private void setComponentsHeaderPanel() {
    panelContainer.addToIndexedContainer("panelHeader", HEADER_DIMENSION, Position.ONE, JPanelComponentType.FLOW_LAYOUT);
    var panelHeader = panelContainer.getComponentFromIndexedContainer("panelHeader");
    panelHeader.add(labelContainer.getComponentFromContainer("loginframe.panel.title"));
  }

  private void buildLinkPanel() {
    panelContainer.addToIndexedContainer("panelLink", HEADER_DIMENSION, Position.THREE, JPanelComponentType.FLOW_LAYOUT);
    setComponentsLinkPanel();
  }

  private void setComponentsLinkPanel() {
    buttonContainer.addToContainer("loginframe.passwordforgoten", null, JButtonComponentType.BUTTON_HYPER_LINK);
    var panelLink = panelContainer.getComponentFromIndexedContainer("panelLink");
    panelLink.add(buttonContainer.getComponentFromContainer("loginframe.passwordforgoten"));
  }

  private void buildFooterPanel() {
    buttonContainer.addToContainer("loginframe.button.connect", BUTTON_DIMENSION, JButtonComponentType.BUTTON);
    setComponentsFooterPanel();
  }

  private void setComponentsFooterPanel() {
    panelContainer.addToIndexedContainer("panelFooter", HEADER_DIMENSION, Position.FOUR, JPanelComponentType.FLOW_LAYOUT);
    var panelFooter = panelContainer.getComponentFromIndexedContainer("panelFooter");
    panelFooter.add(buttonContainer.getComponentFromContainer("loginframe.button.connect"));
  }

  private void buildBodyPanel() {
    panelContainer.addToIndexedContainer("panelBody", null, Position.TWO, JPanelComponentType.SPRING_LAYOUT);
    buildTextFieldsBodyPanel();
    buildLabelsBodyPanel();
    buildBodyLayoutPanel();
  }

  private void buildBodyLayoutPanel() {
    var panelBody = panelContainer.getComponentFromIndexedContainer("panelBody");
    addComponentsByPosition(panelBody,
      List.of(
        textComponentContainer.getJComponentsIndexed(),
        labelContainer.getJComponentsIndexed(),
        buttonContainer.getJComponentsIndexed()
      ));
    SpringUtilities.makeCompactGrid(panelBody, 2, 2, 0, 0, DEFAULT_PADDING,
      DEFAULT_PADDING);
  }

  private void buildLabelsBodyPanel() {
    labelContainer.addToIndexedContainer("application.user", null, Position.ONE,
      JLabelComponentType.LABEL);
    labelContainer.addToIndexedContainer("application.password", null, Position.THREE,
      JLabelComponentType.LABEL);
    labelContainer.setLabelFor(textComponentContainer.getJComponentsNotIndexedFromIndexed());
  }

  private void buildTextFieldsBodyPanel() {
    textComponentContainer.addToIndexedContainer("user", LOGIN_TEXT_FIELD_DIMENSION, Position.TWO,
      JTextComponentType.TEXT_FIELD);
    textComponentContainer.addToIndexedContainer("password", LOGIN_TEXT_FIELD_DIMENSION, Position.FOUR,
      JTextComponentType.PASSWORD_FIELD);
  }

  private void buildFrame() {
    setTitle(message.getMessage("loginframe.title"));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setJMenuBar(menuBarFactory.buildMenuBar(JMenuBarTypeBuilder.LOGIN_FRAME));
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