package org.system.library.frames.menubar;

import javax.swing.*;
import java.util.Set;

public interface IJMenusContainers {

  JComponent addToContainer(String property, JMenuComponentType type);

  void addChildToContainerElement(String property, JComponent menuItem);

  void addChildsToContainerElement(String property, Set<JComponent> menuItems);

  JComponent getFromContainer(String property);

  Set<JComponent> getAllAndClearContainer();

}