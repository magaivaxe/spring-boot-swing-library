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
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Container class used to contains a specific component type.
 *
 * @param <COMPONENT> type that this container contains. The <b>{@code <COMPONENT>}</b> value must be the same
 *                    implemented by {@link ComponentBuilder} enum implementation.
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
  // Map<property, anyComponent>
  private final Map<String, IComponentIndexed<? extends JComponent>> components = new HashMap<>();
  // Map<parentName, <property, anyComponent>>
  private final Map<String, Map<String, IComponentIndexed<? extends JComponent>>> componentsByParent = new HashMap<>();


  @Override
  public Map<String, ? extends JComponent> getAllNotIndexed() {
    return components
      .entrySet().stream()
      .filter(checkComponentNameNotBlank())
      .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getComponent()));
  }

  /**
   * Exclusion of components without name, as void labels.
   */
  private Predicate<Map.Entry<String, IComponentIndexed<? extends JComponent>>> checkComponentNameNotBlank() {
    return entry -> !entry.getValue().getComponent().getName().isBlank();
  }

  @Override
  public List<IComponentIndexed<? extends JComponent>> getAllAndClear(String parentName) {
    var componentsIndexed = List.copyOf(this.components.values());
    componentsByParent.put(parentName, Map.copyOf(components));
    components.clear();
    return componentsIndexed;
  }

  @Override
  public COMPONENT addToContainer(String property, Dimension dimension, Position position,
                                  ComponentBuilder<COMPONENT> type) {
    var text = messageLibrary.getMessage(property);
    var component = type.buildComponent(text, dimension);
    var componentIndexed = buildComponentIndexed(component, position);
    components.put(property, componentIndexed);
    return component;
  }

  @Override
  public COMPONENT getComponentFromContainer(String key) {
    return (COMPONENT) components.get(key).getComponent();
  }

  @Override
  public COMPONENT getComponentFromParent(String parentName, String childProperty) {
    return (COMPONENT) componentsByParent.get(parentName).get(childProperty).getComponent();
  }

  @Override
  public Map<String, ? extends JComponent> getAllFromParent(String parentName) {
    var components = componentsByParent.get(parentName)
                                       .entrySet()
                                       .stream()
                                       .collect(Collectors.toMap(Map.Entry::getKey,
                                                                 entry -> entry.getValue().getComponent()));
    return Map.copyOf(components);
  }
}