package com.rw.fp.function;

import java.util.Set;
import java.util.function.BiFunction;


public class SetUtils {

  private static final BiFunction<Integer, Integer, Integer> MAX = Math::max;
  private static final BiFunction<Integer, Integer, Integer> SUM = (a, b) -> a + b;
  private static final BiFunction<Integer, Integer, Integer> OF = (a, b) -> a * b;

  public static Integer sum(final Set<Integer> data) {
    return fold(0, data, SUM);
  }

  public static Integer product(final Set<Integer> data) {
    return fold(1, data, OF);
  }

  public static Integer max(final Set<Integer> data) {
    return fold(Integer.MIN_VALUE, data, MAX);
  }

  public static Integer fold(final Integer zero, final Set<Integer> data,
      final BiFunction<Integer, Integer, Integer> function) {
    Integer state = zero;

    for (Integer item : data) {
      state = function.apply(item, state);
    }
    return state;
  }
}

