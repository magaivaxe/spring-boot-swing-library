package org.system.library.frames.component;

import lombok.Builder;

import javax.swing.*;

@Builder
public class JComponentIndexed implements IJComponentIndexed {
  private final JComponent component;
  private final Position position;

  @Override
  public JComponent getComponent() {
    return component;
  }

  @Override
  public Position getPosition() {
    return position;
  }
}