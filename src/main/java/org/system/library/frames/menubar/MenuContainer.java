package org.system.library.frames.menubar;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.configuration.messages.MessageLibrary;
import org.system.library.frames.menubar.builder.MenuBuilder;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MenuContainer {

  private final MessageLibrary messageLibrary;
  private final Map<String, MenuComponent> menus = new LinkedHashMap<>();

  public MenuComponent addToContainer(String property, MenuBuilder type) {
    var menu = MenuBuilder.buildByType(messageLibrary.getMessage(property), type);
    menu.setName(property);
    var menuParent = MenuComponent.builder().parentComponent(menu).build();
    menus.put(property, menuParent);
    return menuParent;
  }

  public Set<JComponent> getAllParentsBuilded() {
    var menusBuilded = menus.values().stream()
                            .map(MenuComponent::buildAtParent)
                            .collect(Collectors.toCollection(LinkedHashSet::new));
    menus.clear();
    return menusBuilded;
  }

  public JComponent buildChild(String property, MenuBuilder type) {
    var child = MenuBuilder.buildByType(messageLibrary.getMessage(property), type);
    child.setName(property);
    return child;
  }

}