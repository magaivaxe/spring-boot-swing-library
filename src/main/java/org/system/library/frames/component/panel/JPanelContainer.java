package org.system.library.frames.component.panel;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JPanelContainer implements IJComponentContainer {

  private final Map<String, JComponentIndexed> panelsIndexed = new HashMap<>();

  @Override
  public Map<String, JPanel> getJComponentsFromContainer() {
    return panelsIndexed.entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> (JPanel) entry.getValue().getComponent()));
  }

  @Override
  public List<IJComponentIndexed> getJComponentsIndexed() {
    return List.copyOf(panelsIndexed.values());
  }

  @Override
  public JPanel addToContainer(String name, Dimension dimension, Position position, IJComponentType type) {
    var panel = JPanelComponentType.buildByType(name, (JPanelComponentType) type, dimension);
    var panelIndexed = JComponentIndexed.builder().component(panel).position(position).build();
    panelsIndexed.put(name, panelIndexed);
    return (JPanel) panelIndexed.getComponent();
  }

  @Override
  public JPanel getComponentFromContainer(String name) {
    return (JPanel) panelsIndexed.get(name).getComponent();
  }
}