package org.system.library.frames.component.container;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.component.IComponentIndexed;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.ComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


//TODO: to Document container implementation with explication and possibilities
@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ComponentContainer<COMPONENT> implements IComponentContainer<COMPONENT> {

  private final MessageLibrary messageLibrary;
  private final Map<String, IComponentIndexed<? extends JComponent>> componentsIndexed = new HashMap<>();


  @Override
  public Map<String, ? extends JComponent> getJComponentsFromContainer() {
    return componentsIndexed
      .entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getComponent()));

  }

  @Override
  public List<IComponentIndexed<? extends JComponent>> getJComponentsIndexed() {
    var components = List.copyOf(componentsIndexed.values());
    componentsIndexed.clear();
    return components;

  }

  @Override
  public COMPONENT addToContainer(String property, Dimension dimension, Position position,
                                  ComponentBuilder<COMPONENT> type) {
    var text = messageLibrary.getMessage(property);
    var component = type.buildComponent(text, dimension);
    var componentIndexed = buildComponentIndexed(component, position);
    componentsIndexed.put(property, componentIndexed);
    return component;
  }

  @Override
  public COMPONENT getComponentFromContainer(String key) {
    return (COMPONENT) componentsIndexed.get(key).getComponent();
  }
}