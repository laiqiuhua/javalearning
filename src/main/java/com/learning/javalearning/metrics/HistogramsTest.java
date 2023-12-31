package com.learning.javalearning.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HistogramsTest {

    public static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(1, TimeUnit.SECONDS);

        Histogram histogram = new Histogram(new ExponentiallyDecayingReservoir());
        registry.register(MetricRegistry.name(HistogramsTest.class, "request", "histogram"), histogram);

        while (true)
        {
            Thread.sleep(1000);
            histogram.update(random.nextInt(100000));
        }
    }
}