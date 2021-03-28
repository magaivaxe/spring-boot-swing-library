package org.system.library.frames.component.container;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.component.IComponentIndexed;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Container class used to contains a specific component type.
 *
 * @param <COMPONENT> type that this container contains. The <b>{@code <COMPONENT>}</b> value must be the same implemented by
 *                    {@link ComponentBuilder} enum implementation.
 *                    <p></p>
 *                    <p>Possible values as illustrated below. List not exhaustive.</p>
 *                    <ul>
 *                      <li>{@link AbstractButtonBuilder} -> {@code COMPONENT = }{@link AbstractButton}</li>
 *                      <li>{@link LabelBuilder} -> {@code COMPONENT = }{@link JLabel}</li>
 *                      <li>{@link PanelBuilder} -> {@code COMPONENT = }{@link JPanel}</li>
 *                      <li>{@link TextFieldBuilder} -> {@code COMPONENT = }{@link JComponent}</li>
 *                    </ul>
 * @see AbstractButtonBuilder
 * @see LabelBuilder
 * @see PanelBuilder
 * @see TextFieldBuilder
 * @see #addToContainer(String, Dimension, Position, ComponentBuilder)
 */
@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ComponentContainer<COMPONENT> implements IComponentContainer<COMPONENT> {

  private final MessageLibrary messageLibrary;
  private final Map<String, IComponentIndexed<? extends JComponent>> componentsIndexed = new HashMap<>();


  @Override
  public Map<String, ? extends JComponent> getAllNotIndexed() {
    return componentsIndexed
      .entrySet().stream()
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getComponent()));

  }

  @Override
  public List<IComponentIndexed<? extends JComponent>> getAllAndClear() {
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