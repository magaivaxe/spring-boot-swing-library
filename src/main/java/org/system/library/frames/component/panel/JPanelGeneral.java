package org.system.library.frames.component.panel;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.system.library.frames.component.indexed.IJComponentIndexed;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JPanelGeneral extends JPanel {

  @SafeVarargs
  public final void addComponentsByPanePosition(List<IJComponentIndexed>... listsJComponents) {
    var mergedOrderedList = Stream.of(listsJComponents)
      .flatMap(List::stream)
      .sorted(Comparator.comparing(IJComponentIndexed::getPanePosition))
      .collect(Collectors.toList());

    mergedOrderedList.forEach(component -> add(component.getJComponent()));
  }
}