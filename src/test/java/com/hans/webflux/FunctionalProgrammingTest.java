package com.hans.webflux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

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

  @Test
  public void produceOneToNineStream() {
    IntStream.rangeClosed(1, 9).boxed()
        .map((data) -> data * 4)
        .filter((data) -> data % 4 == 0)
        .forEach(System.out::println); // 최종 연산이 있어야 실행이됨(collect,foreach, min, max)
  }

  @Test
  public void onenineListFlux() {
    Flux<Integer> intFlux = Flux.create(fluxSink -> {
      for (int i = 1; i <= 9; i++) {
        fluxSink.next(i);
      }
      fluxSink.complete();
    });

    // 위에 Fulx는 함수덩어리라서 subscribe로 구독을 해줘야 실행이됨
    intFlux.subscribe(data -> System.out.println("WebFlux가 구독중!! " + data));
    System.out.println("Netty 이벤트 루프로 스레드 복귀 !!");
  }

  @Test
  public void produceOneToNineFluxOperator() {
    Flux.fromIterable(IntStream.rangeClosed(1, 9).boxed().toList())
        .map((data) -> data * 4)  // operator 대부분이 stream과 유사하게 동작
        .filter((data) -> data % 4 == 0)
        .subscribe(System.out::println);
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
