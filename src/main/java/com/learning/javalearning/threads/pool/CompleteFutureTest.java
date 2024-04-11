package com.learning.javalearning.threads.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * createdAt 2024/2/28
 **/
@Slf4j
public class CompleteFutureTest {


    public static ThreadPoolTaskExecutor outerDataTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(256);
        executor.setQueueCapacity(64);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("outer-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    public static  List<Integer> getFeatureValue(int i) {
//        if (i == 2) {
//            throw new IllegalArgumentException("触发了异常机制....");
//        }
        return Arrays.asList(1,2,3);
    }

    public static void main(String[] args) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = outerDataTaskExecutor();
        CompletionService<List<Integer>> completionService = new ExecutorCompletionService<>(threadPoolTaskExecutor);
        int cnt = 0;
        AtomicInteger max = new AtomicInteger(0);

        List<Integer> valueDtos = new ArrayList<>();
        for (int i =0; i<3; i++) {
            int finalI = i;
            completionService.submit(() -> getFeatureValue(finalI));
            cnt ++;
            int flag = max.addAndGet(1);

            if (flag % 3 == 0 || cnt == 3) {
                for (int j=0; j<flag; j ++) {
                    try {
                        List<Integer> featureValueDtos = completionService.take().get();
                        if (!CollectionUtils.isEmpty(featureValueDtos)) {
                            valueDtos.addAll(featureValueDtos);
                            System.out.println(Arrays.toString(valueDtos.toArray()));
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
                max.set(0);
            }
        }
        threadPoolTaskExecutor.shutdown();
    }
}
