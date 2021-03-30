package org.system.library.frames.component.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * provides utility methods for creating form- or grid-style layouts with SpringLayout.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringLayoutUtils {

  private static final int FIRST_CELL = 0;
  private static final int SECOND_CELL = 1;

  /**
   * Aligns the first {@code rows} * {@code cols} components of {@code parent} in a grid. Each component is as big as
   * the maximum preferred width and height of the components. The parent is made just big enough to fit them all.
   *
   * @param parent        container using SpringLayout
   * @param numberRows    number of grid rows
   * @param numberColumns number of grid columns
   * @param xOrigin       x location to start the grid at
   * @param yOrigin       y location to start the grid at
   * @param xPadding      x padding between cells
   * @param yPadding      y padding between cells
   */
  public static void makeGrid(Container parent,
                              int numberRows, int numberColumns,
                              int xOrigin, int yOrigin,
                              int xPadding, int yPadding) {

    if (!(parent.getLayout() instanceof SpringLayout)) {
      System.err.println("The parent container must use SpringLayout.");
      return;
    }

    var layout = (SpringLayout) parent.getLayout();
    var xPadSpring = Spring.constant(xPadding);
    var yPadSpring = Spring.constant(yPadding);
    var initialXSpring = Spring.constant(xOrigin);
    var initialYSpring = Spring.constant(yOrigin);
    var totalCells = numberRows * numberColumns;

    //Calculate the max of width and height.
    var maxWidthAtomic = new AtomicReference<>(layout.getConstraints(parent.getComponent(FIRST_CELL)).getWidth());
    var maxHeightAtomic = new AtomicReference<>(layout.getConstraints(parent.getComponent(FIRST_CELL)).getHeight());
    IntStream.range(SECOND_CELL, totalCells).forEach(cell -> {
      var constraint = layout.getConstraints(parent.getComponent(cell));

      maxWidthAtomic.set(Spring.max(maxWidthAtomic.get(), constraint.getWidth()));
      maxHeightAtomic.set(Spring.max(maxHeightAtomic.get(), constraint.getHeight()));
    });

    // apply the same width and height to all cells
    IntStream.range(FIRST_CELL, totalCells).forEach(cell -> {
      var constraint = layout.getConstraints(parent.getComponent(cell));

      constraint.setWidth(maxWidthAtomic.get());
      constraint.setHeight(maxHeightAtomic.get());
    });

    var lastCellConstraint = new AtomicReference<>(new SpringLayout.Constraints());
    var lastRowConstraint = new AtomicReference<>(new SpringLayout.Constraints());

    // Adjust x and y constraints on all cells
    IntStream.range(FIRST_CELL, totalCells).forEach(cell -> {
      var currentConstraint = layout.getConstraints(parent.getComponent(cell));

      if (isNewRow(cell, numberColumns)) {
        lastRowConstraint.set(lastCellConstraint.get());
        currentConstraint.setX(initialXSpring);
      } else { // x position depends on previous component
        currentConstraint.setX(Spring.sum(lastCellConstraint.get().getConstraint(SpringLayout.EAST), xPadSpring));
      }

      if (isFirstRow(cell, numberColumns)) {
        currentConstraint.setY(initialYSpring);
      } else { // y position depends on previous row
        currentConstraint.setY(Spring.sum(lastRowConstraint.get().getConstraint(SpringLayout.SOUTH), yPadSpring));
      }
      lastCellConstraint.set(currentConstraint);
    });

    // Set the parent's size by sum of padding + last point(east, south)
    var parentConstraint = layout.getConstraints(parent);
    parentConstraint.setConstraint(SpringLayout.SOUTH,
                                   Spring.sum(yPadSpring, lastCellConstraint.get().getConstraint(SpringLayout.SOUTH)));
    parentConstraint.setConstraint(SpringLayout.EAST,
                                   Spring.sum(xPadSpring, lastCellConstraint.get().getConstraint(SpringLayout.EAST)));
  }

  private static boolean isFirstRow(int cell, int numberColumns) {
    return cell / numberColumns == 0;
  }

  private static boolean isNewRow(int cell, int numberColumns) {
    return cell % numberColumns == 0;
  }

  private static SpringLayout.Constraints getConstraintsForCell(int currentRow, int currentColumn, Container parent,
                                                                int numberColumns) {
    var layout = (SpringLayout) parent.getLayout();
    Component component = parent.getComponent(getCurrentGridPosition(currentRow, currentColumn, numberColumns));
    return layout.getConstraints(component);
  }

  private static int getCurrentGridPosition(int currentRow, int currentColumn, int numberColumns) {
    return currentRow * numberColumns + currentColumn;
  }

  /**
   * Aligns the first {@code rows} * {@code cols} components of {@code parent} in a grid. Each component in a column is
   * as wide as the maximum preferred width of the components in that column. Height is similarly determined for each
   * row. The parent is made just big enough to fit them all.
   *
   * @param parent        container using SpringLayout
   * @param numberRows    number of grid rows
   * @param numberColumns number of grid columns
   * @param xOrigin       x location to start the grid at
   * @param yOrigin       y location to start the grid at
   * @param xPadding      x padding between cells
   * @param yPadding      y padding between cells
   */
  public static void makeCompactGrid(Container parent,
                                     int numberRows, int numberColumns,
                                     int xOrigin, int yOrigin,
                                     int xPadding, int yPadding) {

    if (!(parent.getLayout() instanceof SpringLayout)) {
      System.err.println("The parent container must use SpringLayout.");
      return;
    }

    var layout = (SpringLayout) parent.getLayout();

    var xOriginAtomic = new AtomicReference<>(Spring.constant(xOrigin));

    //Align all cells in each column and make them the same width.
    IntStream.range(0, numberColumns).forEach(currentColumn -> {
      var widthAtomic = new AtomicReference<>(Spring.constant(0));

      // Grid with calculus
      IntStream.range(0, numberRows).forEach(currentRow -> {
        widthAtomic.set(Spring.max(widthAtomic.get(),
                                   getConstraintsForCell(currentRow, currentColumn, parent, numberColumns).getWidth()));
      });

      // Compose and set rows constraints
      IntStream.range(0, numberRows).forEach(currentRow -> {
        var constraintRow = getConstraintsForCell(currentRow, currentColumn, parent, numberColumns);
        constraintRow.setX(xOriginAtomic.get());
        constraintRow.setWidth(widthAtomic.get());
      });

      // Set new xOrigin to next cell
      if (isLastColumn(currentColumn, numberColumns) && xOrigin == 0) {
        xOriginAtomic.set(Spring.sum(xOriginAtomic.get(), widthAtomic.get()));
      } else {
        xOriginAtomic.set(Spring.sum(xOriginAtomic.get(), Spring.sum(widthAtomic.get(), Spring.constant(xPadding))));
      }
    });

    var yOriginAtomic = new AtomicReference<>(Spring.constant(yOrigin));

    //Align all cells in each row and make them the same height.
    IntStream.range(0, numberRows).forEach(currentRow -> {
      var heightAtomic = new AtomicReference<>(Spring.constant(0));

      // Grid height calculus
      IntStream.range(0, numberColumns).forEach(currentColumn -> {
        heightAtomic.set(Spring.max(heightAtomic.get(),
                                    getConstraintsForCell(currentRow, currentColumn, parent, numberColumns)
                                      .getHeight()));
      });

      // Compose and set columns constraints
      IntStream.range(0, numberColumns).forEach(currentColumn -> {
        var constraintColumn = getConstraintsForCell(currentRow, currentColumn, parent, numberColumns);
        constraintColumn.setY(yOriginAtomic.get());
        constraintColumn.setHeight(heightAtomic.get());
      });

      // Set new yOrigin to next cell
      if (isLastRow(currentRow, numberRows) && yOrigin == 0) {
        yOriginAtomic.set(Spring.sum(yOriginAtomic.get(), heightAtomic.get()));
      } else {
        yOriginAtomic.set(Spring.sum(yOriginAtomic.get(), Spring.sum(heightAtomic.get(), Spring.constant(yPadding))));
      }
    });

    // Set the parent's size by sum of origins cells
    SpringLayout.Constraints parentConstraint = layout.getConstraints(parent);
    parentConstraint.setConstraint(SpringLayout.SOUTH, yOriginAtomic.get());
    parentConstraint.setConstraint(SpringLayout.EAST, xOriginAtomic.get());
    parent.setSize(xOriginAtomic.get().getValue(), yOriginAtomic.get().getValue());
  }

  private static boolean isLastColumn(int currentColumn, int numberColumns) {
    return currentColumn == numberColumns - 1;
  }

  private static boolean isLastRow(int currentRow, int numberRows) {
    return currentRow == numberRows - 1;
  }

}