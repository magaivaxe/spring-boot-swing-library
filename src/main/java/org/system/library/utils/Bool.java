package org.system.library.utils;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public final class Bool<O> {

  private final Boolean bool;
  private O object;

  private Bool(Boolean bool, O object) {
    this.bool = bool;
    this.object = object;
  }

  private Bool(Boolean bool) {
    this.bool = bool;
  }

  public static <O> Bool<O> of(Boolean bool, O object) {
    return new Bool(Objects.requireNonNullElse(bool, Boolean.FALSE), object);
  }

  public static Bool of(Boolean bool) {
    return new Bool(Objects.requireNonNullElse(bool, Boolean.FALSE));
  }

  public Bool<O> ifTrue(Consumer<? super O> action) {
    if (bool) {
      action.accept(object);
    }
    return this;
  }

  public void ifTrue(Runnable emptyAction) {
    if (bool) {
      emptyAction.run();
    }
  }

  public Bool<O> ifFalse(Consumer<? super O> action) {
    if (!bool) {
      action.accept(object);
    }
    return this;
  }

  public void ifFalse(Runnable emptyAction) {
    if (!bool) {
      emptyAction.run();
    }
  }

  public <U> Bool<U> map(Function<? super O, ? extends U> function) {
    Objects.requireNonNull(function);
    return Bool.of(bool, function.apply(object));
  }

  public O andReturn() {
    if (object == null) {
      throw new NoSuchElementException("No value present");
    }
    return object;
  }

  public Stream<O> stream() {
    return Stream.ofNullable(object);
  }
}