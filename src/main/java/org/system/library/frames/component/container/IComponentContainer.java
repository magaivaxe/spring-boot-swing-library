package org.system.library.frames.component.container;

import org.system.library.frames.component.IComponentIndexed;
import org.system.library.frames.component.ComponentIndexed;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.ComponentBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public interface IComponentContainer<COMPONENT> {

  Map<String, ? extends JComponent> getJComponentsFromContainer();

  List<IComponentIndexed<? extends JComponent>> getJComponentsIndexed();

  COMPONENT addToContainer(String property, Dimension dimension, Position position, ComponentBuilder<COMPONENT> type);

  COMPONENT getComponentFromContainer(String key);

  default IComponentIndexed<? extends JComponent> buildComponentIndexed(COMPONENT component, Position position) {
    return ComponentIndexed.builder()
                           .component((JComponent) component)
                           .position(position)
                           .build();
  }

//TODO: see this and check correction

//  public void setToolTipMessage(String property, String nameTextfield) {
//    textComponentsIndexed.get(nameTextfield).getComponent()
//                         .setToolTipText(messageLibrary.getMessage(property));
//  }

//  public void setLabelFor(Map<String, ? extends JComponent> mapComponents) {
//    labelsIndexed.forEach((name, labelIdexed) -> {
//      if (mapComponents.containsKey(name)) {
//        var label = (JLabel) labelIdexed.getComponent();
//        label.setLabelFor(mapComponents.get(name));
//      }
//    });
//  }
}