package org.system.library.frames.menubar;

import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Builder
public class JMenuParentComponent {

  private final Set<JMenuParentComponent> childs = new LinkedHashSet<>();
  private final JComponent parentComponent;

  public JMenuParentComponent addChildReturnParent(JComponent child) {
    JMenuParentComponent childToParent = JMenuParentComponent.builder().parentComponent(child).build();
    childs.add(childToParent);
    return this;
  }

  public JMenuParentComponent addChildReturnChild(JComponent child) {
    JMenuParentComponent childToParent = JMenuParentComponent.builder().parentComponent(child).build();
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