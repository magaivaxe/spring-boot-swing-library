package org.system.library.frames;

import org.system.library.frames.component.indexed.IJComponentIndexed;
import org.system.library.frames.utils.IDebugUtils;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface IJFrame extends IDebugUtils {

  int DEFAULT_PADDING = 20;
  Component CENTER_POSITION = null;

  Dimension BUTTON_DIMENSION = new Dimension(50, 20);
  Dimension HEADER_DIMENSION = new Dimension(400, 2 * DEFAULT_PADDING);
  Dimension LINK_DIMENSION = new Dimension(0, 0);
  Dimension LOGIN_TEXT_FIELD_DIMENSION = new Dimension(200, 20);

  Color LINK_UNVISITED = new Color(0, 102, 204);
  Color LINK_VISITED = new Color(128, 0, 128);

  default void addComponentsByPosition(Container panel, List<List<IJComponentIndexed>> listsJComponents) {
    var mergedOrderedList = listsJComponents.stream()
                                            .flatMap(List::stream)
                                            .sorted(Comparator.comparing(IJComponentIndexed::getPosition))
                                            .collect(Collectors.toList());

    mergedOrderedList.forEach(component -> {
      setBorderIfRunDebug(component);
      panel.add(component.getComponent());
    });
  }

  default Dimension setDimensionBySizeComponents(Container parentContainer) {
    return DimensionByLayoutType.buildDimensionByLayoutType(parentContainer);
  }
}