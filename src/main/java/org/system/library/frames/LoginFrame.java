package org.system.library.frames;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.menubar.factory.JMenuBarFactory;
import org.system.library.frames.menubar.factory.JMenuBarTypeBuilder;
import org.system.library.frames.component.panel.JPanelGeneral;
import org.system.library.frames.component.ComponentPosition;
import org.system.library.frames.component.panel.button.JButtonComponentType;
import org.system.library.frames.component.panel.button.JButtonComponentContainer;
import org.system.library.frames.component.panel.label.JLabelComponentType;
import org.system.library.frames.component.panel.label.JLabelContainer;
import org.system.library.frames.component.panel.text.JTextComponentContainer;
import org.system.library.frames.component.panel.text.JTextComponentType;
import org.system.library.utils.SpringUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class LoginFrame extends JFrame implements IJFrame {

  private final MessageLibrary message;
  private final JMenuBarFactory menuBarFactory;
  private final JTextComponentContainer textComponentContainer;
  private final JLabelContainer labelContainer;
  private final JButtonComponentContainer buttonContainer;
  private final JPanelGeneral panelBody;
  private final JPanelGeneral panelHeader;
  private final JPanelGeneral panelFooter;

  public LoginFrame(MessageLibrary message, JMenuBarFactory menuBarFactory, JTextComponentContainer textFieldContainer,
                    JLabelContainer labelContainer, JButtonComponentContainer buttonContainer, JPanelGeneral panelBody,
                    JPanelGeneral panelHeader, JPanelGeneral panelFooter) {
    this.message = message;
    this.menuBarFactory = menuBarFactory;
    this.textComponentContainer = textFieldContainer;
    this.labelContainer = labelContainer;
    this.buttonContainer = buttonContainer;
    this.panelBody = panelBody;
    this.panelHeader = panelHeader;
    this.panelFooter = panelFooter;
    build();
  }

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
    panelHeader.setLayout(new FlowLayout());
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
    panelFooter.setLayout(new FlowLayout());
    panelFooter.add(buttonContainer.getComponentFromContainer("loginframe.button.connect"));
    panelFooter.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
  }

  private void buildBodyPanel() {
    panelBody.setLayout(new SpringLayout());
    setTextFieldsBodyPanel();
    setLabelsBodyPanel();
    buildBodyLayoutPanel();
  }

  private void buildBodyLayoutPanel() {
    panelBody.addComponentsByPanePosition(
      textComponentContainer.getJComponentsIndexed(),
      labelContainer.getJComponentsIndexed(),
      buttonContainer.getJComponentsIndexed()
    );
    SpringUtilities.makeCompactGrid(panelBody, 2, 2, 0, 0, DEFAULT_PADDING, DEFAULT_PADDING);
  }

  private void setLabelsBodyPanel() {
    labelContainer.addToIndexedContainer("application.user", null, ComponentPosition.ONE, JLabelComponentType.LABEL);
    labelContainer.addToIndexedContainer("application.password", null, ComponentPosition.THREE, JLabelComponentType.LABEL);
    labelContainer.setLabelFor(textComponentContainer.getJComponentsNotIndexedFromIndexed());
  }

  private void setTextFieldsBodyPanel() {
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

    add(panelHeader);
    add(panelBody);
    add(panelFooter);
  }


  public void setListeners() {
  }
}