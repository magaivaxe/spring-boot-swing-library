package org.system.library.utils;

import org.system.library.frames.component.indexed.IJComponentIndexed;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.lang.management.ManagementFactory;

public interface IDebugUtils {

  default void setBorderIfRunDebug(IJComponentIndexed component) {
    if (isDebugMode()) {
      component.getComponent().setBorder(new LineBorder(Color.BLACK, 1));
    }
  }

  private boolean isDebugMode() {
    return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
  }
}