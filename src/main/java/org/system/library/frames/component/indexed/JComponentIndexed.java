package org.system.library.frames.component.indexed;

import lombok.Builder;
import org.system.library.frames.component.ComponentPosition;

import javax.swing.*;

@Builder
public class JComponentIndexed implements IJComponentIndexed {
  private final JComponent component;
  private final ComponentPosition position;

  @Override
  public JComponent getJComponent() {
    return component;
  }

  @Override
  public ComponentPosition getPanePosition() {
    return position;
  }
}