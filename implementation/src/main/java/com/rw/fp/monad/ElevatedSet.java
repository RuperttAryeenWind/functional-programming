package com.rw.fp.monad;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class ElevatedSet<T> {

  private Set<T> normalSet;

  public ElevatedSet(Set<T> data) {
    normalSet = data;
  }

  public final Set<T> get() {
    return normalSet;
  }

  public ElevatedSet<T> addAll(final Set<T> set) {
    normalSet.addAll(set);
    return this;
  }

  public final <U> ElevatedSet<U> map(final Function<T, U> mapper) {
    return ElevatedSet.of(normalSet.stream().map(mapper).collect(Collectors.toList()));
  }

  public <U> ElevatedSet<U> flatMap(final Function<T, ElevatedSet<U>> mapper) {
    ElevatedSet<U> zero = ElevatedSet.of();
    ElevatedSet<ElevatedSet<U>> higher = this.map(mapper);
    return higher.fold(zero, ElevatedSet::union);
  }

  public T fold(final T zero, final BiFunction<T, T, T> function) {
    T state = zero;

    for (T item : normalSet) {
      state = function.apply(item, state);
    }

    return state;
  }

  public ElevatedSet<T> union(final ElevatedSet<T> b) {
    return this.addAll(b.get());
  }

  public static <T> ElevatedSet<T> of(final T... data) {
    return ElevatedSet.of(Arrays.asList(data));
  }

  public static <T> ElevatedSet<T> of() {
    return new ElevatedSet<T>(Collections.EMPTY_SET);
  }

  private static <T> ElevatedSet<T> of(final List<T> data) {
    return new ElevatedSet<T>(new HashSet<T>(data));
  }
}