package com.example.sampleWebfluxapp;

import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.HashMap;

@Controller
public class SampleController {

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return route(GET("/f/hello"), this::hello)
        .andRoute(GET("/f/hello2"), this::hello2)
        .andRoute(GET("/f/flux2"), this::flux2);
  }

  public Mono<ServerResponse> hello(ServerRequest req) {
    return ok().body(Mono.just("Hello Functional routing world!"), String.class);
  }

  public Mono<ServerResponse> hello2(ServerRequest req) {
    return ok().body(Mono.just("関数ルーティングの世界へようこそ!"), String.class);
  }

  @RequestMapping("/f/flux")
  Mono<Rendering> flux() {
    return Mono.just(Rendering.view("flux").modelAttribute("title", "Flux/Request Handler")
        .modelAttribute("msg", "これはリクエストハンドラのサンプルです。")
        .build());
  }

  Mono<ServerResponse> flux2(ServerRequest req) {
    Map map = new HashMap<>();
    map.put("title", "Flux/Function Routing");
    map.put("msg", "これは関数ルーティングのサンプルです。");
    return ok().contentType(MediaType.TEXT_HTML).render("flux", map);
  }
}
