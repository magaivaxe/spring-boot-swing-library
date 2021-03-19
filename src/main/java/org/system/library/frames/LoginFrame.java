package org.system.library.frames;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.component.ComponentPosition;
import org.system.library.frames.component.panel.JPanelComponentType;
import org.system.library.frames.component.panel.JPanelGeneralContainer;
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
import java.awt.*;

@RequiredArgsConstructor
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class LoginFrame extends JFrame implements IJFrame {

  private final MessageLibrary message;
  private final JMenuBarFactory menuBarFactory;
  private final JTextComponentContainer textComponentContainer;
  private final JLabelContainer labelContainer;
  private final JButtonComponentContainer buttonContainer;
  private final JPanelGeneralContainer panelContainer;

  @PostConstruct
  private void build() {
    buildHeaderPanel();
    buildFooterPanel();
    buildBodyPanel();
    buildFrame();
  }

  private void buildHeaderPanel() {
    setLabelsHeaderPanel();
    buildHeaderLayoutPanel();
  }

  private void setLabelsHeaderPanel() {
    labelContainer.addToContainer("loginframe.panel.title", null, JLabelComponentType.LABEL);
  }

  private void buildHeaderLayoutPanel() {
    panelContainer.addToContainer("panelHeader", null, JPanelComponentType.FLOW_LAYOUT);
    var panelHeader = panelContainer.getComponentFromContainer("panelHeader");
    panelHeader.add(labelContainer.getComponentFromContainer("loginframe.panel.title"));
    panelHeader.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    panelHeader.setBorder(new EmptyBorder(DEFAULT_PADDING, 0, 0, 0));
  }

  private void buildFooterPanel() {
    setButtonsFooter();
    buildFooterLayoutPanel();
  }

  private void setButtonsFooter() {
    buttonContainer.addToContainer("loginframe.button.connect", BUTTON_DIMENSION, JButtonComponentType.BUTTON);
  }

  private void buildFooterLayoutPanel() {
    panelContainer.addToContainer("panelFooter", null, JPanelComponentType.FLOW_LAYOUT);
    var panelFooter = panelContainer.getComponentFromContainer("panelFooter");
    panelFooter.add(buttonContainer.getComponentFromContainer("loginframe.button.connect"));
    panelFooter.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
  }

  private void buildBodyPanel() {
    panelContainer.addToContainer("panelBody", null, JPanelComponentType.SPRING_LAYOUT);
    buildTextFieldsBodyPanel();
    buildLabelsBodyPanel();
    buildBodyLayoutPanel();
  }

  private void buildBodyLayoutPanel() {
    var panelBody = panelContainer.getComponentFromContainer("panelBody");
    panelBody.addComponentsByPanePosition(
      textComponentContainer.getJComponentsIndexed(),
      labelContainer.getJComponentsIndexed(),
      buttonContainer.getJComponentsIndexed()
    );
    SpringUtilities.makeCompactGrid(panelBody, 2, 2, 0, 0, DEFAULT_PADDING, DEFAULT_PADDING);
  }

  private void buildLabelsBodyPanel() {
    labelContainer.addToIndexedContainer("application.user", null, ComponentPosition.ONE, JLabelComponentType.LABEL);
    labelContainer.addToIndexedContainer("application.password", null, ComponentPosition.THREE, JLabelComponentType.LABEL);
    labelContainer.setLabelFor(textComponentContainer.getJComponentsNotIndexedFromIndexed());
  }

  private void buildTextFieldsBodyPanel() {
    textComponentContainer.addToIndexedContainer("user", LOGIN_TEXT_FIELD_DIMENSION, ComponentPosition.TWO, JTextComponentType.TEXT_FIELD);
    textComponentContainer.addToIndexedContainer("password", LOGIN_TEXT_FIELD_DIMENSION, ComponentPosition.FOUR, JTextComponentType.PASSWORD_FIELD);
  }

  private void buildFrame() {
    setTitle(message.getMessage("loginframe.title"));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setJMenuBar(menuBarFactory.buildMenuBar(JMenuBarTypeBuilder.LOGIN_FRAME));
    setLayoutFrame();
  }

  private void setLayoutFrame() {
    setDefaultLookAndFeelDecorated(true);
    setResizable(false);
    setLocationRelativeTo(CENTER_POSITION);
    setSize(SMALL_FRAME_DIMENSION);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

    add(panelContainer.getComponentFromContainer("panelHeader"));
    add(panelContainer.getComponentFromContainer("panelBody"));
    add(panelContainer.getComponentFromContainer("panelFooter"));
  }


  public void setListeners() {
  }
}