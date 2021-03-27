package org.system.library.frames.component.panel.text;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.component.IJComponentContainer;
import org.system.library.frames.component.IJComponentType;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.indexed.IJComponentIndexed;
import org.system.library.frames.component.indexed.JComponentIndexed;

import javax.swing.*;
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
  private final Map<String, IJComponentIndexed> textComponentsIndexed = new HashMap<>();

  @Override
  public Map<String, JComponent> getJComponentsFromContainer() {
    return textComponentsIndexed.entrySet()
                                .stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getComponent()));
  }

  @Override
  public List<IJComponentIndexed> getJComponentsIndexed() {
    var textComponents = List.copyOf(textComponentsIndexed.values());
    textComponentsIndexed.clear();
    return textComponents;
  }

  @Override
  public JComponent addToContainer(String name, Dimension dimension, Position position, IJComponentType type) {
    var textComponent = JTextComponentType.buildByType(name, dimension, (JTextComponentType) type);
    var textFieldIndexed = JComponentIndexed.builder()
                                            .component(textComponent)
                                            .position(position).build();
    textComponentsIndexed.put(name, textFieldIndexed);
    return textComponent;
  }

  @Override
  public JComponent getComponentFromContainer(String name) {
    return textComponentsIndexed.get(name).getComponent();
  }

  public void setToolTipMessage(String property, String nameTextfield) {
    textComponentsIndexed.get(nameTextfield).getComponent()
                         .setToolTipText(messageLibrary.getMessage(property));
  }

}