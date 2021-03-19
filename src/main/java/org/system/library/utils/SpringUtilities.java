package org.system.library.utils;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * A 1.4 file that provides utility methods for
 * creating form- or grid-style layouts with SpringLayout.
 * These utilities are used by several programs, such as
 * SpringBox and SpringCompactGrid.
 */
public class SpringUtilities {
  /**
   * A debugging utility that prints to stdout the component's
   * minimum, preferred, and maximum sizes.
   */
  public static void printSizes(Component c) {
    System.out.println("minimumSize = " + c.getMinimumSize());
    System.out.println("preferredSize = " + c.getPreferredSize());
    System.out.println("maximumSize = " + c.getMaximumSize());
  }

  /**
   * Aligns the first <code>rows</code> * <code>cols</code>
   * components of <code>parent</code> in
   * a grid. Each component is as big as the maximum
   * preferred width and height of the components.
   * The parent is made just big enough to fit them all.
   *
   * @param rows     number of rows
   * @param cols     number of columns
   * @param initialX x location to start the grid at
   * @param initialY y location to start the grid at
   * @param xPad     x padding between cells
   * @param yPad     y padding between cells
   */
  public static void makeGrid(Container parent,
                              int rows, int cols,
                              int initialX, int initialY,
                              int xPad, int yPad) {
    SpringLayout layout;
    try {
      layout = (SpringLayout) parent.getLayout();
    } catch (ClassCastException exc) {
      System.err.println("The first argument to makeGrid must use SpringLayout.");
      return;
    }

    Spring xPadSpring = Spring.constant(xPad);
    Spring yPadSpring = Spring.constant(yPad);
    Spring initialXSpring = Spring.constant(initialX);
    Spring initialYSpring = Spring.constant(initialY);
    int max = rows * cols;

    //Calculate Springs that are the max of the width/height so that all
    //cells have the same size.
    Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).
      getWidth();
    Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).
      getHeight();
    for (int i = 1; i < max; i++) {
      SpringLayout.Constraints cons = layout.getConstraints(
        parent.getComponent(i));

      maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
      maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
    }

    //Apply the new width/height Spring. This forces all the
    //components to have the same size.
    for (int i = 0; i < max; i++) {
      SpringLayout.Constraints cons = layout.getConstraints(
        parent.getComponent(i));

      cons.setWidth(maxWidthSpring);
      cons.setHeight(maxHeightSpring);
    }

    //Then adjust the x/y constraints of all the cells so that they
    //are aligned in a grid.
    SpringLayout.Constraints lastCons = null;
    SpringLayout.Constraints lastRowCons = null;
    for (int i = 0; i < max; i++) {
      SpringLayout.Constraints cons = layout.getConstraints(
        parent.getComponent(i));
      if (i % cols == 0) { //start of new row
        lastRowCons = lastCons;
        cons.setX(initialXSpring);
      } else { //x position depends on previous component
        cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),
          xPadSpring));
      }

      if (i / cols == 0) { //first row
        cons.setY(initialYSpring);
      } else { //y position depends on previous row
        cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH),
          yPadSpring));
      }
      lastCons = cons;
    }

    //Set the parent's size.
    SpringLayout.Constraints pCons = layout.getConstraints(parent);
    pCons.setConstraint(SpringLayout.SOUTH,
      Spring.sum(
        Spring.constant(yPad),
        lastCons.getConstraint(SpringLayout.SOUTH)));
    pCons.setConstraint(SpringLayout.EAST,
      Spring.sum(
        Spring.constant(xPad),
        lastCons.getConstraint(SpringLayout.EAST)));
  }

  /* Used by makeCompactGrid. */
  private static SpringLayout.Constraints getConstraintsForCell(
    int row, int col,
    Container parent,
    int cols) {
    SpringLayout layout = (SpringLayout) parent.getLayout();
    Component c = parent.getComponent(row * cols + col);
    return layout.getConstraints(c);
  }

  /**
   * Aligns the first <code>rows</code> * <code>cols</code>
   * components of <code>parent</code> in
   * a grid. Each component in a column is as wide as the maximum
   * preferred width of the components in that column;
   * height is similarly determined for each row.
   * The parent is made just big enough to fit them all.
   *
   * @param parent        container using SpringLayout
   * @param numberRows    number of rows
   * @param numberColumns number of columns
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

    //Align all cells in each column and make them the same width.
    var xOriginAtomic = new AtomicReference<>(Spring.constant(xOrigin));
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
      xOriginAtomic.set(Spring.sum(xOriginAtomic.get(), Spring.sum(widthAtomic.get(), Spring.constant(xPadding))));
    });

    //Align all cells in each row and make them the same height.
    var yOriginAtomic = new AtomicReference<>(Spring.constant(yOrigin));
    IntStream.range(0, numberRows).forEach(currentRow -> {
      var heightAtomic = new AtomicReference<>(Spring.constant(0));

      // Grid height calculus
      IntStream.range(0, numberColumns).forEach(currentColumn -> {
        heightAtomic.set(Spring.max(heightAtomic.get(),
          getConstraintsForCell(currentRow, currentColumn, parent, numberColumns).getHeight()));
      });

      // Compose and set columns constraints
      IntStream.range(0, numberColumns).forEach(currentColumn -> {
        var constraintColumn = getConstraintsForCell(currentRow, currentColumn, parent, numberColumns);
        constraintColumn.setY(yOriginAtomic.get());
        constraintColumn.setHeight(heightAtomic.get());
      });

      // Set new yOrigin to next cell
      yOriginAtomic.set(Spring.sum(yOriginAtomic.get(), Spring.sum(heightAtomic.get(), Spring.constant(yPadding))));
    });

    //Set the parent's size.
    SpringLayout.Constraints parentConstraint = layout.getConstraints(parent);
    parentConstraint.setConstraint(SpringLayout.SOUTH, yOriginAtomic.get());
    parentConstraint.setConstraint(SpringLayout.EAST, xOriginAtomic.get());
  }
}