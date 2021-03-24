package org.system.library.frames.menubar;

import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Builder
public class JMenuComponent {

  private final Set<JMenuComponent> childs = new LinkedHashSet<>();
  private final JComponent parentComponent;

  public JMenuComponent addChildReturnParent(JComponent child) {
    JMenuComponent childToParent = JMenuComponent.builder().parentComponent(child).build();
    childs.add(childToParent);
    return this;
  }

  public JMenuComponent addChildReturnChild(JComponent child) {
    JMenuComponent childToParent = JMenuComponent.builder().parentComponent(child).build();
    childs.add(childToParent);
    return childToParent;
  }

  public JComponent buildAtParent() {
    childs.forEach(child -> {
      parentComponent.add(child.parentComponent);
      if (!child.childs.isEmpty()) {
        child.buildAtParent();
      }
    });
    return parentComponent;
  }
}