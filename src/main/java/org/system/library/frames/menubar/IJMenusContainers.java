package org.system.library.frames.menubar;

import javax.swing.*;
import java.util.Set;

public interface IJMenusContainers {

  JComponent addToContainer(String property, JMenuComponentType type);

  JComponent addChildToContainerElement(String parentProperty, String childProperty, JMenuComponentType childType);

  void addChildsToContainerElement(String property, Set<JComponent> menuItems);

  JComponent getFromContainer(String property);

  Set<JComponent> getAllAndClearContainer();

}