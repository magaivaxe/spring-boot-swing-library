package org.system.library.frames.component.panel.text;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.component.ComponentPosition;
import org.system.library.frames.component.IJComponentContainer;
import org.system.library.frames.component.IJComponentType;
import org.system.library.frames.component.indexed.IJComponentIndexed;
import org.system.library.frames.component.indexed.JComponentIndexed;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JTextComponentContainer implements IJComponentContainer {

  private final MessageLibrary messageLibrary;
  private final Map<String, JComponentIndexed> textComponentsIndexed = new HashMap<>();
  private final Map<String, JTextComponent> textComponents = new HashMap<>();

  @Override
  public Map<String, JComponent> getJComponentsNotIndexedFromIndexed() {
    return textComponentsIndexed.entrySet()
      .stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getJComponent()));
  }

  @Override
  public List<IJComponentIndexed> getJComponentsIndexed() {
    return List.copyOf(textComponentsIndexed.values());
  }

  @Override
  public void addToIndexedContainer(String name, Dimension dimension, ComponentPosition position, IJComponentType type) {
    var textComponent = JTextComponentType.buildByType(name, dimension, (JTextComponentType) type);
    var textFieldIndexed = JComponentIndexed.builder()
      .component(textComponent)
      .position(position).build();
    textComponentsIndexed.put(name, textFieldIndexed);
  }

  @Override
  public void addToContainer(String name, Dimension dimension, IJComponentType type) {
    var textComponent = JTextComponentType.buildByType(name, dimension, (JTextComponentType) type);
    textComponents.put(name, (JTextComponent) textComponent);
  }

  @Override
  public JComponent getComponentFromIndexedContainer(String name) {
    return textComponentsIndexed.get(name).getJComponent();
  }

  @Override
  public JTextComponent getComponentFromContainer(String name) {
    return textComponents.get(name);
  }

  public void setToolTipMessage(String property, String nameTextfield) {
    textComponentsIndexed.get(nameTextfield).getJComponent()
      .setToolTipText(messageLibrary.getMessage(property));
  }

}