package org.system.library.frames.component;

import org.system.library.frames.component.indexed.IJComponentIndexed;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public interface IJComponentContainer {

  Map<String, ? extends JComponent> getJComponentsNotIndexedFromIndexed();

  List<IJComponentIndexed> getJComponentsIndexed();

  void addToIndexedContainer(String property, Dimension dimension, ComponentPosition position, IJComponentType type);

  void addToContainer(String property, Dimension dimension, IJComponentType type);

  JComponent getComponentFromIndexedContainer(String key);

  JComponent getComponentFromContainer(String key);
}