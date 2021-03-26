package org.system.library.frames.component.panel.label;

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
public class JLabelContainer implements IJComponentContainer {

  private final MessageLibrary messageLibrary;
  private final Map<String, JComponentIndexed> labelsIndexed = new HashMap<>();
  private final Map<String, JLabel> labels = new HashMap<>();

  @Override
  public List<IJComponentIndexed> getJComponentsIndexed() {
    return List.copyOf(labelsIndexed.values());
  }

  @Override
  public Map<String, JLabel> getJComponentsNotIndexedFromIndexed() {
    return labelsIndexed.entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> (JLabel) entry.getValue().getComponent()));
  }

  @Override
  public void addToIndexedContainer(String property, Dimension dimension, Position position, IJComponentType type) {
    var text = messageLibrary.getMessage(property);
    var label = JLabelComponentType.buildByType(text, (JLabelComponentType) type);
    var labelIndexed = JComponentIndexed.builder().component(label).position(position).build();
    labelsIndexed.put(property, labelIndexed);
  }

  @Override
  public void addToContainer(String property, Dimension dimension, IJComponentType type) {
    var text = messageLibrary.getMessage(property);
    var label = JLabelComponentType.buildByType(text, (JLabelComponentType) type);
    labels.put(property, label);
  }

  @Override
  public JLabel getComponentFromIndexedContainer(String property) {
    return (JLabel) labelsIndexed.get(property).getComponent();
  }

  @Override
  public JLabel getComponentFromContainer(String property) {
    return labels.get(property);
  }

  public void setLabelFor(Map<String, ? extends JComponent> mapComponents) {
    labelsIndexed.forEach((name, labelIdexed) -> {
      if (mapComponents.containsKey(name)) {
        var label = (JLabel) labelIdexed.getComponent();
        label.setLabelFor(mapComponents.get(name));
      }
    });
  }

}