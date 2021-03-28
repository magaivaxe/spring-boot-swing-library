package org.system.library.frames;

import org.system.library.frames.component.IComponentIndexed;
import org.system.library.frames.utils.IDebugUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public interface IFrame extends IDebugUtils {

  int DEFAULT_PADDING = 20;
  Component CENTER_POSITION = null;

  Dimension BUTTON_DIMENSION = new Dimension(50, 20);
  Dimension HEADER_DIMENSION = new Dimension(400, 2 * DEFAULT_PADDING);
  Dimension LINK_DIMENSION = new Dimension(0, DEFAULT_PADDING);
  Dimension LOGIN_TEXT_FIELD_DIMENSION = new Dimension(200, DEFAULT_PADDING);

  Color LINK_UNVISITED = new Color(0, 102, 204);
  Color LINK_VISITED = new Color(128, 0, 128);

  /**
   * Add components at indexed order to a container (i.e. JPanel).
   * <p>If it runs on debug mode this method add borders to see and detect elements borders {@link
   * #setBorderIfRunDebug(IComponentIndexed)}.</p>
   *
   * @param container            to add elements in specific order defined by elements {@link
   *                             IComponentIndexed#getPosition()}.
   * @param listOfComponentsList list of components list to be merged, ordered and added to panel.
   */
  default void addComponentsByPosition(Container container,
                                       List<List<IComponentIndexed<? extends JComponent>>> listOfComponentsList) {
    var mergedOrderedList = listOfComponentsList.stream()
                                                .flatMap(List::stream)
                                                .sorted(Comparator.comparing(IComponentIndexed::getPosition))
                                                .collect(Collectors.toList());

    mergedOrderedList.forEach(component -> {
      setBorderIfRunDebug(component);
      container.add(component.getComponent());
    });
  }

  /**
   * Calculate a new dimension based on children {@link Dimension}. The calculus depends on parent {@link
   * LayoutManager}.
   *
   * @param parentContainer that contains elements with {@link Dimension}.
   * @return {@link Dimension} results by children components sum.
   *
   * @see DimensionByLayoutType
   */
  default Dimension setDimensionBySizeComponents(Container parentContainer) {
    return DimensionByLayoutType.buildDimensionByLayoutType(parentContainer);
  }
}