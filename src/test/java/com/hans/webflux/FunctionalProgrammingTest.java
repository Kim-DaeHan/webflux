package com.hans.webflux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FunctionalProgrammingTest {

  @Test
  public void onenineList() {
    List<Integer> sink = new ArrayList<>();
    for (int i = 1; i <= 9; i++) {
      sink.add(i);
    }

    sink = map(sink, (data) -> data * 4);

    sink = filter(sink, (data) -> data % 4 == 0);

    forEach(sink, (data) -> System.out.println(data));
  }

  private void forEach(List<Integer> sink, Consumer<Integer> consumer) {
    for (int i = 0; i < sink.size(); i++) {
      consumer.accept(sink.get(i));
    }
  }

  private List<Integer> filter(List<Integer> sink, Function<Integer, Boolean> predicate) {
    List<Integer> newSink2 = new ArrayList<>();
    for (int i = 0; i <= 8; i++) {
      if (predicate.apply(sink.get(i))) {
        newSink2.add(sink.get(i));
      }
    }
    sink = newSink2;
    return sink;
  }

  private List<Integer> map(List<Integer> sink, Function<Integer, Integer> mapper) {
    List<Integer> newSink1 = new ArrayList<>();
    for (int i = 0; i <= 8; i++) {
      newSink1.add(mapper.apply(sink.get(i)));
    }
    sink = newSink1;
    return sink;
  }
}
