package org.system.library.frames.menubar;

import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Builder
public class MenuComponent {

  private final Set<MenuComponent> children = new LinkedHashSet<>();
  private final JComponent parentComponent;

  public MenuComponent addChildReturnParent(JComponent child) {
    var childToParent = MenuComponent.builder().parentComponent(child).build();
    children.add(childToParent);
    return this;
  }

  public MenuComponent addChildReturnChild(JComponent child) {
    var childToParent = MenuComponent.builder().parentComponent(child).build();
    children.add(childToParent);
    return childToParent;
  }

  public JComponent buildAtParent() {
    children.forEach(child -> {
      parentComponent.add(child.parentComponent);
      if (!child.children.isEmpty()) {
        child.buildAtParent();
      }
    });
    return parentComponent;
  }
}