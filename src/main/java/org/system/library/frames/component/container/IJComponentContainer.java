package org.system.library.frames.component.container;

import org.system.library.frames.component.IJComponentIndexed;
import org.system.library.frames.component.Position;
import org.system.library.frames.component.builder.IJComponentType;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public interface IJComponentContainer<COMPONENT> {

  Map<String, ? extends JComponent> getJComponentsFromContainer();

  List<IJComponentIndexed<? extends JComponent>> getJComponentsIndexed();

  COMPONENT addToContainer(String property, Dimension dimension, Position position, IJComponentType<COMPONENT> type);

  COMPONENT getComponentFromContainer(String key);

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