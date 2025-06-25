package com.hans.webflux.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reactive")
public class ReactiveProgramingExampleController {

  @GetMapping("/onenine/list")
  public List<Integer> onenineList() {
    List<Integer> sink = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(500);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      sink.add(i);
    }
    return sink;
  }

  // 리액티브 스트림 구현체 Flux, Mono를 사용하여 발생하는 데이터를 바로바로 리액티브하게 처리
  // 비동기로 동작 - 논 블로킹하게 동작 해야한다.
  @GetMapping("/onenine/flux")
  public Flux<Integer> onenineListFlux() {
    return Flux.create(fluxSink -> {
      for (int i = 0; i < 10; i++) {
        try {
          Thread.sleep(500);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
        fluxSink.next(i);
      }
      fluxSink.complete();
    });
  }
  // 리액티브 프로그래밍 필수 요소
  // 1. 데이터가 준비 될 때 마다 바로바로 리액티브하게 처리 > 리액티브 스트림 구현체 Flux, Mono를 사용하여 발생하는 데이터를 바로바로 처리
  // 2. 로직을 짤 때는 반드시 블로킹 하게 짜야함 > 이를 위해 비동기 프로그래밍 필요
}
