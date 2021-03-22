package org.system.library.frames.menubar;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenusContainer implements IJMenusContainers {

  private final MessageLibrary messageLibrary;
  private final Map<String, JComponent> menus = new LinkedHashMap<>();

  @Override
  public JComponent addToContainer(String property, JMenuComponentType type) {
    var menu = JMenuComponentType.buildByType(messageLibrary.getMessage(property), type);
    menu.setName(property);
    menus.put(property, menu);
    return menu;
  }

  @Override
  public JComponent addChildToContainerElement(String parentProperty, String childProperty, JMenuComponentType childType) {
    var parent = menus.get(parentProperty);
    var child = JMenuComponentType.buildByType(messageLibrary.getMessage(childProperty), childType);
    child.setName(childProperty);
    parent.add(child);
    return child;
  }

  @Override
  public void addChildsToContainerElement(String property, Set<JComponent> components) {
    var menu = menus.get(property);
    components.forEach(menu::add);
  }

  @Override
  public JComponent getFromContainer(String property) {
    return menus.get(property);
  }

  @Override
  public Set<JComponent> getAllAndClearContainer() {
    var allMenus = new LinkedHashSet<>(menus.values());
    menus.clear();
    return allMenus;
  }

}