package org.system.library.frames.component.container;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.component.IJComponentIndexed;
import org.system.library.frames.component.JComponentIndexed;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.IJComponentType;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JComponentContainer<COMPONENT> implements IJComponentContainer<COMPONENT> {

  private final MessageLibrary messageLibrary;
  private final Map<String, IJComponentIndexed<? extends JComponent>> componentsIndexed = new HashMap<>();


  @Override
  public Map<String, ? extends JComponent> getJComponentsFromContainer() {
    return componentsIndexed
      .entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getComponent()));

  }

  @Override
  public List<IJComponentIndexed<? extends JComponent>> getJComponentsIndexed() {
    var components = List.copyOf(componentsIndexed.values());
    componentsIndexed.clear();
    return components;

  }

  @Override
  public COMPONENT addToContainer(String property, Dimension dimension, Position position,
                                  IJComponentType<COMPONENT> type) {
    var text = messageLibrary.getMessage(property);
    var component = type.buildComponent(text, dimension);
    var buttonComponentIndexed = JComponentIndexed.builder()
                                                  .component((JComponent) component)
                                                  .position(position)
                                                  .build();
    componentsIndexed.put(property, buttonComponentIndexed);
    return component;
  }

  @Override
  public COMPONENT getComponentFromContainer(String key) {
    return (COMPONENT) componentsIndexed.get(key).getComponent();
  }
}