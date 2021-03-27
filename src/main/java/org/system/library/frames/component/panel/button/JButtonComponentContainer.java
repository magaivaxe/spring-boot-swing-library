package org.system.library.frames.component.panel.button;

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
public class JButtonComponentContainer implements IJComponentContainer {

  private final MessageLibrary messageLibrary;
  private final Map<String, IJComponentIndexed> buttonsIndexed = new HashMap<>();

  @Override
  public Map<String, JComponent> getJComponentsFromContainer() {
    return buttonsIndexed.entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getComponent()));
  }

  @Override
  public List<IJComponentIndexed> getJComponentsIndexed() {
    var componentsIndexed = List.copyOf(buttonsIndexed.values());
    buttonsIndexed.clear();
    return componentsIndexed;
  }

  @Override
  public AbstractButton addToContainer(String property, Dimension dimension, Position position, IJComponentType type) {
    var text = messageLibrary.getMessage(property);
    var buttonComponent = JButtonComponentType.buildByType(text, dimension, (JButtonComponentType) type);
    var buttonComponentIndexed = JComponentIndexed.builder()
      .component(buttonComponent)
      .position(position).build();
    buttonsIndexed.put(property, buttonComponentIndexed);
    return buttonComponent;
  }

  @Override
  public AbstractButton getComponentFromContainer(String property) {
    return (AbstractButton) buttonsIndexed.get(property).getComponent();
  }
}