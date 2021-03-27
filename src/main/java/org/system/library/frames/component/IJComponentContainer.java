package org.system.library.frames.component;

import org.system.library.frames.component.indexed.IJComponentIndexed;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public interface IJComponentContainer {

  Map<String, ? extends JComponent> getJComponentsFromContainer();

  List<IJComponentIndexed> getJComponentsIndexed();

  JComponent addToContainer(String property, Dimension dimension, Position position, IJComponentType type);

  JComponent getComponentFromContainer(String key);
}