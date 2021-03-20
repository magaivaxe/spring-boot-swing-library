package org.system.library.frames;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public enum DimensionByLayoutType {
  BOX_LAYOUT(BoxLayout.class) {
    @Override
    public Dimension dimensionByType(Container parentContainer) {
      var childComponents = List.of(parentContainer.getComponents());
      var width = new AtomicInteger(0);
      var height = new AtomicInteger(IJFrame.DEFAULT_PADDING);

      childComponents.forEach(child -> {
        width.set(Math.max(width.get(), child.getWidth()));
        height.set(Math.addExact(height.get(), child.getHeight() + IJFrame.DEFAULT_PADDING));
      });
      width.set(width.addAndGet(2 * IJFrame.DEFAULT_PADDING));
      return new Dimension(width.get(), height.get());
    }
  };

  private final Class<?> typeLayout;

  public abstract Dimension dimensionByType(Container parentContainer);

  public static Dimension buildDimensionByLayoutType(Container parentContainer) {
    var dimensionLayoutType = Arrays.stream(DimensionByLayoutType.values())
      .filter(layoutType -> layoutType.typeLayout == parentContainer.getLayout().getClass())
      .findFirst().orElseThrow();
    return dimensionLayoutType.dimensionByType(parentContainer);
  }
}