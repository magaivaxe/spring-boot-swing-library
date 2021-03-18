package org.system.library.frames.component.panel;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.frames.component.ComponentPosition;
import org.system.library.frames.component.IJComponentContainer;
import org.system.library.frames.component.IJComponentType;
import org.system.library.frames.component.indexed.IJComponentIndexed;
import org.system.library.frames.component.indexed.JComponentIndexed;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JPanelGeneralContainer implements IJComponentContainer {

  private final Map<String, JComponentIndexed> panelsIndexed = new HashMap<>();
  private final Map<String, JPanelGeneral> panels = new HashMap<>();

  @Override
  public Map<String, JPanelGeneral> getJComponentsNotIndexedFromIndexed() {
    return panelsIndexed.entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> (JPanelGeneral) entry.getValue().getJComponent()));
  }

  @Override
  public List<IJComponentIndexed> getJComponentsIndexed() {
    return List.copyOf(panelsIndexed.values());
  }

  @Override
  public void addToIndexedContainer(String name, Dimension dimension, ComponentPosition position, IJComponentType type) {
    var panel = JPanelComponentType.buildByType(name, (JPanelComponentType) type);
    var panelIndexed = JComponentIndexed.builder().component(panel).position(position).build();
    panelsIndexed.put(name, panelIndexed);
  }

  @Override
  public void addToContainer(String name, Dimension dimension, IJComponentType type) {
    var panel = JPanelComponentType.buildByType(name, (JPanelComponentType) type);
    panels.put(name, panel);
  }

  @Override
  public JPanelGeneral getComponentFromIndexedContainer(String name) {
    return (JPanelGeneral) panelsIndexed.get(name).getJComponent();
  }

  @Override
  public JPanelGeneral getComponentFromContainer(String name) {
    return panels.get(name);
  }
}