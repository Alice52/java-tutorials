package boot.webflux.server.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author zack <br>
 * @create 2021-04-11 11:47 <br>
 * @project springboot <br>
 */
@Slf4j
@RestController
public class SampleController {

  @GetMapping("/greeting")
  public Mono<String> greeting() {
    return Mono.just("hello webflux");
  }

  @GetMapping("/mono")
  public Mono<String> timeConsumer() {

    log.info("start");

    Mono<String> mono = Mono.fromCallable(() -> timeConsumerService());

    log.info("end");
    return mono;
  }

  @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> flux() {
    Flux<String> flux =
        Flux.fromStream(
            IntStream.range(1, 10)
                .mapToObj(
                    i -> {
                      try {
                        TimeUnit.SECONDS.sleep(1);
                      } catch (InterruptedException e) {
                      }
                      return "flux " + i;
                    }));

    return flux;
  }

  @SneakyThrows
  private String timeConsumerService() {

    log.info("start processor");
    TimeUnit.SECONDS.sleep(5);
    log.info("end processor");
    return "hello webflux";
  }
}
