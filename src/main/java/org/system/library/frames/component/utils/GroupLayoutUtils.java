package org.system.library.frames.component.utils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupLayoutUtils {

  public static void makeGroup(Container parent, ParallelIndex... parallelArray) {

    if (!(parent.getLayout() instanceof GroupLayout)) {
      log.error("The parent layout must be instance of {}.", GroupLayout.class);
      return;
    }

    var children = List.of(parent.getComponents());
    var removedChildren = new ArrayList<Component>();
    var parallels = List.of(parallelArray);
    var layout = (GroupLayout) parent.getLayout();

    var sequentialGroupHorizontal = layout.createSequentialGroup();
    var currentIndex = new AtomicInteger(0);
    var numberColumns = new AtomicInteger(0);
    var numberRows = new AtomicInteger(0);

    children.forEach(component -> {

      parallels.forEach(parallel -> {
        if (parallel.start == currentIndex.get()) {
          var parallelGroup = layout.createParallelGroup(parallel.alignment);

          IntStream.range(parallel.start, parallel.end).forEach(parallelIndex -> {
            var componentParallel = children.get(parallelIndex);
            parallelGroup.addComponent(componentParallel);
            removedChildren.add(componentParallel);
          });
          sequentialGroupHorizontal.addGroup(parallelGroup);
          numberColumns.incrementAndGet();
        }
        if (!removedChildren.contains(component)) {
          sequentialGroupHorizontal.addComponent(component);
          numberColumns.incrementAndGet();
        }
        numberRows.set(Math.max(numberRows.get(), Math.subtractExact(parallel.end - 1, parallel.start)));
      });
      currentIndex.incrementAndGet();
    });

    layout.setHorizontalGroup(sequentialGroupHorizontal);
  }

  public static ParallelIndex getParallel(int start, int end) {
    return ParallelIndex.builder().start(start).end(end).build();
  }

  /**
   * {@link ParallelIndex#start} -> component index (inclusive) witch begins a parallel group.
   * <p></p>
   * {@link ParallelIndex#end} -> component index (exclusive) witch ends parallel sequence starts by {@link
   * ParallelIndex#start}.
   */
  @Builder
  @RequiredArgsConstructor
  private static class ParallelIndex {
    private final int start;
    private final int end;
    private final Alignment alignment;
  }
}