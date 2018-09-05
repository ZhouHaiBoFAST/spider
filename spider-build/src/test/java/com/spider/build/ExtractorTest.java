package com.spider.build;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author liuzhongkai
 */
@State(Scope.Benchmark)
public class ExtractorTest {

    static {
        SpiderExtractor.getExtractor().set(B.class, C.class);
    }

    //
    @Test
    public void concurrent() throws InterruptedException {
        int size = 10000;
        ExecutorService executors = Executors.newFixedThreadPool(size);
        CountDownLatch downLatch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            executors.execute(() -> {
                try {
                    downLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SpiderExtractor.getInstance(B.class);
            });
            downLatch.countDown();
        }
        executors.shutdown();
        for (; !executors.awaitTermination(1, TimeUnit.SECONDS); ) ;
    }

    @Benchmark
    public void test1() {
        SpiderExtractor.getInstance(B.class);
    }


    private List<String> stringList = new ArrayList<>();

    {
        for (int i = 0; i < 99999; i++) {
            stringList.add(String.valueOf(i));
        }
    }

    @Benchmark
    public void test2() {
        stringList.toArray(new String [stringList.size()]);

    }

//    @Benchmark
    @Test
    public void test3() {
        stringList.toArray(new String[0]);

    }

    public static interface A {
    }

    public static class B {
    }


    public static class C extends B implements A {
        public C() {
            System.out.println(this);
        }
    }
}