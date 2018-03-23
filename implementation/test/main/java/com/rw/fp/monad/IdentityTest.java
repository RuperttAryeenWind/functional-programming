package com.rw.fp.monad;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.testng.Assert;
import org.testng.annotations.Test;


public class IdentityTest {

  @Test
  public void ofTest() {
    Identity intId = Identity.of(1);
    Assert.assertEquals(intId.get(), 1);

    Identity nullId = Identity.of(null);
    Assert.assertEquals(nullId.get(), null);
  }

  @Test
  public void mapTest() {
    Identity<Float> floatId = Identity.of(new Float(1));
    Function<Float, Integer> floatIntegerFunction = (Float p) -> {
      return p.intValue();
    };
    Identity<Integer> intId = floatId.map(floatIntegerFunction);

    Assert.assertTrue(floatId.get().equals(1.0f));
    Assert.assertTrue(intId.get().equals(1));
    Assert.assertTrue(Identity.of("Hello World").map(String::length).get().equals(11));
  }

  @Test
  public void mapTest2() {
    String nullString = null;

    try {
      Identity.of(nullString).map(String::length).get();
    } catch (NullPointerException e) {
      Assert.assertTrue(true);
      return;
    }

    Assert.assertTrue(false);
  }

  @Test
  public void compareTest() {
    Identity<String> stringId1 = Identity.of("Hello World");
    Identity<String> stringId2 = Identity.of("Hi World");

//    BiFunction<Integer,String> biConsumer = (key,value) ->
//        System.out.println("Key:"+ key+" Value:"+ value);

    String str = "Hi World";

    Assert.assertFalse(stringId1.map(str::equals).get());
    Assert.assertFalse(stringId1.map(p -> stringId2.equals(p)).get());
  }


  @Test
  public void biFunctionCompare() {
    BiFunction<String, String, Boolean> comparator = (str1, str2) -> str1.equals(str2);

    Identity<String> stringId1 = Identity.of("Hello World");
    Identity<String> stringId2 = Identity.of("Hi World");

    Assert.assertFalse(comparator.apply(stringId1.get(), stringId2.get()));
    Assert.assertFalse(stringId1.map(x -> comparator.apply(x, "Hi World")).get());
    Assert.assertFalse(stringId1.map(x -> comparator.apply(x, stringId2.get())).get());
  }
}
