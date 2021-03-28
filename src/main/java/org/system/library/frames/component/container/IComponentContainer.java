package org.system.library.frames.component.container;

import org.system.library.frames.component.ComponentIndexed;
import org.system.library.frames.component.IComponentIndexed;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.ComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public interface IComponentContainer<COMPONENT> {

  Map<String, ? extends JComponent> getAllNotIndexed();

  List<IComponentIndexed<? extends JComponent>> getAllAndClear();

  /**
   * Add a component to container defined by <b>{@code COMPONENT}</b> implements.
   * @param property from text message into <b>{@code messages.properties}</b> file.
   * @param dimension components dimension. {@link Dimension}.
   * @param position at parent component. {@link Position}.
   * @param type of component to build. Defined by {@link ComponentBuilder} implementations.
   * @return the <b>{@code COMPONENT}</b> implemented on container.
   */
  COMPONENT addToContainer(String property, Dimension dimension, Position position, ComponentBuilder<COMPONENT> type);

  COMPONENT getComponentFromContainer(String key);

  default IComponentIndexed<? extends JComponent> buildComponentIndexed(COMPONENT component, Position position) {
    return ComponentIndexed.builder()
                           .component((JComponent) component)
                           .position(position)
                           .build();
  }

  default void setToolTipsMessages(String message, JComponent textComponent) {
    textComponent.setToolTipText(message);
  }

  static void setLabelFor(Map<String, ? extends JComponent> mapLabels, Map<String, ? extends JComponent> mapComponents) {
    mapLabels.forEach((name, label) -> {
      if (mapComponents.containsKey(name)) {
        ((JLabel) label).setLabelFor(mapComponents.get(name));
      }
    });
  }
}