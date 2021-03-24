package org.system.library.frames.menubar;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JMenusContainer {

  private final MessageLibrary messageLibrary;
  private final Map<String, JMenuComponent> menus = new LinkedHashMap<>();

  public JMenuComponent addToContainer(String property, JMenuComponentType type) {
    var menu = JMenuComponentType.buildByType(messageLibrary.getMessage(property), type);
    menu.setName(property);
    var menuParent = JMenuComponent.builder().parentComponent(menu).build();
    menus.put(property, menuParent);
    return menuParent;
  }

  public Set<JComponent> getAllParentsBuilded() {
    var menusBuilded = menus.values().stream()
      .map(JMenuComponent::buildAtParent).collect(Collectors.toUnmodifiableSet());
    menus.clear();
    return menusBuilded;
  }

  public JComponent buildChild(String property, JMenuComponentType type) {
    var child = JMenuComponentType.buildByType(messageLibrary.getMessage(property), type);
    child.setName(property);
    return child;
  }

}