package com.rw.fp.monad;

import java.util.function.Function;


public class Identity<T> {

  private T value;

  public Identity(final T value) {
    this.value = value;
  }

  public T get() {
    return this.value;
  }

  public <U> Identity<U> map(Function<T, U> function) {
    return Identity.of(function.apply(this.value));
  }

  public static <E> Identity<E> of(final E value) {
    return new Identity(value);
  }
}