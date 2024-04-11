package com.learning.javalearning;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * createdAt 2024/2/6
 **/
@Slf4j
public class ReactorTest {

    @Test
    public void testSyncFlux() {
        Flux.just(1,2,3,4,5)
                .log()
                .map(i->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return i*10;
                })
                .subscribe(c->log.info("getInt:{}",c));
    }


    @Test
    public void testAsyncFlux() {
        //异步执行乘法
        Flux.just(1,2,3,4,5)
                .log()
                .flatMap(i-> Flux.just(i*10).delayElements(Duration.ofSeconds(1)))
                .subscribe(c ->log.info("getInt:{}",c));
    }


    public void testMonoDefer() {

    }

}
