package org.system.library.frames.utils;

import org.system.library.frames.component.IComponentIndexed;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.lang.management.ManagementFactory;

public interface IDebugUtils {

  default void setBorderIfRunDebug(IComponentIndexed<? extends JComponent> component) {
    if (isDebugMode()) {
      component.getComponent().setBorder(new LineBorder(Color.BLACK, 1));
    }
  }

  private boolean isDebugMode() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
  }
}