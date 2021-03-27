package org.system.library.frames.component.builder;

import java.awt.*;

public interface ComponentBuilder<COMPONENT> {

  COMPONENT buildComponent(String name, Dimension dimension);

}