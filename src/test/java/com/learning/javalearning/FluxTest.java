package com.learning.javalearning;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.util.concurrent.atomic.LongAdder;

/**
 * createdAt 2024/2/6
 **/
public class FluxTest {

    @Test
    public void testFluxFallback() {
        Flux flux = Flux.just(1, 2, 0)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(e -> System.out::println);
        flux.subscribe(System.out::println);
    }

    @Test
    public void testStaticFallback() {
        Flux flux = Flux.just(1, 2, 0)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn("Divided by zero :(");
        flux.subscribe(System.out::println);
    }

    @Test
    public void testOnError() {
        Flux flux = Flux.just(1, 2, 0)
                .map(i -> "100 / " + i + " = " + (100 / i));

        flux.subscribe(System.out::println,
                error -> System.err.println("Error: " + error));
    }

    @Test
    public void testUseDynamicFallback() {
        Flux flux = Flux.just(1, 2, 0)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(error -> Mono.just(
                        MyWrapper.fromError(error)));
        flux.subscribe(System.out::println,
                error -> System.err.println("Error: " + error));
    }

    public static class MyWrapper {
        public static String fromError(Throwable error) {
            return error.getMessage();
        }
    }

    @Test
    public void testReThrow() {
        Flux flux = Flux.just(1, 2, 0)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorResume(error -> Flux.error(
                        new RuntimeException("oops, ArithmeticException!", error)));

        Flux flux2 = Flux.just(1, 2, 0)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorMap(error -> new RuntimeException("oops, ArithmeticException!", error));

        flux.subscribe(System.out::println);

        flux2.subscribe(System.out::println,
                error -> System.err.println("Error: " + error));
    }

    @Test
    public void testDoOnError() {
        Flux flux = Flux.just(1, 2, 0)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .doOnError(error -> System.out.println("we got the error: " + error));
        flux.subscribe(System.out::println);
    }

    @Test
    public void testDoFinally() {
        long start = System.currentTimeMillis();
        LongAdder statsCancel = new LongAdder();

        Flux<String> flux =
                Flux.just("foo", "bar")
                        .doOnSubscribe(s -> System.out.println("请求开始: " + start))
                        .doFinally(type -> {
                            System.out.println("耗时:" + (System.currentTimeMillis() - start));
                            if (type == SignalType.CANCEL)
                                statsCancel.increment();
                        }).take(10);
        flux.subscribe(System.out::println);

    }

    @Test
    public void testRetry(){
        Flux.interval(Duration.ofMillis(250))
                .map(input -> {
                    if (input < 3){
                        return "tick " + input;
                    }
                    throw new RuntimeException("boom");
                })
                .retry(1)
                .elapsed()
                .subscribe(System.out::println, System.err::println);

        try {
            Thread.sleep(2100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubscription() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(10));
    }

    public static class SampleSubscriber<T> extends BaseSubscriber<T> {

        public void hookOnSubscribe(Subscription subscription) {
            System.out.println("Subscribed");
            request(1);
        }

        public void hookOnNext(T value) {
            System.out.println(value);
            request(1);
        }
    }

    @Test
    public void testBaseSubscription() {
        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> {System.out.println("Done");},
                s -> s.request(10));
        ints.subscribe(ss);
    }


}
